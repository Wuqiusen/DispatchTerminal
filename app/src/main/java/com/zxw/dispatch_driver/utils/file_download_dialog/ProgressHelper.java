package com.zxw.dispatch_driver.utils.file_download_dialog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * author MXQ
 * create at 2017/3/10 17:20
 * email: 1299242483@qq.com
 */
public class ProgressHelper {
    private static ProgressBean mProgressBean = new ProgressBean();
    private static ProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        final ProgressListener mProgressListener = new ProgressListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                if(mProgressHandler == null){
                    return;
                }
                   /*由0.处的回调，获取: 下载进度*/
                mProgressBean.setBytesRead(progress);
                mProgressBean.setContentLength(total);
                mProgressBean.setDone(done);
                   /*由于在非主线程运行，则利用Handler传递mProgressBean对象至WelcomeActivity*/
                mProgressHandler.sendMessage(mProgressBean);


            }
        };

        /*OkHttpClient.Builder添加网络拦截器*/
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                /*获得ResponseBody*/
                okhttp3.Response mResponse = chain.proceed(chain.request());
                /*重写ResponseBody{0.传入"获取下载进度"接口对象}*/
                return mResponse.newBuilder().body(new ProgressResponseBody(mResponse.body(),mProgressListener))
                        .build();
            }
        });
        return builder;
    }

    public static void setProgressHandler(ProgressHandler progressHandler){
        mProgressHandler = progressHandler;
    }
}