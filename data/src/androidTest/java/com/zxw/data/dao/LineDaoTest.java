package com.zxw.data.dao;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

/**
 * author：CangJie on 2016/11/4 15:39
 * email：cangjie2016@gmail.com
 */
public class LineDaoTest {

    private LineDao lineDao;

    @Before
    public void init(){
        lineDao = new LineDao(InstrumentationRegistry.getTargetContext());
    }
    @Test
    public void addLine() throws Exception {
        lineDao.addLine(101, "101");
        lineDao.addLine(103, "103");
        lineDao.addLine(104, "104");
    }

    @Test
    public void queryLines() throws Exception {

    }

    @Test
    public void queryLine() throws Exception {
//        LineBean lineBean = lineDao.queryLine("101");
//        Assert.assertEquals(1011, lineBean.getLineId());
    }

    @Test
    public void updateLine() throws Exception {

    }

    @Test
    public void addVoiceCompound() throws Exception{
        VoiceCompoundDao dao = new VoiceCompoundDao(InstrumentationRegistry.getTargetContext());
        dao.addVoiceCompound(1, "#curentStation#到了,请乘客有序从后门下车.",0);
        dao.addVoiceCompound(4, "请乘客抓紧扶稳,下一站是#nextStation#",0);
        dao.addVoiceCompound(2, "各位乘客，终点站：#curentStation#到了，请您携带好行李物品依次下车，开门请当心，下车请走好。",0);
//
    }

}