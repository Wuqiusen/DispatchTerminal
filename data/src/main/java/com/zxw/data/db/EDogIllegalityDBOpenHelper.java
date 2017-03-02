package com.zxw.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EDogIllegalityDBOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = " illegality ";

	public EDogIllegalityDBOpenHelper(Context context) {
		super(context, "illegality.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/**
		 * 电子狗主表

		 private int opId;
		 private String time;
		 private int eventType;
		 private String photoPath1;
		 private String photoPath2;
		 private String videoPath;
		 private String driverId;
		 */
		db.execSQL("create table" + TABLE_NAME + "(id integer primary key, opId integer, time varchar(60), eventType integer, photoPath1 varchar(80), " +
				"photoPath2 varchar(80), videoPath varchar(80), driverId varchar(80))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
