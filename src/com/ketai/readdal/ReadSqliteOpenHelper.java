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
		//1.�������ݿ�
		super(context, dbName, null, version);
	}
	//�������ݿ�ʱ��ͬʱ����onCreate����
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table if not exists ebook(id integer primary key autoincrement,name varcher(20),time varcher(20),auther varcher(20),path varcher(100),classify varcher(20),image binary,label int(200))";
    	db.execSQL(sql);
    	String sql2="create table if not exists bookmark(id integer primary key autoincrement,path varcher(200),mark int(200))";
    	db.execSQL(sql2);
	}
	//�����ݿ�İ汾�����仯ʱ�����onUpgrade
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
