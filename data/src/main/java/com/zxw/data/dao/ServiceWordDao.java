package com.zxw.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.bean.UpdateServiceWordBean;
import com.zxw.data.db.StationReportDBOpenHelper;
import com.zxw.data.db.bean.ServiceWordBean;

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
     *  更新服务用语表
     */
    public void updateServiceWord(UpdateServiceWordBean bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + StationReportDBOpenHelper.TABLE_SERVICE_WORD + "(id,title,keyCode,content,isDele,updateTime) values (?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), bean.getTitle(), bean.getKeyCode(), bean.getContent(),  String.valueOf(bean.getIsDele()),String.valueOf(bean.getUpdateTimeKey())});
        db.close();
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
}
