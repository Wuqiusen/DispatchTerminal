package com.zxw.dispatch_driver.ui;

import android.os.Bundle;

import com.zxw.data.source.LineSource;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;

public class WelcomeActivity extends BaseHeadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        update();
    }

    private void update() {
        LineSource source = new LineSource(MyApplication.mContext);
        source.loadUpdateLineTableData();
//        StationSource stationSource = new StationSource(MyApplication.mContext);
//        stationSource.loadUpdateStationTableData();
//        LineStationSource lineStationSource = new LineStationSource(MyApplication.mContext);
//        lineStationSource.loadUpdateLineStationTableData();
//        ReportPointSource reportPointSource = new ReportPointSource(MyApplication.mContext);
//        reportPointSource.loadUpdateReportPointSource();

    }
}
