package com.zxw.dispatch_driver.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;

/**
 * author：CangJie on 2017/1/17 11:03
 * email：cangjie2016@gmail.com
 */
public class PhotoPathReplaceUtil {
    public static File findFile(Context context, String photoPath){
        File file = null;
        String replaceStr = "/storage/sdcard1";
        String[] storagePath = getStoragePath(context);
        DebugLog.w(storagePath.length +"length");
        for(int i =0 ; i<= storagePath.length - 1; i++){
            String newPhotoPath = photoPath.replace(replaceStr, storagePath[i]);
            file = new File(newPhotoPath);
            if (file.exists())
                return file;
        }
        return null;
    }

    private static String[] getStoragePath(Context context) {
        String[] volumePaths = null;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                StorageManager sm = (StorageManager) context
                        .getSystemService(Context.STORAGE_SERVICE);
                try {
                    volumePaths = (String[]) sm.getClass()
                            .getMethod("getVolumePaths", null).invoke(sm, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return volumePaths;
    }
}
