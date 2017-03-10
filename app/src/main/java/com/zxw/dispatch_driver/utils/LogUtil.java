package com.zxw.dispatch_driver.utils;

import android.os.Environment;

import com.zxw.dispatch_driver.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：CangJie on 2017/3/10 14:36
 * email：cangjie2016@gmail.com
 */
public class LogUtil {

    public static void fenceLog(String str){
        String fileName = "FenceLog.txt";
        log(fileName, str);
    }
    public static void uploadLog(String str){
        String fileName = "UploadLog.txt";
        log(fileName, str);
    }

    private static void log(String fileName, String str){
        String filePath = Environment.getExternalStorageDirectory() + "/BdYy/";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(new Date());

        str = "\r\n" + time +"\r\n" + str +"\r\n";
        MyApplication.writeTxtToFile(str, filePath, fileName);
    }

    public static void log(String str){
        String fileName = "main.txt";
        log(fileName, str);
    }

}
