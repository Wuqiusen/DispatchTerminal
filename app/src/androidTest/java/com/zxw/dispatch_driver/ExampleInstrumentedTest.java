package com.zxw.dispatch_driver;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testPP() throws Exception{

        long currentTime = System.currentTimeMillis();
        Assert.assertEquals(currentTime, 123);

    }
    @Test
    public void useAppContext() throws Exception {
//        double v = calculateDiff(22.6413801906, 114.0095452679, 22.6415131906, 114.0094552679);   //55.91411300581078
//        double v = calculateDiff(22.6410301906, 114.0097882679, 22.6412091906, 114.0096572679);    //53.80170832010174
//        RoadTrack roadTrack = new RoadTrack(22.6412091906, 114.0096572679, 22.6410301906, 114.0097882679); // 233.80170832010177
        RoadTrack roadTrack = new RoadTrack(22.6413801906, 114.0095452679, 22.6413471906, 114.0094372679);
        RoadTrack roadTrack2 = new RoadTrack(22.6410301906, 114.0097882679, 22.6412091906, 114.0096572679); //53.80170832010174

        double b = calculateAngle(roadTrack, roadTrack2);

        Assert.assertEquals(1.0, b);
    }

    private boolean judgeAngle(RoadTrack roadTrack, RoadTrack roadTrack2) {
        return  calculateAngle(roadTrack, roadTrack2) < 90;
    }

    private double calculateAngle(RoadTrack roadTrack, RoadTrack roadTrack2) {
        double v = calculateDirection(roadTrack);
        double v2 = calculateDirection(roadTrack2);
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
