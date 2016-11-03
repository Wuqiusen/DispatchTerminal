package com.zxw.dispatch_driver.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StationReportDBOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_LINE_STATION_REPORT = " line_station_report ";
	public static final String TABLE_SERVICE_WORD = " service_word ";
	public static final String TABLE_VOICE_COMPOUND = " voice_compound ";
	public static final String TABLE_LINE = " line ";
	public static final String TABLE_STATION = " station ";

	public StationReportDBOpenHelper(Context context) {
		super(context, "stationReport.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//线路站点报站点表bean
		db.execSQL("create table" + TABLE_LINE_STATION_REPORT + "(_id integer primary key autoincrement,type integer, lineId integer, stationId integer, " +
				"lng double, lat double, isDele integer, sn integer)");
		// 服务用语
		db.execSQL("create table" + TABLE_SERVICE_WORD + "(_id integer primary key autoincrement, title varchar(20), content varchar(80), isDele integer)");
		// 语音合成模板
		db.execSQL("create table" + TABLE_VOICE_COMPOUND + "(_id integer primary key autoincrement, type integer, content varchar(80), isDele integer)");
		db.execSQL("create table" + TABLE_LINE + "(_id integer primary key autoincrement, lineId integer, lineName varchar(40), isDele integer)");
		db.execSQL("create table" + TABLE_STATION + "(_id integer primary key autoincrement, stationId integer, stationName varchar(40), isDele integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
