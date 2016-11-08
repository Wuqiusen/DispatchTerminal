package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.dao.ReportPointDao;
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
public class ReportPointSource extends BaseSrouce {

    private final Context mContext;
    private final ReportPointDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;
    private OnUpdateReportPointFinishListener mListener;

    public ReportPointSource(Context context) {
        super();
        this.mContext = context;
        mDao = new ReportPointDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = SpUtils.getTableUpdateTime(mContext, SpUtils.TABLE_REPORT_POINT);
    }

    public void loadUpdateReportPointSource() {
        mHttpMethods.ReportPointUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateReportPointBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final BaseBean<List<UpdateReportPointBean>> lineBeanBaseBean) {
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
                                updateDatabase(reportPointBean);
                                partUpdateFinish(mPageNo, mPageSize, lineBeanBaseBean.returnSize);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateReportPointBean> lineBeen) {
        for (UpdateReportPointBean bean : lineBeen) {
            updateDatabase(bean);
        }
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
    private void partUpdateFinish(int pageNo, int pageSize, int returnSize){
        //完成更新
        if (mIsCheckFinish && pageNo * pageSize >= returnSize){
            SpUtils.setCache(mContext, SpUtils.TABLE_REPORT_POINT, Long.valueOf(yyyyMMddHHmm));
            if(mListener != null)
                mListener.onUpdateReportPointFinishListener();
        }
        Log.w("reportPoint", "update : " + pageNo);
    }

    public void setOnUpdateReportPointFinishListener(OnUpdateReportPointFinishListener listener) {
        this.mListener = listener;
    }
    public interface OnUpdateReportPointFinishListener{
        void onUpdateReportPointFinishListener();
    }
}
