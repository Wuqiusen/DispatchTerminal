package com.zxw.dispatch_driver.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zxw.data.source.DogMainSource;
import com.zxw.data.source.DogSecondSource;
import com.zxw.data.source.LineSource;
import com.zxw.data.source.LineStationSource;
import com.zxw.data.source.ReportPointSource;
import com.zxw.data.source.ServiceWordSource;
import com.zxw.data.source.StationSource;
import com.zxw.data.source.VoiceCompoundSource;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.service.UpdateService;
import com.zxw.dispatch_driver.ui.WelcomeActivity;
import com.zxw.dispatch_driver.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private static final String FLAG_APP_NOTICE = "AppNotice";
    private static final String FLAG_DEVICE_NOTICE = "DeviceNotice";
    private static final String FLAG_UPDATE_DEVICE = "UpdateDeviceApp";
    private static final String FLAG_UPDATE_REPORT_DATA = "UpdateReportData";
    private static final String FLAG_UPDATE_DOG_DATA = "UpdateDogData";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /*
    AppNotice手机、终端app通知消息，值：内容
DeviceNotice终端机右侧通知消息，值：内容
UpdateDeviceApp更新终端机app事件标识，值：1
UpdateReportData更新终端机报站app数据标识，值：1
UpdateDogData更新终端机电子狗app数据标识，值：1
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        try {
            JSONObject extraJson = new JSONObject(extras);
            if (extraJson != null && extraJson.length() > 0) {
                Iterator<String> keys = extraJson.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = (String) extraJson.get(key);
                    switch (key) {
                        case FLAG_APP_NOTICE:
                            appNotice(context, value);
                            break;
                        case FLAG_DEVICE_NOTICE:
                            deviceNotice(context, value);
                            break;
                        case FLAG_UPDATE_DEVICE:
                            updateDevice(context, value);
                            break;
                        case FLAG_UPDATE_REPORT_DATA:
                            updateReportData(context, value);
                            break;
                        case FLAG_UPDATE_DOG_DATA:
                            updateDogData(context, value);
                            break;
                    }
                }
            }
        } catch (JSONException e) {

        }
    }

    private void updateDogData(Context context, String value) {
        if (1 != Integer.valueOf(value))
            return;
        DogMainSource dogMainSource = new DogMainSource(context);
        dogMainSource.loadUpdateDogMainTableData();
        DogSecondSource dogSecondSource = new DogSecondSource(context);
        dogSecondSource.loadUpdateDogSecondTableData();
    }

    private void updateReportData(Context context, String value) {
        if (1 != Integer.valueOf(value))
            return;
        LineSource source = new LineSource(context);
        source.loadUpdateLineTableData();
        StationSource stationSource = new StationSource(context);
        stationSource.loadUpdateStationTableData();
        LineStationSource lineStationSource = new LineStationSource(context);
        lineStationSource.loadUpdateLineStationTableData();
        ReportPointSource reportPointSource = new ReportPointSource(context);
        reportPointSource.loadUpdateReportPointSource();
        ServiceWordSource serviceWordSource = new ServiceWordSource(context);
        serviceWordSource.loadUpdateServiceWordTableData();
        VoiceCompoundSource voiceCompoundSource = new VoiceCompoundSource(context);
        voiceCompoundSource.loadUpdateVoiceCompoundTableData();
    }

    private void updateDevice(Context context, String value) {
        if (1 != Integer.valueOf(value))
            return;
        context.startService(new Intent(context, UpdateService.class));
    }

    private void deviceNotice(Context context, String value) {
        if(TextUtils.isEmpty(value))
            return;
        ToastHelper.showToast(value, context);
    }

    private void appNotice(Context context, String value) {
        if(TextUtils.isEmpty(value))
            return;
        Notification.Builder builder = new Notification.Builder(context);
        Intent mIntent = new Intent(context, WelcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        builder.setContentText(value);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
