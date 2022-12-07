package com.ketai.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;

import com.ketai.R;

import sf.hmg.turntest.turntest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ReadBook extends Activity 
{
	private SQLiteDatabase sqliteDatabase=null;
	private ContentResolver contentResolver,contentResolver2;
	private  final static String CONTENT_URI_EBOOK = "content://com.ketai.readdal.MyContentProvider/ebook"; 
	private  final static String CONTENT_URI_MARK = "content://com.ketai.readdal.MyContentProvider/bookmark"; 
	private TextView readbookTxt;
	//private TextView readPages;
	private Button uppage,downpage;
	private SeekBar progressskipSB;
	private String novelTxt="";
	private String path;
	private int mark;
	private int label,readProgress,readSpace,position1;
	private Gallery gallery =null;
	private Integer[] views = new Integer[]{R.drawable.back2,R.drawable.back3,R.drawable.back4};
	private int redCol,greenCol,blueCol;
	
	private boolean font_setting() {
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.textsettings, null);
		SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekBar1);
		SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.seekBar2);
		SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.seekBar3);
		SeekBar seekBar4 = (SeekBar) view.findViewById(R.id.seekBar4);
		final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
		final TextView textView2 = (TextView) view.findViewById(R.id.textView2);
		final TextView textView3 = (TextView) view.findViewById(R.id.textView3);
		final TextView textView4 = (TextView) view.findViewById(R.id.textView4);
		final TextView textView5 = (TextView) view.findViewById(R.id.textView5);
		readProgress = (int) readbookTxt.getTextSize();
		
		seekBar1.setProgress(readProgress);
		textView1.setText(""+readProgress);
		textView2.setText(""+redCol);
		textView3.setText(""+greenCol);
		textView4.setText(""+blueCol);
		textView5.setTextSize(readProgress);
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textView1.setText(""+progress);
				textView5.setTextSize(progress);
				readProgress = progress;
			}
		});
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				redCol = progress;
				textView2.setText(""+progress);
				textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
			}
		});
		seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				greenCol = progress;
				textView3.setText(""+progress);
				textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
			}
		});
		seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				blueCol = progress;
				textView4.setText(""+progress);
				textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(ReadBook.this);
		builder.setTitle("字体设置");
		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				readbookTxt.setTextSize(readProgress);
				readbookTxt.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
		return false;
	
	}
	
	
	private boolean set_line_space() {
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.textspace, null);
		SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekBar1);
		final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
		final TextView textView2 = (TextView) view.findViewById(R.id.textView2);
		textView1.setText(""+1);
		textView2.setLineSpacing(0, 1);
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textView1.setText(""+progress);
				textView2.setLineSpacing(0, progress);
				readSpace = progress;
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(ReadBook.this);
		builder.setTitle("行间距设置");
		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				readbookTxt.setLineSpacing(0, readSpace);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();

		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem continueMenu = menu.add("翻页阅读模式");
		continueMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(ReadBook.this,turntest.class);
				intent.putExtra("path", path);
				startActivity(intent);
				return false;
			}
		});
		MenuItem notenewMenu = menu.add("新建笔记");
		notenewMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(ReadBook.this,NoteNew.class);
				startActivity(intent);
				return false;
			}
		});
		MenuItem scriptSizeMenu =menu.add("字体设置");
		scriptSizeMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return font_setting();
			}
		});
		MenuItem scriptSPace = menu.add("行间距");
		scriptSPace.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return set_line_space();
				
			}
		});
		MenuItem progressskipMenu =menu.add("进度跳转");
		progressskipMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				LinearLayout progressskipLL = (LinearLayout)getLayoutInflater().inflate(R.layout.progressskip_item, null);
				progressskipSB = (SeekBar)progressskipLL.findViewById(R.id.seekbar);
				final TextView seekbarTxt = (TextView)progressskipLL.findViewById(R.id.seekbarTxt);
				AlertDialog.Builder progressskipAD = new AlertDialog.Builder(ReadBook.this);
				progressskipAD.setIcon(R.drawable.a1);
				progressskipAD.setTitle("进度跳转");
				progressskipAD.setView(progressskipLL);
				double flag = (double)(readbookTxt.getScrollY()/readbookTxt.getLineHeight())/(double)readbookTxt.getLineCount()*10000;
				
				progressskipSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
				{
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						double flag2 = progressskipSB.getProgress()/100;
						seekbarTxt.setText("当前进度"+flag2+"%");
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						
					}
				});
				
				progressskipSB.setProgress((int)flag);
				progressskipAD.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								double flag2 = (double)progressskipSB.getProgress()/10000*(double)readbookTxt.getLineCount();
								readbookTxt.scrollTo(0, readbookTxt.getLineBounds((int)flag2, null));
								int line = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
								int pageLine = readbookTxt.getHeight()/readbookTxt.getLineHeight();	
								int i=1;
								//int line2;
								while(true)
								{
									line=line-pageLine;
									if(line>0)
										i++;
									else 	
										break;
								}
								//readPages.setText("当前页为："+i);
							}
						}).setNegativeButton("取消", 
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
										
								}
							}).show();				
				return false;
			}
		});
		MenuItem addLabelMenu =menu.add("添加书签");
		addLabelMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
