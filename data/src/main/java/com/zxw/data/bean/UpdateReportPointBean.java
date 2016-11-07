package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/7 16:23
 * email：cangjie2016@gmail.com
 */
public class UpdateReportPointBean {
    @Override
    public String toString() {
        return "UpdateReportPointBean{" +
                "id=" + id +
                ", isDele=" + isDele +
                ", keyCode='" + keyCode + '\'' +
                ", lat=" + lat +
                ", lineId=" + lineId +
                ", lng=" + lng +
                ", stationId=" + stationId +
                ", type=" + type +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

    /**
     * id : 2
     * isDele : 0
     * keyCode : 42_6940_4
     * lat : 22.59874
     * lineId : 42
     * lng : 114.31317
     * stationId : 6940
     * type : 4
     * updateTimeKey : 201611041636
     */

    private int id;
    private int isDele;
    private String keyCode;
    private double lat;
    private int lineId;
    private double lng;
    private int stationId;
    private int type;
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

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }
}
