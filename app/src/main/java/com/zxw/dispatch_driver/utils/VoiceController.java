package com.zxw.dispatch_driver.utils;

import com.concox.gmctrl.gmCtrl;
import com.zxw.dispatch_driver.MyApplication;

/**
 * author：CangJie on 2017/1/18 14:44
 * email：cangjie2016@gmail.com
 */
public class VoiceController {
    public static void outside(){
        gmCtrl.GM_Audio_Switch(MyApplication.mContext, 1);
    }
    public static void inside(){
        gmCtrl.GM_Audio_Switch(MyApplication.mContext, 0);
    }
}
