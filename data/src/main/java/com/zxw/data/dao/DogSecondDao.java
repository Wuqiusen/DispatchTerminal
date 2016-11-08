package com.zxw.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.db.DOGDBOpenHelper;
import com.zxw.data.db.bean.TbDogLineSecond;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/8 11:58
 * email：cangjie2016@gmail.com
 */
public class DogSecondDao {
    private DOGDBOpenHelper mHelper;

    public DogSecondDao(Context context) {
        mHelper = new DOGDBOpenHelper(context);
    }

    public void mockData(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 前方红绿灯违章拍摄
        String sql1 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (1,1,22.6372340000,114.0155950000,0,0)";
        db.execSQL(sql1, new String[]{});
        // 多点判断 限速
        String sql2 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (2,2,22.6362930000,114.0152510000,0,0)";
        db.execSQL(sql2, new String[]{});
        String sql3 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (3,2,22.6342830000,114.0120220000,0,0)";
        db.execSQL(sql3, new String[]{});
        db.close();
    }

    public void update(TbDogLineSecond bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "(id,mainId,lat,lng,isDele,updateTime) values (?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), String.valueOf(bean.getMainId()), String.valueOf(bean.getLng()),
                String.valueOf(bean.getLat()), String.valueOf(bean.getIsDele()), String.valueOf(bean.getUpdateTimeKey())});
        db.close();
    }

    /**
     *  根据线路ID查主表
     * @param queryId 线路ID
     * @return 主表LIST
     */
    public List<TbDogLineSecond> queryByLineID(long queryId){
        List<TbDogLineSecond> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from" + DOGDBOpenHelper.TABLE_DOB_SECOND + "where mainId=? and isDele='0'";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(queryId)});
        TbDogLineSecond element;
        while (cursor.moveToNext()){
            Long id = cursor.getLong(0);
            Long mainId = cursor.getLong(1);
            double lat = cursor.getDouble(2);
            double lng = cursor.getDouble(3);
            int isDele = cursor.getInt(4);
            Long updateTimeKey = cursor.getLong(5);
            element = new TbDogLineSecond();
            element.setId(id);
            element.setMainId(mainId);
            element.setLng(lng);
            element.setLat(lat);
            element.setIsDele(isDele);
            element.setUpdateTimeKey(updateTimeKey);
            list.add(element);
        }
        cursor.close();
        db.close();
        return list;
    }
}
