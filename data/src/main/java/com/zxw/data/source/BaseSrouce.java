package com.zxw.data.source;

import com.zxw.data.http.HttpMethods;

/**
 * author：CangJie on 2016/11/4 10:37
 * email：cangjie2016@gmail.com
 */
public class BaseSrouce {

    protected int mPageNo = 1;
    protected int mPageSize = 200;


    protected final HttpMethods mHttpMethods;

    public BaseSrouce(){
        mHttpMethods = HttpMethods.getInstance();
    }
    // 标识码,必填,格式设备码加time
    protected String code(){
        return "code";
    }
    protected String time(){
        return String.valueOf(System.currentTimeMillis());
    }
}
