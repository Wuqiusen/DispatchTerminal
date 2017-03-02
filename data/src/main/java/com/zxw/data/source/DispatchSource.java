package com.zxw.data.source;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.Journey;
import com.zxw.data.bean.LineDetail;
import com.zxw.data.bean.Login;
import com.zxw.data.bean.Receive;
import com.zxw.data.http.HttpMethods;

import java.util.List;

import rx.Subscriber;

/**
 * author：CangJie on 2016/12/7 16:38
 * email：cangjie2016@gmail.com
 */
public class DispatchSource {
    public static final int TYPE_ACCEPT = 3, TYPE_REJECT = 2;
    public void login(String username, String time, String password, Subscriber<Login> subscriber){
        HttpMethods.getInstance().login(subscriber, username, password, time);
    }
    public void loginByEmployeeCard(String code, String time, Subscriber<Login> subscriber){
        HttpMethods.getInstance().loginByEmployeeCard(subscriber, code, time, 2);
    }
    public void receiveList(String code, String keyCode, int pageNo, int pageSize, Subscriber<BaseBean<List<Receive>>> subscriber){
        HttpMethods.getInstance().receiveList(code, keyCode, pageNo, pageSize, subscriber);
    }
    public void receiveOperator(String userId,String keyCode,int billId, int status, Subscriber<BaseBean> subscriber){
        HttpMethods.getInstance().receiveOperator(userId, keyCode, billId, status, subscriber);
    }
    public void journeyList(String code, String keyCode, int pageNo, int pageSize, Subscriber<BaseBean<List<Journey>>> subscriber){
        HttpMethods.getInstance().journeyList(code, keyCode, pageNo, pageSize, subscriber);
    }
    public void journeyOperator(String code, String keyCode,  int scheduleId, String lngLat, Subscriber<BaseBean> subscriber){
        HttpMethods.getInstance().journeyOperator(code, keyCode, scheduleId, lngLat, subscriber);
    }
    public void journeyEnd(String code, String keyCode,  int scheduleId, int status, Subscriber<BaseBean> subscriber){
        HttpMethods.getInstance().journeyEnd(code, keyCode, scheduleId, status, subscriber);
    }
    public void lineDetail(String code, String keyCode, int lineId, Subscriber<LineDetail> subscriber){
        HttpMethods.getInstance().lineDetail(code, keyCode, lineId, subscriber);
    }
}
