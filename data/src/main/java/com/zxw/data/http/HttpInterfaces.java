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
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author：CangJie on 2016/10/12 15:14
 * email：cangjie2016@gmail.com
 */
public class HttpInterfaces {

    public interface UpdateVersion {
        /**
         * 获取版本信息
         */
        @FormUrlEncoded
        @POST
        Observable<BaseBean<VersionBean>> checkVersion(@Url String url, @Field("time") String time);

        /**
         * 下载安装包
         */
        @GET
        Call<ResponseBody> getFile(@Url String url);
    }

    /**
     * 用户信息
     */
    public interface User {
        /**
         * @param code
         * @param password
         * @param time
         * @param type     1:手机 2：终端机
         * @return
         */
        @FormUrlEncoded
        @POST("phone/driver/manage/login")
        Observable<BaseBean<Login>> login(@Field("code") String code,
                                          @Field("password") String password,
                                          @Field("time") String time,
                                          @Field("type") int type);

        @FormUrlEncoded
        @POST("phone/driver/manage/login/card")
        Observable<BaseBean<Login>> loginByEmployeeCard(@Field("code") String code,
                                                        @Field("time") String time,
                                                        @Field("type") int type
                                                        );

        @FormUrlEncoded
        @POST
        Call<ResponseBody> face(@Url String url,
                                @Field("api_key") String api_key,
                                @Field("api_secret") String api_secret,
                                @Field("image_url1") String image_url1,
                                @Field("image_url2") String image_url2);

        @POST
        Call<ResponseBody> verifyFaceByFile(@Url String url,
                                            @Body RequestBody Body);
    }

    /**
     * 调度
     */
    public interface Dispatch {
        @FormUrlEncoded
        @POST("phone/driver/manage/task/bill/list")
        Observable<BaseBean<List<Receive>>> receiveList(@Field("userId") String userId,
                                                        @Field("keyCode") String keyCode,
                                                        @Field("type") int type,
                                                        @Field("pageNo") int pageNo,
                                                        @Field("pageSize") int pageSize
        );

        @FormUrlEncoded
        @POST("phone/driver/manage/task/bill/deal")
        Observable<BaseBean> receiveOperator(@Field("userId") String userId,
                                             @Field("keyCode") String keyCode,
                                             @Field("type") int type,
                                             @Field("billId") int billId,  //对应接单列表id唯一值
                                             @Field("status") int status  //操作类型,必填,格式：2开始、3异常终止、4正常结束
        );

        @FormUrlEncoded
        @POST("phone/driver/manage/task/schedule/list")
        Observable<BaseBean<List<Journey>>> journeyList(@Field("userId") String userId,
                                                        @Field("keyCode") String keyCode,
                                                        @Field("type") int type,
                                                        @Field("pageNo") int pageNo,
                                                        @Field("pageSize") int pageSize
        );

        @FormUrlEncoded
        @POST("phone/driver/manage/task/schedule/begin")
        Observable<BaseBean> journeyOperator(@Field("userId") String userId,
                                             @Field("keyCode") String keyCode,
                                             @Field("scheduleId") int scheduleId,
                                             @Field("type") int type,
                                             @Field("lngLat") String lngLat
        );

        @FormUrlEncoded
        @POST("phone/driver/manage/task/schedule/complete")
        Observable<BaseBean> journeyEnd(@Field("userId") String userId,
                                        @Field("keyCode") String keyCode,
                                        @Field("scheduleId") int scheduleId,
                                        @Field("type") int type,
                                        @Field("status") int status
        );

        @FormUrlEncoded
        @POST("phone/driver/line/detail/2")
        Observable<BaseBean<LineDetail>> lineDetail(@Field("code") String code,
                                                    @Field("keyCode") String keyCode,
                                                    @Field("lineId") int lineId
        );
    }


    /**
     * 报站模块
     */
    public interface ReportStation {
        // 通过线路获取线路电子围栏
        @FormUrlEncoded
        @POST("phone/driver/manage/line/electronrail/list")
        Observable<BaseBean<List<ElectronRail>>> electronRail(@Field("userId") String userId,
                                                              @Field("keyCode") String keyCode,
                                                              @Field("type") int type,
                                                              @Field("lineId") int lineId
        );

        // 车辆进站
        @FormUrlEncoded
        @POST("phone/driver/manage/task/vehicle/in/station")
        Observable<BaseBean> enterStation(@Field("userId") String userId,
                                                              @Field("keyCode") String keyCode,
                                                              @Field("type") int type,
                                                              @Field("electronRailId") int electronRailId,
                                                              @Field("vehicleId") int vehicleId
        );

        // 车辆出站
        @FormUrlEncoded
        @POST("phone/driver/manage/task/vehicle/out/station")
        Observable<BaseBean> leaveStation(@Field("userId") String userId,
                                          @Field("keyCode") String keyCode,
                                          @Field("type") int type,
                                          @Field("electronRailId") int electronRailId,
                                          @Field("vehicleId") int vehicleId
        );


        // 获取车辆ID
        @FormUrlEncoded
        @POST("phone/driver/manage/device/vehicle/id")
        Observable<BaseBean<Integer>> vehicleId(@Field("userId") String userId,
                                                @Field("keyCode") String keyCode,
                                                @Field("type") int type,
                                                @Field("imei") String imei
        );
        // 获取所有围栏
        @FormUrlEncoded
        @POST("phone/driver/manage/electronrail/all/list")
        Observable<BaseBean<List<ElectronRail>>> loadAllFence(@Field("userId") String userId,
                                                @Field("keyCode") String keyCode,
                                                @Field("type") int type
        );

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
        @POST("phone/dog/line/main/data")
        Observable<BaseBean<List<TbDogLineMain>>> updateDogMain(@Field("code") String code,
                                                                @Field("updateTimeKey") String updateTimeKey,
                                                                @Field("time") String time,
                                                                @Field("pageNo") int pageNo,
                                                                @Field("pageSize") int pageSize
        );

        //
        @FormUrlEncoded
        @POST("phone/dog/line/second/data")
        Observable<BaseBean<List<TbDogLineSecond>>> updateDogSecond(@Field("code") String code,
                                                                    @Field("updateTimeKey") String updateTimeKey,
                                                                    @Field("time") String time,
                                                                    @Field("pageNo") int pageNo,
                                                                    @Field("pageSize") int pageSize
        );
    }

    /**
     * 上传异常报告
     */
    public interface UpLoadLog {
        @FormUrlEncoded
        @POST
        Observable<BaseBean> upLoadLog(@Url String url, @Field("log") String log,
                                       @Field("phone") String phone,
                                       @Field("key") String key);
    }
}