//				String sql="create table if not exists bookmark(id integer primary key autoincrement,path varcher(200),mark int(200))";
//				sqliteDatabase.execSQL(sql);
//				int label = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
//				String sql2 = "insert into bookmark(path,mark) values ('"+path+"',"+label+")";
//				sqliteDatabase.execSQL(sql2);
				contentResolver2 = getContentResolver();
				int mark = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
				Uri uri = Uri.parse(CONTENT_URI_MARK);
				ContentValues values = new ContentValues();
				values.put("path",path);
				values.put("mark",mark);				
				contentResolver2.insert(uri, values);
				Toast.makeText(ReadBook.this, "书签以添加", Toast.LENGTH_LONG)
				.show();
				return false;
			}
		});
		MenuItem backsetMenu =menu.add("背景设置");
		backsetMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			@Override
			public boolean onMenuItemClick(MenuItem item) 
			{
				LinearLayout loginLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.background, null);
				gallery=(Gallery)loginLayout.findViewById(R.id.gallery);
				gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						position1=position;
						
					}
				});
				List imageList = new ArrayList();
				for (int i = 0; i < views.length; i++) 
				{
					Map<String, Object> imageMap = new HashMap<String, Object>();
					imageMap.put("imageId", views[i]);
					imageList.add(imageMap);
				}
				SimpleAdapter adapter = new SimpleAdapter(ReadBook.this,imageList,R.layout.subitem,new String[]{"imageId"},new int[] {R.id.imageItem1});
				gallery.setAdapter(adapter);
				new AlertDialog.Builder(ReadBook.this).setTitle("背景选择：")
					.setView(loginLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							readbookTxt.setBackgroundResource(views[position1]);
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					}).show();
				return false;
			}
		});
//		MenuItem Menu =menu.add("退出阅读");
//		Menu.setOnMenuItemClickListener(new OnMenuItemClickListener()
//		{	
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				readbookTxt.setSingleLine(false);
//				readbookTxt.setMaxLines(readbookTxt.getHeight()/readbookTxt.getLineHeight());
//				readbookTxt.setMovementMethod(ScrollingMovementMethod.getInstance());
//				return false;
//			}
//		});

		MenuItem exitMenu =menu.add("退出阅读");
		exitMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{	
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int label = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
//				String sql = "update ebook set label="+label+" where path='"+path+"'";
//				sqliteDatabase.execSQL(sql);
				Uri uri = Uri.parse(CONTENT_URI_EBOOK);
				ContentValues values = new ContentValues();
				values.put("label",label);				
				contentResolver.update(uri, values, "path=?",new String[]{path});
				Intent intent = new Intent(ReadBook.this,MyMainActivity.class);	
				startActivity(intent);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.readbook);
		contentResolver = getContentResolver();
