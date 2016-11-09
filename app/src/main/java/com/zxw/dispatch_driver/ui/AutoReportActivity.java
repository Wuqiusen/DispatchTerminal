package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zxw.data.db.bean.InnerReportPointBean;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.AutoReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;
import com.zxw.dispatch_driver.presenter.AutoReportPresenter;
import com.zxw.dispatch_driver.presenter.view.AutoReportView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AutoReportActivity extends PresenterActivity<AutoReportPresenter> implements AutoReportView {
    private List<Marker> mStationMarkers = new ArrayList<>();
    private List<Marker> mTrackMarkers = new ArrayList<>();

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.rv_service_word)
    RecyclerView mRecyclerViewServiceWord;
    private AMap mMap;
    private long mLineId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String lineName = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_NAME);
        showTitle(lineName + " 自动报站");
        showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.deleteLineHistory(MyApplication.mContext);
                finish();
            }
        });
        showRightImageButton(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutoReportActivity.this, ManualReportActivity.class);
                intent.putExtra("lineId", mLineId);
                startActivity(intent);
            }
        });

        ButterKnife.bind(this);
        com.amap.api.maps2d.MapView mapView = (com.amap.api.maps2d.MapView) findViewById(R.id.mv);
        mapView.onCreate(savedInstanceState);
        mMap = mapView.getMap();
        mMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                LatLng marker1 = new LatLng(22.59877,114.31347);    //设置中心点和缩放比例
                if (mMap != null) {
                    mMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                }
                DebugLog.i("地图加载完成");
            }
        });
        mMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                drawDriverTrackMarker(latLng);
                presenter.drive(latLng.latitude, latLng.longitude);
            }
        });

        mLineId = Long.valueOf(SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID));
        presenter.loadStationsName(mLineId);
        presenter.loadReportPotins(mLineId);
        presenter.loadServiceWord();
    }

    @Override
    public void setStationListAdapter(AutoReportStationAdapter stationReportAdapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setAdapter(stationReportAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));
    }

    @Override
    public void setServiceWordAdapter(ServiceWordAdapter serviceWordAdapter) {
        mRecyclerViewServiceWord.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerViewServiceWord.setAdapter(serviceWordAdapter);
    }

    @Override
    protected AutoReportPresenter createPresenter() {
        return new AutoReportPresenter(this, this);
    }

    @Override
    public void drawStationMarker(List<InnerReportPointBean> lineStationReportBeen) {
        for (InnerReportPointBean bean : lineStationReportBeen){
            Double lat = bean.getLat();
            Double lng = bean.getLng();
            LatLng latLng = new LatLng(lat, lng);
            Marker marker;
            if (bean.getType() == 1){
                marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(latLng).title(bean.getStationId() + " type:" + bean.getType())
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.mipmap.on_station_point))
                        .draggable(true));
            }else{
                marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(latLng).title(bean.getStationId() + " type:" + bean.getType())
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.mipmap.end_point))
                        .draggable(true));
            }
            mStationMarkers.add(marker);
        }
    }

    private void drawDriverTrackMarker(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.off_station_point))
                .draggable(true));
        mTrackMarkers.add(marker);
    }
}
