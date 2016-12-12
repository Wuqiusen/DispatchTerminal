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
//        String sql1 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (1,1,22.6372340000,114.0155950000,0,0)";
//        db.execSQL(sql1, new String[]{});
        // 多点判断 限速
        String sql2 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (12,14,22.595886,114.324829,0,0)";
        db.execSQL(sql2, new String[]{});
        String sql3 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (13,14,22.59572,114.324501,0,0)";
        db.execSQL(sql3, new String[]{});
        String sql4 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (14,14,22.595674,114.324066,0,0)";
        db.execSQL(sql4, new String[]{});
        String sql5 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (15,14,22.595651,114.323661,0,0)";
        db.execSQL(sql5, new String[]{});
        String sql6 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (16,14,22.595651,114.323181,0,0)";
        db.execSQL(sql6, new String[]{});
        String sql7 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (17,14,22.595645,114.322784,0,0)";
        db.execSQL(sql7, new String[]{});
        String sql8 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (18,14,22.595491,114.322486,0,0)";
        db.execSQL(sql8, new String[]{});
        String sql9 = "insert into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "values (19,14,22.595466,114.32209,0,0)";
        db.execSQL(sql9, new String[]{});


        String sql10 = "insert into" + DOGDBOpenHelper.TABLE_DOG_MAIN + "values (14,42,2,0,1,75,3,0,0)";
        db.execSQL(sql10, new String[]{});

        db.close();
    }

    public void update(TbDogLineSecond bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + DOGDBOpenHelper.TABLE_DOB_SECOND + "(id,mainId,lat,lng,isDele,updateTime) values (?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), String.valueOf(bean.getMainId()), String.valueOf(bean.getLat()),
                String.valueOf(bean.getLng()), String.valueOf(bean.getIsDele()), String.valueOf(bean.getUpdateTimeKey())});
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
