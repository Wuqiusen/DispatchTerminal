package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.TestPresenter;
import com.zxw.dispatch_driver.presenter.view.TestView;
import com.zxw.dispatch_driver.service.VerifyFaceService;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

public class TestActivity extends PresenterActivity<TestPresenter> implements TestView {
    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }
    public void takePhoto(View view){
        Intent intent = new Intent(this, VerifyFaceService.class);
        String comparePhotoUrl = "http://wx4.sinaimg.cn/mw690/bdbb6334gy1fdbyb2i2iij20zk0k00tu.jpg";
        intent.putExtra("photoPath", comparePhotoUrl);
        startService(intent);
    }

}
