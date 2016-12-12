package com.zxw.data.bean;

import java.io.Serializable;

/**
 * author：CangJie on 2016/12/8 09:49
 * email：cangjie2016@gmail.com
 */
public class Journey implements Serializable{
    /**
     * driverCode : 901969
     * id : 2
     * lineCode : 387
     * lineId : 42
     * mainId : 2
     * projectTime : 0800
     * runDate : 20161206
     * stationName : 方兴副站
     * status : 1
     * vehCode : F4547
     */

    private String driverCode;
    private int id;
    private String lineCode;
    private int lineId;
    private int mainId;
    private String projectTime;
    private int runDate;
    private String stationName;
    private int status;
    private String vehCode;

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public String getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(String projectTime) {
        this.projectTime = projectTime;
    }

    public int getRunDate() {
        return runDate;
    }

    public void setRunDate(int runDate) {
        this.runDate = runDate;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
}
