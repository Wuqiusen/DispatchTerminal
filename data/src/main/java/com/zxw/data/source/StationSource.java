package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateStationBean;
import com.zxw.data.dao.StationDao;
import com.zxw.data.sp.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * author：CangJie on 2016/11/4 10:37
 * email：cangjie2016@gmail.com
 */
public class StationSource extends BaseSrouce {

    private final Context mContext;
    private final StationDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;
    private OnUpdateStationFinishListener mListener;

    public StationSource(Context context) {
        super();
        this.mContext = context;
        mDao = new StationDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = SpUtils.getTableUpdateTime(mContext, SpUtils.TABLE_STATION);
    }

    public void loadUpdateStationTableData() {
        mHttpMethods.stationUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateStationBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final BaseBean<List<UpdateStationBean>> lineBeanBaseBean) {
                // 检查是否还有内容
                isCheckNextPage(lineBeanBaseBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(lineBeanBaseBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UpdateStationBean>>() {
                            @Override
                            public void call(List<UpdateStationBean> stationBeen) {
                                updateDatabase(stationBeen);
                                partUpdateFinish(mPageNo, mPageSize, lineBeanBaseBean.returnSize);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateStationBean> lineBeen) {
        for (UpdateStationBean bean : lineBeen) {
            updateDatabase(bean);
        }
    }


    private void updateDatabase(UpdateStationBean bean) {
        mDao.updateStation(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateStationTableData();
        } else {
            //完成更新!
            mIsCheckFinish = true;
        }
    }
    private void partUpdateFinish(int pageNo, int pageSize, int returnSize){
        //完成更新
        if (mIsCheckFinish && pageNo * pageSize >= returnSize){
            SpUtils.setCache(mContext, SpUtils.TABLE_STATION, Long.valueOf(yyyyMMddHHmm));
            if(mListener != null)
                mListener.onUpdateStationFinish();
        }
        Log.w("station", "update : " + pageNo);
    }

    public void setOnUpdateStationFinishListener(OnUpdateStationFinishListener listener) {
        this.mListener = listener;
    }

    public interface OnUpdateStationFinishListener{
        void onUpdateStationFinish();
    }
}
