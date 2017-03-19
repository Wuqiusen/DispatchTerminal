package com.zxw.dispatch_driver.trace;

import java.util.List;

/**
 * author：CangJie on 2017/3/19 11:52
 * email：cangjie2016@gmail.com
 */
public class RailBean {
    private List<Point> points;
    private String lastStatus;
    private String currentStatus;
    private long eventTime;

    private boolean isHappenRailEvent = false;

    public boolean isHappenRailEvent() {
        return isHappenRailEvent;
    }

    public void setHappenRailEvent(boolean happenRailEvent) {
        isHappenRailEvent = happenRailEvent;
        eventTime = System.currentTimeMillis();
    }

    public long getEventTime() {
        return eventTime;
    }

    public RailBean(List<Point> areaPoint) {
        this.points = areaPoint;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus() {
        this.lastStatus = this.currentStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "RailBean{" +
                "points=" + points +
                ", lastStatus='" + lastStatus + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", eventTime=" + eventTime +
                ", isHappenRailEvent=" + isHappenRailEvent +
                '}';
    }

    public void clear(){
        isHappenRailEvent = false;
        eventTime = -1;
        lastStatus = "";
        currentStatus = "";
    }

}
