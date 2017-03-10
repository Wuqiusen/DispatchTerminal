package com.zxw.dispatch_driver.utils.file_download_dialog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * author MXQ
 * create at 2017/3/10 17:21
 * email: 1299242483@qq.com
 */
public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));

        }
        return bufferedSource;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            // 已经下载的总字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead+= bytesRead !=-1 ? bytesRead : 0;
                progressListener.onProgress(totalBytesRead,responseBody.contentLength(),bytesRead == -1);
                return bytesRead;
            }
        };
    }
}