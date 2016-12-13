package com.zxw.dispatch_driver.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zxw.data.bean.VersionBean;
import com.zxw.data.http.HttpInterfaces;
import com.zxw.data.http.HttpMethods;
import com.zxw.data.utils.MD5;
import com.zxw.dispatch_driver.Constants;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

import static com.zxw.dispatch_driver.MyApplication.mContext;

/**
 * author：CangJie on 2016/12/13 14:20
 * email：cangjie2016@gmail.com
 */
public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLog.w("onStartCommand");
        checkVersion();
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkVersion() {
        DebugLog.w("checkVersion");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);//设置日期格式
        String keyCode = MD5.MD5Encode(df.format(new Date()));
        HttpMethods.getInstance().checkVersion(keyCode,
                new Subscriber<VersionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        checkVersion();
                    }

                    @Override
                    public void onNext(final VersionBean versionBean) {
                        try {
                            PackageManager pm = mContext.getPackageManager();
                            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), 0);
                            //获取当前的版本
                            int currentCode = packageInfo.versionCode;
                            if (versionBean != null && versionBean.codeNum > currentCode) {
                                download(versionBean.url);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                });

    }

    private void download(String url) {
        Call<ResponseBody> downloadApk = HttpMethods.getInstance().retrofit.create(HttpInterfaces.UpdateVersion.class).getFile(url);
        downloadApk.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断SD卡是否挂载
                        File foder = new File(Environment.getExternalStorageDirectory(), Constants.Path.SECONDPATH + "/");
                        File file = new File(foder, Constants.Path.APKNAME);
                        if (!foder.exists()) {
                            foder.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        bis.close();
                        is.close();
                        install(file.getAbsolutePath());

                    }else {
                        ToastHelper.showToast("请检查你的SD卡", MyApplication.mContext);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void install(String filePath) {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
//        getBaseContext().startActivity(intent);
//        SilentInstallUtil installUtil = new SilentInstallUtil();
//        if(installUtil.install(filePath)){
//            DebugLog.w("install success");
//            boolean delete = new File(filePath).delete();
//            DebugLog.w("delete is "+ delete);
//        }
    }

}
