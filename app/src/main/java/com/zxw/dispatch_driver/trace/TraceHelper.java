package com.zxw.dispatch_driver.trace;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.Trace;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.LogUtil;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * author：CangJie on 2017/2/20 17:12
 * email：cangjie2016@gmail.com
 */
public class TraceHelper {
    private static final String TAG = "TraceHelper";
    private static TraceHelper instance;
    private final Context mContext;
    // 轨迹服务ID
    private long serviceId = 134434;
    // 设备名称
    String entityName = "";
    // 轨迹服务类型，traceType必须设置为UPLOAD_LOCATION才能追踪轨迹
    private int traceType = 2;
    private Trace mTrace;
    private LBSTraceClient mTraceClient;



    public static TraceHelper getInstance(Context context) {
        if (instance == null)
            instance = new TraceHelper(context);
        return instance;
    }

    private TraceHelper(Context context) {
        this.mContext = context;
        TelephonyManager tm = (TelephonyManager) MyApplication.mContext.getSystemService(TELEPHONY_SERVICE);
        entityName = tm.getDeviceId();
        DebugLog.w(entityName);
        initTrace();
    }

    private void initTrace() {
        // 初始化轨迹服务
        mTrace = new Trace(mContext, serviceId, entityName, traceType);
        // 初始化轨迹服务客户端
        mTraceClient = new LBSTraceClient(mContext);
// 采集周期
        int gatherInterval = 2;
// 打包周期
        int packInterval = 2;
// http协议类型
        int protocolType = 1;

// 设置采集和打包周期
        mTraceClient.setInterval(gatherInterval, packInterval);
// 设置定位模式
        mTraceClient.setLocationMode(LocationMode.High_Accuracy);
// 设置http协议类型
        mTraceClient.setProtocolType(protocolType);
        mTraceClient.startTrace(mTrace, new OnStartTraceListener() {
            @Override
            public void onTraceCallback(int i, String s) {
                Log.w(TAG, "onTraceCallback: " + s);
                LogUtil.log("onTraceCallback: " + s);
            }

            @Override
            public void onTracePushCallback(byte b, String s) {

            }
        });
    }

}
