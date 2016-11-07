package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/7 16:22
 * email：cangjie2016@gmail.com
 */
public class UpdateLineStationBean {
    @Override
    public String toString() {
        return "UpdateLineStationBean{" +
                "id=" + id +
                ", isDele=" + isDele +
                ", lineId=" + lineId +
                ", sortNum=" + sortNum +
                ", stationId=" + stationId +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

    /**
     * id : 2
     * isDele : 0
     * lineId : 63
     * sortNum : 9
     * stationId : 3877
     * updateTimeKey : 201610291004
     */

    private int id;
    private int isDele;
    private int lineId;
    private int sortNum;
    private int stationId;
    private long updateTimeKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDele() {
        return isDele;
    }

    public void setIsDele(int isDele) {
        this.isDele = isDele;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }
}
