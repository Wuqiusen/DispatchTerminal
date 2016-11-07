package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.dao.ReportPointDao;

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
public class ReportPointSource extends BaseSrouce {

    private final Context mContext;
    private final ReportPointDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;

    public ReportPointSource(Context context) {
        super();
        this.mContext = context;
        mDao = new ReportPointDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = mDao.lastUpdateTime();
    }

    public void loadUpdateReportPointSource() {
//        long lineUpdateTime = -253171910;
        mHttpMethods.ReportPointUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateReportPointBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<List<UpdateReportPointBean>> lineBeanBaseBean) {
                // 检查是否还有内容
                isCheckNextPage(lineBeanBaseBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(lineBeanBaseBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UpdateReportPointBean>>() {
                            @Override
                            public void call(List<UpdateReportPointBean> reportPointBean) {
                                Log.w("lineBean", reportPointBean.toString());
                                updateDatabase(reportPointBean);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateReportPointBean> lineBeen) {
        for (UpdateReportPointBean bean : lineBeen) {
            updateDatabase(bean);
        }
        partUpdateFinish();
    }


    private void updateDatabase(UpdateReportPointBean bean) {
        mDao.updateReportPoint(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateReportPointSource();
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
