package com.zxw.dispatch_driver.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.zxw.data.db.bean.InnerReportPointBean;
import com.zxw.data.db.bean.ServiceWordBean;
import com.zxw.data.db.bean.StationBean;
import com.zxw.data.db.bean.VoiceCompoundBean;
import com.zxw.data.dao.LineStationDao;
import com.zxw.data.dao.ReportPointDao;
import com.zxw.data.dao.ServiceWordDao;
import com.zxw.data.dao.StationDao;
import com.zxw.data.dao.VoiceCompoundDao;
import com.zxw.dispatch_driver.adapter.AutoReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;
import com.zxw.dispatch_driver.presenter.view.AutoReportView;
import com.zxw.dispatch_driver.utils.CalculateAngleUtil;
import com.zxw.dispatch_driver.utils.CalculateDistanceUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.SpeakUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.List;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2016/11/3 17:08
 * email：cangjie2016@gmail.com
 */
public class AutoReportPresenter extends BasePresenter<AutoReportView> {

    public static final double RADIUS = 50;

//    private final List<LineStationReportBean> lineStationReportBeen;
    private final VoiceCompoundDao mVoiceCompoundDao;
    private final List<VoiceCompoundBean> mVoiceCompundList;

    private int currentPoint = 1, lastArriveOnPoint, lastArriveOffPoint;
    private SpeakUtil mSpeakUtil;
    private final StationDao mStationDao;
    private final ReportPointDao mReportPointDao;
    private List<InnerReportPointBean> mLineStationReportList;
    private double lastLat,lastLng;
    private List<StationBean> mOrderStationList;
    private AutoReportStationAdapter mStationNameAdapter;

    public AutoReportPresenter(AutoReportView mvpView, Context context) {
        super(mvpView);
        mReportPointDao = new ReportPointDao(context);
        mVoiceCompoundDao = new VoiceCompoundDao(context);
        mStationDao = new StationDao(context);
//        lineStationReportBeen = mStationReportDao.queryLineStationReportBean();
        mVoiceCompundList = mVoiceCompoundDao.queryVoiceCompound();
        mSpeakUtil = SpeakUtil.getInstance(mContext);
        mSpeakUtil.init();
    }
    public void drive(double lat, double lng){
        checkNearPoint(lat, lng);
        lastLat = lat;
        lastLng = lng;
    }

    public void checkNearPoint(double lat, double lng) {
        for (int i = 0; i < mLineStationReportList.size(); i++) {
            InnerReportPointBean bean = mLineStationReportList.get(i);
            InnerReportPointBean nextBean = null;
            // 非末站
            if (i < mLineStationReportList.size() - 1) {
                nextBean = mLineStationReportList.get(i + 1);
            }
            // 距离对
            if (CalculateDistanceUtil.isInCircle(lat, lng, RADIUS, bean.getLat(), bean.getLng())) {
                //  站点类型
                switch (bean.getType()) {
                    //进站
                    case 1:
                        //判断这个标记点是否已经报过, 如果报过,则跳过此次循环判断, 进行判断下个标记点
                        if (lastArriveOnPoint >= bean.getSortNum())
                            continue;

                        if(i == mLineStationReportList.size() - 1){
                            //到达终点站
                            // 报站操作
                            speakVoice(2, bean.getStationId());
                            // 记录标记
                            lastArriveOnPoint = bean.getSortNum();
                            mStationNameAdapter.reportPositionRecord(bean.getStationId());
                            // 退出此次循环判断,等待下次位置移动
                            return;
                        }
                        if (nextBean == null)
                            throw new RuntimeException("next bean can not be null");
                        // 角度对
                        if (judgeAngle(lastLat, lastLng, lat, lng,
                                bean.getLat(), bean.getLng(), nextBean.getLat(), nextBean.getLng())) {
                            // 报站操作
                            speakVoice(bean.getType(), bean.getStationId());
                            // 记录标记
                            lastArriveOnPoint = bean.getSortNum();
                            mStationNameAdapter.reportPositionRecord(bean.getStationId());
                            // 退出此次循环判断,等待下次位置移动
                            return;
                        } else {
                            //角度错误,
                            DebugLog.w("wrong direction");
                        }
                        break;
                    // 出站
                    case 4:
                        // 报过上站, 没报下站,则报下站  否则进行下一个标记点判断
                        if (lastArriveOnPoint == bean.getSortNum() && lastArriveOffPoint != lastArriveOnPoint) {
                            if (nextBean == null)
                                throw new RuntimeException("next bean can not be null");
                            // 报站操作
                            speakVoice(bean.getType(), nextBean.getStationId());
                            // 记录标记
                            lastArriveOffPoint = bean.getSortNum();
                            mStationNameAdapter.reportPositionRecord(bean.getStationId());
                        }
                        break;
                }
            }

        }
    }
    private boolean judgeAngle(double driveLat1, double driveLng1, double driveLat2, double driveLng2,
                               double checkLat1, double checkLng1, double checkLat2, double checkLng2) {
        CalculateAngleUtil.RoadTrack track = new CalculateAngleUtil.RoadTrack(driveLat1, driveLng1, driveLat2, driveLng2);
        CalculateAngleUtil.RoadTrack track2 = new CalculateAngleUtil.RoadTrack(checkLat1, checkLng1, checkLat2, checkLng2);
        return CalculateAngleUtil.judgeAngle(track, track2);
    }

    private void speakVoice(int type, int stationId) {
        String result = compoundVoice(type, queryStationName(stationId));
        mSpeakUtil.playText(result);
        ToastHelper.showToast(result);
    }

    private String queryStationName(int stationId) {
        return mStationDao.queryStation(stationId).getStationName();
    }

    private String compoundVoice(int type, String stationName) {
        String content = null;
        for (VoiceCompoundBean voice : mVoiceCompundList) {
            if (voice.getType() == type) {
                content = voice.getContent();
                break;
            }
        }
        if (TextUtils.isEmpty(content))
            throw new RuntimeException("speak content can not be null");
        switch (type) {
            case 1:
            case 2:
                content = content.replace("#curentStation#", stationName);
                break;
            case 4:
                content = content.replace("#nextStation#", stationName);
                break;
        }
        return content;
    }

    public void loadReportPotins(int mLineId) {
        mLineStationReportList = mReportPointDao.queryByLineId(mLineId);
        mvpView.drawStationMarker(mLineStationReportList);
    }

    public void loadStationsName(int mLineId) {
        LineStationDao lineStationDao = new LineStationDao(mContext);
        mOrderStationList = lineStationDao.queryStations(mLineId);
        mStationNameAdapter = new AutoReportStationAdapter(mOrderStationList, mContext);
        mvpView.setStationListAdapter(mStationNameAdapter);
    }

    public void loadServiceWord(){
        ServiceWordDao serviceWordDao = new ServiceWordDao(mContext);
        List<ServiceWordBean> serviceWordBeen = serviceWordDao.queryServiceWord();
        ServiceWordAdapter serviceWordAdapter = new ServiceWordAdapter(mContext, serviceWordBeen);
        mvpView.setServiceWordAdapter(serviceWordAdapter);
    }


}
