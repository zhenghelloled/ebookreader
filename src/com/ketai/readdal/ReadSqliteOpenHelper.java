package com.ketai.readdal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ReadSqliteOpenHelper extends SQLiteOpenHelper
{
	private static String dbName = "ebook.db";
	
	public ReadSqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) 
	{
		//1.创建数据库
		super(context, dbName, null, version);
	}
	//创建数据库时会同时调用onCreate方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table if not exists ebook(id integer primary key autoincrement,name varcher(20),time varcher(20),auther varcher(20),path varcher(100),classify varcher(20),image binary,label int(200))";
    	db.execSQL(sql);
    	String sql2="create table if not exists bookmark(id integer primary key autoincrement,path varcher(200),mark int(200))";
    	db.execSQL(sql2);
	}
	//当数据库的版本发生变化时会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "drop table if exists ebook";
		db.execSQL(sql);
		String sql2 = "drop table if exists bookmark";
		db.execSQL(sql2);
		onCreate(db);

	}

}
