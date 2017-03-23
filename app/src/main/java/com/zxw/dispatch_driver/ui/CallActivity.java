package com.zxw.dispatch_driver.ui;

import android.os.Bundle;

import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.CallPresenter;
import com.zxw.dispatch_driver.presenter.view.CallView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

public class CallActivity extends PresenterActivity<CallPresenter> implements CallView {
    @Override
    protected CallPresenter createPresenter() {
        return new CallPresenter(this);
    }

// text git fork
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }
}
