package com.zxw.dispatch_driver.trace;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.ElectronRail;
import com.zxw.data.http.HttpMethods;
import com.zxw.data.sp.SpUtils;

import java.util.List;

import rx.Subscriber;

import static com.zxw.dispatch_driver.MyApplication.mContext;
import static com.zxw.dispatch_driver.MyApplication.writeTxtToFile;

/**
 * author：CangJie on 2017/2/24 10:40
 * email：cangjie2016@gmail.com
 */
public class TraceService extends Service {

    private int lineId;
    private long lastEnterOrLeaveTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadElectronRail();
        final int vehicleId = SpUtils.getVehicleId(mContext);
        TraceHelper.getInstance(mContext).setOnEnterOrLeaveFenceListener(new TraceHelper.OnEnterOrLeaveFenceListener() {
            @Override
            public void enterFence(final int fenceId) {
//                if (checkTime()) return;
                traceStatusChange(fenceId + "进站");
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
                        fenceId, vehicleId);
            }

            @Override
            public void leaveFence(int fenceId) {
//                if (checkTime()) return;
                traceStatusChange(fenceId + "出站");
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
                        fenceId, vehicleId);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean checkTime() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnterOrLeaveTime >= 60000){
            lastEnterOrLeaveTime = currentTime;
            return true;
        }
        lastEnterOrLeaveTime = currentTime;
        return false;
    }

    private void traceStatusChange(String str) {
        //保存到本地
        String filePath = Environment.getExternalStorageDirectory() + "/BdYy";
        String fileName = "sendLog.txt";
        writeTxtToFile("str : " + str, filePath, fileName);
    }

    private void loadElectronRail() {
        HttpMethods.getInstance().loadAllFence(new Subscriber<List<ElectronRail>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ElectronRail> listElectronRail) {
//                createBaiDuRail(listElectronRail);
                TraceHelper.getInstance(mContext).initFenceList(listElectronRail);
            }
        }, SpUtils.getCache(mContext, SpUtils.USER_ID),
                SpUtils.getCache(mContext, SpUtils.KEYCODE));
    }

    private void createBaiDuRail(List<ElectronRail> listElectronRail) {
        for (ElectronRail rail : listElectronRail){
            if (TextUtils.isEmpty(rail.getRadius()) || "0".equals(rail.getRadius())){
                createPolygonRail(rail);
            }else{
                createCircleRail(rail);
            }
            // 将服务器围栏信息与百度围栏ID结合在一起.
        }
    }

    private void createCircleRail(ElectronRail rail) {
        TraceHelper.getInstance(mContext).createCircleFence(rail);
    }

    private void createPolygonRail(ElectronRail rail) {
        TraceHelper.getInstance(mContext).createPolygonFence(rail);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TraceHelper.getInstance(mContext).unregister();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        TraceHelper.getInstance(mContext).unregister();
    }
}
