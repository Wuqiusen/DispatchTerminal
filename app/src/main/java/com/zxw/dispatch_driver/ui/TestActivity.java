package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.concox.gmctrl.gmCtrl;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.utils.SpeakUtil;

public class TestActivity extends BaseHeadActivity {

    private SpeakUtil mSpeakUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        mSpeakUtil = SpeakUtil.getInstance(mContext);
        mSpeakUtil.init();
    }

    public void inside(View view){
        gmCtrl.GM_Speaker_switch(0);
    }
    public void outside(View view){
        gmCtrl.GM_Speaker_switch(1);

    }

    String notices[] = new String[]{"下雨路滑，请慢行！！", "金贵银贵生命最贵，千好万好平安最好。", "人车互让，得到的是和谐；争道抢行，缺失的是道德。",
    "车祸的惊人相似之处在于后来者比前人更“勇敢”。", "红灯是安全的警铃，绿灯是文明的标志。"};
    int current;
    public void notice(View view){
        String noticeIntent = "android.intent.action.NOTIFICATION_DISPALY";
        Intent intent = new Intent(noticeIntent);
        if(current == notices.length - 1)
            current = 0;
        current++;
        String notice = notices[current];
        intent.putExtra("deviceNotice", notice);
        sendBroadcast(intent);
        mSpeakUtil.playText(notice);
    }
}
