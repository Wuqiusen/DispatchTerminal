package com.zxw.data.db.bean;

/**
 * author：CangJie on 2016/11/3 14:24
 * email：cangjie2016@gmail.com
 */
public class LineBean {
    private int lineId;
    private String lineName;
    private int type; // 0上行 1下行
    private String isDele;
    private int updateTime;

    public LineBean(int lineId, String lineName, int type, String isDele, int updateTime) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.type = type;
        this.isDele = isDele;
        this.updateTime = updateTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIsDele() {
        return isDele;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

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

}
