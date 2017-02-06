package com.zxw.dispatch_driver.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.concox.gmctrl.gmCtrl;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.service.VerifyFaceService;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.PhotoPathReplaceUtil;
import com.zxw.dispatch_driver.utils.SpeakUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;
import com.zxw.dispatch_driver.utils.VerifyFaceUtil;

import java.io.File;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class TestActivity extends BaseHeadActivity {

    private SpeakUtil mSpeakUtil;
    private ImageView iv;
    private TextView tv;
    private PhotoReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        iv = (ImageView) findViewById(R.id.iv_test);
        tv = (TextView) findViewById(R.id.tv_test);
        mSpeakUtil = SpeakUtil.getInstance(mContext);
        mSpeakUtil.init();

        Observable.just(1).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer + "num";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                DebugLog.w(s);
            }
        });
    }

    public void inside(View view){
        gmCtrl.GM_Speaker_switch(0);
    }
    public void outside(View view){
        gmCtrl.GM_Speaker_switch(1);

    }

    String notices[] = new String[]{"下雨路滑，请慢行！！", "金贵银贵生命最贵，千好万好平安最好。", "人车互让，得到的是和谐；争道抢行，缺失的是道德。",
    "车祸的惊人相似之处在于后来者比前人更“勇敢”。", "红灯是安全的警铃，绿灯是文明的标志。"};
    int current;
    public void notice(View view){
        String noticeIntent = "android.intent.action.NOTIFICATION_DISPALY";
        Intent intent = new Intent(noticeIntent);
        if(current == notices.length - 1)
            current = 0;
        current++;
        String notice = notices[current];
        intent.putExtra("deviceNotice", notice);
        sendBroadcast(intent);
        mSpeakUtil.playText(notice);
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

    public final static String ACTION_USB_CAMERA_CAPTURE_START = "android.intent.concox.action.ACTION_USB_CAMERA_CAPTURE_START";
    public final static String ACTION = "android.intent.action.PHOTO_PATH";

    public void photo(View view){
        Intent intent = new Intent(this, VerifyFaceService.class);
        intent.putExtra("photoPath", picUrl);
        startService(intent);
    }

    public String picUrl;

    public String comparePhotoUrl = "http://ww1.sinaimg.cn/mw690/bdbb6334gw1faf4b3g7fjj20qo0zkgsk.jpg";
    public String filePath;
    public void compare (View view){
        if(TextUtils.isEmpty(filePath))
            return;
        File file = new File(filePath);
        VerifyFaceUtil.verifyFaceByFile(comparePhotoUrl, file, new VerifyFaceUtil.VerifyFaceListener() {
            @Override
            public void success(float compareValue) {
                if (compareValue >= 70){
                    DebugLog.w("success");
                    tv.setText("success");
                }else{
                    DebugLog.w("failed");
                    tv.setText("failed");
                }
            }

            @Override
            public void canNotFoundFace() {
                DebugLog.w("canNotFoundFace");
                tv.setText("canNotFoundFace");
            }

            @Override
            public void error(String errorMessage) {
                DebugLog.w(errorMessage);
                tv.setText(errorMessage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myReceiver);
    }

    public class PhotoReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            filePath = intent.getStringExtra("file");
            DebugLog.w(filePath);
            tv.setText(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            iv.setImageBitmap(bitmap);
        }
    }

    public void pic1(View view){
        picUrl = "http://ww2.sinaimg.cn/mw690/bdbb6334gw1fbp0m2bcmej20zk0k040b.jpg";
        ToastHelper.showToast("已选用侧脸照");
    }
    public void pic2(View view){
        picUrl = "http://ww4.sinaimg.cn/mw690/bdbb6334gw1fbp0uwjjraj20qo0zk3zy.jpg";
        ToastHelper.showToast("已选用正脸照");
    }

    public void file(View view){

        initPermission();
        String photoPath = "/storage/sdcard1/DVRMEDIA/PHOTO/2017_01_16/2017_01_16_16_58_45.jpg";
        File file = PhotoPathReplaceUtil.findFile(mContext, photoPath);
        DebugLog.w(file.length()+" size");
    }

}
