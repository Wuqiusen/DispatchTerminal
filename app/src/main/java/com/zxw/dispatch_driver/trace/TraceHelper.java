package com.zxw.dispatch_driver.trace;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnGeoFenceListener;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.Trace;
import com.zxw.data.bean.ElectronRail;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.LogUtil;
import com.zxw.dispatch_driver.utils.MyGsonUtils;

import java.util.List;

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

    // 围栏名称
    String fenceName = "众行网";
    // 围栏描述
    String fenceDesc = "留下买路钱";
    // 坐标类型 （1：GPS经纬度，2：国测局经纬度，3：百度经纬度）
    int coordType = 3;
    //去燥经度
    int precision = 100;
    //报警条件 1：进入时触发提醒    2：离开时触发提醒    3：进入离开均触发提醒
    int alarmCondition = 3;

    private FenceIdManager mFenceIdManager;
    private String validTimes = "0001,2359";
    //生效周期 （参数validTimes是否周期性生效，当为5时，需要定义validDays，标识在周几生效）            1：不重复    2：工作日循环    3：周末循环    4：每天循环    5：自定义
    private int validCycle = 4;
    //围栏生效日期
    private String validDate = null;
    // 生效日期列表
    private String validDays = null;
    private OnEnterOrLeaveFenceListener listener;
    private int currentRailId;


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
        mFenceIdManager = FenceIdManager.getInstance();
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
//                LogUtil.fenceLog(s);
//                OnTracePushBean onTracePushBean = MyGsonUtils.fromJson(s, OnTracePushBean.class);
//                OutsideInsideFenceBean outsideInsideFence = mFenceIdManager.queryServiceFenceByBaiDuFenceId(onTracePushBean.getFence_id());
//                if (outsideInsideFence == null || onTracePushBean.getPre_point().getRadius() > 500)
//                    return;
////                //查看最近几个坐标点是否有漂移
////                if(fenceCallBackFilter.isPY() || onTracePushBean.getPre_point().getRadius() > 100){
////                    ToastHelper.showToast("检测到漂移导致的进出围栏, 已经过滤 radius:" + onTracePushBean.getPre_point().getRadius());
////                    return;
////                }
//                String logStr = outsideInsideFence.getServiceFenceId() + " 发生了两个围栏事件 事件类型为 " + outsideInsideFence.getEventType();
//                LogUtil.fenceLog(logStr);
//                sendEnterOrLeaveFenceBroadcast(outsideInsideFence.getServiceFenceId(), outsideInsideFence.getEventType());
//                //复位
//                mFenceIdManager.restoreFlag();
            }
        });
    }

    /**
     * @param serverFenceId 服务器围栏ID
     * @param action        1:进入, 2:离开
     */
    private void sendEnterOrLeaveFenceBroadcast(int serverFenceId, int action) {
        if (listener == null)
            throw new RuntimeException("OnEnterOrLeaveFenceListener can not be null!");
        if (action == 1) {
            listener.enterFence(serverFenceId);
        } else {
            listener.leaveFence(serverFenceId);
        }
    }

    /**
     * 围栏圆心（圆心位置, 格式 : "经度,纬度"）
     * 围栏半径（单位 : 米）
     */
    public void createCircleFence(final ElectronRail rail) {
        DebugLog.w(rail.toString());
        double radius = Double.valueOf(rail.getRadius());
        String center = rail.getPoints();
        mTraceClient.createCircularFence(serviceId, entityName, fenceName, fenceDesc, entityName, entityName,
                validTimes, validCycle, validDate, validDays, coordType, center, radius, precision, alarmCondition, new OnGeoFenceListener() {
                    @Override
                    public void onRequestFailedCallback(String s) {

                    }

                    @Override
                    public void onCreateCircularFenceCallback(String s) {
                        Log.w(TAG, "onCreateCircularFenceCallback: " + s);
                        CreateFenceBean createFenceBean = MyGsonUtils.fromJson(s, CreateFenceBean.class);
                        if (createFenceBean == null || createFenceBean.getStatus() != 0) {
                            return;
                        }
                        int fenceId = createFenceBean.getFence_id();
                        // 通过当前id, 围栏id 和当前序号 就可以确定这个围栏是哪个一个的内环或者外环
                        mFenceIdManager.addFenceIdPair(currentRailId, fenceId, currentPointer);
                    }
                });
    }


    private int currentPointer = 0;
    private List<ElectronRail> listElectronRail;

    public interface OnEnterOrLeaveFenceListener {
        void enterFence(int fenceId);

        void leaveFence(int fenceId);
    }
}
