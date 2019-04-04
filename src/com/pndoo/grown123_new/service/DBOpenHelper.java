package com.pndoo.grown123_new.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "umybook.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table user(id integer primary key autoincrement,userid varchar(50),username varchar(50),password varchar(50),email varchar(50),booksdata text,isrememberpd varchar(10),publishName varchar(100),publishId varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("ALTER TABLE dairy ADD amount integer");
		db.execSQL("ALTER TABLE user ADD publishId varchar(20)");// alter table
																	// t1 add
																	// column
																	// addr
																	// varchar(20)
																	// not null;
	}

}
