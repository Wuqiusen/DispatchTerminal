package com.zxw.data.bean;

/**
 * author：CangJie on 2017/2/23 20:08
 * email：cangjie2016@gmail.com
 */
public class ElectronRail {

    /**
     * electronRailId : 1
     * name : 淘金地停车场
     * points : 114.01137,22.639567
     * radius : 22
     */

    private int electronRailId;
    private String name;
    private String points;
    private String radius;

    @Override
    public String toString() {
        return "ElectronRail{" +
                "electronRailId=" + electronRailId +
                ", name='" + name + '\'' +
                ", points='" + points + '\'' +
                ", radius='" + radius + '\'' +
                '}';
    }

    public int getElectronRailId() {
        return electronRailId;
    }

    public void setElectronRailId(int electronRailId) {
        this.electronRailId = electronRailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}
