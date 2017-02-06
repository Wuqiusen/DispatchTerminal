package com.zxw.dispatch_driver.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.PhotoPathReplaceUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;
import com.zxw.dispatch_driver.utils.VerifyFaceUtil;

import java.io.File;

/**
 * author：CangJie on 2016/12/27 17:46
 * email：cangjie2016@gmail.com
 */
public class VerifyFaceService extends Service {
    public String comparePhotoUrl = "http://ww2.sinaimg.cn/mw690/bdbb6334gw1fbp0m2bcmej20zk0k040b.jpg";

    public PhotoReceiver myReceiver;

    public final static String ACTION_USB_CAMERA_CAPTURE_START = "android.intent.concox.action.ACTION_USB_CAMERA_CAPTURE_START";
    public final static String ACTION = "android.intent.action.PHOTO_PATH";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLog.w("onStartCommand");
        ToastHelper.showToast("正在计算");
        comparePhotoUrl = intent.getStringExtra("photoPath");
        myReceiver = new PhotoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        registerReceiver(myReceiver, filter);
        sendBroadcast(new Intent(ACTION_USB_CAMERA_CAPTURE_START));

        return super.onStartCommand(intent, flags, startId);
    }

    private void verifyFaceByFile(String path1, String filePath) {
        File file = PhotoPathReplaceUtil.findFile(MyApplication.mContext, filePath);
        VerifyFaceUtil.verifyFaceByFile(path1, file, new VerifyFaceUtil.VerifyFaceListener() {
            @Override
            public void success(float compareValue) {
                if (compareValue >= 70){
                    ToastHelper.showToast("success compareValue : " + compareValue);
                }else{
                    ToastHelper.showToast("failed compareValue : " + compareValue);
                }
            }

            @Override
            public void canNotFoundFace() {
                DebugLog.w("canNotFoundFace");
                ToastHelper.showToast("canNotFoundFace");
            }

            @Override
            public void error(String errorMessage) {
                DebugLog.w(errorMessage);
                ToastHelper.showToast(errorMessage);
            }
        });
    }

    public class PhotoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String filePath = intent.getStringExtra("file");
            DebugLog.w("PhotoReceiver" + filePath);
            verifyFaceByFile(comparePhotoUrl, filePath);
        }
    }
}
