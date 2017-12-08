package com.nxt.ott.util;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHelper {
	private static final String TableName = "talk";
	private SQLiteDatabase myDataBase;
	private DataBaseHelper dbhelper;
    private Context context;
    private SharedPreferences spf;
	public DbHelper(Context context) {
		dbhelper = new DataBaseHelper(context);
		myDataBase = dbhelper.openDataBase();
		this.context=context;
		spf=context.getSharedPreferences("spf", Context.MODE_PRIVATE);
	}

	public void createuser() {
		String sql = "CREATE TABLE " + TableName
				+ "(_id integer primary key autoincrement,groupname VARCHAR)";

		try {
			myDataBase.execSQL(sql);
			spf.edit().putBoolean("isgptbexit",true).commit();  
		} catch (SQLException e) {
           e.printStackTrace();
		}
	}

	public void insertgroup(String groupname) {
		ContentValues values = new ContentValues();

		values.put("groupname", groupname);
		myDataBase.insert(TableName, null, values);
	}

	public List<String> queryAllGroup() {
     List<String> list=new ArrayList<String>();
		Cursor cursor=myDataBase.query(TableName, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getColumnName(0));
		}
		return null;
	}
	public String queryWeather(String zkind, String zwid) {
		Map<String, String> map = new HashMap<String, String>();
		String ztitle = null;
		
		
		String sql = "select * from ZWEATHER where ZKIND='" + zkind + "'"
				+ " AND ZWID='" + zwid + "'";
		// System.out.println(sql);
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ztitle = cursor.getString(cursor.getColumnIndex("ZTITLE"));
				System.out.println("-----ztitle----"+ztitle);
				/*zdetail = cursor.getString(cursor.getColumnIndex("ZDETAIL"));
				zsuoxie = cursor.getString(cursor.getColumnIndex("ZSUOXIE"));
				zname = cursor.getString(cursor.getColumnIndex("ZNAME"));*/
			}
		}
	/*	map.put("ztitle", ztitle);
		map.put("zdetail", zdetail);
		map.put("zsuoxie", zsuoxie);
		map.put("zname", zname);
		cursor.close();*/
		//myDataBase.close();
		return ztitle;
	}
}
