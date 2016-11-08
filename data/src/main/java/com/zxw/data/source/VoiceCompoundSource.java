package com.zxw.data.source;

import android.content.Context;
import android.util.Log;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.UpdateVoiceCompoundBean;
import com.zxw.data.dao.VoiceCompoundDao;
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
public class VoiceCompoundSource extends BaseSrouce {

    private final Context mContext;
    private final VoiceCompoundDao mDao;
    private final String yyyyMMddHHmm;
    private boolean mIsCheckFinish;
    private long mLineUpdateTime;
    private OnUpdateVoiceCompoundTableFinishListener mListener;

    public VoiceCompoundSource(Context context) {
        super();
        this.mContext = context;
        mDao = new VoiceCompoundDao(mContext);
        // 记录这个类工作的时间yyyyMMddHHmm 当更新完成后,把这个值赋给SP中的记录时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        yyyyMMddHHmm = format.format(new Date());
        mLineUpdateTime = SpUtils.getTableUpdateTime(mContext, SpUtils.TABLE_VOICE_COMPOUND);
    }

    public void loadUpdateVoiceCompoundTableData() {
        mHttpMethods.voiceCompoundUpdate(code(), String.valueOf(mLineUpdateTime), time(), mPageNo, mPageSize, new Subscriber<BaseBean<List<UpdateVoiceCompoundBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final BaseBean<List<UpdateVoiceCompoundBean>> voiceCompoundBeanBaseBean) {
                // 检查是否还有内容
                isCheckNextPage(voiceCompoundBeanBaseBean.returnSize);
                // 用子线程 更新数据库
                Observable.just(voiceCompoundBeanBaseBean.returnData)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<UpdateVoiceCompoundBean>>() {
                            @Override
                            public void call(List<UpdateVoiceCompoundBean> voiceCompoundBeen) {
                                updateDatabase(voiceCompoundBeen);
                                partUpdateFinish(mPageNo, mPageSize, voiceCompoundBeanBaseBean.returnSize);
                            }
                        });
            }
        });
    }

    private void updateDatabase(List<UpdateVoiceCompoundBean> beans) {
        for (UpdateVoiceCompoundBean bean : beans) {
            updateDatabase(bean);
        }
    }


    private void updateDatabase(UpdateVoiceCompoundBean bean) {
        mDao.updateVoiceCompound(bean);
    }

    private void isCheckNextPage(int returnSize) {
        if (mPageNo * mPageSize < returnSize) {
            mPageNo++;
            loadUpdateVoiceCompoundTableData();
        } else {
            //完成更新!
            mIsCheckFinish = true;
        }
    }
    private void partUpdateFinish(int pageNo, int pageSize, int returnSize){
        //完成更新
        if (mIsCheckFinish && pageNo * pageSize >= returnSize){
            SpUtils.setCache(mContext, SpUtils.TABLE_VOICE_COMPOUND, Long.valueOf(yyyyMMddHHmm));
            if (mListener != null)
                mListener.onUpdateVoiceCompoundTableFinish();
        }
        Log.w("line", "update : " + pageNo);
    }

    public void setOnUpdateVoiceCompoundTableFinishListener(OnUpdateVoiceCompoundTableFinishListener listener) {
        this.mListener = listener;
    }
    public interface OnUpdateVoiceCompoundTableFinishListener{
        void onUpdateVoiceCompoundTableFinish();
    }
}
