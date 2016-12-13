package com.zxw.dispatch_driver.presenter;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.zxw.data.bean.Login;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.data.utils.MD5;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.jpush.ExampleUtil;
import com.zxw.dispatch_driver.presenter.view.LoginView;
import com.zxw.dispatch_driver.utils.DebugLog;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscriber;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2016/12/7 16:35
 * email：cangjie2016@gmail.com
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private DispatchSource mSource;
    private final Handler mHandler ;
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    public LoginPresenter(LoginView mvpView, Activity activity) {
        super(mvpView);
        this.mSource = new DispatchSource();
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

    public void verifyAccount(String userName, String password) {
        String time = String.valueOf(new Date().getTime());
        String md5Password = MD5.MD5Encode(MD5.MD5Encode(password) + time);
        mSource.login(userName, String.valueOf(time), md5Password, new Subscriber<Login>() {
            @Override
            public void onCompleted() {
                mvpView.loginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.loginFail(e.getMessage());
            }

            @Override
            public void onNext(Login loginBean) {
                SpUtils.setCache(MyApplication.mContext, SpUtils.CODE, loginBean.getCode());
                SpUtils.setCache(MyApplication.mContext, SpUtils.NAME, loginBean.getName());
                SpUtils.setCache(MyApplication.mContext, SpUtils.KEYCODE, loginBean.getKeyCode());

                jPushComponent();
            }
        });
    }

    public void jPushComponent() {
        jPushInit();
        setAlias();
        setTag();
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
