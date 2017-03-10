package com.zxw.dispatch_driver.utils.file_download_dialog;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * author MXQ
 * create at 2017/3/10 17:20
 * email: 1299242483@qq.com
 */
public abstract class ProgressHandler {

    /*发送消息*/
    protected abstract void sendMessage(ProgressBean progressBean);
    /*处理消息*/
    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private ProgressHandler mProgressHandler;
        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }




}