package com.zxw.dispatch_driver;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * author：CangJie on 2016/9/28 14:45
 * email：cangjie2016@gmail.com
 */
public class MyApplication extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID +"=581aeb22");
    }
}
