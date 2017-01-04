package com.zxw.dispatch_driver.utils;

import com.google.gson.Gson;
import com.zxw.data.http.HttpInterfaces;
import com.zxw.data.http.HttpMethods;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：CangJie on 2016/12/27 17:01
 * email：cangjie2016@gmail.com
 */
public class VerifyFaceUtil {
    public static final String URL = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    public static final String api_key = "1otuqZmkL5I-W8-qtwGwsAGsGz1Hmc9V";
    public static final String api_secret = "QPRabfBVSC5DHgdMUl27zR0B32QmvY02";
    public static void verifyFace(String image_url1, String image_url2, final VerifyFaceListener listener){
        Call<ResponseBody> verifyFace = HttpMethods.getInstance().retrofit.create(HttpInterfaces.User.class).face(URL, api_key, api_secret, image_url1, image_url2);
        verifyFace.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        DebugLog.w(string);
                        Gson gson = new Gson();
                        CompareResult compareResult = gson.fromJson(string, CompareResult.class);
                        if (compareResult == null){
                            listener.canNotFoundFace();
                            return;
                        }
                        listener.success(compareResult.confidence);
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.error(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }
    public static void verifyFaceByFile(String image_url1, File image_file2, final VerifyFaceListener listener){
        if (image_file2 == null || !image_file2.exists())
            throw new RuntimeException("file is not exists");
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("api_key", api_key)
                .addFormDataPart("api_secret", api_secret)
                .addFormDataPart("image_url1", image_url1)
                .addFormDataPart("image_file2", image_file2.getName(), RequestBody.create(MediaType.parse("image/*"), image_file2))
                .build();
        Call<ResponseBody> verifyFace = HttpMethods.getInstance().retrofit.create(HttpInterfaces.User.class).verifyFaceByFile(URL, requestBody);
        verifyFace.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        DebugLog.w(string);
                        Gson gson = new Gson();
                        CompareResult compareResult = gson.fromJson(string, CompareResult.class);
                        if (compareResult == null){
                            listener.canNotFoundFace();
                            return;
                        }
                        listener.success(compareResult.confidence);
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.error(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }

    public interface VerifyFaceListener{
        void success(float compareValue);
        void canNotFoundFace();
        void error(String errorMessage);
    }

    public class CompareResult{
        float confidence;
    }
}
