package com.zxw.dispatch_driver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zxw.dispatch_driver.db.LineStationReportBean;
import com.zxw.dispatch_driver.db.StationReportDAO;
import com.zxw.dispatch_driver.db.VoiceCompoundBean;
import com.zxw.dispatch_driver.utils.CalculateAngleUtil;
import com.zxw.dispatch_driver.utils.CalculateDistanceUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.SpeakUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final double RADIUS = 50;
    /**
     * 模拟车辆行驶轨迹
     * 22.6410301906,114.0097882679
     * 22.6412091906,114.0096572679
     * 22.6413671906,114.0095542679
     * 22.6415051906,114.0094642679
     * 22.6416261906,114.0093662679 准备掉头
     * <p>
     * 22.6418040000,114.0092240000
     * 22.6420320000,114.0090740000
     * 22.6423140000,114.0089400000
     * 22.6426020000,114.0091330000
     * 22.6428350000,114.0094870000
     * 22.6428350000,114.0094870000
     * 22.6431970000,114.0100880000
     * 22.6433160000,114.0100130000
     * 22.6431770000,114.0097390000
     * 22.6430340000,114.0094980000
     * 22.6428750000,114.0092190000
     * 22.6427120000,114.0089670000
     * 22.6425630000,114.0087630000
     * 22.6423410000,114.0087090000
     * 22.6421870000,114.0088380000
     * 22.6420080000,114.0089310000
     * 22.6418990000,114.0090170000
     * 22.6418340000,114.0091080000
     * <p>
     * 22.6416341906,114.0092442679
     * 22.6414761906,114.0093432679
     * 22.6412341906,114.0095182679
     * 22.6410421906,114.0096662679
     * 22.6408881906,114.0097702679
     */
    private StationReportDAO dao;
    private double[] lats = new double[]{22.6410301906,
            22.6412091906,
            22.6413671906,
            22.6415051906,
            22.6416261906,

            22.6418040000,
            22.6420320000,
            22.6423140000,
            22.6426020000,
            22.6428350000,
            22.6431970000,
            22.6433160000,
            22.6431770000,
            22.6430340000,
            22.6428750000,
            22.6427120000,
            22.6425630000,
            22.6423410000,
            22.6421870000,
            22.6420080000,
            22.6418990000,
            22.6418340000,

            22.6416341906,
            22.6414761906,
            22.6412341906,
            22.6410421906,
            22.6408881906};

    private double[] lngs = new double[]{
            114.0097882679,
            114.0096572679,
            114.0095542679,
            114.0094642679,
            114.0093662679,

            114.0092240000,
            114.0090740000,
            114.0089400000,
            114.0091330000,
            114.0094870000,
            114.0100880000,
            114.0100130000,
            114.0097390000,
            114.0094980000,
            114.0092190000,
            114.0089670000,
            114.0087630000,
            114.0087090000,
            114.0088380000,
            114.0089310000,
            114.0090170000,
            114.0091080000,

            114.0092442679,
            114.0093432679,
            114.0095182679,
            114.0096662679,
            114.0097702679
    };


    private double historyLat, historyLng;
    private List<Marker> mStationMarkers = new ArrayList<>();
    private List<Marker> mTrackMarkers = new ArrayList<>();

    private int currentPoint = 1, lastArriveOnPoint, lastArriveOffPoint;
    private List<LineStationReportBean> lineStationReportBeen;
    private AMap mMap;
    private List<VoiceCompoundBean> mVoiceCompundList;
    private SpeakUtil mSpeakUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mapView = (MapView) findViewById(R.id.mv);
        mapView.onCreate(savedInstanceState);
        mMap = mapView.getMap();
        mMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                LatLng marker1 = new LatLng(22.6410301906, 114.0097882679);    //设置中心点和缩放比例
                if (mMap != null) {
                    mMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(19));
                }
                DebugLog.i("地图加载完成");
            }
        });

        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
        mSpeakUtil.init();

        dao = new StationReportDAO(this);
        lineStationReportBeen = dao.queryLineStationReportBean();
        mVoiceCompundList = dao.queryVoiceCompound();
    }

    public void add(View view) {
        if (currentPoint == lngs.length) {
            //轮回
            currentPoint = 1;
            lastArriveOnPoint = -1;
            lastArriveOffPoint = -1;
        }
        cleanMarker();
        // 在地图上画点
        drawMarker();
        // 计算当前经纬度附近x米有没有报站经纬度
        checkNearPoint(lats[currentPoint], lngs[currentPoint]);
        currentPoint++;
    }

    private void drawMarker() {
        drawDriverTrackMarker(currentPoint);
        for (LineStationReportBean bean : lineStationReportBeen) {
            drawStationMarker(bean, bean.getLat(), bean.getLng());
        }
    }

    private void cleanMarker() {
        for (Marker marker : mTrackMarkers)
            marker.remove();
        for (Marker marker : mStationMarkers)
            marker.remove();
    }

    public void checkNearPoint(double lat, double lng) {
        for (int i = 0; i < lineStationReportBeen.size(); i++) {
            LineStationReportBean bean = lineStationReportBeen.get(i);
            LineStationReportBean nextBean = null;
            if (i < lineStationReportBeen.size() - 1) {
                nextBean = lineStationReportBeen.get(i + 1);
            }
            // 距离对
            if (CalculateDistanceUtil.isInCircle(lat, lng, RADIUS, bean.getLat(), bean.getLng())) {
                //  站点类型
                switch (bean.getType()) {
                    //进站
                    case 1:
                        //判断这个标记点是否已经报过, 如果报过,则跳过此次循环判断, 进行判断下个标记点
                        if (lastArriveOnPoint >= bean.getStationId() || lastArriveOffPoint != lastArriveOnPoint || i == lineStationReportBeen.size() - 1)
                            continue;
                        if (nextBean == null)
                            throw new RuntimeException("next bean can not be null");
                        // 角度对
                        if (judgeAngle(lats[currentPoint - 1], lngs[currentPoint - 1], lats[currentPoint], lngs[currentPoint],
                                bean.getLat(), bean.getLng(), nextBean.getLat(), nextBean.getLng())) {
                            // 报站操作
                            speakVoice(bean.getType(), bean.getStationId());
                            // 记录标记
                            lastArriveOnPoint = bean.getStationId();
                            // 退出此次循环判断,等待下次位置移动
                            return;
                        } else {
                            //角度错误,
                            DebugLog.w("wrong direction");
                        }
                        break;
                    //末站
                    case 2:
                        if (lastArriveOnPoint == bean.getStationId() && lastArriveOffPoint != lastArriveOnPoint) {
                            speakVoice(bean.getType(), bean.getStationId());
                            lastArriveOffPoint = bean.getStationId();
                        }
                        break;
                    // 出站
                    case 4:
                        // 报过上站, 没报下站,则报下站  否则进行下一个标记点判断
                        if (lastArriveOnPoint == bean.getStationId() && lastArriveOffPoint != lastArriveOnPoint) {
                            if (nextBean == null)
                                throw new RuntimeException("next bean can not be null");
                            // 报站操作
                            speakVoice(bean.getType(), nextBean.getStationId());
                            // 记录标记
                            lastArriveOffPoint = bean.getStationId();
                        }
                        break;
                }
            }
        }
    }

    private void speakVoice(int type, int stationId) {
        String result = compoundVoice(type, queryStationName(stationId));
        mSpeakUtil.playText(result);
        ToastHelper.showToast(result);
    }

    private String queryStationName(int stationId) {
        return dao.queryStation(stationId).getStationName();
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

    private void drawDriverTrackMarker(int currentPoint) {
        LatLng latLng = new LatLng(lats[currentPoint], lngs[currentPoint]);
        Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.off_station_point))
                .draggable(true));
        mTrackMarkers.add(marker);
        LatLng latLng2 = new LatLng(lats[currentPoint - 1], lngs[currentPoint - 1]);
        Marker marker2 = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng2)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.off_station_point))
                .draggable(true));
        mTrackMarkers.add(marker2);

    }

    private void drawStationMarker(LineStationReportBean bean, Double lat, Double lng) {
        LatLng latLng = new LatLng(lat, lng);
        Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng).title(bean.getStationId() + " type:" + bean.getType())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.on_station_point))
                .draggable(true));
        mStationMarkers.add(marker);
    }

    private boolean judgeAngle(double driveLat1, double driveLng1, double driveLat2, double driveLng2,
                               double checkLat1, double checkLng1, double checkLat2, double checkLng2) {
        CalculateAngleUtil.RoadTrack track = new CalculateAngleUtil.RoadTrack(driveLat1, driveLng1, driveLat2, driveLng2);
        CalculateAngleUtil.RoadTrack track2 = new CalculateAngleUtil.RoadTrack(checkLat1, checkLng1, checkLat2, checkLng2);
        return CalculateAngleUtil.judgeAngle(track, track2);
    }
}
