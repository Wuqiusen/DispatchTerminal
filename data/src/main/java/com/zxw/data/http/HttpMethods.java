package com.zxw.data.http;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateLineBean;
import com.zxw.data.bean.UpdateLineStationBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.bean.UpdateStationBean;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author：CangJie on 2016/10/12 15:23
 * email：cangjie2016@gmail.com
 */
public class HttpMethods {
    public static final String BASE_URL = "http://192.168.0.93:8080/yd_app/";
    public Retrofit retrofit = RetrofitSetting.getInstance();

    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<BaseBean<T>, T> {
        @Override
        public T call(BaseBean<T> httpResult) {
            if (httpResult.returnCode == 505) {
                throw new ApiException(httpResult.returnCode ,httpResult.returnInfo);
            }
            return httpResult.returnData;
        }
    }

    public void login(Subscriber subscriber, String code, String password, String time){
        HttpInterfaces.User user = retrofit.create(HttpInterfaces.User.class);
        Observable observable = user.login(code, password, time).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    public void lineUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateLineBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateLineBean>>> observable = reportStation.updateLine(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void stationUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateStationBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateStationBean>>> observable = reportStation.updateStation(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void lineStationUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateLineStationBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateLineStationBean>>> observable = reportStation.updateLineStation(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void ReportPointUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateReportPointBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateReportPointBean>>> observable = reportStation.updateReportPoint(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
}
