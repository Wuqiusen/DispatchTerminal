package com.zxw.dispatch_driver.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.Journey;
import com.zxw.data.bean.Receive;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.JourneyAdapter;
import com.zxw.dispatch_driver.adapter.ReceiveAdapter;
import com.zxw.dispatch_driver.jpush.ExampleUtil;
import com.zxw.dispatch_driver.presenter.view.MainView;
import com.zxw.dispatch_driver.utils.DebugLog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscriber;

import static com.zxw.dispatch_driver.Constants.SUCCESS;
import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2016/12/7 17:23
 * email：cangjie2016@gmail.com
 */
public class MainPresenter extends BasePresenter<MainView> {
    private final Activity mActivity;
    private int mCurrentReceivePageNo, mCurrentJourneyPageNo, mReceivePageSize, mJourneyPageSize;
    private boolean isReceiveLoading, isJourneyLoading;
    private DispatchSource mSource = new DispatchSource();

    private List<Receive> mReceiveData = new ArrayList<>();
    private ReceiveAdapter mReceiveAdapter;
    private List<Journey> mJourneyData = new ArrayList<>();
    private JourneyAdapter mJourneyAdapter;

    private final Handler mHandler ;
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private final static int RECEIVE_DEFAULT_SIZE = 20, JOURNEY_DEFAULT_SIZE = 20;
    private MessageReceiver mMessageReceiver;

