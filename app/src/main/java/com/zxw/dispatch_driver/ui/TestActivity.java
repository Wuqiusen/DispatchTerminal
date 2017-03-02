package com.zxw.dispatch_driver.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseHeadActivity {

    private ImageView iv;
    private TextView tv;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        SDKInitializer.initialize(getApplicationContext());

        mMapView = (MapView) findViewById(R.id.mv);
        //定义多边形的五个顶点
        mBaiduMap = mMapView.getMap();
        String cache = SpUtils.getCache(mContext, SpUtils.MAP_POINT);
        DebugLog.w(cache);
        String[] split = cache.split("\\|");
        for (String str : split) {
            List<LatLng> pts = new ArrayList<LatLng>();
            String[] points = str.split(";");
            for (String point : points) {
                String[] position = point.split(",");
                LatLng pt1 = new LatLng(Double.valueOf(position[1]), Double.valueOf(position[0]));
                pts.add(pt1);
            }
//构建用户绘制多边形的Option对象
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(pts)
                    .stroke(new Stroke(5, 0xAA00FF00))
                    .fillColor(0xAAFFFF00);
//在地图上添加多边形Option，用于显示
            mBaiduMap.addOverlay(polygonOption);

            //设定中心点坐标
            LatLng cenpt = new LatLng(22.6468630000, 114.0168560000);
//定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    //要移动的点
                    .target(cenpt)
                    //放大地图到20倍
                    .zoom(20)
                    .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }

        //init position
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        DebugLog.w(latLng.toString());
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latLng);
        LatLng desLatLng = converter.convert();
        DotOptions color = new DotOptions().center(desLatLng).color(Color.RED);
        mBaiduMap.addOverlay(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
