package com.zxw.dispatch_driver.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.data.bean.VersionBean;
import com.zxw.data.http.HttpInterfaces;
import com.zxw.data.http.HttpMethods;
import com.zxw.dispatch_driver.Constants;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;


public class WelcomeActivity extends BaseHeadActivity {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_version_name)
    TextView tv_version_name;
    private AlertDialog show;
    private PackageManager pm;
    private int currentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initData();
        hideHeadArea();
        initPermission();
        initView(imageView);
    }

    private void initData() {
        pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            tv_version_name.setText("V " + versionName);
            currentVersion = info.versionCode;
            DebugLog.i("当前版本号:" + currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    public void initView(ImageView view) {
        // 初始化控件
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.4F, 0.9F);
        animationSet.setDuration(400);
        animationSet.addAnimation(alphaAnimation);
        // 监听动画过程
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkVersion();
            }
        });
        view.startAnimation(animationSet);
    }


    private void goMain() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void checkVersion() {
        DebugLog.w("checkVersion");
        long time = System.currentTimeMillis();
        HttpMethods.getInstance().checkVersion(String.valueOf(time),
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
                        if (versionBean != null && versionBean.codeNum > currentVersion) {
                            showLoading();
                            showDialog();
                            download(versionBean.url);
                        } else {
                            goMain();
                        }
                    }
                });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("正在下载安装包");
        builder.setCancelable(false);
        show = builder.show();
    }

    @Override
    protected void onDestroy() {
        if (show != null && show.isShowing()) {
            show.dismiss();
        }
        super.onDestroy();
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

                    } else {
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
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
        finish();
    }
}
