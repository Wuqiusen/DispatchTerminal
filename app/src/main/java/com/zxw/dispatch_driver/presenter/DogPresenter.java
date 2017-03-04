package com.zxw.dispatch_driver.presenter;

import com.zxw.data.dao.DogMainDao;
import com.zxw.data.dao.DogSecondDao;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.presenter.view.DogView;
import com.zxw.dispatch_driver.ui.DogActivity;
import com.zxw.dispatch_driver.utils.CalculateDistanceUtil;
import com.zxw.dispatch_driver.utils.SpeakUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.util.List;

import static com.zxw.dispatch_driver.presenter.AutoReportPresenter.RADIUS;

/**
 * author：CangJie on 2016/11/8 14:55
 * email：cangjie2016@gmail.com
 */
public class DogPresenter extends BasePresenter<DogView> {

    private final DogMainDao mDogMainDao;
    private final DogSecondDao mDogSecondDao;
    private final SpeakUtil mSpeakUtil;
    private List<TbDogLineMain> mDogLineMains;
    private double lastLat, lastLng;
    private long lastSinglePointPosition, lastCompoundPointPosition;
    private int mockSpeed = -1;
    private boolean isOverspeedRoad;
    private DogActivity mActivity;


    public DogPresenter(DogView mvpView, DogActivity activity) {
        super(mvpView);
        mDogMainDao = new DogMainDao(MyApplication.mContext);
        mDogSecondDao = new DogSecondDao(MyApplication.mContext);
        this.mActivity = activity;

        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
//        DogSecondDao dogSecondDao = new DogSecondDao(MyApplication.mContext);
//        dogSecondDao.mockData();
    }

    public void loadDogByLineId(int lineId){
        mDogLineMains = mDogMainDao.queryByLineID(lineId);
        if (mDogLineMains == null || mDogLineMains.size() == 0)
            return;
        mvpView.drawMarker(mDogLineMains);
    }
    public void drive(double lat, double lng){
        checkNearPoint(lat, lng);
        lastLat = lat;
        lastLng = lng;
    }

    private void checkNearPoint(double lat, double lng) {
        for(TbDogLineMain main : mDogLineMains){
            for(TbDogLineSecond second : main.getSecondList()){
//                int i = main.getSecondList().
                if(CalculateDistanceUtil.isInCircle(lat, lng, RADIUS, second.getLat(), second.getLng())){
                    // 车辆在判断条件附近
                    if(isSingleTypeEvent(main)){
                        onSinglePoint(main, second);
                    }else{
                        onCompoundPoint(main, second);
                    }
                }
            }
        }
    }

    private void onCompoundPoint(TbDogLineMain main, TbDogLineSecond second) {
        // 判断不是刚才报过的点, 防止多次报告
        if(lastCompoundPointPosition != second.getId()){
            if(isCompare(main)){
                // 1需要比较车速 才启动语音事件
                if(isOverSpeed(main)){
                    //当前值大于或等于比较值 则报告
//                    ToastHelper.showToast("比较后播放语音id: " + main.getVoiceId());
                    voice(main.getVoiceContent());
                    // 判断是否需要报告后台

                    if(isCommit(main)){
                        ToastHelper.showToast("报告后台 " + main.getId() +" 驾驶员违反这条规定");
                    }
                }
            }else{
                //直接播放
//                ToastHelper.showToast("直接播放 播放语音id: " + main.getVoiceId());
                voice(main.getVoiceContent());
            }
            lastCompoundPointPosition = second.getId();
        }
    }

    private void onSinglePoint(TbDogLineMain main, TbDogLineSecond second) {
        // 判断不是刚才报过的点, 防止多次报告
        if(lastSinglePointPosition != second.getId()){
            if(isCompare(main)){
                // 1需要比较车速 才启动语音事件
                if(isOverSpeed(main)){
                    //当前值大于或等于比较值 则报告
//                    ToastHelper.showToast("比较后播放语音id: " + main.getVoiceId());
                    voice(main.getVoiceContent());
                    // 判断是否需要报告后台

                    if(isCommit(main)){
                        ToastHelper.showToast("报告后台 " + main.getId() +" 驾驶员违反这条规定");
                    }
                }
            }else{
                //直接播放
//                ToastHelper.showToast("直接播放 播放语音id: " + main.getVoiceId());
                voice(main.getVoiceContent());
            }
            lastSinglePointPosition = second.getId();
        }
    }

    private void voice(String voiceContent) {
        mSpeakUtil.playText(voiceContent);
    }

    private boolean isCommit(TbDogLineMain main){
        return main.getIsCommit() == 1;
    }

    private boolean isOverSpeed(TbDogLineMain main){
        mockSpeed = mActivity.obtainSpeed();
        return main.getCompareValue() <= mockSpeed;
    }

    private boolean isCompare(TbDogLineMain main){
        return main.getIsCompare() == 1;
    }

    private boolean isSingleTypeEvent(TbDogLineMain main) {
        // 判断是否为多点组合线事件
        if (main.getType() == 1){
            //单点事件
            return true;
        }else if(main.getType() == 2){
            //多点事件
            return false;
        }
        return false;
    }

}
