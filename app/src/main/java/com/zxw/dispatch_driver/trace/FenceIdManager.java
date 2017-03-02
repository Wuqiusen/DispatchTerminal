package com.zxw.dispatch_driver.trace;

import com.zxw.dispatch_driver.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2017/2/20 17:59
 * email：cangjie2016@gmail.com
 */
public class FenceIdManager {
    private static FenceIdManager fenceIdManager;
    private List<FenceIdPair> mFenceIdList;

    public static FenceIdManager getInstance() {
        if(fenceIdManager == null)
            fenceIdManager = new FenceIdManager();
        return fenceIdManager;
    }

    private FenceIdManager() {
        mFenceIdList = new ArrayList<>();
    }

    public int queryServiceFenceByBaiDuFenceId(int fenceId){
        for (FenceIdPair fenceIdPair : mFenceIdList){
            if (fenceIdPair.BaiDuFenceId == fenceId)
                return fenceIdPair.ServiceFenceId;
        }
        return -1;
    }

    public void addFenceIdPair(FenceIdPair pair){
        mFenceIdList.add(pair);
        DebugLog.w(pair.toString());
    }

    public void clearFenceIdPair(){
        mFenceIdList.clear();
    }

    public List<FenceIdPair> getFenceIdList(){
        return mFenceIdList;
    }

}
