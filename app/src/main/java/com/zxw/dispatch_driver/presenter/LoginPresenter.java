package com.zxw.dispatch_driver.presenter;

import com.zxw.data.bean.Login;
import com.zxw.data.source.DispatchSource;
import com.zxw.data.sp.SpUtils;
import com.zxw.data.utils.MD5;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.presenter.view.LoginView;

import java.util.Date;

import rx.Subscriber;

/**
 * author：CangJie on 2016/12/7 16:35
 * email：cangjie2016@gmail.com
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private DispatchSource mSource;
    public LoginPresenter(LoginView mvpView) {
        super(mvpView);
        this.mSource = new DispatchSource();
    }

    public void verifyAccount(String userName, String password) {
        String time = String.valueOf(new Date().getTime());
        String md5Password = MD5.MD5Encode(MD5.MD5Encode(password) + time);
        mSource.login(userName, String.valueOf(time), md5Password, new Subscriber<Login>() {
            @Override
            public void onCompleted() {
                mvpView.loginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mvpView.loginFail(e.getMessage());
            }

            @Override
            public void onNext(Login loginBean) {
                SpUtils.setCache(MyApplication.mContext, SpUtils.CODE, loginBean.getCode());
                SpUtils.setCache(MyApplication.mContext, SpUtils.NAME, loginBean.getName());
                SpUtils.setCache(MyApplication.mContext, SpUtils.KEYCODE, loginBean.getKeyCode());
            }
        });
    }
}
