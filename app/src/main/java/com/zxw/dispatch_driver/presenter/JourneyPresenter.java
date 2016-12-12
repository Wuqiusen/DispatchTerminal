package com.zxw.dispatch_driver.presenter;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.Journey;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.Constants;
import com.zxw.dispatch_driver.presenter.view.JourneyView;

import rx.Subscriber;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2016/12/8 15:52
 * email：cangjie2016@gmail.com
 */
public class JourneyPresenter extends BasePresenter<JourneyView> {
    private final Journey mJourney;
    private final DispatchSource mSource;

    public JourneyPresenter(JourneyView mvpView, Journey journey) {
        super(mvpView);
        this.mJourney = journey;
        this.mSource = new DispatchSource();
    }

    public void start() {
        realJourneyOperator(2, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.returnCode == Constants.SUCCESS) {
                    SpUtils.setCache(mContext, SpUtils.CURRENT_LINE_ID, String.valueOf(mJourney.getLineId()));
                    mvpView.startSuccess(baseBean.returnInfo);
                } else if (baseBean.returnCode == Constants.FAILED) {
                    mvpView.startFailed(baseBean.returnInfo);
                }
            }
        });
    }

    public void normalFinish() {
        realJourneyOperator(4, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.returnCode == Constants.SUCCESS) {
                    mvpView.normalFinishSuccess(baseBean.returnInfo);
                } else if (baseBean.returnCode == Constants.FAILED) {
                    mvpView.normalFinishFailed(baseBean.returnInfo);
                }
            }
        });
    }

    public void errorFinish() {
        realJourneyOperator(3, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.returnCode == Constants.SUCCESS) {
                    mvpView.errorFinishSuccess(baseBean.returnInfo);
                } else if (baseBean.returnCode == Constants.FAILED) {
                    mvpView.errorFinishFailed(baseBean.returnInfo);
                }
            }
        });
    }

    private void realJourneyOperator(int status, Subscriber<BaseBean> subscriber) {
        mvpView.showLoading();
        mSource.journeyOperator(code(), keyCode(), mJourney.getId(), status, subscriber);
    }
}
