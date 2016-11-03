package com.zxw.dispatch_driver.db;

/**
 * author：CangJie on 2016/11/1 18:50
 * email：cangjie2016@gmail.com
 */
public class LineStationReportBean {
    private Long id;			//自增id
    private Long mainId;		//主表id
    private Integer type;		//类型,1进站 2终点进站  3保留未用 4离站
    private int lineId;		//线路id
    private int stationId;		//站点id
    private Double lng;			//经度,格式：保留小数点后五位
    private Double lat;			//纬度,格式：保留小数点后五位
    private String keyCode;		//唯一标识,格式：lineId_stationId_type
    private Integer isDele;		//是否删除,格式：0否、1是
    private Long updateTimeKey;	//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public Integer getIsDele() {
        return isDele;
    }

    public void setIsDele(Integer isDele) {
        this.isDele = isDele;
    }

    public Long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(Long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }

    @Override
    public String toString() {
        return "LineStationReportBean{" +
                "id=" + id +
                ", mainId=" + mainId +
                ", type=" + type +
                ", lineId=" + lineId +
                ", stationId=" + stationId +
                ", lng=" + lng +
                ", lat=" + lat +
                ", keyCode='" + keyCode + '\'' +
                ", isDele=" + isDele +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }
}
