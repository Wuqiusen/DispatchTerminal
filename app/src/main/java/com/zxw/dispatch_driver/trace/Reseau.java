package com.zxw.dispatch_driver.trace;

import com.amap.api.maps2d.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author：CangJie on 2017/3/17 14:17
 * email：cangjie2016@gmail.com
 */
public class Reseau {

    // 网格边长
    private double sideSize = 0.04;
    // 划分网格总大小
    private final Point leftTop, rightBottom;

    private static Reseau reseau;

    private Map<String, List<Integer>> fenceMaps;

    private Reseau() {
        leftTop = new Point(23.08, 113.60);
        rightBottom = new Point(22.44, 114.80);
        fenceMaps = new HashMap<>();
        calculateReseau(leftTop, rightBottom);
    }

    private void calculateReseau(Point leftTop, Point rightBottom) {
        // 经纬度之差
        double sumDiffY = subtract(rightBottom.y, leftTop.y);
        double sumDiffX = subtract(rightBottom.x, leftTop.x);
        // 可以分成多少个单位
        int countX = (int) (Math.abs(sumDiffX) / sideSize);
        int countY = (int) (Math.abs(sumDiffY) / sideSize);
        createMap(leftTop, sumDiffY, sumDiffX, countX, countY);
    }

    private void createMap(Point leftTop, double sumDiffY, double sumDiffX, int countX, int countY) {
        for(int i = 0; i < countX; i++){
            for (int j = 0; j < countY; j++){
                double tempX = leftTop.x, tempY = leftTop.y;
                double diffX = i * sideSize;
                if (sumDiffX > 0){
                    tempX = add(tempX, diffX);
                }else{
                    tempX = subtract(tempX, diffX);
                }

                double diffY = j * sideSize;
                if (sumDiffY > 0){
                    tempY = add(tempY, diffY);
                }else{
                    tempY = subtract(tempY, diffY);
                }
                Point point = new Point(tempX, tempY);
                fenceMaps.put(point.getHashCode(), new ArrayList<Integer>());
            }
        }
    }

    public static Reseau getInstance() {
        if (reseau == null)
            reseau = new Reseau();
        return reseau;
    }

    /**
     *  计算围栏需要放入哪个方格中
     * @param rails
     */
    public void put(List<List<LatLng>> rails){
        for (List<LatLng> rail : rails){
            Rect rect = generateMaxRectByRail(rail);

        }
    }

    /**
     *  获取包裹围栏的最小矩形
     * @param points 围栏的各个顶点
     */
    private Rect generateMaxRectByRail(List<LatLng> points){
        // 获得包裹该围栏最大矩形的对角坐标点
        double maxX = 0, minX = 0, maxY = 0, minY = 0;
        for (LatLng point : points){
            maxX = Math.max(maxX, point.latitude);
            minX = Math.min(minX, point.latitude);
            maxY = Math.max(maxY, point.longitude);
            minY = Math.min(minY, point.longitude);
        }
        Rect rect = new Rect();
        rect.x1 = minX;
        rect.y1 = minY;
        rect.x2 = maxX;
        rect.y2 = maxY;
        return rect;
    }

    private double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.subtract(b2).doubleValue();
    }
    private double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.add(b2).doubleValue();
    }


    private class Point{
        private double x;
        private double y;

        public Point(double latitude, double longitude) {
            this.x = latitude;
            this.y = longitude;
        }

        public String getHashCode(){
            return String.valueOf(x * 100) + String.valueOf(y * 100);
        }
    }

    public class Rect{
        public double x1;
        public double y1;
        public double x2;
        public double y2;
    }
}
