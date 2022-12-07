package com.ketai.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.ketai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

public class NoteRead extends Activity 
{
	private TextView noteNameTxt;
	private TextView authorNameTxt;
	private TextView timeTxt;
	private TextView contentTxt;
	private String appName;
	private int flag;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem editMenu = menu.add(R.string.edit_but);
		editMenu.setIcon(R.drawable.check);
		editMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			public boolean onMenuItemClick(MenuItem item) 
			{
				Intent intent = new Intent(NoteRead.this,NoteEdit.class);	
				intent.putExtra("name", appName);
				intent.putExtra("flag", flag);
				startActivity(intent);
				return false;
			}
		});
		MenuItem exitMenu = menu.add(R.string.exit_but);
		exitMenu.setIcon(R.drawable.re);
		exitMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(NoteRead.this,Main.class);
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
		setContentView(R.layout.note_read);
		Bundle bundle = getIntent().getExtras();
		noteNameTxt = (TextView)findViewById(R.id.notenametxt);
		authorNameTxt = (TextView)findViewById(R.id.authornametxt);
		timeTxt = (TextView)findViewById(R.id.timetxt);
		contentTxt = (TextView)findViewById(R.id.contenttxt);
		appName=bundle.getString("name");
		flag=bundle.getInt("falg");
		try
		{
			FileInputStream inputStream = new FileInputStream("//sdcard//"+bundle.getString("name")+".txt");
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			
			noteNameTxt.setText(br.readLine());
			authorNameTxt.setText(br.readLine());
			timeTxt.setText(br.readLine());
			contentTxt.setText(br.readLine());
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
}
