/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.concox.gmctrl;

import android.media.AudioManager;
import android.content.Context;
import android.util.Log;


public class gmCtrl{
    private static final String TAG = "gmctrl";

    public static void GM_Audio_Switch(Context context, int value)
    {
        Context mContext = context;

        final AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        if(value == 1)
        {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            GM_Speaker_switch(1);
        }
        else
        {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            GM_Speaker_switch(0);
        }

    }


    static {
        System.loadLibrary("gmctrl_jni");
    }

    public static native int GM_Get_ACC_State();
    public static native void GM_Speaker_switch(int value); // 0 for inside , 1 for outside
}
