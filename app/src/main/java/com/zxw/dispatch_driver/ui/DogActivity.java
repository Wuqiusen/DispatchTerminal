package com.zxw.dispatch_driver.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.DogPresenter;
import com.zxw.dispatch_driver.presenter.view.DogView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DogActivity extends PresenterActivity<DogPresenter> implements DogView {
    @Bind(R.id.mv_dog)
    MapView mMapView;
    @Bind(R.id.et_mock_speed)
    EditText mEtMockSpeed;
    private AMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        showTitle("电子狗测试");
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();
        mMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.mipmap.off_station_point))
                        .draggable(true));
                presenter.drive(latLng.latitude, latLng.longitude);
            }
        });
        presenter.loadDogByLineId(365);
    }


    @Override
    protected DogPresenter createPresenter() {
        return new DogPresenter(this, this);
    }

    @Override
    public void drawMarker(List<TbDogLineMain> tbDogLineMains) {
        TbDogLineSecond tbDogLineSecond = tbDogLineMains.get(0).getSecondList().get(0);
        LatLng latLng1 = new LatLng(tbDogLineSecond.getLat(), tbDogLineSecond.getLng());
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng1));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        }
        for(TbDogLineMain main : tbDogLineMains){
            for(TbDogLineSecond second : main.getSecondList()){
                LatLng latLng = new LatLng(second.getLat(), second.getLng());
                mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.mipmap.on_station_point))
                        .draggable(true));
            }
        }
    }
    public int obtainSpeed(){
        return Integer.valueOf(mEtMockSpeed.getText().toString().trim());
    }
}
