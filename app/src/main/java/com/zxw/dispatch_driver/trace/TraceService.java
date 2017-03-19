package com.zxw.dispatch_driver.trace;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.ElectronRail;
import com.zxw.data.http.HttpMethods;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2017/2/24 10:40
 * email：cangjie2016@gmail.com
 */
public class TraceService extends Service {

    private List<ComplexRailBean> railList;


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location == null)
                return;
            if (location.getSpeed() == 0){
                LogUtil.log("速度为0 丢掉");
                return;
            }
            LatLng sourceLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 坐标转换
            CoordinateConverter converter  = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
            converter.coord(sourceLatLng);
            LatLng desLatLng = converter.convert();
            LogUtil.log("定位 " + desLatLng.toString());
            // 查询是否有进出围栏行为
            checkRail(desLatLng);
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

    // 检查当前坐标点对于各个围栏的状态.
    private void checkRail(LatLng location) {
        for (ComplexRailBean complexRail : railList){
            // 内围栏
            RailBean insideRail = complexRail.getInsideRail();
            // 设置上一个状态值
            insideRail.setLastStatus();
            // 设置当前状态值
            String insideStatus = rayCasting(insideRail.getPoints(), location);
            LogUtil.log(complexRail.getName() + " inside " + insideStatus);
            insideRail.setCurrentStatus(insideStatus);

            if(isEnterRailEvent(insideRail)){
                // 当上一次和这一次的状态值不一样时, 则将boolean值置true;
                insideRail.setHappenRailEvent(true);
            }

            // 外围栏
            RailBean outsideRail = complexRail.getOutsideRail();
            // 设置上一个状态值
            outsideRail.setLastStatus();
            // 设置当前状态值
            String outsideStatus = rayCasting(outsideRail.getPoints(), location);
            LogUtil.log(complexRail.getName() + " outside " + outsideStatus);
            outsideRail.setCurrentStatus(outsideStatus);


            if (isEnterRailEvent(outsideRail))
                // 当上一次和这一次的状态值不一样时, 则将boolean值置true;
                outsideRail.setHappenRailEvent(true);

            // 该场站的内外围栏皆发生了触发事件  开始上报
            if (insideRail.isHappenRailEvent() && outsideRail.isHappenRailEvent()){
                // 根据事件的触发时间判断, 看是先触发内围栏还是先触发外围栏
                if (insideRail.getEventTime() > outsideRail.getEventTime()){
                    // 内围栏时间比外围栏时间大, 进入事件
                    enterRail(complexRail);
                }else if (insideRail.getEventTime() < outsideRail.getEventTime()){
                    // 内围栏时间比外围栏时间小, 离开事件
                    leaveRail(complexRail);
                }else{
                    LogUtil.log("内外围栏的触发时间一样 \n" + complexRail.toString() );
                }
                clearStatus();
                return;
            }
        }
    }

    private boolean isEnterRailEvent(RailBean rail) {
        if (TextUtils.isEmpty(rail.getLastStatus()))
            return false;
        return (rail.getCurrentStatus().equals("in") && (rail.getLastStatus().equals("out") || rail.getLastStatus().equals("on")))
                || ((rail.getCurrentStatus().equals("out") ||  rail.getCurrentStatus().equals("on")) && rail.getLastStatus().equals("in"));
    }

    private void clearStatus() {
        LogUtil.log("清理围栏痕迹");
        for (ComplexRailBean complexRail : railList) {
            complexRail.getInsideRail().clear();
            complexRail.getOutsideRail().clear();
        }
    }

    private void leaveRail(ComplexRailBean complexRail) {
        int vehicleId = SpUtils.getVehicleId(mContext);
        String logStr = "后台围栏ID : " + complexRail.getRailId() + " ,出站, userId: " + SpUtils.getCache(mContext, SpUtils.USER_ID) + " vehicleId : " + vehicleId;
        LogUtil.uploadLog(logStr);
        HttpMethods.getInstance().leaveStation(new Subscriber<BaseBean>() {
                                                   @Override
                                                   public void onCompleted() {

                                                   }

                                                   @Override
                                                   public void onError(Throwable e) {

                                                   }

                                                   @Override
                                                   public void onNext(BaseBean baseBean) {

                                                   }
                                               }, SpUtils.getCache(mContext, SpUtils.USER_ID),
                SpUtils.getCache(mContext, SpUtils.KEYCODE),
                complexRail.getRailId(), vehicleId);
    }

    private void enterRail(ComplexRailBean complexRail) {
        int vehicleId = SpUtils.getVehicleId(mContext);
        String logStr = "后台围栏ID : " + complexRail.getRailId() + " ,进站, userId: " + SpUtils.getCache(mContext, SpUtils.USER_ID) + " vehicleId : " + vehicleId;
        LogUtil.uploadLog(logStr);
        HttpMethods.getInstance().enterStation(new Subscriber<BaseBean>() {
                                                   @Override
                                                   public void onCompleted() {

                                                   }

                                                   @Override
                                                   public void onError(Throwable e) {

                                                   }

                                                   @Override
                                                   public void onNext(BaseBean baseBean) {

                                                   }
                                               }, SpUtils.getCache(mContext, SpUtils.USER_ID),
                SpUtils.getCache(mContext, SpUtils.KEYCODE),
                complexRail.getRailId(), vehicleId);
    }

    /// 射线发判断点是否在多边形内部
    /// p 待判断的点。格式{x:X坐标,y:Y坐标}
    /// {Array} poly 多边形定点，数组成员格式相同
    private String rayCasting(List<Point> areas, LatLng location) {
        double px = location.latitude;
        double py = location.longitude;
        Boolean flag = false;
        for (int i = 0, l = areas.size(), j = l - 1; i < l; j = i, i++) {
            double sx = areas.get(i).getPx();
            double sy = areas.get(i).getPy();
            double tx = areas.get(j).getPx();
            double ty = areas.get(j).getPy();

            if ((sx == px && sy == py) || (tx == px && ty == py)) {
                return "on";
            }
            //*****判断线段两短点是否在射线两侧
            if ((sy < py && ty >= py) || (sy >= py && ty < py)) {

                double x = sx + (py - sy) * (tx - sx) / (ty - sy);
                if (x == px) {
                    return "on";
                }
                if (x > px) {
                    flag = !flag;
                }
            }
        }
        return flag ? "in" : "out";

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (railList == null) {
            railList = new ArrayList<>();
        } else {
            railList.clear();
        }
        loadElectronRail();

        initLocationListener();

        receive();
        return super.onStartCommand(intent, flags, startId);
    }

    private void receive() {
        //实例化过滤器；
        IntentFilter intentFilter = new IntentFilter();
        //添加过滤的Action值；
        intentFilter.addAction("ZXWTEST");

        //实例化广播监听器；
        TestPointReceive receive = new TestPointReceive();

        //将广播监听器和过滤器注册在一起；
        registerReceiver(receive, intentFilter);
    }

    private void initLocationListener() {
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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
    }


    private void loadElectronRail() {
        HttpMethods.getInstance().loadAllFence(new Subscriber<List<ElectronRail>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.log("fence fail : " + e.getMessage());
            }

            @Override
            public void onNext(List<ElectronRail> listElectronRail) {
                createRail(listElectronRail);
                LogUtil.log("fence size : " + listElectronRail.size());
            }
        }, SpUtils.getCache(mContext, SpUtils.USER_ID),
                SpUtils.getCache(mContext, SpUtils.KEYCODE));
    }

    private void createRail(List<ElectronRail> listElectronRail) {
                createPolygonRail(listElectronRail);
    }

    private void createPolygonRail(List<ElectronRail> rail) {
        for (int i = 0; i < rail.size(); i = i + 2){
            // 实例化复合围栏
            ComplexRailBean comPlexRailBean = new ComplexRailBean();
            // 生成内围栏对象
            List<Point> insideAreaPoint = generatePointList(rail, i);
            if (insideAreaPoint == null) return;
            comPlexRailBean.setInsideRail(new RailBean(insideAreaPoint));

            // 生成外围栏对象
            List<Point> outsideAreaPoint = generatePointList(rail, i + 1);
            if (outsideAreaPoint == null) return;
            comPlexRailBean.setOutsideRail(new RailBean(outsideAreaPoint));
            // 设置复合围栏的名称
            comPlexRailBean.setName(rail.get(i).getName());
            // 设置围栏ID
            comPlexRailBean.setRailId(rail.get(i).getElectronRailId());
            // 添加到复合围栏列表中
            railList.add(comPlexRailBean);
            LogUtil.log(" 创建复合围栏 : " + comPlexRailBean.toString());
        }
    }

    @Nullable
    private List<Point> generatePointList(List<ElectronRail> rail, int i) {
        if (rail.get(i) == null)
            return null;
        String points = rail.get(i).getPoints();
        if (TextUtils.isEmpty(points))
            return null;
        String[] split = points.split(";");
        if (split.length == 0)
            return null;
        List<Point> areaPoint = new ArrayList<>();
        for (String str : split){
            String[] latLng = str.split(",");
            Double longitude = Double.valueOf(latLng[0]);
            Double latitude = Double.valueOf(latLng[1]);
            areaPoint.add(new Point(latitude, longitude));
        }
        return areaPoint;
    }


    public class TestPointReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            double lat = intent.getDoubleExtra("lat", -1);
            double lng = intent.getDoubleExtra("lng", -1);
            LatLng latLng = new LatLng(lat, lng);
            checkRail(latLng);
        }
    }
}
