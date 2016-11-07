package com.zxw.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.db.bean.VoiceCompoundBean;
import com.zxw.data.db.StationReportDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/3 16:50
 * email：cangjie2016@gmail.com
 */
public class VoiceCompoundDao {
    private StationReportDBOpenHelper mHelper;

    public VoiceCompoundDao(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
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
     *  查询全部语音合成信息
     * @return 语音对象组
     */
    public List<VoiceCompoundBean> queryVoiceCompound(){
        List<VoiceCompoundBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from" + StationReportDBOpenHelper.TABLE_VOICE_COMPOUND +" where isDele='0'" , null);
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
        String sql = "select * from" + StationReportDBOpenHelper.TABLE_VOICE_COMPOUND + "where type=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
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
    public void init(){
//        addVoiceCompound(1, "#curentStation#到了,请乘客有序从后门下车.",0);
//        addVoiceCompound(4, "请乘客抓紧扶稳,下一站是#nextStation#",0);
        addVoiceCompound(2, "各位乘客，终点站：#curentStation#到了，请您携带好行李物品依次下车，开门请当心，下车请走好。",0);

//        VoiceCompoundBean voiceCompoundBean = queryVoiceCompound(4);
//        Log.w("Voice", voiceCompoundBean.getContent());
    }
}
