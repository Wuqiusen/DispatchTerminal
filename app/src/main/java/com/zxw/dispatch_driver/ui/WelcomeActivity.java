package com.zxw.dispatch_driver.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.zxw.data.source.DogMainSource;
import com.zxw.data.source.DogSecondSource;
import com.zxw.data.source.LineSource;
import com.zxw.data.source.LineStationSource;
import com.zxw.data.source.ReportPointSource;
import com.zxw.data.source.ServiceWordSource;
import com.zxw.data.source.StationSource;
import com.zxw.data.source.VoiceCompoundSource;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.InitializeAssertFileUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WelcomeActivity extends BaseHeadActivity implements LineSource.OnUpdateLineTableFinishListener, StationSource.OnUpdateStationFinishListener, LineStationSource.OnUpdateLineStationFinishListener, ReportPointSource.OnUpdateReportPointFinishListener, ServiceWordSource.OnUpdateServiceWordTableFinishListener, VoiceCompoundSource.OnUpdateVoiceCompoundTableFinishListener, DogMainSource.OnUpdateDogMainTableFinishListener, DogSecondSource.OnUpdateDogSecondTableFinishListener {
    @Bind(R.id.imageView)
    ImageView imageView;
    private boolean isUpdateLineFinish, isUpdateStationFinish, isUpdateLineStationFinish, isUpdateReportPointFinish, isUpdateServiceWordFinish, isUpdateVoiceCompoundFinish, isUpdateDogMain, isUpdateDogSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        hideHeadArea();
        initPermission();
        initView(imageView);
        InitializeAssertFileUtil.initialize(MyApplication.mContext);
//        update();
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


    private void update() {
        LineSource source = new LineSource(MyApplication.mContext);
        source.setOnUpdateLineTableFinishListener(this);
        source.loadUpdateLineTableData();
        StationSource stationSource = new StationSource(MyApplication.mContext);
        stationSource.setOnUpdateStationFinishListener(this);
        stationSource.loadUpdateStationTableData();
        LineStationSource lineStationSource = new LineStationSource(MyApplication.mContext);
        lineStationSource.setOnUpdateLineStationFinishListener(this);
        lineStationSource.loadUpdateLineStationTableData();
        ReportPointSource reportPointSource = new ReportPointSource(MyApplication.mContext);
        reportPointSource.setOnUpdateReportPointFinishListener(this);
        reportPointSource.loadUpdateReportPointSource();
        ServiceWordSource serviceWordSource = new ServiceWordSource(MyApplication.mContext);
        serviceWordSource.setOnUpdateServiceWordTableFinishListener(this);
        serviceWordSource.loadUpdateServiceWordTableData();
        VoiceCompoundSource voiceCompoundSource = new VoiceCompoundSource(MyApplication.mContext);
        voiceCompoundSource.setOnUpdateVoiceCompoundTableFinishListener(this);
        voiceCompoundSource.loadUpdateVoiceCompoundTableData();

        DogMainSource dogMainSource = new DogMainSource(MyApplication.mContext);
        dogMainSource.setOnUpdateDogMainTableFinishListener(this);
        dogMainSource.loadUpdateDogMainTableData();
        DogSecondSource dogSecondSource = new DogSecondSource(MyApplication.mContext);
        dogSecondSource.setOnUpdateDogSecondTableFinishListener(this);
        dogSecondSource.loadUpdateDogSecondTableData();

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
                update();
            }
        });
        view.startAnimation(animationSet);
    }

    @Override
    public void onUpdateLineTableFinish() {
        isUpdateLineFinish = true;
        goMain();
    }

    @Override
    public void onUpdateStationFinish() {
        isUpdateStationFinish = true;
        goMain();
    }

    @Override
    public void onUpdateLineStationFinishListener() {
        isUpdateLineStationFinish = true;
        goMain();
    }

    @Override
    public void onUpdateReportPointFinishListener() {
        isUpdateReportPointFinish = true;
        goMain();
    }

    private void goMain() {
        if (isUpdateLineFinish && isUpdateLineStationFinish && isUpdateReportPointFinish && isUpdateStationFinish && isUpdateServiceWordFinish && isUpdateVoiceCompoundFinish && isUpdateDogMain && isUpdateDogSecond) {
            hideLoading();
//            String lineId = SpUtils.getCache(mContext, SpUtils.CURRENT_LINE_ID);
//            if(TextUtils.isEmpty(lineId)){
//                startActivity(new Intent(this, SelectLineActivity.class));
//            }else{
//                Intent intent = new Intent(mContext, AutoReportActivity.class);
//                intent.putExtra("lineId", lineId);
//                mContext.startActivity(intent);
//            }
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onUpdateServiceWordTableFinish() {
        isUpdateServiceWordFinish = true;
        goMain();
    }

    @Override
    public void onUpdateVoiceCompoundTableFinish() {
        isUpdateVoiceCompoundFinish = true;
        goMain();
    }

    @Override
    public void onUpdateDogMainTableFinish() {
        isUpdateDogMain = true;
        goMain();
    }

    @Override
    public void onUpdateDogSecondTableFinish() {
        isUpdateDogSecond = true;
        goMain();
    }

}
