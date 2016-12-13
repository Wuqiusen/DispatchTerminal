package com.zxw.dispatch_driver.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.zxw.data.db.bean.ServiceWordBean;
import com.zxw.data.db.bean.StationBean;
import com.zxw.data.db.bean.VoiceCompoundBean;
import com.zxw.data.dao.LineStationDao;
import com.zxw.data.dao.ServiceWordDao;
import com.zxw.data.dao.StationDao;
import com.zxw.data.dao.VoiceCompoundDao;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.adapter.ManualReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;
import com.zxw.dispatch_driver.presenter.view.StationReportView;
import com.zxw.dispatch_driver.utils.SpeakUtil;

import java.util.List;

/**
 * author：CangJie on 2016/11/3 17:41
 * email：cangjie2016@gmail.com
 */
public class StationReportPresenter extends BasePresenter<StationReportView> implements ManualReportStationAdapter.OnClickStationListener {

    private final StationDao mStationDao;
    private final Context mContext;
    private final VoiceCompoundDao mVoiceCompoundDao;
    private final List<VoiceCompoundBean> mVoiceCompoundList;
    private final SpeakUtil mSpeakUtil;
    private List<StationBean> mStations;

    public StationReportPresenter(StationReportView mvpView, Context context) {
        super(mvpView);
        mStationDao = new StationDao(context);
        mVoiceCompoundDao = new VoiceCompoundDao(context);
        mVoiceCompoundList = mVoiceCompoundDao.queryVoiceCompound();

        this.mContext = context;
        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
        mSpeakUtil.init();
    }

    public void loadStations(long mLineId) {
        LineStationDao lineStationDao = new LineStationDao(mContext);
        List<StationBean> stationBeen = lineStationDao.queryStations(mLineId);
        ManualReportStationAdapter manualReportStationAdapter = new ManualReportStationAdapter(stationBeen, mContext);
        manualReportStationAdapter.setOnClickStationListener(this);
        mvpView.setStationListAdapter(manualReportStationAdapter);
    }

    private void speakVoice(int type, String stationName) {
        String result = compoundVoice(type, stationName);
        mSpeakUtil.playText(result);
    }

    private String compoundVoice(int type, String stationName) {
        String content = null;
        for (VoiceCompoundBean voice : mVoiceCompoundList) {
            if (voice.getType() == type) {
                content = voice.getContent();
                break;
            }
        }
        if (TextUtils.isEmpty(content))
            throw new RuntimeException("speak content can not be null");
        switch (type) {
            case 1:
                content = content.replace("#currentStation#", stationName);
                break;
            case 2:
                content = content.replace("#currentStation#", stationName);
                break;
            case 4:
                content = content.replace("#nextStation#", stationName);
                break;
        }
        return content;
    }

    @Override
    public void onClickStationOn(StationBean currentBean) {
        speakVoice(1, currentBean.getStationName());
    }

    @Override
    public void onClickLastStationOn(StationBean currentBean) {
        speakVoice(2, currentBean.getStationName());
    }

    @Override
    public void onClickStationOff(StationBean nextBean) {
        speakVoice(4, nextBean.getStationName());
    }

    public void loadServiceWord(){
        ServiceWordDao serviceWordDao = new ServiceWordDao(mContext);
        List<ServiceWordBean> serviceWordBeen = serviceWordDao.queryServiceWord();
        ServiceWordAdapter serviceWordAdapter = new ServiceWordAdapter(mContext, serviceWordBeen);
        mvpView.setServiceWordAdapter(serviceWordAdapter);
    }

}
