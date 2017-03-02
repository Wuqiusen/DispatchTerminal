package com.zxw.dispatch_driver.trace;

/**
 * author：CangJie on 2017/2/20 18:14
 * email：cangjie2016@gmail.com
 */
public class FenceIdPair{
    public int BaiDuFenceId;
    public int ServiceFenceId;
    public FenceIdPair(int baiDuFenceId, int serviceFenceId) {
        BaiDuFenceId = baiDuFenceId;
        ServiceFenceId = serviceFenceId;
    }

    @Override
    public String toString() {
        return "FenceIdPair{" +
                "BaiDuFenceId=" + BaiDuFenceId +
                ", ServiceFenceId=" + ServiceFenceId +
                '}';
    }
}