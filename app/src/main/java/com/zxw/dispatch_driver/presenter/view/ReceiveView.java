package com.zxw.dispatch_driver.presenter.view;

import com.zxw.data.bean.LineDetail;
import com.zxw.dispatch_driver.presenter.ReceivePresenter;

import java.util.List;

/**
 * author：CangJie on 2016/12/8 16:24
 * email：cangjie2016@gmail.com
 */
public interface ReceiveView extends BaseView {

    void drawStation(List<ReceivePresenter.Station> stations);

    void operatorReceiveOrderSuccess();

    void baseInfo(LineDetail lineDetail);
}
