package com.zxw.dispatch_driver.edog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * author：CangJie on 2017/2/8 14:42
 * email：cangjie2016@gmail.com
 */
public class EDogIllegalityService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
