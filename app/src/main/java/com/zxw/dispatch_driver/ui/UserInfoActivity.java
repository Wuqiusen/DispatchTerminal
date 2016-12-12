package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.UserInfoPresenter;
import com.zxw.dispatch_driver.presenter.view.UserInfoView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends PresenterActivity<UserInfoPresenter> implements UserInfoView {
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.tv_user_id)
    TextView tv_user_id;
    @Bind(R.id.btn_logout)
    TextView btn_logout;
    @Bind(R.id.btn_login)
    TextView btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        if(SpUtils.isLogin(MyApplication.mContext)){
            showUserInfo();
        }else{
            hasNotLogin();
        }
    }

    private void hasNotLogin() {
//        btn_login.setVisibility(View.VISIBLE);
        login();
    }

    private void showUserInfo() {
        String name = SpUtils.getCache(MyApplication.mContext, SpUtils.NAME);
        String code = SpUtils.getCache(MyApplication.mContext, SpUtils.CODE);
        tv_username.setText(getResources().getString(R.string.username, name));
        tv_user_id.setText(getResources().getString(R.string.code, code));
        btn_logout.setVisibility(View.VISIBLE);
        tv_username.setVisibility(View.VISIBLE);
        tv_user_id.setVisibility(View.VISIBLE);
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @OnClick(R.id.btn_logout)
    public void logout(){
        SpUtils.logOut(MyApplication.mContext);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    @OnClick(R.id.btn_login)
    public void login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
