package com.zxw.dispatch_driver.trace;

/**
 * author：CangJie on 2017/3/4 14:59
 * email：cangjie2016@gmail.com
 */
public class OutsideInsideFenceBean {
    //内电子围栏ID
    private int insideFenceId;
    //外电子围栏ID
    private int outsideFenceId;
    // 服务器电子围栏ID
    private int serviceFenceId;
    // 是否触发内电子围栏
    private boolean isTouchInside;
    private boolean isTouchOutside;
    private int eventType = -1;
    // 1进入 2离开
    public final static int TYPE_IN = 1, TYPE_OUT = 2;

    public OutsideInsideFenceBean(int serviceFenceId){
        this.serviceFenceId = serviceFenceId;
    }

    // 标记值复位
    public void restoreFlag(){
        this.isTouchInside = false;
        this.isTouchOutside = false;
        this.eventType = -1;
    }

    // 两个围栏是否都被触碰
    public boolean isTouchBothFence(){
        return isTouchInside && isTouchOutside;
    }

    // 传入围栏ID, 修改其对应的触发电子围栏标识
    public void alertTouchFlagByFenceId(int fenceId){
        if (fenceId == insideFenceId){
            isTouchInside = true;
            //当触发了里面的围栏, 判断外面围栏是否有触发, 如果有, 那么就是在外面进入里面
            if (isTouchOutside)
                eventType = TYPE_IN;
        }
        if (fenceId == outsideFenceId){
            isTouchOutside = true;
            // 当触发外面的围栏, 判断里面的围栏是否有触发, 如果有, 那么就是由里面出去外面.
            if (isTouchInside)
                eventType = TYPE_OUT;
        }
    }
    //设置内外围栏ID值.
    public void setInsideFenceId(int insideFenceId) {
        this.insideFenceId = insideFenceId;
    }

    public void setOutsideFenceId(int outsideFenceId) {
        this.outsideFenceId = outsideFenceId;
    }

    public int getServiceFenceId() {
        return serviceFenceId;
    }

    public int getEventType() {
        return eventType;
    }
}
