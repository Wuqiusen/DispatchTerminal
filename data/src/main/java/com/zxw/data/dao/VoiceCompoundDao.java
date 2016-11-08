package com.zxw.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.bean.UpdateVoiceCompoundBean;
import com.zxw.data.db.StationReportDBOpenHelper;
import com.zxw.data.db.bean.VoiceCompoundBean;

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
     *  更新服务用语表
     */
    public void updateVoiceCompound(UpdateVoiceCompoundBean bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + StationReportDBOpenHelper.TABLE_VOICE_COMPOUND + "(id,content,type,isDele,updateTime) values (?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), bean.getContent(), String.valueOf(bean.getType()), String.valueOf(bean.getIsDele()), String.valueOf(bean.getUpdateTimeKey())});
        db.close();
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
}
