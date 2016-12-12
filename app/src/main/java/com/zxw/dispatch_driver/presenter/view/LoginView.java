package com.zxw.dispatch_driver.presenter.view;

/**
 * author：CangJie on 2016/12/7 16:35
 * email：cangjie2016@gmail.com
 */
public interface LoginView extends BaseView {
    void loginSuccess();

    void loginFail(String info);
}
