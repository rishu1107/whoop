package com.whoop.in;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Home_offline_games_Helper extends SQLiteOpenHelper
{	static int version=1;static String dbName="GameDBn";
	String table_name="gameinphone",notify_id,message,date,img_name;
	public Home_offline_games_Helper(Context context)
	{	super(context,dbName,null,version);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{	String sql="create table "+table_name+"(_id integer primary key autoincrement,notify_id text,name_id text,date text,message text,img_name)";
		db.execSQL(sql);
	}
	public void addNotification(String notify_id,String name_id,String dt,String msg,String imgName)
	{	String sql="insert into "+table_name+" (notify_id,name_id,date,message,img_name) values('"+notify_id+"','"+name_id+"','"+dt+"','"+msg+"','"+imgName+"')";
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL(sql);
	}
	public ArrayList<String> getNotification()
	{	ArrayList<String> list=new ArrayList<String>();
		String sql="select * from "+table_name;
		String notify_id,name_id,date,msg,imgName;
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor c=db.rawQuery(sql,null);
		if(c.getCount()>0)
		{	while(c.moveToNext())
			{	notify_id=c.getString(1);
                name_id=c.getString(2);
				date=c.getString(3);
				msg=c.getString(4);
				imgName=c.getString(5);
				list.add(notify_id+"::"+name_id+"::"+date+"::"+msg+"::"+imgName);
			}
		}
		if(c!=null)
			c.close();
		return list;
	}
	public int getNotificationCount()
	{	String sql="select count(*) from "+table_name;int count=0;
		try
		{	SQLiteDatabase db=this.getWritableDatabase();
			Cursor c=db.rawQuery(sql,null);
			if(c!=null)
			{	c.moveToFirst();
				count=c.getInt(0);
			}
		}catch(Exception e){Log.v("Err getNotificationCount()",e.toString());}
		return count;
	}

	public void delMessage(int id)
	{	try
		{	String sql="delete from "+table_name+" where notify_id="+id;
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(table_name,"notify_id = "+id,null);
			Log.v("Deleted","Id:"+id);
		}catch(Exception e){}
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0,int arg1,int arg2)
	{}
}