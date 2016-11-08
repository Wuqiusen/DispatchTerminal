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
    }

}