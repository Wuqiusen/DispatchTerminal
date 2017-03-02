package com.zxw.data.db.bean;

/**
 * author：CangJie on 2017/2/8 11:42
 * email：cangjie2016@gmail.com
 */
public class IllegalityBean {
    private int opId;
    private String time;
    private int eventType;
    private String photoPath1;
    private String photoPath2;
    private String videoPath;
    private String driverId;

    public IllegalityBean() {
    }

    public IllegalityBean(int opId, String time, int eventType, String photoPath1, String photoPath2, String videoPath, String driverId) {
        this.opId = opId;
        this.time = time;
        this.eventType = eventType;
        this.photoPath1 = photoPath1;
        this.photoPath2 = photoPath2;
        this.videoPath = videoPath;
        this.driverId = driverId;
    }

    public int getOpId() {
        return opId;
    }

    public void setOpId(int opId) {
        this.opId = opId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getPhotoPath1() {
        return photoPath1;
    }

    public void setPhotoPath1(String photoPath1) {
        this.photoPath1 = photoPath1;
    }

    public String getPhotoPath2() {
        return photoPath2;
    }

    public void setPhotoPath2(String photoPath2) {
        this.photoPath2 = photoPath2;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
