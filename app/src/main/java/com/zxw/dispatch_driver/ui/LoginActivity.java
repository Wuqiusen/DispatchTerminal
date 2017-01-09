package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.LoginPresenter;
import com.zxw.dispatch_driver.presenter.view.LoginView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends PresenterActivity<LoginPresenter> implements LoginView {
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_forget_password)
    TextView tv_forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        if(SpUtils.isLogin(MyApplication.mContext)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void initView() {
         showTitle("智能运调司机端");
         tv_forget_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @OnClick(R.id.btn_login)
    public void verifyAccount(){
        String userName = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            disPlay("请输入用户名");
            return;
        }
        if(TextUtils.isEmpty(password)){
            disPlay("请输入密码");
            return;
        }
        mPresenter.verifyAccount(userName, password);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFail(String info) {
        disPlay(info);
    }

    @OnClick(R.id.tv_forget_password)
    public void forgetPassword(){

    }
}
