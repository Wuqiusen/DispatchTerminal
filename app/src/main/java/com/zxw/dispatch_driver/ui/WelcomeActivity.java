package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.zxw.data.source.LineSource;
import com.zxw.data.source.LineStationSource;
import com.zxw.data.source.ReportPointSource;
import com.zxw.data.source.ServiceWordSource;
import com.zxw.data.source.StationSource;
import com.zxw.data.source.VoiceCompoundSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;

public class WelcomeActivity extends BaseHeadActivity implements LineSource.OnUpdateLineTableFinishListener, StationSource.OnUpdateStationFinishListener, LineStationSource.OnUpdateLineStationFinishListener, ReportPointSource.OnUpdateReportPointFinishListener, ServiceWordSource.OnUpdateServiceWordTableFinishListener, VoiceCompoundSource.OnUpdateVoiceCompoundTableFinishListener {

    private boolean isUpdateLineFinish, isUpdateStationFinish, isUpdateLineStationFinish, isUpdateReportPointFinish, isUpdateServiceWordFinish, isUpdateVoiceCompoundFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        showLoading();
        showTitle("欢迎页");
//        update();
        String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
        if(TextUtils.isEmpty(lineId)){
            startActivity(new Intent(this, SelectLineActivity.class));
        }else{
            Intent intent = new Intent(mContext, AutoReportActivity.class);
            intent.putExtra("lineId", lineId);
            mContext.startActivity(intent);
        }
        finish();
    }

    private void update() {
        LineSource source = new LineSource(MyApplication.mContext);
        source.setOnUpdateLineTableFinishListener(this);
        source.loadUpdateLineTableData();
        StationSource stationSource = new StationSource(MyApplication.mContext);
        stationSource.setOnUpdateStationFinishListener(this);
        stationSource.loadUpdateStationTableData();
        LineStationSource lineStationSource = new LineStationSource(MyApplication.mContext);
        lineStationSource.setOnUpdateLineStationFinishListener(this);
        lineStationSource.loadUpdateLineStationTableData();
        ReportPointSource reportPointSource = new ReportPointSource(MyApplication.mContext);
        reportPointSource.setOnUpdateReportPointFinishListener(this);
        reportPointSource.loadUpdateReportPointSource();
        ServiceWordSource serviceWordSource = new ServiceWordSource(MyApplication.mContext);
        serviceWordSource.setOnUpdateServiceWordTableFinishListener(this);
        serviceWordSource.loadUpdateServiceWordTableData();
        VoiceCompoundSource voiceCompoundSource = new VoiceCompoundSource(MyApplication.mContext);
        voiceCompoundSource.setOnUpdateVoiceCompoundTableFinishListener(this);
        voiceCompoundSource.loadUpdateVoiceCompoundTableData();


    }

    @Override
    public void onUpdateLineTableFinish() {
        isUpdateLineFinish = true;
        goMain();
    }

    @Override
    public void onUpdateStationFinish() {
        isUpdateStationFinish = true;
        goMain();
    }

    @Override
    public void onUpdateLineStationFinishListener() {
        isUpdateLineStationFinish = true;
        goMain();
    }

    @Override
    public void onUpdateReportPointFinishListener() {
        isUpdateReportPointFinish = true;
        goMain();
    }

    private void goMain(){
        if (isUpdateLineFinish && isUpdateLineStationFinish && isUpdateReportPointFinish && isUpdateStationFinish && isUpdateServiceWordFinish && isUpdateVoiceCompoundFinish){
            String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
            if(TextUtils.isEmpty(lineId)){
                startActivity(new Intent(this, SelectLineActivity.class));
                finish();
            }else{
                Intent intent = new Intent(mContext, AutoReportActivity.class);
                intent.putExtra("lineId", lineId);
                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public void onUpdateServiceWordTableFinish() {
        isUpdateServiceWordFinish = true;
        goMain();
    }

    @Override
    public void onUpdateVoiceCompoundTableFinish() {
        isUpdateVoiceCompoundFinish = true;
        goMain();
    }
}
