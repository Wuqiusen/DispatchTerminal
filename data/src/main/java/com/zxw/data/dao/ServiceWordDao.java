package com.zxw.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zxw.data.db.bean.ServiceWordBean;
import com.zxw.data.db.StationReportDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/3 16:49
 * email：cangjie2016@gmail.com
 */
public class ServiceWordDao {

    private StationReportDBOpenHelper mHelper;

    public ServiceWordDao(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
    }
    /**
     * 添加 服务用语
     * @param title 标题
     * @param content 内容
     * @param isDele 是否删除  0否 1是
     * @return rowID
     */
    public long addServiceWord(int id, String title, String keyCode, String content, Integer isDele, int updateTime){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("title",title);
        values.put("keyCode",keyCode);
        values.put("content",content);
        values.put("isDele",isDele);
        values.put("updateTime",updateTime);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_SERVICE_WORD, null, values);
        db.close();
        return rowid;
    }
    /**
     *  根据ID删除 服务用语表
     * @param id 需要删除的数据ID
     * @return 1/成功
     */
    public int deleteServiceWord(int id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db.delete(StationReportDBOpenHelper.TABLE_SERVICE_WORD, "_id", new String[]{String.valueOf(id)});
    }
    /**
     *  更新服务用语表
     */
    public int updateServiceWord(int id, String title, String content, Integer isDele){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("isDele",isDele);
        return db.update(StationReportDBOpenHelper.TABLE_SERVICE_WORD, values, "_id", new String[]{String.valueOf(id)});
    }
    /**
     *  查询所有服务用语表
     */
    public List<ServiceWordBean> queryServiceWord(){
        List<ServiceWordBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from" + StationReportDBOpenHelper.TABLE_SERVICE_WORD + "where isDele='0'", null);
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            String keyCode = cursor.getString(2);
            String content = cursor.getString(3);
            ServiceWordBean serviceWordBean = new ServiceWordBean();
            serviceWordBean.setTitle(title);
            serviceWordBean.setKeyCode(keyCode);
            serviceWordBean.setContent(content);
            list.add(serviceWordBean);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void initInsert(){
        addServiceWord(1, "服务用语一", "尊老爱幼", "尊老爱幼是中华民族的传统美德", 0, 0);
        addServiceWord(2, "服务用语二", "遵纪守法", "遵纪守法共同维护社会风气", 0, 0);
        addServiceWord(3, "服务用语三", "和谐社会", "文明用语构建和谐社会", 0, 0);

        List<ServiceWordBean> serviceWordBeen = queryServiceWord();
        Log.w("serviceWord", serviceWordBeen.toString());
    }
}
