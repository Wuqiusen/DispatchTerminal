package com.zxw.dispatch_driver.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author：CangJie on 2016/9/28 14:11
 * email：cangjie2016@gmail.com
 */
public class SpUtils {
    private static SharedPreferences sp;
    private final static String CACHE_FILE_NAME = "eastSmartDispatch";

    public final static String CODE = "code";
    public final static String KEYCODE = "keycode";
    public final static String NAME = "name";

    private static void initSp(Context mContext, String fileName) {
        sp = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }
    public static void setCache(Context mContext,String key,String value){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static String getCache(Context mContext, String key){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getString(key,null);
    }
    public static void logOut(Context mContext){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(CODE).remove(KEYCODE).commit();
    }
}
