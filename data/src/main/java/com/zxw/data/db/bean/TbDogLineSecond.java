package com.zxw.data.db.bean;

/**
 * author：CangJie on 2016/11/8 11:50
 * email：cangjie2016@gmail.com
 */
public class TbDogLineSecond {
    private Long id;				//自增id
    private Long mainId;			//主表id
    private Double lng;				//经度
    private Double lat;				//纬度
    private Integer isDele;			//是否删除,格式：0否、1是
    private Long updateTimeKey;		//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm

    @Override
    public String toString() {
        return "TbDogLineSecond{" +
                "id=" + id +
                ", mainId=" + mainId +
                ", lng=" + lng +
                ", lat=" + lat +
                ", isDele=" + isDele +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

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
}
