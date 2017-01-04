package com.zxw.dispatch_driver.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.VerifyFaceUtil;

import java.io.File;

/**
 * author：CangJie on 2016/12/27 17:46
 * email：cangjie2016@gmail.com
 */
public class VerifyFaceService extends Service {
    public String comparePhotoUrl = "http://ww1.sinaimg.cn/mw690/bdbb6334gw1faf4b3g7fjj20qo0zkgsk.jpg";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File file = new File(Environment.getExternalStorageDirectory(), "verifyPhoto.jpg");
        verifyFaceByFile(comparePhotoUrl, file);
        return super.onStartCommand(intent, flags, startId);
    }

    private void verifyFaceByFile(String path1, File file) {
        VerifyFaceUtil.verifyFaceByFile(path1, file, new VerifyFaceUtil.VerifyFaceListener() {
            @Override
            public void success(float compareValue) {
                if (compareValue >= 70){
                    DebugLog.w("success");
                }else{
                    DebugLog.w("failed");
                }
            }

            @Override
            public void canNotFoundFace() {
                DebugLog.w("canNotFoundFace");
            }

            @Override
            public void error(String errorMessage) {
                DebugLog.w(errorMessage);
            }
        });
    }
}
