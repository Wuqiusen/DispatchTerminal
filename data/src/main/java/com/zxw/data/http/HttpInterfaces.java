package com.zxw.data.http;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateLineBean;
import com.zxw.data.bean.UpdateLineStationBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.bean.UpdateStationBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author：CangJie on 2016/10/12 15:14
 * email：cangjie2016@gmail.com
 */
public class HttpInterfaces {
    /**
     * 用户信息
     */
    public interface User {
        @FormUrlEncoded
        @POST("phone/visitor/login")
        Observable<BaseBean> login(@Field("code") String code,
                                   @Field("password") String password,
                                   @Field("time") String time);
    }

    /**
     * 报站模块
     */
    public interface ReportStation {
        @FormUrlEncoded
        @POST("phone/station/report/line/data")
        Observable<BaseBean<List<UpdateLineBean>>> updateLine(@Field("code") String code,
                                                              @Field("updateTimeKey") String updateTimeKey,
                                                              @Field("time") String time,
                                                              @Field("pageNo") int pageNo,
                                                              @Field("pageSize") int pageSize
        );
        @FormUrlEncoded
        @POST("phone/station/report/station/data")
        Observable<BaseBean<List<UpdateStationBean>>> updateStation(@Field("code") String code,
                                                              @Field("updateTimeKey") String updateTimeKey,
                                                              @Field("time") String time,
                                                              @Field("pageNo") int pageNo,
                                                              @Field("pageSize") int pageSize
        );
        // 4.获取需要更新的线路站点基础信息
        @FormUrlEncoded
        @POST("phone/station/report/line/station/data")
        Observable<BaseBean<List<UpdateLineStationBean>>> updateLineStation(@Field("code") String code,
                                                                      @Field("updateTimeKey") String updateTimeKey,
                                                                      @Field("time") String time,
                                                                      @Field("pageNo") int pageNo,
                                                                      @Field("pageSize") int pageSize
        );

        // 4.获取需要更新的线路站点报站点信息
        @FormUrlEncoded
        @POST("phone/station/report/line/station/point/data")
        Observable<BaseBean<List<UpdateReportPointBean>>> updateReportPoint(@Field("code") String code,
                                                                      @Field("updateTimeKey") String updateTimeKey,
                                                                      @Field("time") String time,
                                                                      @Field("pageNo") int pageNo,
                                                                      @Field("pageSize") int pageSize
        );
    }
}
