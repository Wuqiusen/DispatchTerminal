package com.zxw.dispatch_driver.utils.file_download_dialog.install_apk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.zxw.dispatch_driver.Constants;

import java.io.File;

/**
 * author MXQ
 * create at 2017/3/10 17:23
 * email: 1299242483@qq.com
 */
public class SetUpAPK {

    public void setUpAPK(Activity activity){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" +
                Constants.Path.SECONDPATH, Constants.Path.APKNAME)), "application/vnd.android.package-archive");
        activity.startActivityForResult(intent, 0);
        activity.finish();
    }

}