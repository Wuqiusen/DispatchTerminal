package com.zxw.dispatch_driver.presenter;

import android.app.Activity;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.Journey;
import com.zxw.data.bean.Receive;
import com.zxw.data.source.DispatchSource;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.JourneyAdapter;
import com.zxw.dispatch_driver.adapter.ReceiveAdapter;
import com.zxw.dispatch_driver.presenter.view.MainView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.zxw.dispatch_driver.Constants.SUCCESS;

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

    private final static int RECEIVE_DEFAULT_SIZE = 20, JOURNEY_DEFAULT_SIZE = 20;

    public MainPresenter(MainView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
    }

    public void reloadReceiveList() {
        mCurrentReceivePageNo = 1;
        realLoadReceivePage();
    }

    public void realLoadReceivePage() {
        if(isReceiveLoading)
            return;
        isJourneyLoading= true;
        mSource.receiveList(code(), keyCode(), mCurrentReceivePageNo, RECEIVE_DEFAULT_SIZE, new Subscriber<BaseBean<List<Receive>>>() {
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
        mSource.journeyList(code(), keyCode(), mCurrentJourneyPageNo, JOURNEY_DEFAULT_SIZE, new Subscriber<BaseBean<List<Journey>>>() {
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
                    mJourneyAdapter = new JourneyAdapter(mActivity, R.layout.item_journey, mJourneyData);
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
        receiveListOperator(id, 1);
    }

    /**
     * 接受工单
     * @param id 工单id
     */
    public void confirm(int id) {
        receiveListOperator(id, 3);
    }

    public void receiveListOperator(int id, int status){
        mSource.receiveOperator(code(), keyCode(), id, status, new Subscriber<BaseBean>() {
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
}
