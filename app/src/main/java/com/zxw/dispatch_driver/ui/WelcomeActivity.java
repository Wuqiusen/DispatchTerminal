package com.zxw.dispatch_driver.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxw.data.bean.VersionBean;
import com.zxw.data.http.HttpMethods;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.ToastHelper;
import com.zxw.dispatch_driver.utils.file_download_dialog.install_apk.DownLoadAndSetUpAPK;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;


public class WelcomeActivity extends BaseHeadActivity {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_version_name)
    TextView tv_version_name;
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
        // 运行时权限处理：检查并申请写入和读取的权限
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
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2F, 0.9F);
        animationSet.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        // 监听动画过程
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                 checkVersion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

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
                        ToastHelper.showToast("正在获取版本信息!!请稍候!!");
                        checkVersion();
                    }

                    @Override
                    public void onNext(final VersionBean versionBean) {
                        if (versionBean != null && versionBean.codeNum > currentVersion) {
                            showLoading();
                            downLoadDialog(versionBean);
                        }else {
                            goMain();
                        }
                    }
                });
    }

    private void downLoadDialog(final VersionBean versionBean) {
        hideLoading();
        new DownLoadAndSetUpAPK().DownLoadAndSetUpAPK(WelcomeActivity.this, versionBean.url, new DownLoadAndSetUpAPK.LoadFailure() {
            @Override
            public void onLoadFailureListener() {
                ToastHelper.showToast("更新失败", mContext);
            }
        });


    }

}
