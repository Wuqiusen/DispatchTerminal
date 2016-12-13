package com.zxw.dispatch_driver.ui.base;

import android.content.Intent;
import android.os.Bundle;

import com.zxw.dispatch_driver.ui.LoginActivity;

/**
 * author：CangJie on 2016/8/18 16:44
 * email：cangjie2016@gmail.com
 */
public abstract class PresenterActivity<P> extends BaseHeadActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    public void invalidKeyCode(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
