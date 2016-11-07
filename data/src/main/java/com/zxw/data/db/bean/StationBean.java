package com.zxw.data.db.bean;

/**
 * author：CangJie on 2016/11/3 14:24
 * email：cangjie2016@gmail.com
 */
public class StationBean {
    private int stationId;
    private String stationName;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public StationBean(int stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }
}
