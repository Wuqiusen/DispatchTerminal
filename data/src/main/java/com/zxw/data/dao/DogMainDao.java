package com.zxw.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.db.DOGDBOpenHelper;
import com.zxw.data.db.bean.TbDogLineMain;
import com.zxw.data.db.bean.TbDogLineSecond;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/8 11:58
 * email：cangjie2016@gmail.com
 */
public class DogMainDao {
    private final Context mContext;
    private DOGDBOpenHelper mHelper;

    public DogMainDao(Context context) {
        this.mContext = context;
        mHelper = new DOGDBOpenHelper(context);
    }

    public void mockData(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 前方红绿灯违章拍摄
        String sql1 = "insert into" + DOGDBOpenHelper.TABLE_DOG_MAIN + "values (1,365,1,1,0,0,101,0,0)";
        db.execSQL(sql1, new String[]{});
        // 多点判断 限速
        String sql2 = "insert into" + DOGDBOpenHelper.TABLE_DOG_MAIN + "values (2,365,2,1,1,70,102,0,0)";
        db.execSQL(sql2, new String[]{});
        db.close();
    }

    public void update(TbDogLineMain bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + DOGDBOpenHelper.TABLE_DOG_MAIN + "(id,lineId,type,isCommit,isCompare,compareValue,voiceContent,isDele,updateTime) values (?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), String.valueOf(bean.getLineId()), String.valueOf(bean.getType()),
                String.valueOf(bean.getIsCommit()), String.valueOf(bean.getIsCompare()), String.valueOf(bean.getCompareValue()),
                bean.getVoiceContent(), String.valueOf(bean.getIsDele()), String.valueOf(bean.getUpdateTimeKey())});
        db.close();
    }

    /**
     *  根据线路ID查主表
     * @param queryId 线路ID
     * @return 主表LIST
     */
    public List<TbDogLineMain> queryByLineID(int queryId){
        List<TbDogLineMain> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from" + DOGDBOpenHelper.TABLE_DOG_MAIN + "where lineId=? and isDele='0'";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(queryId)});
        DogSecondDao dogSecondDao = new DogSecondDao(mContext);
        TbDogLineMain element;
        while (cursor.moveToNext()){
            Long id = cursor.getLong(0);
            Long lineId = cursor.getLong(1);
            int type = cursor.getInt(2);
            int isCommit = cursor.getInt(3);
            int isCompare = cursor.getInt(4);
            int compareValue = cursor.getInt(5);
            String voiceContent = cursor.getString(6);
            int isDele = cursor.getInt(7);
            Long updateTimeKey = cursor.getLong(8);
            element = new TbDogLineMain();
            element.setId(id);
            element.setLineId(lineId);
            element.setType(type);
            element.setIsCommit(isCommit);
            element.setIsCompare(isCompare);
            element.setCompareValue(compareValue);
            element.setVoiceContent(voiceContent);
            element.setIsDele(isDele);
            element.setUpdateTimeKey(updateTimeKey);
            List<TbDogLineSecond> tbDogLineSeconds = dogSecondDao.queryByLineID(id);
            element.setSecondList(tbDogLineSeconds);
            list.add(element);
        }
        cursor.close();
        db.close();
        return list;
    }
}
