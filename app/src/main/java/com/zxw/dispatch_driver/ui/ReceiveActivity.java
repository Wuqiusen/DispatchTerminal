package com.zxw.dispatch_driver.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.zxw.data.bean.LineDetail;
import com.zxw.data.bean.Receive;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.ReceivePresenter;
import com.zxw.dispatch_driver.presenter.view.ReceiveView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveActivity extends PresenterActivity<ReceivePresenter> implements ReceiveView, AMap.OnMapLoadedListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter {
    @Bind(R.id.mv)
    MapView mMapView;
    private AMap aMap;
    private List<Marker> mMarkers = new ArrayList<>();
    private Marker currentMarker;
    @Bind(R.id.tv_line_code)
    TextView tv_line_code;
    @Bind(R.id.tv_company)
    TextView tv_company;
    @Bind(R.id.tv_org)
    TextView tv_org;
    @Bind(R.id.tv_prompt)
    TextView tv_prompt;
    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.btn_refuse)
    Button btn_refuse;
    private Receive mReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ButterKnife.bind(this);
        hideHeadArea();
        mMapView.onCreate(savedInstanceState);
        initMap();
        initView();
        mPresenter.loadLineDetail();
    }

    private void initView() {
        switch(mReceive.getStatus()){
            case 0:
                btn_refuse.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
                tv_prompt.setVisibility(View.GONE);
                break;
            case 1:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_1));
                break;
            case 2:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_2));
                break;
            case 3:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_3));
                break;
        }
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(){
        mPresenter.confirm();
    }

    @OnClick(R.id.btn_refuse)
    public void refuse(){
        mPresenter.refuse();
    }

    private void initMap() {
        aMap = mMapView.getMap();
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        if (aMap != null) {
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setOnMapLoadedListener(this);
            aMap.setOnMarkerClickListener(this);
            aMap.setInfoWindowAdapter(this);
        }
    }

    @Override
    protected ReceivePresenter createPresenter() {
        mReceive = (Receive)getIntent().getSerializableExtra("receive");
        return new ReceivePresenter(this, mReceive);
    }

    @Override
    public void onMapLoaded() {
        LatLng marker1 = new LatLng(22.543096, 114.057865);    //设置中心点和缩放比例
        if (aMap != null) {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        }
    }

    @Override
    public void drawStation(List<ReceivePresenter.Station> stations) {
        Marker marker = null;
        for (ReceivePresenter.Station station : stations){
            LatLng latLng = new LatLng(station.lat, station.lng);
            marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(latLng).title(station.stationName)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.on_station_point))
                    .draggable(true));
            mMarkers.add(marker);
        }
    }

    @Override
    public void operatorReceiveOrderSuccess() {
        finish();
    }

    @Override
    public void baseInfo(LineDetail lineDetail) {
        tv_line_code.setText(lineDetail.getLine().getLineCode());
        tv_company.setText(lineDetail.getLine().getCompanyName());
        tv_org.setText(lineDetail.getLine().getOrgName());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        currentMarker = marker;
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
            R.layout.custom_info_window, null);
        TextView tv_custom_info_window = (TextView) infoWindow.findViewById(R.id.tv_custom_info_window);
        tv_custom_info_window.setText(marker.getTitle());
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
