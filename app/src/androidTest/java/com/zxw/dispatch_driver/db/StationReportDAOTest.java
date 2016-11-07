package com.zxw.dispatch_driver.db;

import android.support.test.InstrumentationRegistry;

import com.zxw.data.dao.LineDao;

import org.junit.Before;
import org.junit.Test;

/**
 * author：CangJie on 2016/11/1 19:15
 * email：cangjie2016@gmail.com
 */
public class StationReportDAOTest{

//    private StationReportDAO dao;
    private LineDao dao;

    @Before
    public void init(){
        dao = new LineDao(InstrumentationRegistry.getTargetContext());
    }

//    @Test
//    public void addLineStationReport() throws Exception {
//        dao.addLineStationReport(1, 101, 202, 22.6413801906, 114.0095452679, 0, 1); // A1 腾龙路靠近淘金地一侧  进站
//        dao.addLineStationReport(4, 101, 202, 22.6415131906, 114.0094552679, 0, 2);  //A2 出站
//        dao.addLineStationReport(1, 101, 203, 22.6421120000, 114.0090990000, 0, 3);  //B1  即将到路口 进站
//        dao.addLineStationReport(4, 101, 203, 22.6424590000, 114.0090880000, 0, 4);  //B2 出站
//        dao.addLineStationReport(1, 101, 204, 22.6429190000,114.0097800000, 0, 5); // C1 掉头前 进站
//        dao.addLineStationReport(4, 101, 204, 22.6430630000,114.0100100000, 0, 6);  //C2 出站
//        dao.addLineStationReport(1, 101, 205, 22.6432320000,114.0096890000, 0, 7);  //D1  掉头后 进站
//        dao.addLineStationReport(4, 101, 205, 22.6431620000,114.0095490000, 0, 8);  //D2 出站
//        dao.addLineStationReport(1, 101, 206, 22.6427520000,114.0088520000, 0, 9); // E1 腾龙路靠近淘金地一侧  进站
//        dao.addLineStationReport(4, 101, 206, 22.6426080000,114.0086910000, 0, 10);  //E2 出站
//        dao.addLineStationReport(1, 101, 207, 22.6415181906, 114.0092982679, 0, 11);  //F1  靠近曼海宁一侧 进站
//        dao.addLineStationReport(2, 101, 207, 22.6413471906, 114.0094372679, 0, 12);  //F2 终点站
//
//    }
    @Test
    public void testLine() throws Exception{
        dao.addLine(101, "淘金地线");
//        dao.addLine(100, "测试无数据1线");
//        dao.addLine(102, "测试无数据2线");
//        dao.addLine(103, "测试无数据3线");
//        dao.addLine(104, "测试无数据4线");
    }
//    @Test
//    public void testStation() throws Exception{
//        dao.addStation(202, "淘金地站");
//        dao.addStation(203, "淘金地路口站");
//        dao.addStation(204, "龙胜地铁一站");
//        dao.addStation(205, "龙胜地铁二站");
//        dao.addStation(206, "和平路口站");
//        dao.addStation(207, "曼海宁站");
//    }
//
//    @Test
//    public void deleteLineStationReport() throws Exception {
//        dao.deleteLineStationReport(1);
//        dao.deleteLineStationReport(5);
//        dao.deleteLineStationReport(6);
//        dao.deleteLineStationReport(7);
//
//    }
//
//    @Test
//    public void updateLineStationReport() throws Exception {
//        dao.updateLineStationReport(1, 1, 101, 202, 22.6402001906, 114.0103542679, 0);
//
//    }
//
//    @Test
//    public void queryLineStationReportBean() throws Exception {
//        List<LineStationReportBean> lineStationReportBeen = dao.queryLineStationReportBean();
//        System.out.print(lineStationReportBeen.toString());
//    }
//
//    @Test
//    public void testAddServiceWord() throws Exception{
//        dao.addServiceWord("尊老爱幼", "尊老爱幼是中华民族的传统美德",0);
//        dao.addServiceWord("遵纪守法", "遵纪守法共同维护社会风气",0);
//    }
//
//    @Test
//    public void testAddVoiceCompund() throws Exception{
//        dao.addVoiceCompound(1, "#curentStation#到了,请乘客有序从后门下车.",0);
//        dao.addVoiceCompound(4, "请乘客抓紧扶稳,下一站是#nextStation#",0);
//        dao.addVoiceCompound(2, "各位乘客，终点站：#curentStation#到了，请您携带好行李物品依次下车，开门请当心，下车请走好。",0);
//
//    }

}