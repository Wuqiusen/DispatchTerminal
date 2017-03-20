package com.zxw.dispatch_driver.trace;

public class Point {


    public Point(Double px, Double py) {
        super();
        this.px = px;
        this.py = py;
    }
    private Double px;
    private Double py;
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

    @Override
    public String toString() {
        return "Point{" +
                "px=" + px +
                ", py=" + py +
                '}';
    }
}