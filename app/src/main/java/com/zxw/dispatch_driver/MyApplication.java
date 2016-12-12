package com.zxw.dispatch_driver;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.jpush.android.api.JPushInterface;

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

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
