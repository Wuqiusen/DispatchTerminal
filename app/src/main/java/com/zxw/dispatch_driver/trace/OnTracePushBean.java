package com.zxw.dispatch_driver.trace;

/**
 * author：CangJie on 2017/2/20 19:13
 * email：cangjie2016@gmail.com
 */
public class OnTracePushBean {

    /**
     * fence_id : 49
     * fence : 众行网
     * monitored_person : meizu
     * action : 1
     * time : 1487579469
     * longitude : 114.01765340299934
     * latitude : 22.645328933261286
     * coord_type : 3
     * radius : 5
     * pre_point : {"longitude":114.0178293079416,"latitude":22.645244451335277,"coord_type":3,"time":1487579464,"radius":3}
     */

    private int fence_id;
    private String fence;
    private String monitored_person;
    private int action;
    private int time;
    private double longitude;
    private double latitude;
    private int coord_type;
    private int radius;
    /**
     * longitude : 114.0178293079416
     * latitude : 22.645244451335277
     * coord_type : 3
     * time : 1487579464
     * radius : 3
     */

    private PrePointBean pre_point;

    public int getFence_id() {
        return fence_id;
    }

    public void setFence_id(int fence_id) {
        this.fence_id = fence_id;
    }

    public String getFence() {
        return fence;
    }

    public void setFence(String fence) {
        this.fence = fence;
    }

    public String getMonitored_person() {
        return monitored_person;
    }

    public void setMonitored_person(String monitored_person) {
        this.monitored_person = monitored_person;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(int coord_type) {
        this.coord_type = coord_type;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public PrePointBean getPre_point() {
        return pre_point;
    }

    public void setPre_point(PrePointBean pre_point) {
        this.pre_point = pre_point;
    }

    public static class PrePointBean {
        private double longitude;
        private double latitude;
        private int coord_type;
        private int time;
        private int radius;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getCoord_type() {
            return coord_type;
        }

        public void setCoord_type(int coord_type) {
            this.coord_type = coord_type;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }
    }
}
