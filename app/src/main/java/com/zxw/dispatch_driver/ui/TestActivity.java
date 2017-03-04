package com.zxw.dispatch_driver.ui;

import android.os.Bundle;

import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.TestPresenter;
import com.zxw.dispatch_driver.presenter.view.TestView;
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

}
