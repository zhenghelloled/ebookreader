package com.ketai.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.ketai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteEdit extends Activity 
{
	private String[] info= null;
	private ArrayList<String[]> infoall = new ArrayList<String[]>();
	private String appName;
	private EditText noteNameTxt;
	private EditText authorNameTxt;
	private TextView timeTxt;
	private EditText contentTxt;
	private String s;
	private int flag;
	private FileOutputStream outputStream;
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		MenuItem saveMenu = menu.add(R.string.save_but);
		saveMenu.setIcon(R.drawable.save);
		saveMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			public boolean onMenuItemClick(MenuItem item) 
			{			
				try 
				{
					infoall.remove(flag+1);
					info = new String[3];
					info[0]=noteNameTxt.getText().toString();
					info[1]=authorNameTxt.getText().toString();
					info[2]=timeTxt.getText().toString();
					infoall.add(info);
					outputStream = new FileOutputStream("/mnt/sdcard/file.txt");
					for(int i=0;i<infoall.size();i++)
					{
							info = new String[3];
							info=infoall.get(i);
							outputStream.write((info[0]+"\r\n").getBytes());
							outputStream.write((info[1]+"\r\n").getBytes());
							outputStream.write((info[2]+"\r\n").getBytes());
					}	
					outputStream.close();
					File f = new File("/mnt/sdcard/"+s+".txt");
					f.delete();

					FileOutputStream outputStream2 = new FileOutputStream("/mnt/sdcard/"+noteNameTxt.getText().toString()+".txt");
					outputStream2.write((noteNameTxt.getText().toString()+"\r\n").getBytes());
					outputStream2.write((authorNameTxt.getText().toString()+"\r\n").getBytes());
					outputStream2.write((timeTxt.getText().toString()+"\r\n").getBytes());
					outputStream2.write(contentTxt.getText().toString().getBytes());
					outputStream2.close();
				} catch (Exception e) 
				{
					e.printStackTrace();
				} 
				Toast.makeText(NoteEdit.this, "**笔记内容**已修改", Toast.LENGTH_LONG)
				.show();
				Intent intent = new Intent(NoteEdit.this,Main.class);	
				startActivity(intent);
				return false;
			}
		});
		MenuItem canMenu = menu.add(R.string.exit_but);
		canMenu.setIcon(R.drawable.re);
		canMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {		
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				try 
				{
					Intent intent = new Intent(NoteEdit.this,Main.class);	
					startActivity(intent);
				} catch (Exception e) 
				{
					e.printStackTrace();
				} 
				Intent intent = new Intent(NoteEdit.this,Main.class);	
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
		setContentView(R.layout.note_edit);
		getdata();
		Bundle bundle = getIntent().getExtras();
		s = bundle.getString("name");
		flag = bundle.getInt("flag");
		noteNameTxt = (EditText)findViewById(R.id.editnotenametxt);
		authorNameTxt = (EditText)findViewById(R.id.editauthornametxt);
		timeTxt = (TextView)findViewById(R.id.edittimetxt);
		contentTxt = (EditText)findViewById(R.id.editcontenttxt);
		try
		{
			FileInputStream inputStream = new FileInputStream("//sdcard//"+s+".txt");
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			
			noteNameTxt.setText(br.readLine());
			authorNameTxt.setText(br.readLine());
		    br.readLine();
			contentTxt.setText(br.readLine());
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
			Calendar calender = Calendar.getInstance();
			calender.set(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE),calendar.get(calendar.HOUR),calendar.get(calendar.MINUTE));
			timeTxt.setText(sdf.format(calender.getTime()));
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	public void getdata()
	{
		  try 
			{
				FileInputStream inputStream = new FileInputStream("//sdcard//file.txt");
				InputStreamReader isr = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(isr);
				while((appName=br.readLine())!=null)
				{
					info = new String[3];
					info[0]=appName;
					info[1]=br.readLine();
					info[2]=br.readLine();
					infoall.add(info);
				}			
			} catch (Exception e) 
			{
				e.printStackTrace();
			}		
	}
}
