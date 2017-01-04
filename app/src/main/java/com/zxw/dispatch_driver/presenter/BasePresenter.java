package com.zxw.dispatch_driver.presenter;

import com.zxw.data.sp.SpUtils;
import com.zxw.data.utils.MD5;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.presenter.view.BaseView;

import java.util.Date;

/**
 * author：CangJie on 2016/8/18 14:38
 * email：cangjie2016@gmail.com
 */
public class BasePresenter<V extends BaseView> {
    public V mvpView;
    protected int httpCount;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
    }

    protected String keyCode(){
        return SpUtils.getCache(MyApplication.mContext, SpUtils.KEYCODE);
    }
//    protected String userName(){
//        return SpUtils.getCache(MyApplication.mContext, SpUtils.USERPHONE);
//    }
    protected String code(){
        return SpUtils.getCache(MyApplication.mContext, SpUtils.CODE);
    }
    protected String timestamp(){
        Date date = new Date();
        return String.valueOf(date.getTime());
    }
    protected String md5(String str){
        return MD5.MD5Encode(str);
    }

    protected void invalidKeyCode(int returnCode){
        if(returnCode == 510){
            SpUtils.logOut(MyApplication.mContext);
            mvpView.invalidKeyCode();
        }
    }
}
