package com.ketai.reader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ketai.readdal.ReadSqliteOpenHelper;

import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Toast;
import com.ketai.R;
public class SearchSDcard extends Activity implements OnFileBrowserListener
{
	private String filenameT;
    private SQLiteDatabase sqLiteDatabase;
    private ReadSqliteOpenHelper myOpenHelper;
	@Override
	public void onFileItemClick(String filename)
	{
		setTitle(filename);
		filenameT=filename;
	}

	@Override
	public void onDirItemClick(String path)
	{
		setTitle(path);
		
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myOpenHelper = new ReadSqliteOpenHelper(this,null,null,1);
		sqLiteDatabase = myOpenHelper.getWritableDatabase();
		FileBrowser fileBrowser = (FileBrowser)findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
		
		findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(null == filenameT || filenameT.isEmpty() ||  filenameT.length() <= 0) {
					Toast.makeText(SearchSDcard.this, "请先选中文件再进行收录！", 
							Toast.LENGTH_LONG).show();
					return;
				}
				File file = new File(filenameT);
				
				String name= file.getName(); 
				if(name.endsWith(".txt"))  
				{
					setTitle(""+name.length());
				name=name.substring(0,name.length()-4);
				String sql = "insert into ebook(name,time,auther,path,classify,image,label) values ('"
						+name+"','"+getTime()+"','未知','"+filenameT+"','其他','my',0)";
				sqLiteDatabase.execSQL(sql);
				Toast.makeText(SearchSDcard.this, "收录成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SearchSDcard.this,MyMainActivity.class);	
				startActivity(intent);
				}
				else
					Toast.makeText(SearchSDcard.this, "本软件只收录TXT文档！", Toast.LENGTH_LONG).show();
				//return false;
			}
		});
		
		findViewById(R.id.btn_back_book).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchSDcard.this,MyMainActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem addMenu=menu.add("加入书架");
		addMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				File file = new File(filenameT);
				
				String name= file.getName(); 
				if(name.endsWith(".txt"))  
				{
					setTitle(""+name.length());
				name=name.substring(0,name.length()-4);
				String sql = "insert into ebook(name,time,auther,path,classify,image,label) values ('"
						+name+"','"+getTime()+"','未知','"+filenameT+"','其他','my',0)";
				sqLiteDatabase.execSQL(sql);
				Toast.makeText(SearchSDcard.this, "收录成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SearchSDcard.this,MyMainActivity.class);	
				startActivity(intent);
				}
				else
					Toast.makeText(SearchSDcard.this, "本软件只收录TXT文档！", Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		MenuItem sdcardMenu=menu.add("返回书架");
		sdcardMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(SearchSDcard.this,MyMainActivity.class);
				startActivity(intent);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	public String getTime()
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar calender = Calendar.getInstance();
		calender.set(
				calendar.get(Calendar.YEAR), 
				calendar.get(calendar.MONTH), 
				calendar.get(calendar.DATE),
				calendar.get(calendar.HOUR),
				calendar.get(calendar.MINUTE));
		return sdf.format(calender.getTime());
	}
   
    
}
