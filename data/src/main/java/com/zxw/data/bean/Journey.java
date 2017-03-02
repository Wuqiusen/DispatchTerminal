package com.zxw.data.bean;

import java.io.Serializable;

/**
 * author：CangJie on 2016/12/8 09:49
 * email：cangjie2016@gmail.com
 */
public class Journey implements Serializable{

    /**
     * lineCode : 982上行
     * runDate : 20170223
     * scheduleId : 1
     * status : 1
     * vehCode : 粤B001KN
     * vehTime : 0646
     */

    private String lineCode;
    private int runDate;
    private int scheduleId;
    private int status;
    private int lineId;
    private String vehCode;
    private String vehTime;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public int getRunDate() {
        return runDate;
    }

    public void setRunDate(int runDate) {
        this.runDate = runDate;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVehCode() {
        return vehCode;
    }

    public void setVehCode(String vehCode) {
        this.vehCode = vehCode;
    }

    public String getVehTime() {
        return vehTime;
    }

    public void setVehTime(String vehTime) {
        this.vehTime = vehTime;
    }
}
