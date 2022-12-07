package com.ketai.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.ketai.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class NoteCheck extends ListActivity 
{
	String checkTxt;
	private String appName; 
	private String[] info= null;
	private ArrayList<String[]> infoall = new ArrayList<String[]>();
	private String[] currentAppInfo;
	private Boolean flag = false;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getListView().setBackgroundResource(R.drawable.image1);
		Bundle bundle = getIntent().getExtras();
		checkTxt=bundle.getString("checktxt");
		getdata();
		if(flag=false)
		{
			Toast.makeText(NoteCheck.this, "Î´ÕÒµ½ÄÚÈÝ", Toast.LENGTH_LONG)
			.show();
		}
		ListAdapter adapter = new ListAdapter();
		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() 
		 {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(NoteCheck.this,NoteRead.class);
				   info=infoall.get(arg2);
				   intent.putExtra("name", info[0]);
				   intent.putExtra("flag", arg2);
				   startActivity(intent);
			}
		});
	}

	public void getdata()
	{
		  try 
			{
				FileInputStream inputStream = new FileInputStream("//sdcard//file.txt");
				InputStreamReader isr = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(isr);
				int position=0;
				while((appName=br.readLine())!=null)
				{
					info = new String[4];
					info[0]=appName;
					info[1]=br.readLine();
					info[2]=br.readLine();
					info[3]=""+position;
					if(info[0].indexOf(checkTxt)>=0)
					{
							position++;	
							infoall.add(info);
							flag=true;
					}
				}
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}		
	}

	class ListAdapter extends BaseAdapter
	{

		public int getCount() {
			return infoall.size();
		}

		public Object getItem(int position) {			
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			currentAppInfo = infoall.get(position);
			LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.listview_rating, null);
			ImageView logoimg = (ImageView)view.findViewById(R.id.logoImg);
			TextView timeTxt = (TextView)view.findViewById(R.id.timeTxt);
			TextView appNameTxt = (TextView)view.findViewById(R.id.appNameTxt);
			TextView authorNameTxt = (TextView)view.findViewById(R.id.authorNameTxt);
				
			logoimg.setBackgroundResource(R.drawable.check);
			appNameTxt.setText(currentAppInfo[0]);
			authorNameTxt.setText(currentAppInfo[1]);
			timeTxt.setText(currentAppInfo[2]);	
			
			return view;
		}
		
	}

}

