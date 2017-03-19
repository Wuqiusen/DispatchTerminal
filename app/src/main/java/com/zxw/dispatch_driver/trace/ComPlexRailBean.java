package com.zxw.dispatch_driver.trace;

/**
 * author：CangJie on 2017/3/19 12:08
 * email：cangjie2016@gmail.com
 */
public class ComplexRailBean {
    private int railId;
    private String name;
    private RailBean insideRail;
    private RailBean outsideRail;

    public int getRailId() {
        return railId;
    }

    public void setRailId(int railId) {
        this.railId = railId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RailBean getInsideRail() {
        return insideRail;
    }

    public void setInsideRail(RailBean insideRail) {
        this.insideRail = insideRail;
    }

    public RailBean getOutsideRail() {
        return outsideRail;
    }

    public void setOutsideRail(RailBean outsideRail) {
        this.outsideRail = outsideRail;
    }

    @Override
    public String toString() {
        return "ComplexRailBean{" +
                "name='" + name + '\'' +
                ", insideRail=" + insideRail +
                ", outsideRail=" + outsideRail +
                '}';
    }
}
