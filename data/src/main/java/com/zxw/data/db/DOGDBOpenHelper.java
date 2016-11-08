package com.zxw.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DOGDBOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_DOG_MAIN = " dog_main ";
	public static final String TABLE_DOB_SECOND = " dog_second ";

	public DOGDBOpenHelper(Context context) {
		super(context, "dog.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/**
		 * 电子狗主表
		 *     private Long id;				//自增id
		 private Long lineId;			//线路id
		 private Integer type;			//类型,格式：1单点事件、2多点组合的线事件
		 private Integer isCommit;		//是否需要上报,格式：0否、1是
		 private Integer isCompare;		//是否需要比较才启动语音事件
		 private Integer compareValue;	//比较值
		 private Long voiceId;			//语音模板id
		 private Integer isDele;			//是否删除,格式：0否、1是
		 private Long updateTimeKey;		//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm
		 */
		db.execSQL("create table" + TABLE_DOG_MAIN + "(id integer primary key, lineId integer, type integer, isCommit integer, isCompare integer, " +
				"compareValue integer, voiceId integer, isDele integer, updateTime integer)");
		/**
		 * 电子狗从表
		 *     private Long id;				//自增id
		 private Long mainId;			//主表id
		 private Double lng;				//经度
		 private Double lat;				//纬度
		 private Integer isDele;			//是否删除,格式：0否、1是
		 private Long updateTimeKey;		//更新时间,无论是新增、修改、删除都需要更新此值,格式：yyyyMMddHHmm
		 */
		db.execSQL("create table" + TABLE_DOB_SECOND + "(id integer primary key, mainId integer, lat double, lng double, isDele integer, updateTime integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