    public MainPresenter(MainView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
        this.mHandler =  new Handler(activity.getMainLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_SET_ALIAS:
                        DebugLog.e("Set alias in handler.");
                        JPushInterface.setAliasAndTags(MyApplication.mContext, (String) msg.obj, null, mAliasCallback);
                        break;

                    case MSG_SET_TAGS:
                        DebugLog.e("Set tags in handler.");
                        JPushInterface.setAliasAndTags(MyApplication.mContext, null, (Set<String>) msg.obj, mTagsCallback);
                        break;

                    default:
                        DebugLog.e("Unhandled msg - " + msg.what);
                }
            }
        };
    }

    public void reloadReceiveList() {
        mCurrentReceivePageNo = 1;
        realLoadReceivePage();
    }

    public void realLoadReceivePage() {
        if(isReceiveLoading)
            return;
        isJourneyLoading= true;
        mvpView.showLoadingReceivePage();
        mSource.receiveList(userId(), keyCode(), mCurrentReceivePageNo, RECEIVE_DEFAULT_SIZE, new Subscriber<BaseBean<List<Receive>>>() {
            @Override
            public void onCompleted() {
                isJourneyLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                mvpView.receivePageLoadFailed();
                isJourneyLoading = false;
            }

            @Override
            public void onNext(BaseBean<List<Receive>> listBaseBean) {
                invalidKeyCode(listBaseBean.returnCode);
                mReceivePageSize = listBaseBean.returnSize;
                if (mCurrentReceivePageNo == 1){
                    mReceiveData.clear();
                    if (listBaseBean.returnData == null){
                        mvpView.emptyReceiveData();
                        return;
                    }
                }
                mReceiveData.addAll(listBaseBean.returnData);
                if (mReceiveData.size() == 0) {
                    mvpView.emptyReceiveData();
                    return;
                }
                if (mReceiveAdapter == null) {
                    mReceiveAdapter = new ReceiveAdapter(mActivity, R.layout.item_receive, mReceiveData, MainPresenter.this);
                    mvpView.receivePageSetAdapter(mReceiveAdapter);
                } else {
                    mReceiveAdapter.setItems(mReceiveData);
                    mvpView.receivePageLoadComplete();
                }
            }

        });
    }

    public void reloadJourneyList() {
        mCurrentJourneyPageNo = 1;
        realLoadJourneyPage();
    }

    public void loadReceivePage() {
        if (mCurrentReceivePageNo * RECEIVE_DEFAULT_SIZE >= mReceivePageSize) {
            mvpView.noMoreReceiveData();
        } else {
            mCurrentReceivePageNo++;
            realLoadReceivePage();
        }
    }

    public void loadJourneyPage() {
        if (mCurrentJourneyPageNo * RECEIVE_DEFAULT_SIZE >= mJourneyPageSize) {
            mvpView.noMoreJourneyData();
        } else {
            mCurrentJourneyPageNo++;
            realLoadJourneyPage();
        }
    }

    private void realLoadJourneyPage() {
        if(isJourneyLoading)
            return;
        isJourneyLoading= true;
        mvpView.showLoadingJourneyPage();
        mSource.journeyList(userId(), keyCode(), mCurrentJourneyPageNo, JOURNEY_DEFAULT_SIZE, new Subscriber<BaseBean<List<Journey>>>() {
            @Override
            public void onCompleted() {
                isJourneyLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                mvpView.journeyPageLoadFailed();
                isJourneyLoading = false;
            }

            @Override
            public void onNext(BaseBean<List<Journey>> listBaseBean) {
                invalidKeyCode(listBaseBean.returnCode);
                mJourneyPageSize = listBaseBean.returnSize;
                if (mCurrentJourneyPageNo == 1){
                    mJourneyData.clear();
                    if (listBaseBean.returnData == null){
                        mvpView.emptyJourneyData();
                        return;
                    }
                }
                mJourneyData.addAll(listBaseBean.returnData);
                if (mJourneyData.size() == 0) {
                    mvpView.emptyJourneyData();
                    return;
                }
                if (mJourneyAdapter == null) {
                    mJourneyAdapter = new JourneyAdapter(mActivity, MainPresenter.this, R.layout.item_journey, mJourneyData);
                    mvpView.journeyPageSetAdapter(mJourneyAdapter);
                } else {
                    mJourneyAdapter.setItems(mJourneyData);
                    mvpView.journeyPageLoadComplete();
                }
            }

        });
    }

    /**
     * 拒绝工单
     * @param id 工单id
     */
    public void refuse(int id) {
        receiveListOperator(id, DispatchSource.TYPE_REJECT);
    }

    /**
     * 接受工单
     * @param id 工单id
     */
    public void confirm(int id) {
        receiveListOperator(id, DispatchSource.TYPE_ACCEPT);
    }

    /**
     *
     * @param id 工单ID
     * @param status 2/拒绝 3/接受
     */
    public void receiveListOperator(int id, int status){
        mSource.receiveOperator(userId(), keyCode(), id, status, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.returnCode == SUCCESS){
                    reloadReceiveList();
                }else{
                    mvpView.disPlay(baseBean.returnInfo);
                }
            }
        });
    }


    public void jPushComponent() {
        jPushInit();
        if (SpUtils.isLogin(mContext)){
            isSetAlias();
        }
        registerMessageReceiver();  // used for receive msg
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        mActivity.registerReceiver(mMessageReceiver, filter);
    }

    public void unregisterReceiver() {
        mActivity.unregisterReceiver(mMessageReceiver);
    }

    public void isSetAlias() {
        if (!SpUtils.getIsSetAlias(mContext)){
            setAlias();
            setTag();
        }
    }

    public void startJourney(int scheduleId, final int lineId) {
        mvpView.showLoading();
        mSource.journeyOperator(userId(), keyCode(), scheduleId, "", new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.hideLoading();
            }

            @Override
            public void onNext(BaseBean baseBean) {
                reloadJourneyList();
            }
        });
    }

    public void normalFinishJourney(int scheduleId) {
        mvpView.showLoading();
        mSource.journeyEnd(userId(), keyCode(), scheduleId, 4, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.hideLoading();
            }

            @Override
            public void onNext(BaseBean baseBean) {
                reloadJourneyList();
            }
        });
    }

    public void errorFinishJourney(int scheduleId) {
        mvpView.showLoading();
        mSource.journeyEnd(userId(), keyCode(), scheduleId, 3, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.hideLoading();
            }

            @Override
            public void onNext(BaseBean baseBean) {
                reloadJourneyList();
            }
        });
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void jPushInit() {
        JPushInterface.init(MyApplication.mContext);
    }

    private String tag;
    /**
     * 设置Alias
     */
    public void setAlias(){
        String alias;
        String code = SpUtils.getCache(mContext, SpUtils.CODE);
        if (TextUtils.isEmpty(code)){
            alias = null;
            tag = "nologin";
        }else {
            alias = "yd_" + code + "_device";
            tag = "yd_device";
        }
        //调用JPush API设置Alias
        try{
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
        }catch (Exception e){
            DebugLog.w(e.getMessage());
        }
    }

    /**
     * 设置tag
     */
    private void setTag(){
        // 检查 tag 的有效性
        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            tagSet.add(sTagItme);
        }
        try{
            //调用JPush API设置Tag
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
        }catch (Exception e){
            DebugLog.w(e.getMessage());
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    DebugLog.i(logs);
                    DebugLog.e(alias);
                    SpUtils.setAlias(mContext, true);

                    DebugLog.e(JPushInterface.getRegistrationID(mContext));

                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    DebugLog.i(logs);
                    if (ExampleUtil.isConnected(MyApplication.mContext)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        DebugLog.i("No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    DebugLog.e(logs);
            }

        }

    };
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    DebugLog.i(logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    DebugLog.i(logs);
                    if (ExampleUtil.isConnected(MyApplication.mContext)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        DebugLog.i("No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    DebugLog.e(logs);
            }

        }

    };
}
