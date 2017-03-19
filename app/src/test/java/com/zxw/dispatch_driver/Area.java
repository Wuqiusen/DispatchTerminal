package com.zxw.dispatch_driver;

import java.util.Random;

/**
 * 点实体类<br/>
 *
 * @author liuZhiwei
 * 2016年8月15日 下午7:16:14
 */
public class Area {

    public static Random rd=new Random();

    public Double createDouble(){
        return (double) rd.nextInt(1000);
    }
    public Area() {
        this.px = createDouble();
        this.py = createDouble();
    }

    public Area(Double px, Double py) {
        super();
        this.px = px;
        this.py = py;
    }

    public Area(Double px, Double py, String name) {
        super();
        this.px = px;
        this.py = py;
        this.name = name;
    }
    private Double px;
    private Double py;
    private String name;
    public Double getPx() {
        return px;
    }
    public void setPx(Double px) {
        this.px = px;
    }
    public Double getPy() {
        return py;
    }
    public void setPy(Double py) {
        this.py = py;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Point [px=" + px + ", py=" + py + ", name=" + name + "]";
    }

    public String getPoint() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("(").append(px).append(",").append(py).append(")");
        return buffer.toString();
    }
}