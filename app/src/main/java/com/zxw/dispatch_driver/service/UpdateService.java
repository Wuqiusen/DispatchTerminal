package com.zxw.dispatch_driver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.SilentInstallUtil;

import java.io.File;

/**
 * author：CangJie on 2016/12/13 14:20
 * email：cangjie2016@gmail.com
 */
public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLog.w("onStartCommand");
        download();
        return super.onStartCommand(intent, flags, startId);
    }

    private void download() {

//        install();
    }

    private void install(String filePath) {
        SilentInstallUtil installUtil = new SilentInstallUtil();
        if(installUtil.install(filePath)){
            DebugLog.w("install success");
            boolean delete = new File(filePath).delete();
            DebugLog.w("delete is "+ delete);
        }
    }

}
