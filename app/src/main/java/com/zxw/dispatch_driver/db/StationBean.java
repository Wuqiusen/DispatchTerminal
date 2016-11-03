package com.zxw.dispatch_driver.db;

/**
 * author：CangJie on 2016/11/3 14:24
 * email：cangjie2016@gmail.com
 */
public class StationBean {
    private int stationId;
    private String stationName;

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
