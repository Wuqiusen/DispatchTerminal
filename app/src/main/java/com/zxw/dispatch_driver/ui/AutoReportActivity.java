package com.zxw.dispatch_driver.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zxw.data.db.bean.InnerReportPointBean;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.Constants;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.AutoReportStationAdapter;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;
import com.zxw.dispatch_driver.presenter.AutoReportPresenter;
import com.zxw.dispatch_driver.presenter.view.AutoReportView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.ToastHelper;
import com.zxw.dispatch_driver.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutoReportActivity extends PresenterActivity<AutoReportPresenter> implements AutoReportView {
    private List<Marker> mStationMarkers = new ArrayList<>();
    private List<Marker> mTrackMarkers = new ArrayList<>();

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.rv_service_word)
    RecyclerView mRecyclerViewServiceWord;
    @Bind(R.id.btn_rengong)
    Button btn_rengong;

    @Bind(R.id.ll_container)
    LinearLayout ll_container;

    private AMap mMap;
    private long mLineId;
    private LatLngReceiver myLatLngReceiver;
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//        hideHeadArea();
//        String lineName = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_NAME);
        showTitle("自动报站");
        hideHeadArea();
        showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SpUtils.deleteLineHistory(MyApplication.mContext);
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
        mapView = (MapView) findViewById(R.id.mv);
        mapView.onCreate(savedInstanceState);
        mMap = mapView.getMap();
        mMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                LatLng marker1 = new LatLng(22.640081712773213,114.0111847705186);    //设置中心点和缩放比例
                if (mMap != null) {
                    mMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                }
                DebugLog.i("地图加载完成");
            }
        });
//        mMap.setOnMapClickListener(new AMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                drawDriverTrackMarker(latLng);
//                mPresenter.drive(latLng.latitude, latLng.longitude);
//            }
//        });

        String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
        if (TextUtils.isEmpty(lineId)){
            startActivity(new Intent(this, SelectLineActivity.class));
            finish();
            return;
        }
        mLineId = Long.valueOf(lineId);
        mPresenter.loadStationsName(mLineId);
        mPresenter.loadReportPotins(mLineId);
        mPresenter.loadServiceWord();

        myLatLngReceiver = new LatLngReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.RECEIVER_GPS);
        registerReceiver(myLatLngReceiver, filter);

    }

    @OnClick(R.id.btn_rengong)
    public void rengong(){
        Intent intent = new Intent(AutoReportActivity.this, ManualReportActivity.class);
        intent.putExtra("lineId", mLineId);
        startActivity(intent);
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
        mRecyclerViewServiceWord.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
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

    @Override
    public void scrollView(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    private void drawDriverTrackMarker(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.off_station_point))
                .draggable(true));
        mTrackMarkers.add(marker);
    }
    public class LatLngReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.RECEIVER_GPS.equals(intent.getAction())){
                double lat = intent.getDoubleExtra("lat", -1d);
                double lng = intent.getDoubleExtra("lng", -1d);
                LatLng latLng = new LatLng(lat, lng);
                drawDriverTrackMarker(latLng);
                mPresenter.drive(lat, lng);
                ToastHelper.showToast(lat+ "," + lng);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myLatLngReceiver != null)
            unregisterReceiver(myLatLngReceiver);
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private boolean isShowMap;
    @OnClick(R.id.btn_map)
    public void switchView(){
        if(isShowMap){
            mapView.setVisibility(View.GONE);
            ll_container.setVisibility(View.VISIBLE);
        }else{
            mapView.setVisibility(View.VISIBLE);
            ll_container.setVisibility(View.GONE);
        }
        isShowMap = !isShowMap;
    }
}
