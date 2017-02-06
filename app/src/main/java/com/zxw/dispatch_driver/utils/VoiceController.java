package com.zxw.dispatch_driver.utils;

import com.concox.gmctrl.gmCtrl;

/**
 * author：CangJie on 2017/1/18 14:44
 * email：cangjie2016@gmail.com
 */
public class VoiceController {
    public static void outside(){
        gmCtrl.GM_Speaker_switch(1);
    }
    public static void inside(){
        gmCtrl.GM_Speaker_switch(0);
    }
}
