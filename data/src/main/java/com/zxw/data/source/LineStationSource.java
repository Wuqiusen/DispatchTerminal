package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateLineStationBean;
import com.zxw.data.dao.LineStationDao;

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
public class LineStationSource extends BaseSrouce {

    private final Context mContext;
    private final LineStationDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;

    public LineStationSource(Context context) {
        super();
        this.mContext = context;
        mDao = new LineStationDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = mDao.lastUpdateTime();
    }

    public void loadUpdateLineStationTableData() {
//        long lineUpdateTime = -253171910;
        mHttpMethods.lineStationUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateLineStationBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<List<UpdateLineStationBean>> lineStationBean) {
                // 检查是否还有内容
                isCheckNextPage(lineStationBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(lineStationBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UpdateLineStationBean>>() {
                            @Override
                            public void call(List<UpdateLineStationBean> lineStationBean) {
                                Log.w("lineStationBean", lineStationBean.toString());
                                updateDatabase(lineStationBean);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateLineStationBean> lineBeen) {
        for (UpdateLineStationBean bean : lineBeen) {
            updateDatabase(bean);
        }
        partUpdateFinish();
    }


    private void updateDatabase(UpdateLineStationBean bean) {
        mDao.updateLineStation(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateLineStationTableData();
        } else {
            //完成更新!
            mIsCheckFinish = true;
        }
    }
    private void partUpdateFinish(){
        if (mIsCheckFinish){
            //完成更新

        }
    }
}
