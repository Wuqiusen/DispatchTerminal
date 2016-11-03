package com.zxw.dispatch_driver;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void useAppContext() throws Exception {
//        double v = calculateDiff(22.6413801906, 114.0095452679, 22.6415131906, 114.0094552679);   //55.91411300581078
//        double v = calculateDiff(22.6410301906, 114.0097882679, 22.6412091906, 114.0096572679);    //53.80170832010174
//        RoadTrack roadTrack = new RoadTrack(22.6415131906, 114.0094552679, 22.6413801906, 114.0095452679); // A2 和 A1 235.91411300581078
//        RoadTrack roadTrack = new RoadTrack( 22.6415131906, 114.0094552679, 22.6415181906, 114.0092982679); // A2 和 B1 //1.8240898323761205
//        RoadTrack roadTrack2 = new RoadTrack(22.6410301906, 114.0097882679, 22.6412091906, 114.0096572679);  // 模拟车辆行走的路段 //53.80170832010174
        RoadTrack roadTrack2 = new RoadTrack( 22.6367340000,114.0160200000, 22.6381190000,114.0150370000);


        RoadTrack roadTrack = new RoadTrack(22.6367340000,114.0160200000, 22.6358460000,114.0142970000 );



        boolean b = judgeAngle(roadTrack, roadTrack2);

        Assert.assertEquals(true, b);
    }

    private boolean judgeAngle(RoadTrack roadTrack, RoadTrack roadTrack2) {
        double angle = calculateAngle(roadTrack, roadTrack2);
        return   angle < 90 || 360 - angle < 90;
    }

    private double calculateAngle(RoadTrack roadTrack, RoadTrack roadTrack2) {
        double v = calculateDirection(roadTrack);
        double v2 = calculateDirection(roadTrack2);
        System.out.println(v +"==" + v2);
        System.out.println("angle < 90 = " + (Math.abs(v - v2) < 90 ));
        System.out.println("360 - angle < 90 = " + (360 - Math.abs(v - v2) < 90));
        return Math.abs(v - v2);
    }

    private double calculateDirection(RoadTrack track) {
        double lat1 = track.getLat1();
        double lng1 = track.getLng1();
        double lat2 = track.getLat2();
        double lng2 = track.getLng2();
        lat1 = lat1 * 1000000;
        lng1 = lng1 * 1000000;
        lat2 = lat2 * 1000000;
        lng2 = lng2 * 1000000;
        double latDiff = lat1 - lat2;
        double lngDiff = lng1 - lng2;
        return calculateDirection(latDiff, lngDiff);
    }

    // 返回方向度数
    public double calculateDirection(double x, double y){
        double diff = 1 / 2d * Math.PI;
        double p25 = Math.atan2(y, x) - diff;
        if (p25 < 0)
        {
            p25 += 2 * Math.PI;
        }
        return p25 / Math.PI * 180;
    }

    public class RoadTrack{
        private double lat1;
        private double lng1;
        private double lat2;
        private double lng2;

        public RoadTrack(double lat1, double lng1, double lat2, double lng2) {
            this.lat1 = lat1;
            this.lng1 = lng1;
            this.lat2 = lat2;
            this.lng2 = lng2;
        }

        public double getLat1() {
            return lat1;
        }

        public void setLat1(double lat1) {
            this.lat1 = lat1;
        }

        public double getLng1() {
            return lng1;
        }

        public void setLng1(double lng1) {
            this.lng1 = lng1;
        }

        public double getLat2() {
            return lat2;
        }

        public void setLat2(double lat2) {
            this.lat2 = lat2;
        }

        public double getLng2() {
            return lng2;
        }

        public void setLng2(double lng2) {
            this.lng2 = lng2;
        }
    }
}