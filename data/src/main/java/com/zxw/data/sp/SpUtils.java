package com.zxw.data.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

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
    public final static String FIRST = "first";
    public final static String TABLE_LINE = "table_line";
    public final static String TABLE_STATION = "table_station";
    public final static String TABLE_LINE_STATION = "table_line_station";
    public final static String TABLE_REPORT_POINT = "table_report_point";
    public final static String TABLE_SERVICE_WORD = "table_service_word";
    public final static String TABLE_VOICE_COMPOUND = "table_voice_compound";
    public final static String TABLE_DOG_MAIN = "table_dog_main";
    public final static String TABLE_DOG_SECOND = "table_dog_second";

    public final static String CURRENT_LINE_ID = "current_line_id";
    public final static String CURRENT_LINE_NAME = "current_line_name";
    public final static String IS_SET_ALIAS = "is_set_alias";//是否已经设置别名

    private static void initSp(Context mContext, String fileName) {
        sp = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }
    public static boolean isFirst(Context mContext){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        return sp.getBoolean(FIRST, true);
    }
    public static void inited(Context mContext){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(FIRST, false);
        edit.commit();
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
    // 数据库更新时间记录方法
    public static long getTableUpdateTime(Context context, String key){
        if(sp == null){
            initSp(context, CACHE_FILE_NAME);
        }
        return sp.getLong(key, -1);
    }
    public static void setCache(Context mContext,String key,Long value){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }
    public static void deleteLineHistory(Context mContext){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(CURRENT_LINE_ID).remove(CURRENT_LINE_NAME).commit();
    }

    public static boolean isLogin(Context mContext){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        String userPhone = sp.getString(CODE, "");
        String userId = sp.getString(NAME, "");
        String keycode = sp.getString(KEYCODE, "");
        if(TextUtils.isEmpty(userPhone)){
            return false;
        }
        if(TextUtils.isEmpty(userId)){
            return false;
        }
        if(TextUtils.isEmpty(keycode)){
            return false;
        }
        return true;
    }


    //是否已经设置别名参数
    public static void isSetAlias(Context context){
        if (sp == null){
            initSp(context, CACHE_FILE_NAME);
        }
        if (!sp.contains(IS_SET_ALIAS)){
            setAlias(context, false);
        }
    }
    //是否已经设置别名
    public static boolean getIsSetAlias(Context context){
        if(sp == null){
            initSp(context, CACHE_FILE_NAME);
        }
        return sp.getBoolean(IS_SET_ALIAS, Boolean.parseBoolean(""));
    }
    //设置别名参数
    public static void setAlias(Context mContext, boolean isSetAlias){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(IS_SET_ALIAS, isSetAlias);
        edit.commit();
    }
}
