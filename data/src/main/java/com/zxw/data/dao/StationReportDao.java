package com.zxw.data.dao;

import android.content.Context;

import com.zxw.data.db.StationReportDBOpenHelper;

/**
 * author：CangJie on 2016/11/3 16:41
 * email：cangjie2016@gmail.com
 */
public class StationReportDao {

    private StationReportDBOpenHelper mHelper;

    public StationReportDao(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
    }
//
//    /**
//     * 添加线站报告表
//     * @param type //类型,1进站 2终点进站  3保留未用 4离站
//     * @return rowID
//     */
//    public long addLineStationReport(int type, int lineId, int station, double lat, double lng, int isDele, int order){
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("type",type);
//        values.put("lineId",lineId);
//        values.put("stationId",station);
//        values.put("lat",lat);
//        values.put("lng",lng);
//        values.put("isDele",isDele);
//        values.put("sn",order);
//        long rowid = db.insert(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, null, values);
//        db.close();
//        return rowid;
//    }
//
//    /**
//     *  根据ID删除线站报告表
//     * @param id 需要删除的数据ID
//     * @return 1/成功
//     */
//    public void deleteLineStationReport(int id){
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        String sql = "delete from " + StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT +" where _id=" + id;
//        db.execSQL(sql);
//    }
//
//    /**
//     * 更新线站报告表
//     *     db.execSQL("insert into person(name, age) values(?,?)", new Object[]{"林计钦", 4});
//     db.execSQL("update person set name=? where personid=?", new Object[]{"abc", 1});
//     */
//    public int updateLineStationReport(int id, int type, int lineId, int stationId, double lng, double lat, int isDele){
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("type",type);
//        values.put("lineId",lineId);
//        values.put("stationId",stationId);
//        values.put("lng",lng);
//        values.put("lat",lat);
//        values.put("isDele",isDele);
//        return db.update(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, values, "_id="+id, null);
//    }
//
//    /**
//     *  根据ID查询线站报告表
//     */
//    public List<LineStationReportBean> queryLineStationReportBean(){
//        List<LineStationReportBean> datas = new ArrayList<>();
//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        Cursor cursor = db.query(StationReportDBOpenHelper.TABLE_LINE_STATION_REPORT, null, null, null, null, null, "sn asc");
//        while (cursor.moveToNext()){
//            int id = cursor.getInt(0);
//            int type = cursor.getInt(1);
//            int lineId = cursor.getInt(2);
//            int stationId = cursor.getInt(3);
//            double lng = cursor.getDouble(4);
//            double lat = cursor.getDouble(5);
//
//            LineStationReportBean lineStationReportBean = new LineStationReportBean();
//            lineStationReportBean.setId((long) id);
//            lineStationReportBean.setType(type);
//            lineStationReportBean.setLineId(lineId);
//            lineStationReportBean.setStationId(stationId);
//            lineStationReportBean.setLng(lng);
//            lineStationReportBean.setLat(lat);
//
//            datas.add(lineStationReportBean);
//        }
//        return datas;
//    }

}
