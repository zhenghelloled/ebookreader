package com.ketai.reader;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ketai.R;
public class WelcomeReader extends Activity 
{
	DrawWaterWave m_DrawWaterWave;
//	private Button Login = null;
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.welcome);
		
		super.onCreate(savedInstanceState);  
		setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		setContentView(R.layout.welcome);
		/*
		m_DrawWaterWave = new DrawWaterWave(this); 
		m_DrawWaterWave.DropStone(160,240,20,200);
		setContentView(m_DrawWaterWave);
		*/
	
		MyThread thread = new MyThread();
	    handler.postDelayed(thread, 2000);
	}
	class MyThread extends Thread
	{
		public void run()
		{
			Intent intent = new Intent(WelcomeReader.this,MyMainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}

//		Login = (Button)findViewById(R.id.login);
//		Login.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View view) 
//			{
//				Intent intent = new Intent(WelcomeReader.this,MyMainActivity.class);	
//				startActivity(intent);
//			}
//		});
