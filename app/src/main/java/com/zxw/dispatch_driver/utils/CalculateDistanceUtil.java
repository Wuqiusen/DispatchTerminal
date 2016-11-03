package com.zxw.dispatch_driver.utils;

/**
 * author：CangJie on 2016/11/1 20:59
 * email：cangjie2016@gmail.com
 */
public class CalculateDistanceUtil {
    public static boolean isInCircle(Double lat1, Double lng1, Double distance, Double lat2, Double lng2) {
        double r = 6371;//地球半径千米
        double dis = distance / 1000;//0.150千米距离
        double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat1 * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;//角度转为弧度
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = lat1 - dlat;
        double maxlat = lat1 + dlat;
        double minlng = lng1 - dlng;
        double maxlng = lng1 + dlng;
        if (lng2 > minlng && maxlng > lng2 && lat2 > minlat && maxlat > lat2) {
            return true;
        } else {
            return false;
        }
    }
}
