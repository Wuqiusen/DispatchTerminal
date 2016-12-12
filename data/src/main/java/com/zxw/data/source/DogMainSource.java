package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.dao.DogMainDao;
import com.zxw.data.db.bean.TbDogLineMain;
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
public class DogMainSource extends BaseSrouce {

    private final Context mContext;
    private final DogMainDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;
    private OnUpdateDogMainTableFinishListener mListener;

    public DogMainSource(Context context) {
        super();
        this.mContext = context;
        mDao = new DogMainDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = SpUtils.getTableUpdateTime(mContext, SpUtils.TABLE_DOG_MAIN);
    }

    public void loadUpdateDogMainTableData() {
        mHttpMethods.dogMainUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<TbDogLineMain>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final BaseBean<List<TbDogLineMain>> dogMainBeanBaseBean) {
                // 检查是否还有内容
                isCheckNextPage(dogMainBeanBaseBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(dogMainBeanBaseBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<TbDogLineMain>>() {
                            @Override
                            public void call(List<TbDogLineMain> dogMainBeen) {
                                updateDatabase(dogMainBeen);
                                partUpdateFinish(mPageNo, mPageSize, dogMainBeanBaseBean.returnSize);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<TbDogLineMain> beanList) {
        for (TbDogLineMain bean : beanList) {
            updateDatabase(bean);
        }
    }


    private void updateDatabase(TbDogLineMain bean) {
        mDao.update(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateDogMainTableData();
        } else {
            //完成更新!
            mIsCheckFinish = true;
        }
    }
    private void partUpdateFinish(int pageNo, int pageSize, int returnSize){
        //完成更新
        if (mIsCheckFinish && pageNo * pageSize >= returnSize){
            SpUtils.setCache(mContext, SpUtils.TABLE_DOG_MAIN, Long.valueOf(yyyyMMddHHmm));
            if (mListener != null)
                mListener.onUpdateDogMainTableFinish();
        }
        Log.w("line", "update : " + pageNo);
    }

    public void setOnUpdateDogMainTableFinishListener(OnUpdateDogMainTableFinishListener listener) {
        this.mListener = listener;
    }
    public interface OnUpdateDogMainTableFinishListener{
        void onUpdateDogMainTableFinish();
    }
}
