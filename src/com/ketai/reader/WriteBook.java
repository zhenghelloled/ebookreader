package com.ketai.reader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ketai.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteBook extends Activity 
{
	private TextView currentTimeTxt = null;
	private EditText newNoteNameTxt = null;
	private EditText newAuthorNameTxt = null;
	private EditText newContentTxt = null;
	private EditText newType = null;
	private SQLiteDatabase sqliteDatabase=null;
	private Bitmap icon; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem saveMenu =menu.add("保存");
		saveMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				try 
				{
					Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.book)).getBitmap();
					ByteArrayOutputStream os = new ByteArrayOutputStream(); 
					bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);				
					String name = newNoteNameTxt.getText().toString();
					String auther = newAuthorNameTxt.getText().toString();
					String path = "/mnt/sdcard/novel/"+name+".txt";
					String time = currentTimeTxt.getText().toString();
					String type = newType.getText().toString();
					FileOutputStream outputStream2 = new FileOutputStream(path);
					outputStream2.write(newContentTxt.getText().toString().getBytes());
					outputStream2.close();
					String sql = "insert into ebook(name,time,auther,path,classify,image) values ('"+name+"','"+time+"','"+auther+"','"+path+"','"+type+"','"+os.toByteArray()+"')";
					sqliteDatabase.execSQL(sql);
				} catch (Exception e) 
				{
					e.printStackTrace();
				} 
				Toast.makeText(WriteBook.this, "**小说**已保存", Toast.LENGTH_LONG)
				.show();
				Intent intent = new Intent(WriteBook.this,MyMainActivity.class);	
				startActivity(intent);
				return false;
			}
		});
		MenuItem canMenu =menu.add("返回");
		return true;
	}	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writebook);
		sqliteDatabase = openOrCreateDatabase("ebook.db", MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE, null);
		String sql="create table if not exists ebook(id integer primary key autoincrement,name varcher(20),time varcher(20),auther varcher(20),path varcher(100),classify varcher(20))";
		sqliteDatabase.execSQL(sql);
		init();
	}
	public void init()
	{
		currentTimeTxt = (TextView)findViewById(R.id.currenttimetxt);
		newNoteNameTxt = (EditText)findViewById(R.id.newnotenametxt);
		newAuthorNameTxt = (EditText)findViewById(R.id.newauthornametxt);
		newContentTxt = (EditText)findViewById(R.id.newcontenttxt);
		newType = (EditText)findViewById(R.id.newType);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar calender = Calendar.getInstance();
		calender.set(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE),calendar.get(calendar.HOUR),calendar.get(calendar.MINUTE));
		currentTimeTxt.setText(sdf.format(calender.getTime()));
		
	
	}
}
