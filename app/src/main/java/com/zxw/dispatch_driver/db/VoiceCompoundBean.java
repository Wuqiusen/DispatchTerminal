package com.zxw.dispatch_driver.db;

/**
 * author：CangJie on 2016/11/1 19:26
 * email：cangjie2016@gmail.com
 */
public class VoiceCompoundBean {
    private Integer type;		//类型,唯一,1进站 2终点进站  3保留未用 4离站
    private String content;		//模板内容
    private Integer isDele;		//是否删除,格式：0否、1是
    private Long updateTimeKey;	//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm
	/*
	 * 到站报：XXX到了，请您从后门下车，开门请当心，下车请走好
	 * 离站报：车辆关门起步，请坐稳扶好，车厢内请不要吸烟，上车的乘客请向后门移动，下一站：XXX，请准备从后门下车。
	 * 终点报：各位乘客，终点站：XXX到了，请您携带好行李物品依次下车，开门请当心，下车请走好。
	 */

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "VoiceCompoundBean{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", isDele=" + isDele +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }
}
