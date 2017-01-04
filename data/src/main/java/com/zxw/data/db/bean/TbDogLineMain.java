package com.zxw.data.db.bean;

import java.util.List;

/**
 * author：CangJie on 2016/11/8 11:49
 * email：cangjie2016@gmail.com
 */
public class TbDogLineMain {
    private Long id;				//自增id
    private Long lineId;			//线路id
    private Integer type;			//类型,格式：1单点事件、2多点组合的线事件
    private Integer isCommit;		//是否需要上报,格式：0否、1是
    private Integer isCompare;		//是否需要比较才启动语音事件 格式：0否、1是
    private Integer compareValue;	//比较值
    private String voiceContent;			//语音模板id
    private Integer isDele;			//是否删除,格式：0否、1是
    private Long updateTimeKey;		//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm
    private List<TbDogLineSecond> secondList;

    public List<TbDogLineSecond> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<TbDogLineSecond> secondList) {
        this.secondList = secondList;
    }

    public String getVoiceContent() {
        return voiceContent;
    }

    public void setVoiceContent(String voiceContent) {
        this.voiceContent = voiceContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(Integer isCommit) {
        this.isCommit = isCommit;
    }

    public Integer getIsCompare() {
        return isCompare;
    }

    public void setIsCompare(Integer isCompare) {
        this.isCompare = isCompare;
    }

    public Integer getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Integer compareValue) {
        this.compareValue = compareValue;
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
