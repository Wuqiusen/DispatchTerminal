package com.zxw.dispatch_driver.trace;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.zxw.dispatch_driver.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2017/2/28 15:58
 * email：cangjie2016@gmail.com
 */
public class FenceCallBackFilter {

    public List<Location> latLngs = new ArrayList<>();
    public FenceCallBackFilter(){

        //init position
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            if (latLngs.size() >= 6){
                latLngs.remove(0);
            }
            latLngs.add(location);
            DebugLog.w(location.toString());
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
    public boolean isPY(){
        for (int i = 0;i< latLngs.size() - 1;i ++){
            float v = latLngs.get(i).distanceTo(latLngs.get(i + 1));
            if (v >= 100){
                return true;
            }
        }
        return false;
    }
}
