package com.zxw.dispatch_driver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.zxw.data.bean.Login;
import com.zxw.data.http.HttpMethods;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.data.utils.Base64;
import com.zxw.data.utils.DESPlus;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.trace.TraceService;
import com.zxw.dispatch_driver.ui.WelcomeActivity;
import com.zxw.dispatch_driver.utils.BroadcastUtil;
import com.zxw.dispatch_driver.utils.ByteToHexUtil;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.LogUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;
import com.zxw.dispatch_driver.utils.VoiceController;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscriber;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * author：CangJie on 2016/12/16 09:46
 * email：cangjie2016@gmail.com
 */
public class EmployeeCardReceiver extends BroadcastReceiver {
    private Context mContext;

    private String uid;
    private String befUid;
    private Long befTimeStamp;
    private boolean loginOut;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        loginOut = intent.getBooleanExtra("loginOut",false);
        if (loginOut) {
            befUid = null;
            befTimeStamp = null;
            loginOut = false;
            return;
        }
        beep();
        byte[] rfidData = intent.getByteArrayExtra("RFIDData");
        // 转16进制
        String rfStr = ByteToHexUtil.bytesToHexString(rfidData);
        DebugLog.e("rfStr"+rfStr);
        try {
            // uid
            uid = rfStr.substring(4, 34);
            if (!TextUtils.isEmpty(befUid) && uid.equals(befUid)){
                Date date = new Date(System.currentTimeMillis());
                long time = date.getTime();
                long l = time - befTimeStamp;
                long min = (long)(l/1000/60);
                if (min < 30){
                    ToastHelper.showToast("30分钟内，不允许重复登录",mContext);
                }else{
                    loginByEmployeeCard(uid);
                }
            }else{
                loginByEmployeeCard(uid);
            }
        } catch (Exception e) {
            DebugLog.w(e.getMessage());
        }
    }

    private void beep() {
        VoiceController.inside();
        MyApplication.soundPool.play(1,1, 1, 0, 0, 1);
    }

    private void loginByEmployeeCard(final String uid) {
        DispatchSource dispatchSource = new DispatchSource();
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        String code = uid + time;
        try {
            code = new DESPlus().encrypt(Base64.encode(code.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dispatchSource.loginByEmployeeCard(code, time, new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastHelper.showToast(e.getMessage());
                LogUtil.log("login fail : " + e.getMessage());
            }

            @Override
            public void onNext(Login loginBean) {
                befUid = uid;
                Date date = new Date(System.currentTimeMillis());
                befTimeStamp = date.getTime();

                cacheLoginInfo(loginBean);
                BroadcastUtil.loginIn(loginBean.getName(), loginBean.getCode());
                startWelcomeActivity();
                loadVehicleId(loginBean.getUserId(), loginBean.getKeyCode());
                startTraceService();
                LogUtil.log("userId : " + loginBean.getUserId());
            }
        });
    }

    private void startTraceService() {
        Intent intent = new Intent(mContext, TraceService.class);
        mContext.startService(intent);
    }

    public void loadVehicleId(String userId, String keyCode) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        HttpMethods.getInstance().vehicleId(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.log("vehicleId fail : " + e.getMessage());
            }

            @Override
            public void onNext(Integer vehicleId) {
                SpUtils.setVehicleId(mContext, vehicleId);
                LogUtil.log("vehicleId : " + vehicleId);
            }
        }, userId, keyCode, 2, imei);
    }

    private void cacheLoginInfo(Login loginBean) {
        SpUtils.setCache(MyApplication.mContext, SpUtils.USER_ID, loginBean.getUserId());
        SpUtils.setCache(MyApplication.mContext, SpUtils.CODE, loginBean.getCode());
        SpUtils.setCache(MyApplication.mContext, SpUtils.NAME, loginBean.getName());
        SpUtils.setCache(MyApplication.mContext, SpUtils.KEYCODE, loginBean.getKeyCode());
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(mContext, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }
}
