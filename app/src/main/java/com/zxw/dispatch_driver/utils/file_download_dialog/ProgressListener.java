package com.zxw.dispatch_driver.utils.file_download_dialog;

/**
 * author MXQ
 * create at 2017/3/10 17:20
 * email: 1299242483@qq.com
 */
public interface ProgressListener {
    /**
     *
     * @param progress 已经下载后上传字节数
     * @param total 总字节数
     * @param done  是否完成
     */
    void onProgress(long progress,long total,boolean done);
}
