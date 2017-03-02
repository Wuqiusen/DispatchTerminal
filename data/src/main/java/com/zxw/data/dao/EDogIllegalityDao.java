package com.zxw.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.db.EDogIllegalityDBOpenHelper;
import com.zxw.data.db.bean.IllegalityBean;

/**
 * author：CangJie on 2017/2/8 15:59
 * email：cangjie2016@gmail.com
 */
public class EDogIllegalityDao {

    private EDogIllegalityDBOpenHelper mHelper;

    public EDogIllegalityDao(Context context) {
        mHelper = new EDogIllegalityDBOpenHelper(context);
    }
    public long add(IllegalityBean bean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("opId", bean.getOpId());
        values.put("time", bean.getTime());
        values.put("eventType", bean.getEventType());
        values.put("photoPath1", bean.getPhotoPath1());
        values.put("photoPath2", bean.getPhotoPath2());
        values.put("videoPath", bean.getVideoPath());
        values.put("driverId", bean.getDriverId());
        long rowid = db.insert(EDogIllegalityDBOpenHelper.TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    /**
     * (id integer primary key, opId integer, time varchar(60), eventType integer, photoPath1 varchar(80), " +
     "photoPath2 varchar(80), videoPath varchar(80), driverId varchar(80))");
     * @return
     */
    public IllegalityBean popupFirst(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        IllegalityBean illegalityBean = null;
        int id;
        String sql = "select * from" + EDogIllegalityDBOpenHelper.TABLE_NAME + "limit 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()){
            id = cursor.getInt(0);
            int opId = cursor.getInt(1);
            String time = cursor.getString(2);
            int eventType = cursor.getInt(3);
            String photoPath1 = cursor.getString(4);
            String photoPath2 = cursor.getString(5);
            String videoPath = cursor.getString(6);
            String driverId = cursor.getString(7);
            illegalityBean =  new IllegalityBean(opId,time,eventType,photoPath1,photoPath2,videoPath,driverId);
        }else{
            return null;
        }
        cursor.close();
        String deleteSql = "delete from" + EDogIllegalityDBOpenHelper.TABLE_NAME + "where id = " + id;
        db.execSQL(deleteSql);
        db.close();
        return illegalityBean;
    }

    public void removeTable(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from" + EDogIllegalityDBOpenHelper.TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }
}
