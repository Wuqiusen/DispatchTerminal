package com.zxw.data.http;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateLineBean;
import com.zxw.data.bean.UpdateLineStationBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.bean.UpdateServiceWordBean;
import com.zxw.data.bean.UpdateStationBean;
import com.zxw.data.bean.UpdateVoiceCompoundBean;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;

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
        // 6.获取需要更新服务用语的数据
        @FormUrlEncoded
        @POST("phone/station/report/service/words/data")
        Observable<BaseBean<List<UpdateServiceWordBean>>> updateServiceWord(@Field("code") String code,
                                                                            @Field("updateTimeKey") String updateTimeKey,
                                                                            @Field("time") String time,
                                                                            @Field("pageNo") int pageNo,
                                                                            @Field("pageSize") int pageSize
        );
        // 7.获取需要更新语音合成模板的数据
        @FormUrlEncoded
        @POST("phone/station/report/voice/template/data")
        Observable<BaseBean<List<UpdateVoiceCompoundBean>>> updateVoiceCompound(@Field("code") String code,
                                                                                @Field("updateTimeKey") String updateTimeKey,
                                                                                @Field("time") String time,
                                                                                @Field("pageNo") int pageNo,
                                                                                @Field("pageSize") int pageSize
        );
        //
        @FormUrlEncoded
        @POST("phone/station/report/voice/template/data")
        Observable<BaseBean<List<TbDogLineMain>>> updateDogMain(@Field("code") String code,
                                                                @Field("updateTimeKey") String updateTimeKey,
                                                                @Field("time") String time,
                                                                @Field("pageNo") int pageNo,
                                                                @Field("pageSize") int pageSize
        );
        //
        @FormUrlEncoded
        @POST("phone/station/report/voice/template/data")
        Observable<BaseBean<List<TbDogLineSecond>>> updatedogSecond(@Field("code") String code,
                                                                    @Field("updateTimeKey") String updateTimeKey,
                                                                    @Field("time") String time,
                                                                    @Field("pageNo") int pageNo,
                                                                    @Field("pageSize") int pageSize
        );
    }
}
