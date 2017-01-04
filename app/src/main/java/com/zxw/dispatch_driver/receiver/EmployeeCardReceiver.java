package com.zxw.dispatch_driver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zxw.data.bean.Login;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.ui.MainActivity;
import com.zxw.dispatch_driver.utils.ByteToHexUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.BroadcastUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.Date;

import rx.Subscriber;

/**
 * author：CangJie on 2016/12/16 09:46
 * email：cangjie2016@gmail.com
 */
public class EmployeeCardReceiver extends BroadcastReceiver {
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        byte[] rfidData = intent.getByteArrayExtra("RFIDData");
        // 转16进制
        String rfStr = ByteToHexUtil.bytesToHexString(rfidData);
        try {
            String uid = rfStr.substring(4, 34);
            DebugLog.w(uid);
            loginByEmployeeCard(uid);
        } catch (Exception e) {
            DebugLog.w(e.getMessage());
        }
    }

    private void loginByEmployeeCard(String uid) {
        DispatchSource dispatchSource = new DispatchSource();
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        dispatchSource.loginByEmployeeCard(uid, time, new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastHelper.showToast(e.getMessage());
            }

            @Override
            public void onNext(Login loginBean) {
                cacheLoginInfo(loginBean);
                BroadcastUtil.loginIn(loginBean.getName(), loginBean.getCode());
                startMainActivity();
            }
        });
    }

    private void cacheLoginInfo(Login loginBean) {
        SpUtils.setCache(MyApplication.mContext, SpUtils.CODE, loginBean.getCode());
        SpUtils.setCache(MyApplication.mContext, SpUtils.NAME, loginBean.getName());
        SpUtils.setCache(MyApplication.mContext, SpUtils.KEYCODE, loginBean.getKeyCode());
    }

    private void startMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }
}
