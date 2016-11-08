package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateServiceWordBean;
import com.zxw.data.dao.ServiceWordDao;
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
public class ServiceWordSource extends BaseSrouce {

    private final Context mContext;
    private final ServiceWordDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;
    private OnUpdateServiceWordTableFinishListener mListener;

    public ServiceWordSource(Context context) {
        super();
        this.mContext = context;
        mDao = new ServiceWordDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = SpUtils.getTableUpdateTime(mContext, SpUtils.TABLE_SERVICE_WORD);
    }

    public void loadUpdateServiceWordTableData() {
        mHttpMethods.serviceWordUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateServiceWordBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final BaseBean<List<UpdateServiceWordBean>> serviceWordBaseBean) {
                // 检查是否还有内容
                isCheckNextPage(serviceWordBaseBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(serviceWordBaseBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UpdateServiceWordBean>>() {
                            @Override
                            public void call(List<UpdateServiceWordBean> serviceWordBean) {
                                updateDatabase(serviceWordBean);
                                partUpdateFinish(mPageNo, mPageSize, serviceWordBaseBean.returnSize);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateServiceWordBean> beans) {
        for (UpdateServiceWordBean bean : beans) {
            updateDatabase(bean);
        }
    }


    private void updateDatabase(UpdateServiceWordBean bean) {
        mDao.updateServiceWord(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateServiceWordTableData();
        } else {
            //完成更新!
            mIsCheckFinish = true;
        }
    }
    private void partUpdateFinish(int pageNo, int pageSize, int returnSize){
        //完成更新
        if (mIsCheckFinish && pageNo * pageSize >= returnSize){
            SpUtils.setCache(mContext, SpUtils.TABLE_SERVICE_WORD, Long.valueOf(yyyyMMddHHmm));
            if (mListener != null)
                mListener.onUpdateServiceWordTableFinish();
        }
        Log.w("line", "update : " + pageNo);
    }

    public void setOnUpdateServiceWordTableFinishListener(OnUpdateServiceWordTableFinishListener listener) {
        this.mListener = listener;
    }
    public interface OnUpdateServiceWordTableFinishListener{
        void onUpdateServiceWordTableFinish();
    }
}
