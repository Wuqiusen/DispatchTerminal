package com.zxw.dispatch_driver.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/1 18:38
 * email：cangjie2016@gmail.com
 */
public class StationReportDAO {
    private StationReportDBOpenHelper mHelper;

    public StationReportDAO(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
    }

    /**
     * 添加线站报告表
     * @param type //类型,1进站 2终点进站  3保留未用 4离站
     * @return rowID
     */
    public long addLineStationReport(int type, int lineId, int station, double lat, double lng, int isDele, int order){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("lineId",lineId);
        values.put("stationId",station);
        values.put("lat",lat);
        values.put("lng",lng);
        values.put("isDele",isDele);
        values.put("sn",order);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, null, values);
        db.close();
        return rowid;
    }

    /**
     * 添加 服务用语
     * @param title 标题
     * @param content 内容
     * @param isDele 是否删除  0否 1是
     * @return rowID
     */
    public long addServiceWord(String title, String content, Integer isDele){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("isDele",isDele);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_SERVICE_WORD, null, values);
        db.close();
        return rowid;
    }

    /**
     * 添加语音合成表
     * @param type 类型,唯一,1进站 2终点进站  3保留未用 4离站
     * @param content 内容
     * @param isDele 是否删除  0否 1是
     * @return rowID
     */
    public long addVoiceCompound(int type, String content, Integer isDele){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("content",content);
        values.put("isDele",isDele);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_VOICE_COMPOUND, null, values);
        db.close();
        return rowid;
    }

    public long addStation(int stationId, String stationName){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stationId",stationId);
        values.put("stationName",stationName);
        values.put("isDele",0);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_STATION, null, values);
        db.close();
        return rowid;
    }

    public long addLine(int lineId, String lineName){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lineId",lineId);
        values.put("lineName",lineName);
        values.put("isDele",0);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_LINE, null, values);
        db.close();
        return rowid;
    }


    /**
     *  根据ID删除线站报告表
     * @param id 需要删除的数据ID
     * @return 1/成功
     */
    public void deleteLineStationReport(int id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from " + StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT +" where _id=" + id;
        db.execSQL(sql);
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
     *  根据ID删除 语音合成表
     * @param id 需要删除的数据ID
     * @return 1/成功
     */
    public int deleteVoiceCompound(int id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db.delete(StationReportDBOpenHelper.TABLE_VOICE_COMPOUND, "_id", new String[]{String.valueOf(id)});
    }

    /**
     * 更新线站报告表
     *     db.execSQL("insert into person(name, age) values(?,?)", new Object[]{"林计钦", 4});
     db.execSQL("update person set name=? where personid=?", new Object[]{"abc", 1});
     */
    public int updateLineStationReport(int id, int type, int lineId, int stationId, double lng, double lat, int isDele){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("lineId",lineId);
        values.put("stationId",stationId);
        values.put("lng",lng);
        values.put("lat",lat);
        values.put("isDele",isDele);
        return db.update(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, values, "_id="+id, null);
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
     *  更新服务用语表
     */
    public int updateVoiceCompound(int id, int type, String content, Integer isDele){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("content",content);
        values.put("isDele",isDele);
        return db.update(StationReportDBOpenHelper.TABLE_VOICE_COMPOUND, values, "_id", new String[]{String.valueOf(id)});
    }

    /**
     *  根据ID查询线站报告表
     */
    public List<LineStationReportBean> queryLineStationReportBean(){
        List<LineStationReportBean> datas = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, null, null, null, null, null, "sn asc");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int type = cursor.getInt(1);
            int lineId = cursor.getInt(2);
            int stationId = cursor.getInt(3);
            double lng = cursor.getDouble(4);
            double lat = cursor.getDouble(5);

            LineStationReportBean lineStationReportBean = new LineStationReportBean();
            lineStationReportBean.setId((long) id);
            lineStationReportBean.setType(type);
            lineStationReportBean.setLineId(lineId);
            lineStationReportBean.setStationId(stationId);
            lineStationReportBean.setLng(lng);
            lineStationReportBean.setLat(lat);

            datas.add(lineStationReportBean);
        }
        return datas;
    }


    /**
     *  根据站点ID查询站点信息
     * @param queryId 站点ID
     * @return 站点对象
     */
    public StationBean queryStation(int queryId){
        StationBean stationBean = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from" + StationReportDBOpenHelper.TABLE_STATION + "where stationId=?", new String[]{String.valueOf(queryId)});
        if (cursor.moveToNext()){
            int stationId = cursor.getInt(1);
            String stationName = cursor.getString(2);
            stationBean = new StationBean(stationId, stationName);
        }
        cursor.close();
        db.close();
        return stationBean;
    }

    /**
     *  查询所有服务用语表
     */
    public List<ServiceWordBean> queryServiceWord(){
        List<ServiceWordBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from" + StationReportDBOpenHelper.TABLE_SERVICE_WORD, null);
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            int isDele = cursor.getInt(3);
            ServiceWordBean serviceWordBean = new ServiceWordBean();
            serviceWordBean.setTitle(title);
            serviceWordBean.setContent(content);
            serviceWordBean.setIsDele(isDele);
            list.add(serviceWordBean);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     *  查询全部语音合成信息
     * @return 语音对象组
     */
    public List<VoiceCompoundBean> queryVoiceCompound(){
        List<VoiceCompoundBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from" + StationReportDBOpenHelper.TABLE_VOICE_COMPOUND , null);
        while (cursor.moveToNext()){
            int type = cursor.getInt(1);
            String content = cursor.getString(2);
            int isDele = cursor.getInt(3);

            VoiceCompoundBean voiceCompoundBean = new VoiceCompoundBean();
            voiceCompoundBean.setType(type);
            voiceCompoundBean.setContent(content);
            voiceCompoundBean.setIsDele(isDele);
            list.add(voiceCompoundBean);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     *  根据ID查询语音合成表
     */
    public VoiceCompoundBean queryVoiceCompound(int id){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(StationReportDBOpenHelper.TABLE_VOICE_COMPOUND, null, "id", new String[]{String.valueOf(id)}, null, null, "_id desc");
        if (cursor.moveToNext()){
            int type = cursor.getInt(1);
            String content = cursor.getString(2);
            int isDele = cursor.getInt(3);

            VoiceCompoundBean voiceCompoundBean = new VoiceCompoundBean();
            voiceCompoundBean.setType(type);
            voiceCompoundBean.setContent(content);
            voiceCompoundBean.setIsDele(isDele);
            return voiceCompoundBean;
        }
        return null;
    }

}
