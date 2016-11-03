package com.zxw.dispatch_driver.db;

/**
 * author：CangJie on 2016/11/3 14:24
 * email：cangjie2016@gmail.com
 */
public class LineBean {
    private int lineId;
    private String lineName;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public LineBean(int lineId, String lineName) {
        this.lineId = lineId;
        this.lineName = lineName;
    }
}
