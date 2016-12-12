package com.zxw.dispatch_driver.presenter.view;

/**
 * author：CangJie on 2016/12/8 15:52
 * email：cangjie2016@gmail.com
 */
public interface JourneyView extends BaseView {
    void startSuccess(String returnInfo);

    void startFailed(String returnInfo);

    void normalFinishSuccess(String returnInfo);

    void normalFinishFailed(String returnInfo);

    void errorFinishSuccess(String returnInfo);

    void errorFinishFailed(String returnInfo);
}
