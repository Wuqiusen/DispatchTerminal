package com.zxw.dispatch_driver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.trace.TraceService;
import com.zxw.dispatch_driver.utils.BroadcastUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.LogUtil;

/**
 * author：CangJie on 2016/12/26 17:31
 * email：cangjie2016@gmail.com
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String name = SpUtils.getCache(context, SpUtils.NAME);
        String code = SpUtils.getCache(context, SpUtils.CODE);
        DebugLog.w("name" + name);
        DebugLog.w(code);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(code))
            BroadcastUtil.loginIn(name, code);
        startTraceService();
    }

    private void startTraceService() {
        LogUtil.log("开机启动Service");
        Intent intent = new Intent(mContext, TraceService.class);
        mContext.startService(intent);
    }
}
