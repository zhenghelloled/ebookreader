package com.ketai.reader;

import com.ketai.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MyMainActivity extends TabActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		TabHost th = getTabHost();
		
		TabSpec tabSpec1 = th.newTabSpec("tab1");
		tabSpec1.setIndicator("����Ķ�",getResources().getDrawable(R.drawable.a1));
		tabSpec1.setContent(new Intent(this,RecentlyRead.class));
		
		TabSpec tabSpec2 = th.newTabSpec("tab2");
		tabSpec2.setIndicator("�������",getResources().getDrawable(R.drawable.a2));
		tabSpec2.setContent(new Intent(this,LocalityRead.class)); // LocalityRead.class
		
		TabSpec tabSpec3 = th.newTabSpec("tab3");
		tabSpec3.setIndicator("��������",getResources().getDrawable(R.drawable.a2));
		tabSpec3.setContent(new Intent(this,SearchSDcard.class));
		
		TabSpec tabSpec4 = th.newTabSpec("tab4");
		tabSpec4.setIndicator("�������",getResources().getDrawable(R.drawable.a3));
		tabSpec4.setContent(new Intent(this,OnlineRead.class));
		
		TabSpec tabSpec5 = th.newTabSpec("tab5");
		tabSpec5.setIndicator("�ҵıʼ�",getResources().getDrawable(R.drawable.a3));
		tabSpec5.setContent(new Intent(this,Main.class));
		th.addTab(tabSpec1);
		th.addTab(tabSpec2);
		th.addTab(tabSpec3);
		th.addTab(tabSpec4);
		th.addTab(tabSpec5);
		Log.i("hello", "hello");
	}

}
