package com.zxw.dispatch_driver.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;

import com.zxw.dispatch_driver.utils.ClickUtil;
import com.zxw.dispatch_driver.utils.ToastHelper;


public abstract class BaseActivity extends Activity {
    public Context mContext;
    public static int REQUEST_PERMISSION = 110;//写入权限
    private RequestPermissionsSuccess requestPermissionsSuccess;
    private RequestPermissionsFailed requestPermissionsFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    protected abstract void showLoading();
    protected abstract void hideLoading();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (ClickUtil.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
}

    public void disPlay(String toast){
        ToastHelper.showToast(toast,this);
    }


    public void setRequestPermissionsSuccess(RequestPermissionsSuccess requestPermissionsSuccess){
        this.requestPermissionsSuccess = requestPermissionsSuccess;
    }

    public void setRequestPermissionsFailed(RequestPermissionsFailed requestPermissionsFailed){
        this.requestPermissionsFailed = requestPermissionsFailed;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (requestPermissionsSuccess != null)
                        requestPermissionsSuccess.PermissionsSuccess(requestCode);
                } else {
                    if (requestPermissionsFailed != null)
                        requestPermissionsFailed.PermissionsFailed(requestCode);
                }
            }
        }
    }

    public interface RequestPermissionsSuccess{
        void PermissionsSuccess(int requestCode);
    }

    public interface RequestPermissionsFailed{
        void PermissionsFailed(int requestCode);
    }
}
