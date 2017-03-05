package com.zxw.dispatch_driver.trace;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnGeoFenceListener;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.Trace;
import com.zxw.data.bean.ElectronRail;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.utils.MyGsonUtils;
import com.zxw.dispatch_driver.utils.ToastHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.zxw.dispatch_driver.MyApplication.writeTxtToFile;

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
    int precision = 0;
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
        //延时1分钟进行删除围栏 , 避免和生成围栏逻辑冲突
        // 原因是百度调用删除再调用创建, 会没创建的回调触发, 这样就获取不到围栏ID
        Observable.timer(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                deleteOldFence();
            }
        });
    }

    private void deleteOldFence() {
        String cache = SpUtils.getCache(mContext, SpUtils.OLD_FENCE_LIST);
        if (!TextUtils.isEmpty(cache)){
            String[] split = cache.split(";");
            for (String str : split){
                if (!TextUtils.isEmpty(str)){
                    try{
                        Integer fenceId = Integer.valueOf(str);
                        delete(fenceId);
                    }catch (Exception e){

                    }
                }
            }
        }
        cache = SpUtils.getCache(mContext, SpUtils.NEW_FENCE_LIST);
        SpUtils.setCache(mContext, SpUtils.OLD_FENCE_LIST, cache);
        SpUtils.setCache(mContext, SpUtils.NEW_FENCE_LIST, "");
    }


    public void delete(int fenceId) {
            mTraceClient.deleteFence(serviceId, fenceId, new OnGeoFenceListener() {
                @Override
                public void onRequestFailedCallback(String s) {
                    Log.w(TAG, "onRequestFailedCallback: " + s);
                }

                @Override
                public void onDeleteFenceCallback(String s) {
                    Log.w(TAG, "onDeleteFenceCallback: " + s);
                }
            });

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
            }

            @Override
            public void onTracePushCallback(byte b, String s) {
                traceStatusChange(s);
                OnTracePushBean onTracePushBean = MyGsonUtils.fromJson(s, OnTracePushBean.class);
                OutsideInsideFenceBean outsideInsideFence = mFenceIdManager.queryServiceFenceByBaiDuFenceId(onTracePushBean.getFence_id());
                if (outsideInsideFence == null || onTracePushBean.getPre_point().getRadius() > 500)
                    return;
//                //查看最近几个坐标点是否有漂移
//                if(fenceCallBackFilter.isPY() || onTracePushBean.getPre_point().getRadius() > 100){
//                    ToastHelper.showToast("检测到漂移导致的进出围栏, 已经过滤 radius:" + onTracePushBean.getPre_point().getRadius());
//                    return;
//                }
                traceStatusChange(outsideInsideFence.getServiceFenceId() + " 发生了两个围栏事件 事件类型为 " + outsideInsideFence.getEventType());
                sendEnterOrLeaveFenceBroadcast(outsideInsideFence.getServiceFenceId(), outsideInsideFence.getEventType());
                ToastHelper.showToast(s);

                //复位
                mFenceIdManager.restoreFlag();
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

    private void traceStatusChange(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(new Date());
        //保存到本地
        String filePath = Environment.getExternalStorageDirectory() + "/BdYy";
        String fileName = "log.txt";
        writeTxtToFile(time + "\n str : " + str, filePath, fileName);
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
                        ToastHelper.showToast(s);
                    }
                });
    }

    public void createPolygonFence(ElectronRail rail) {
        DebugLog.w(rail.toString());
//        String cache = SpUtils.getCache(mContext, SpUtils.MAP_POINT);
//        if (TextUtils.isEmpty(cache)) {
//            SpUtils.setCache(mContext, SpUtils.MAP_POINT, rail.getPoints());
//        } else {
//            SpUtils.setCache(mContext, SpUtils.MAP_POINT, cache + "|" + rail.getPoints());
//        }
        currentRailId = rail.getElectronRailId();
        mTraceClient.createVertexesFence(serviceId, entityName, fenceName, fenceDesc, entityName, entityName, validTimes, validCycle, validDate, validDays, coordType,
                rail.getPoints(), precision, alarmCondition, new OnGeoFenceListener() {
                    @Override
                    public void onRequestFailedCallback(String s) {
                        Log.w(TAG, "onRequestFailedCallback: " + s);
                    }

                    @Override
                    public void onCreateVertexesFenceCallback(String s) {
                        Log.w(TAG, "onCreateVertexesFenceCallback: " + s);
                        CreateFenceBean createFenceBean = MyGsonUtils.fromJson(s, CreateFenceBean.class);
                        if (createFenceBean == null || createFenceBean.getStatus() != 0) {
                            return;
                        }
                        int fenceId = createFenceBean.getFence_id();
                        mFenceIdManager.addFenceIdPair(currentRailId, fenceId, currentPointer);
                        createFenceList();
                    }
                });
    }

    public void setOnEnterOrLeaveFenceListener(OnEnterOrLeaveFenceListener listener) {
        this.listener = listener;
    }


    private int currentPointer = 0;
    private List<ElectronRail> listElectronRail;
    public void createFenceList() {
        if (listElectronRail.size() != currentPointer) {
            ElectronRail rail = listElectronRail.get(currentPointer);
            currentPointer++;
            if (TextUtils.isEmpty(rail.getRadius()) || "0".equals(rail.getRadius())) {
                createPolygonFence(rail);
            } else {
                createCircleFence(rail);
            }
        }
    }

    public void initFenceList(List<ElectronRail> listElectronRail) {
        this.listElectronRail = listElectronRail;
        currentPointer = 0;
        createFenceList();
    }

    public void unregister() {
        List<OutsideInsideFenceBean> fenceList = mFenceIdManager.getFenceList();
        for (OutsideInsideFenceBean fence : fenceList){
            delete(fence.getInsideFenceId());
            DebugLog.w("delete " + fence.getInsideFenceId());
            delete(fence.getOutsideFenceId());
            DebugLog.w("delete " + fence.getOutsideFenceId());
        }
    }

    public interface OnEnterOrLeaveFenceListener {
        void enterFence(int fenceId);

        void leaveFence(int fenceId);
    }
}
