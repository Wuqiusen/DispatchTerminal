package com.zxw.dispatch_driver.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.trace.Point;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class MapTestActivity extends BaseHeadActivity {

    private ImageView iv;
    private TextView tv;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private List<Point> areas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        initData();
        SDKInitializer.initialize(getApplicationContext());

        mMapView = (MapView) findViewById(R.id.mv);
        //定义多边形的五个顶点
        mBaiduMap = mMapView.getMap();
        List<LatLng> pts = new ArrayList<LatLng>();
        for (Point area : areas) {
            LatLng pt1 = new LatLng(area.getPx(), area.getPy());
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
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                String s = rayCasting(latLng);
//                DebugLog.w(latLng.toString() + " , " + s);
//                ToastHelper.showToast(s);

//                Intent intent = new Intent();
//                intent.setAction("ZXWTEST");
//                intent.putExtra("lat", latLng.latitude);
//                intent.putExtra("lng", latLng.longitude);
//                sendBroadcast(intent);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        LatLng latLng = new LatLng(22.6468400000, 114.2187170000);
        String s = rayCasting(latLng);
        DebugLog.w(latLng.toString() + " , " + s);
        ToastHelper.showToast(s);
    }

    private void initData() {
        Point a1 = new Point(22.6472320000, 114.0163270000);
        Point a2 = new Point(22.6481910000, 114.0189950000);
        Point a3 = new Point(22.6467150000, 114.0197590000);
        Point a4 = new Point(22.6464900000, 114.0193000000);
        Point a5 = new Point(22.6468400000, 114.0187170000);
//        Point a6=new Point(22.6470570000,114.0184650000);
        Point a7 = new Point(22.6461890000, 114.0187520000);
        Point a8 = new Point(22.6455310000, 114.0175220000);
        areas = new ArrayList<Point>();
        areas.add(a1);
        areas.add(a2);
        areas.add(a3);
        areas.add(a4);
        areas.add(a5);
        areas.add(a7);
        areas.add(a8);
    }

    /// 射线发判断点是否在多边形内部
    /// p 待判断的点。格式{x:X坐标,y:Y坐标}
    /// {Array} poly 多边形定点，数组成员格式相同
    private String rayCasting(LatLng p) {
        double px = p.latitude;
        double py = p.longitude;
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
