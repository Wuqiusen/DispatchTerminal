package com.zxw.data.http;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.ElectronRail;
import com.zxw.data.bean.Journey;
import com.zxw.data.bean.LineDetail;
import com.zxw.data.bean.Login;
import com.zxw.data.bean.Receive;
import com.zxw.data.bean.UpdateLineBean;
import com.zxw.data.bean.UpdateLineStationBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.bean.UpdateServiceWordBean;
import com.zxw.data.bean.UpdateStationBean;
import com.zxw.data.bean.UpdateVoiceCompoundBean;
import com.zxw.data.bean.VersionBean;
import com.zxw.data.db.bean.IllegalityBean;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;

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
    public static final String BASE_URL = "http://120.77.48.103:8080/yd_driver_app/";
//    public static final String BASE_URL = "http://192.168.0.166:8080/yd_driver_app/";
    public Retrofit retrofit = RetrofitSetting.getInstance();
    public static final int TYPE_DEVICES = 2;


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

    public void login(Subscriber<Login> subscriber, String code, String password, String time){
        HttpInterfaces.User user = retrofit.create(HttpInterfaces.User.class);
        Observable<Login> observable = user.login(code, password, time, 2).map(new HttpResultFunc<Login>());
        toSubscribe(observable, subscriber);
    }

    public void loginByEmployeeCard(Subscriber<Login> subscriber, String code, String time, int type){
        HttpInterfaces.User user = retrofit.create(HttpInterfaces.User.class);
        Observable<Login> observable = user.loginByEmployeeCard(code, time, type).map(new HttpResultFunc<Login>());
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
    public void serviceWordUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateServiceWordBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateServiceWordBean>>> observable = reportStation.updateServiceWord(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void voiceCompoundUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<UpdateVoiceCompoundBean>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<UpdateVoiceCompoundBean>>> observable = reportStation.updateVoiceCompound(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void dogMainUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<TbDogLineMain>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<TbDogLineMain>>> observable = reportStation.updateDogMain(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }
    public void dogSecondUpdate(String code, String updateTimeKey, String time, int pageNo, int pageSize, Subscriber<BaseBean<List<TbDogLineSecond>>> subscriber){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean<List<TbDogLineSecond>>> observable = reportStation.updateDogSecond(code, updateTimeKey, time, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }

    /**
     * 调度
     */
    public void receiveList(String code, String keyCode, int pageNo, int pageSize, Subscriber<BaseBean<List<Receive>>> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<BaseBean<List<Receive>>> observable = dispatch.receiveList(code, keyCode, 2, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }

    public void receiveOperator(String userId,String keyCode, int billId, int status, Subscriber<BaseBean> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<BaseBean> observable = dispatch.receiveOperator(userId, keyCode, TYPE_DEVICES, billId, status);
        toSubscribe(observable, subscriber);
    }
    public void journeyList(String code, String keyCode, int pageNo, int pageSize, Subscriber<BaseBean<List<Journey>>> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<BaseBean<List<Journey>>> observable = dispatch.journeyList(code, keyCode,TYPE_DEVICES, pageNo, pageSize);
        toSubscribe(observable, subscriber);
    }

    public void journeyOperator(String userId, String keyCode, int scheduleId, String lngLat, Subscriber<BaseBean> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<BaseBean> observable = dispatch.journeyOperator(userId, keyCode, scheduleId, TYPE_DEVICES, lngLat);
        toSubscribe(observable, subscriber);
    }

    public void journeyEnd(String userId, String keyCode, int scheduleId, int status, Subscriber<BaseBean> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<BaseBean> observable = dispatch.journeyEnd(userId, keyCode, scheduleId, TYPE_DEVICES, status);
        toSubscribe(observable, subscriber);
    }

    public void lineDetail(String code, String keyCode, int lineId, Subscriber<LineDetail> subscriber){
        HttpInterfaces.Dispatch dispatch = retrofit.create(HttpInterfaces.Dispatch.class);
        Observable<LineDetail> observable = dispatch.lineDetail(code, keyCode, lineId).map(new HttpResultFunc<LineDetail>());
        toSubscribe(observable, subscriber);
    }

    public void checkVersion(String keyCode, Subscriber<VersionBean> subscriber) {
        HttpInterfaces.UpdateVersion updateVersion = retrofit.create(HttpInterfaces.UpdateVersion.class);
        Observable<VersionBean> observable = updateVersion.checkVersion("http://120.77.48.103:8080/yd_driver_app/phone/driver/manage/app/new/version", keyCode)
                .map(new HttpResultFunc<VersionBean>());
        toSubscribe(observable, subscriber);
    }

    public void edogIllegality(IllegalityBean illegalityBean, Subscriber<BaseBean> subscriber){

    }

    //上传异常日志
    public void upLoadLog(Subscriber<BaseBean> subscriber,String log, String phone, String key){
        HttpInterfaces.UpLoadLog upLoadLog = retrofit.create(HttpInterfaces.UpLoadLog.class);
        Observable<BaseBean> observable = upLoadLog.upLoadLog("http://120.24.252.195:7002/app_ebus/upload/phone/error/log",log, phone, key);
        toSubscribe(observable, subscriber);
    }

    // 根据线路ID获取电子围栏
    public void electronRail(Subscriber<List<ElectronRail>> subscriber, String userId, String keyCode, int lineId){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<List<ElectronRail>> observable = reportStation.electronRail(userId, keyCode, TYPE_DEVICES, lineId).map(new HttpResultFunc<List<ElectronRail>>());
        toSubscribe(observable, subscriber);
    }
    // 获取全部电子围栏
    public void loadAllFence(Subscriber<List<ElectronRail>> subscriber, String userId, String keyCode){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<List<ElectronRail>> observable = reportStation.loadAllFence(userId, keyCode, TYPE_DEVICES).map(new HttpResultFunc<List<ElectronRail>>());
        toSubscribe(observable, subscriber);
    }

    // 车辆进站
    public void enterStation(Subscriber<BaseBean> subscriber, String userId, String keyCode, int electronRailId, int vehicleId){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean> observable = reportStation.enterStation(userId, keyCode, TYPE_DEVICES, electronRailId, vehicleId);
        toSubscribe(observable, subscriber);
    }
    // 车辆进站
    public void leaveStation(Subscriber<BaseBean> subscriber, String userId, String keyCode, int electronRailId, int vehicleId){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<BaseBean> observable = reportStation.leaveStation(userId, keyCode, TYPE_DEVICES, electronRailId, vehicleId);
        toSubscribe(observable, subscriber);
    }

    // 获取车辆ID
    public void vehicleId(Subscriber<Integer> subscriber, String userId, String keyCode, int type, String imei){
        HttpInterfaces.ReportStation reportStation = retrofit.create(HttpInterfaces.ReportStation.class);
        Observable<Integer> observable = reportStation.vehicleId(userId, keyCode, type, imei).map(new HttpResultFunc<Integer>());
        toSubscribe(observable, subscriber);
    }
}
