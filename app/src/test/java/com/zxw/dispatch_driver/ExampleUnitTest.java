package com.zxw.dispatch_driver;

import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.zxw.dispatch_driver.trace.Reseau;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testGps1() throws  Exception{
        Reseau instance = Reseau.getInstance();
    }

    @Test
    public void testGps() throws Exception{
//(113.944421,22.528841) (113.94629,22.529208)
//        isPointInPolygon(area.createDouble(),area.createDouble()); (114.082402,22.550271) 114.075323,22.543528)
        Boolean pointInPolygon = isPointInPolygon(22.6467810000,114.0194800000);
        Assert.assertEquals(true, pointInPolygon);
    }
    private static Boolean isPointInPolygon( double px , double py ){
        Area a1=new Area(22.6472320000,114.0163270000);
        Area a3=new Area(22.6481910000,114.0189950000);
        Area a2=new Area(22.6467150000,114.0197590000);
        Area a4=new Area(22.6464900000,114.0193000000);
        Area a5=new Area(22.6470570000,114.0189320000);
        Area a6=new Area(22.6467310000,114.0184650000);
        Area a7=new Area(22.6461890000,114.0187520000);
        Area a8=new Area(22.6455310000,114.0175220000);
        List<Area> areas=new ArrayList<Area>();
        areas.add(a1);
        areas.add(a2);
        areas.add(a3);
        areas.add(a4);
        areas.add(a5);
        areas.add(a6);
        areas.add(a7);
        areas.add(a8);
        ArrayList<Double> polygonXA = new ArrayList<Double>();
        ArrayList<Double> polygonYA = new ArrayList<Double>();
        for(int i=0;i<areas.size();i++){
            Area area=areas.get(i);
            polygonXA.add(area.getPx());
            polygonYA.add(area.getPy());
        }
        Point point=new Point();
        Boolean flag= point.isPointInPolygon(px, py, polygonXA, polygonYA);
        StringBuffer buffer=new StringBuffer();
        buffer.append("目标点").append("(").append(px).append(",").append(py).append(")").append("\n");
        buffer.append(flag?"在":"不在").append("\t").append("由\n");
        for(int i=0;i<areas.size();i++){
            Area area=areas.get(i);
            buffer.append(area.getPoint()).append("; ");
            //buffer.append("第"+i+"个点"+area.getPoint()).append("\n");
            System.out.println("第"+(i+1)+"个点"+area.getPoint());
        }
        StringBuffer sb=new StringBuffer();
        sb.append("目标点:").append("(").append(px).append(",").append(py).append(")").append("\n");
        System.out.println(sb);
        buffer.append(areas.size()).append("个点组成的").append(areas.size()).append("边行内");
        System.out.println(buffer.toString());
        return  flag;
    }

    @Test
    public void testMapCopy() throws Exception{
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("s1", "s1");
        map1.put("s2", "s2");
        map1.put("s3", "s3");

        HashMap<String, String> map2 = new HashMap<>();
        map2.putAll(map1);

        print(map1, map2);
        map1.put("s1", "s11");

        print(map1, map2);
    }

    private void print(HashMap<String, String> map1, HashMap<String, String> map2) {
        Iterator<Map.Entry<String, String>> it = map1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        System.out.println("=================== map1.memory length" + map1);
        Iterator<Map.Entry<String, String>> it2 = map2.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, String> entry = it2.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        System.out.println("=================== map2.memory length" + map2);
    }


    @Test
    public void addition_isCorrect() throws Exception {
//        long i = 201610291002L;
//        Integer integer = Integer.valueOf("201610291002");

        LatLng sourceLatLng = new LatLng(22.6406051906,114.0115392679);
        CoordinateConverter converter  = new CoordinateConverter();
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU);
// sourceLatLng待转换坐标点 LatLng类型
        converter.coord(sourceLatLng);
// 执行转换操作
        LatLng desLatLng = converter.convert();
        assertEquals("", desLatLng.latitude + "," + desLatLng.longitude);
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

        assertEquals(true, b);
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