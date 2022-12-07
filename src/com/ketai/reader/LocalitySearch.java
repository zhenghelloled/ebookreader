package com.ketai.reader;

import com.ketai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class LocalitySearch extends Activity
{
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem newWBMenu = menu.add("SDcard");
		newWBMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(LocalitySearch.this,SearchSDcard.class);	
				startActivity(intent);
				return false;
			}
		});
		MenuItem canMenu =menu.add("É¾³ý");
		MenuItem returnMenu =menu.add("·µ»Ø");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localitysearch);
		
	}

}
