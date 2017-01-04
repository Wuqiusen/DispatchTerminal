package com.zxw.dispatch_driver.utils;

import android.content.Intent;

import com.zxw.dispatch_driver.MyApplication;

/**
 * author：CangJie on 2016/12/26 14:36
 * email：cangjie2016@gmail.com
 */
public class BroadcastUtil {
    private static final String FLAG_LOGOUT = "android.intent.action.DRIVER_LOGOUT";
    private static final String FLAG_LOGIN = "android.intent.action.DRIVER_LOGIN";
    private static final String FLAG_NOTICATION = "android.intent.action.NOTIFICATION_DISPALY";
    public static void loginOut(){
        Intent intent = new Intent(FLAG_LOGOUT);
        MyApplication.mContext.sendBroadcast(intent);
    }
    public static void loginIn(String name, String code){
        Intent intent = new Intent(FLAG_LOGIN);
        intent.putExtra("name", name);
        intent.putExtra("num", code);
        MyApplication.mContext.sendBroadcast(intent);
    }

    public static void notice(String notice){
        Intent intent = new Intent(FLAG_NOTICATION);
        intent.putExtra("deviceNotice", notice);
        MyApplication.mContext.sendBroadcast(intent);
    }
}