//		sqliteDatabase = openOrCreateDatabase("ebook.db", MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE, null);
//		String sql="create table if not exists ebook(id integer primary key autoincrement,name varcher(20),time varcher(20),auther varcher(20),path varcher(100),classify varcher(20),label int(200))";
//		sqliteDatabase.execSQL(sql);

		readbookTxt = (TextView)findViewById(R.id.readbookTxt);
		//readPages = (TextView)findViewById(R.id.readPages);
		uppage = (Button)findViewById(R.id.uppage);
		downpage = (Button)findViewById(R.id.downpage);
		
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		mark = intent.getIntExtra("mark", -1);
		try
		{
			FileInputStream inputStream = new FileInputStream(path);
			int length = inputStream.available();
			byte[] content = new byte[length];
			inputStream.read(content);
			novelTxt = EncodingUtils.getString(content, "UTF-8");
			inputStream.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
//		String sql2 = "select label from ebook where path='"+path+"'";
//		Cursor cursor = sqliteDatabase.rawQuery(sql2,null);
		Uri uri = Uri.parse(CONTENT_URI_EBOOK);
		Cursor cursor = contentResolver.query
		(
			uri,
			new String[]{"label"}, "path=?", new String[]{path}, null
		);
		while(cursor.moveToNext())
		{
			System.out.println(cursor.getInt(cursor.getColumnIndex("label")));
			label=cursor.getInt(cursor.getColumnIndex("label"));	
			System.out.println(label);
		}
		cursor.close();
		
		readbookTxt.setText(novelTxt);
		readbookTxt.setTextColor(Color.BLACK);
		readbookTxt.requestFocus();
		readbookTxt.setMovementMethod(ScrollingMovementMethod.getInstance());
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				if(mark==-1)
				{			
					readbookTxt.scrollTo(0, readbookTxt.getLineBounds(label, null));
					readbookTxt.scrollBy(0, -readbookTxt.getBaseline());
				}
				else 
				{
					System.out.println(mark);
					readbookTxt.scrollTo(0, readbookTxt.getLineBounds(mark, null));
				}
			}
		};
		handler.sendEmptyMessageDelayed(0, 100);
		readbookTxt.setOnTouchListener(new OnTouchListener() {		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int line = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
				int pageLine = readbookTxt.getHeight()/readbookTxt.getLineHeight();	
				int i=1;
				//int line2;
				while(true)
				{
					line=line-pageLine;
					if(line>0)
						i++;
					else 	
						break;
				}
				//readPages.setText("当前页为："+i);
				return false;
			}
		});
		uppage.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				int line = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
				int pageLine = readbookTxt.getHeight()/readbookTxt.getLineHeight();	
				int upline = line-pageLine;
				if(upline<0)
				{
					Toast.makeText(ReadBook.this, "已至首页", Toast.LENGTH_LONG).show();
				}
				else
				{
					readbookTxt.scrollTo(0, readbookTxt.getLineBounds(upline, null));
					int i=1;
					//int line2;
					while(true)
					{
						line=line-pageLine;
						if(line>0)
							i++;
						else 	
							break;
					}
					//readPages.setText("当前页为："+i);
				}
			}
		});
		downpage.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				int line = readbookTxt.getScrollY()/readbookTxt.getLineHeight();
				int pageLine = readbookTxt.getHeight()/readbookTxt.getLineHeight();	
				int  downpage= line+pageLine;
				if(downpage>readbookTxt.getLineCount())
				{
					Toast.makeText(ReadBook.this, "已至尾页", Toast.LENGTH_LONG).show();
				}
				else
				{
					readbookTxt.scrollTo(0, readbookTxt.getLineBounds(downpage, null));
					int i=1;
					//int line2;
					while(true)
					{
						line=line-pageLine;
						if(line>0)
							i++;
						else 	
							break;
					}
					//readPages.setText("当前页为："+i);
				}	
			}
		});
		
		
		findViewById(R.id.btn_font).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 字体设置
				font_setting();
			}
		});
		
		findViewById(R.id.btn_scriptSPace).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 行间距
				// font_setting();
				set_line_space();
			}
		});
	}
}

