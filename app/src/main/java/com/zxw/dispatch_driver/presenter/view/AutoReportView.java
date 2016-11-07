package com.zxw.dispatch_driver.presenter.view;

import com.zxw.data.db.bean.InnerReportPointBean;
import com.zxw.dispatch_driver.adapter.AutoReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;

import java.util.List;

/**
 * author：CangJie on 2016/11/3 17:09
 * email：cangjie2016@gmail.com
 */
public interface AutoReportView extends BaseView {
//    void drawMarker(double lat, double lng, double lat1, double lng1, List<LineStationReportBean> lineStationReportBeen);
//
//    void cleanMarker();

    void setStationListAdapter(AutoReportStationAdapter stationReportAdapter);

    void setServiceWordAdapter(ServiceWordAdapter serviceWordAdapter);

    void drawStationMarker(List<InnerReportPointBean> lineStationReportBeen);
}
