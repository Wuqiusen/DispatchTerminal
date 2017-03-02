package com.zxw.dispatch_driver.edog;

import android.content.Context;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.dao.EDogIllegalityDao;
import com.zxw.data.db.bean.IllegalityBean;
import com.zxw.data.http.HttpMethods;
import com.zxw.dispatch_driver.Constants;

import rx.Subscriber;

/**
 * author：CangJie on 2017/2/8 14:42
 * email：cangjie2016@gmail.com
 */
public class EDogIllegalityController {

    private static EDogIllegalityController controller = null;
    private EDogIllegalityDao eDogIllegalityDao = null;
    private Context mContext;

    private EDogIllegalityController(Context context){
        eDogIllegalityDao = new EDogIllegalityDao(context);
        this.mContext = context;
    }

    public static EDogIllegalityController getInstance(Context context){
        if (controller == null)
            controller = new EDogIllegalityController(context);
        return controller;
    }

    /**
     * 行驶中发生违规
     * @param illegalityBean 违规记录对象
     */
    public void uploadIllegalityEvent(final IllegalityBean illegalityBean){
        HttpMethods.getInstance().edogIllegality(illegalityBean, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                saveUploadFailedEventToDatabase(illegalityBean);
            }

            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.returnCode == Constants.SUCCESS){
                    uploadDatabaseEvent();
                }else{
                    saveUploadFailedEventToDatabase(illegalityBean);
                }
            }
        });
    }

    private void saveUploadFailedEventToDatabase(IllegalityBean illegalityBean){
        eDogIllegalityDao.add(illegalityBean);
    }

    /**
     * 弹出数据库最上面一条记录, 若有,则上传到服务器
     */
    private void uploadDatabaseEvent(){
        IllegalityBean illegalityBean = eDogIllegalityDao.popupFirst();
        if (illegalityBean == null)
            return;
        uploadIllegalityEvent(illegalityBean);
    }

    /**
     * 行驶结束
     */
    public void driveEnd(){
        uploadDatabaseEvent();
    }
}
