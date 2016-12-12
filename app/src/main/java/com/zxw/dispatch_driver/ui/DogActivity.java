package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.DogPresenter;
import com.zxw.dispatch_driver.presenter.view.DogView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;
import com.zxw.dispatch_driver.utils.DebugLog;

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
        showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.deleteLineHistory(MyApplication.mContext);
                finish();
            }
        });
        String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
        if(TextUtils.isEmpty(lineId)){
            Intent intent = new Intent(mContext, SelectLineActivity.class);
            intent.putExtra("dog_start", true);
            startActivity(intent);

            finish();
        }else{
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
                    mPresenter.drive(latLng.latitude, latLng.longitude);
                    DebugLog.w(latLng.latitude+ "," + latLng.longitude);
                }
            });
            mPresenter.loadDogByLineId(Integer.valueOf(lineId));
        }
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
