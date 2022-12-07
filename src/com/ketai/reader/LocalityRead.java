package com.ketai.reader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

import com.ketai.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LocalityRead extends Activity
{
	private ArrayList<String> novelClassify = new ArrayList<String>();
	private String novelClassify2 = "";
	private ArrayList<String> novelName = null;
	private ArrayList<String> novelPath = null;
	private int[] images=new int[]{R.drawable.xuanhuan,R.drawable.wuxia,R.drawable.dushi,R.drawable.kehuan,R.drawable.other};
	private Spinner bookSpinner=null;
	private ListView bookList = null;
	private SQLiteDatabase sqliteDatabase=null;
	private int position=-1;
	private EditText CheckName;
	private EditText modName;
	private EditText modAuther;
	private EditText modClassify;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem addNewMenu =menu.add("修改");
		addNewMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				to_book_alter();
				return false;
			}
		});
		
		MenuItem delMenu =menu.add("删除");
		delMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				to_book_delete();
				return false;   
				
			}
		});
		MenuItem returnMenu =menu.add("查询");
		returnMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				to_book_query();
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localityread);
		
		bookSpinner = (Spinner)findViewById(R.id.bookSpinner);
		bookList = (ListView)findViewById(R.id.bookList);
		
		sqliteDatabase = openOrCreateDatabase("ebook.db", MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE, null);
		String sql="create table if not exists ebook(id integer primary key autoincrement,name varcher(20),time varcher(20),auther varcher(20),path varcher(100),classify varcher(20),label int(200))";
		sqliteDatabase.execSQL(sql);

		String sql2 = "select classify from ebook"; // "select classify from ebook"
		Cursor cursor = sqliteDatabase.rawQuery(sql2,null);
		Log.i("java", "locality, cursor#1 ");
		while(cursor.moveToNext())
		{
			
			novelClassify2=cursor.getString(cursor.getColumnIndex("classify")); // classify
			Log.i("java", "locality, cursor : " + novelClassify2);
			boolean flag=true;
			for(String x:novelClassify)
			{
				if(x.equals(novelClassify2))
				{
					Log.i("java", "x : " + x );
					flag=false;
				}
			}
			if(flag)
			{
				novelClassify.add(novelClassify2);
			}

		}
		cursor.close();
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocalityRead.this,android.R.layout.simple_spinner_item,novelClassify);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocalityRead.this,R.layout.simple_spinner_item001, novelClassify);
		bookSpinner.setAdapter(adapter);
		
        bookSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				novelName = new ArrayList<String>();
				novelPath = new ArrayList<String>();
				String sql2 = "select name,path from ebook where classify='"+novelClassify.get(arg2)+"'";
				//String sql2 = "select name,path from ebook ";
				Cursor cursor = sqliteDatabase.rawQuery(sql2,null);
				Log.i("java", "ADD start ");
				while(cursor.moveToNext())
				{
					String novelName2=cursor.getString(cursor.getColumnIndex("name"));
					String novelPath2=cursor.getString(cursor.getColumnIndex("path"));
					Log.i("java", "ADD: " + novelName2);
					novelName.add(novelName2);
					novelPath.add(novelPath2);
				}
				Log.i("java", "ADD end ");
				cursor.close();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocalityRead.this,android.R.layout.simple_list_item_single_choice,novelName);
				bookList.setAdapter(adapter);	
				bookList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				
			}		
		});
        bookList.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				position=arg2;			
			}
		});
        bookList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(LocalityRead.this,ReadBook.class);
				intent.putExtra("path",novelPath.get(position));
				startActivity(intent);
				return false;
			}
        	
		});
        
        
        findViewById(R.id.btn_alter).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				to_book_alter();
			}
		});
        
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				to_book_delete() ;
			}
		});
        
        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				to_book_query();
			}
		});
        
	}
	
	private void to_book_alter() {
		AlertDialog.Builder builder = new AlertDialog.Builder(LocalityRead.this);
		builder.setTitle("对书籍的修改");
		builder.setMessage("书名");
		LinearLayout ratingLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.localityread_modification, null);
		modName = (EditText)ratingLayout.findViewById(R.id.modName);
		modAuther = (EditText)ratingLayout.findViewById(R.id.modAuther);
		modClassify = (EditText)ratingLayout.findViewById(R.id.modclass);
		builder.setView(ratingLayout);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String sql2 = "select id from ebook where name='"+novelName.get(position)+"'";
				int id=0;
				Cursor cursor = sqliteDatabase.rawQuery(sql2,null);
				while(cursor.moveToNext())
				{
					id=cursor.getInt(cursor.getColumnIndex("id"));		
				}
				cursor.close();
				String sql3 = "update ebook set name='"+modName.getText().toString()+"',auther='"
				+modAuther.getText().toString()+"',classify='"
				+modClassify.getText().toString()+"' where id="+id;
				sqliteDatabase.execSQL(sql3);
				Intent intent = new Intent(LocalityRead.this,MyMainActivity.class);	
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
	
	private void to_book_delete() {
		System.out.println(novelName.get(position));
		String sql = "delete from ebook where name='"+novelName.get(position)+"'";
		sqliteDatabase.execSQL(sql);
		File f = new File("/mnt/sdcard/novel/"+novelName.get(position)+".txt");
		f.delete();
		Toast.makeText(LocalityRead.this, "小说信息已删除", Toast.LENGTH_LONG)
		.show();
		Intent intent = new Intent(LocalityRead.this,MyMainActivity.class);	
		startActivity(intent);
	}
	
	private void to_book_query() {
		AlertDialog.Builder builder = new AlertDialog.Builder(LocalityRead.this);
		builder.setTitle("对书籍的查询");
		builder.setMessage("输入你要查找的内容：");
		LinearLayout ratingLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.localityread_check, null);
		CheckName = (EditText)ratingLayout.findViewById(R.id.CheckName);
		builder.setView(ratingLayout);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<String> novelName3 = new ArrayList<String>();
				boolean flag = false;
				for(String x:novelName)
				{
					if(x.indexOf(CheckName.getText().toString())>=0)
					{
						novelName3.add(x);
					}
				}
				if(novelName3.size()==0)
				{
					Toast.makeText(LocalityRead.this, "未找到任何相书籍！", Toast.LENGTH_LONG)
					.show();
				}
				else 
				{
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocalityRead.this,android.R.layout.simple_list_item_single_choice,novelName3);
					bookList.setAdapter(adapter);	
					bookList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				}
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
}
