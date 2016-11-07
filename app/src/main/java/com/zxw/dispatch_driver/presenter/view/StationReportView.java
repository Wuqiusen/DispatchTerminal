package com.zxw.dispatch_driver.presenter.view;

import com.zxw.dispatch_driver.adapter.ManualReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;

/**
 * author：CangJie on 2016/11/3 17:41
 * email：cangjie2016@gmail.com
 */
public interface StationReportView extends BaseView {
    void setStationListAdapter(ManualReportStationAdapter manualReportStationAdapter);

    void setServiceWordAdapter(ServiceWordAdapter serviceWordAdapter);
}
