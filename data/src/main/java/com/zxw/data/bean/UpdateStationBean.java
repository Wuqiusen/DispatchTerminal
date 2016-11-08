package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/7 16:20
 * email：cangjie2016@gmail.com
 */
public class UpdateStationBean {
    @Override
    public String toString() {
        return "UpdateStationBean{" +
                "id=" + id +
                ", isDele=" + isDele +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", realId=" + realId +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

    /**
     * id : 1
     * isDele : 0
     * lat : 22.6808
     * lng : 114.22302
     * name : 荷坳新村
     * realId : 4273
     * updateTimeKey : 201610291002
     */

    private int id;
    private int isDele;
    private double lat;
    private double lng;
    private String name;
    private int realId;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }
}
