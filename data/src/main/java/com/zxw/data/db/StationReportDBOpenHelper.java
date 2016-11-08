package com.zxw.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StationReportDBOpenHelper extends SQLiteOpenHelper {

//	public static final String TABLE_LINE_STATION_REPORT = " line_station_report ";
	public static final String TABLE_SERVICE_WORD = " service_word ";
	public static final String TABLE_VOICE_COMPOUND = " voice_compound ";
	public static final String TABLE_LINE = " line ";
	public static final String TABLE_STATION = " station ";
	public static final String TABLE_LINE_STATION = " tb_line_station ";
	public static final String TABLE_REPORT_POINT = " tb_report_point ";

	public StationReportDBOpenHelper(Context context) {
		super(context, "stationReport.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//线路站点报站点表bean
//		db.execSQL("create table" + TABLE_LINE_STATION_REPORT + "(_id integer primary key autoincrement,type integer, lineId integer, stationId integer, " +
//				"lng double, lat double, isDele integer, sn integer)");
		// 服务用语
		db.execSQL("create table" + TABLE_SERVICE_WORD + "(id integer primary key, title varchar(80), keyCode varchar(80), content varchar(80), isDele integer, updateTime integer)");
		// 语音合成模板
		db.execSQL("create table" + TABLE_VOICE_COMPOUND + "(id integer primary key, type integer, content varchar(80), isDele integer, updateTime integer)");
		// 线路名字 id
		db.execSQL("create table" + TABLE_LINE + "(lineId integer primary key, lineName varchar(40), type integer, isDele varchar(20), updateTime integer)");
		// 站点名字 经纬度 ID
		db.execSQL("create table" + TABLE_STATION + "(id integer primary key, realId integer, stationName varchar(40), lng double, lat double, isDele integer, updateTime integer)");
		// 线路有什么站点
		db.execSQL("create table" + TABLE_LINE_STATION + "(id integer primary key, lineId integer, stationId integer, sortNum integer, isDele integer, updateTime integer)");

		// 线路站点报站点信息
		db.execSQL("create table" + TABLE_REPORT_POINT + "(id integer primary key, isDele integer, keyCode varchar(80),lat double, lineId integer, lng double, stationId integer, type integer, updateTime integer,mainId integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
