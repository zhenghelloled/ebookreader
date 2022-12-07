package com.ketai.reader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ketai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteNew extends Activity 
{
	private TextView currentTimeTxt = null;
	private EditText newNoteNameTxt = null;
	private EditText newAuthorNameTxt = null;
	private EditText newContentTxt = null;
	String g_path;
	
	private void save_note() {
		try 
		{
			FileOutputStream outputStream = new FileOutputStream(g_path+"file.txt",true);
			outputStream.write((newNoteNameTxt.getText().toString()+"\r\n").getBytes());
			outputStream.write((newAuthorNameTxt.getText().toString()+"\r\n").getBytes());
			outputStream.write((currentTimeTxt.getText().toString()+"\r\n").getBytes());
			outputStream.close();

			FileOutputStream outputStream2 = new FileOutputStream(g_path+newNoteNameTxt.getText().toString()+".txt");
			outputStream2.write((newNoteNameTxt.getText().toString()+"\r\n").getBytes());
			outputStream2.write((newAuthorNameTxt.getText().toString()+"\r\n").getBytes());
			outputStream2.write((currentTimeTxt.getText().toString()+"\r\n").getBytes());
			outputStream2.write(newContentTxt.getText().toString().getBytes());
			outputStream2.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
		Toast.makeText(NoteNew.this, "**笔记内容**已保存", Toast.LENGTH_LONG)
		.show();
		Intent intent = new Intent(NoteNew.this,Main.class);	
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem saveMenu =menu.add("保存");
		saveMenu.setIcon(R.drawable.save);
		saveMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{	
			public boolean onMenuItemClick(MenuItem item) {
				save_note() ;
				return false;
			}
		});
		MenuItem canMenu =menu.add("返回");
		canMenu.setIcon(R.drawable.re);
		canMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(NoteNew.this,Main.class);	
				startActivity(intent);
				return false;
			}
		});
		return true;
	}	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_addnew);
		
		g_path = android.os.Environment.getExternalStorageDirectory()
				.toString()+"/Download/Weixin/";
		
		currentTimeTxt = (TextView)findViewById(R.id.currenttimetxt);
		newNoteNameTxt = (EditText)findViewById(R.id.newnotenametxt);
		newAuthorNameTxt = (EditText)findViewById(R.id.newauthornametxt);
		newContentTxt = (EditText)findViewById(R.id.newcontenttxt);
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar calender = Calendar.getInstance();
		calender.set(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), 
				calendar.get(calendar.DATE),calendar.get(calendar.HOUR),
				calendar.get(calendar.MINUTE));
		currentTimeTxt.setText(sdf.format(calender.getTime()));
		
		
		findViewById(R.id.btn_note_back).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoteNew.this,Main.class);	
				startActivity(intent);
			}
		});
		
		findViewById(R.id.btn_note_save).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save_note() ;
			}
		});
		
	}
	
}
