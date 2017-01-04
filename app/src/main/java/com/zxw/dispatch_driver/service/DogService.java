package com.zxw.dispatch_driver.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.zxw.data.dao.DogMainDao;
import com.zxw.data.dao.DogSecondDao;
import com.zxw.data.dao.VoiceCompoundDao;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.Constants;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.CalculateDistanceUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.SpeakUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zxw.dispatch_driver.MyApplication.mContext;
import static com.zxw.dispatch_driver.presenter.AutoReportPresenter.RADIUS;

/**
 * author：CangJie on 2016/12/12 15:37
 * email：cangjie2016@gmail.com
 */
public class DogService extends Service {
    private DogMainDao mDogMainDao;
    private DogSecondDao mDogSecondDao;
    private VoiceCompoundDao mVoiceCompoundDao;
    private SpeakUtil mSpeakUtil;
    private List<TbDogLineMain> mDogLineMains;
    private double lastLat, lastLng, mCurrentSpeed;
    private long lastSinglePointPosition, lastCompoundPointPosition;

    private Intent latLngIntent = new Intent(Constants.RECEIVER_GPS);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLog.w("onStartCommand");
        String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
        if (!TextUtils.isEmpty(lineId))
            dogWork(lineId);
        return super.onStartCommand(intent, flags, startId);
    }

    private void dogWork(String lineId) {
        init();
//        mockLatLng();
        mDogLineMains = mDogMainDao.queryByLineID(Integer.valueOf(lineId));
    }

    private void mockLatLng() {
        final double[] lat = new double[]{22.64364, 22.644945, 22.645887, 22.641946};
        final double[] lng = new double[]{114.010765, 114.012847, 114.014228, 114.009048};
        Observable.interval(6, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    int current = 0;
                    @Override
                    public void call(Long aLong) {
                        if (current < 3){
                            LatLng latLng = new LatLng(lat[current], lng[current]);
                            drive(lat[current], lng[current]);
                            sendPositionBroadcast(latLng);
                            current++;
                        }else{
                            current = 0;
                        }
                    }
                });
    }

    private void init() {
        mDogMainDao = new DogMainDao(MyApplication.mContext);
        mDogSecondDao = new DogSecondDao(MyApplication.mContext);
        mVoiceCompoundDao = new VoiceCompoundDao(MyApplication.mContext);

        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
        mSpeakUtil.init();


        //init position
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocation(Location location) {
        CoordinateConverter converter  = new CoordinateConverter();
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标点 LatLng类型
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        converter.coord(latLng);
// 执行转换操作
        LatLng desLatLng = converter.convert();
        mCurrentSpeed = location.getSpeed();
        drive(desLatLng.latitude, desLatLng.longitude);


        sendPositionBroadcast(desLatLng);
    }

    private void sendPositionBroadcast(LatLng desLatLng) {
        latLngIntent.putExtra("lat", desLatLng.latitude);
        latLngIntent.putExtra("lng", desLatLng.longitude);
        latLngIntent.putExtra("speed", mCurrentSpeed);
        sendBroadcast(latLngIntent);
    }

    public void drive(double lat, double lng){
        checkNearPoint(lat, lng);
        lastLat = lat;
        lastLng = lng;
    }

    private void checkNearPoint(double lat, double lng) {
        for(TbDogLineMain main : mDogLineMains){
            for(TbDogLineSecond second : main.getSecondList()){
//                int i = main.getSecondList().
                if(CalculateDistanceUtil.isInCircle(lat, lng, RADIUS, second.getLat(), second.getLng())){
                    // 车辆在判断条件附近
                    if(isSingleTypeEvent(main)){
                        onSinglePoint(main, second);
                    }else{
                        onCompoundPoint(main, second);
                    }
                }
            }
        }
    }
    private boolean isSingleTypeEvent(TbDogLineMain main) {
        // 判断是否为多点组合线事件
        if (main.getType() == 1){
            //单点事件
            return true;
        }else if(main.getType() == 2){
            //多点事件
            return false;
        }
        return false;
    }
    private void onSinglePoint(TbDogLineMain main, TbDogLineSecond second) {
        // 判断不是刚才报过的点, 防止多次报告
        if(lastSinglePointPosition != second.getId()){
            if(isCompare(main)){
                // 1需要比较车速 才启动语音事件
                if(isOverSpeed(main)){
                    //当前值大于或等于比较值 则报告
//                    ToastHelper.showToast("比较后播放语音id: " + main.getVoiceId());
                    voice(main.getVoiceContent());
                    // 判断是否需要报告后台
                    if(isCommit(main)){
                        ToastHelper.showToast("报告后台 " + main.getId() +" 驾驶员违反这条规定");
                    }
                }
            }else{
                //直接播放
//                ToastHelper.showToast("直接播放 播放语音id: " + main.getVoiceId());
                voice(main.getVoiceContent());
            }
            lastSinglePointPosition = second.getId();
        }
    }

    private void voice(String voiceContent) {
        mSpeakUtil.playText(voiceContent);
    }

    private boolean isCommit(TbDogLineMain main){
        return main.getIsCommit() == 1;
    }

    private boolean isOverSpeed(TbDogLineMain main){
        return main.getCompareValue() <= mCurrentSpeed;
    }

    private boolean isCompare(TbDogLineMain main){
        return main.getIsCompare() == 1;
    }

    private void onCompoundPoint(TbDogLineMain main, TbDogLineSecond second) {
        // 判断不是刚才报过的点, 防止多次报告
        if(lastCompoundPointPosition != second.getId()){
            if(isCompare(main)){
                // 1需要比较车速 才启动语音事件
                if(isOverSpeed(main)){
                    //当前值大于或等于比较值 则报告
//                    ToastHelper.showToast("比较后播放语音id: " + main.getVoiceId());
                    voice(main.getVoiceContent());
                    // 判断是否需要报告后台

                    if(isCommit(main)){
                        ToastHelper.showToast("报告后台 " + main.getId() +" 驾驶员违反这条规定");
                    }
                }
            }else{
                //直接播放
//                ToastHelper.showToast("直接播放 播放语音id: " + main.getVoiceId());
                voice(main.getVoiceContent());
            }
            lastCompoundPointPosition = second.getId();
        }
    }


}
