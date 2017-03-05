package com.zxw.dispatch_driver.trace;

import android.text.TextUtils;

import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2017/2/20 17:59
 * email：cangjie2016@gmail.com
 */
// 发生围栏触发事件时, 应该先修改触发围栏的标识, 再去检查是否有符合判断条件(内外围栏皆被触发),
// 如果有, 则所有围栏的标识进行复位,且返回符合判断条件的服务器围栏ID
public class FenceIdManager {
    private static FenceIdManager fenceIdManager;
    private List<OutsideInsideFenceBean> mFenceList;

    public static FenceIdManager getInstance() {
        if(fenceIdManager == null)
            fenceIdManager = new FenceIdManager();
        return fenceIdManager;
    }

    private FenceIdManager() {
        mFenceList = new ArrayList<>();
    }



    // 先修改触发围栏的标识, 内部会匹配百度围栏的ID值来识别到底是内围栏还是外围栏, 检查围栏列表是否有符合判断条件的
    public OutsideInsideFenceBean queryServiceFenceByBaiDuFenceId(int fenceId){
        for (OutsideInsideFenceBean fenceIdPair : mFenceList){
            fenceIdPair.alertTouchFlagByFenceId(fenceId);
            //符合条件
            if(fenceIdPair.isTouchBothFence()){
                return fenceIdPair;
            }
        }
        return null;
    }

    public void restoreFlag() {
        for (OutsideInsideFenceBean fenceIdPair : mFenceList) {
            fenceIdPair.restoreFlag();
        }
    }

    public void addFenceIdPair(int currentRailId, int fenceId, int currentPointer) {
        //储存围栏ID 在下一次打开APP时进行删除.
        cacheFenceId(fenceId);
        //如果已经有就返回
        OutsideInsideFenceBean createdFence = isCreatedFence(currentRailId);
        // 如果没有就新建一个, 把对应的服务器围栏ID 传入构造方法. 并将其加入到list中
        if (createdFence == null){
            createdFence = new OutsideInsideFenceBean(currentRailId);
            mFenceList.add(createdFence);
        }

        /**
         *  判断当前指针 是否为 0 2 4 如果是 那就是内围栏;  \
            ***重点***
         * 按道理来说,list第一个就是内围栏, 第二个是外围栏, 但是在TraceHelper createFenceList()中, 取值后就直接自增1, 所以导致下标偏移一位
         * 即原来的  0 1 2 3 4  在这里会变成 1 2 3 4 5  而 1 3 5 是内围栏.
         * 所以此处判断条件  取余为0即 为外圈. 不为0 即为内圈
         */
        if (currentPointer % 2 != 0){
            createdFence.setInsideFenceId(fenceId);
        }else{
            // 否则都是外围栏.
            createdFence.setOutsideFenceId(fenceId);
        }
    }

    private void cacheFenceId(int fenceId) {
        String cache = SpUtils.getCache(MyApplication.mContext, SpUtils.NEW_FENCE_LIST);
        if(TextUtils.isEmpty(cache)){
            cache = fenceId + ";";
        }else{
            cache += fenceId + ";";
        }
        SpUtils.setCache(MyApplication.mContext, SpUtils.NEW_FENCE_LIST, cache);
    }

    // 是否已创建这个围栏容器
    private OutsideInsideFenceBean isCreatedFence(int currentRailId){
        for (OutsideInsideFenceBean fence : mFenceList){
            if (fence.getServiceFenceId() == currentRailId)
                return fence;
        }
        return null;
    }

    public List<OutsideInsideFenceBean> getFenceList() {
        return mFenceList;
    }
}
