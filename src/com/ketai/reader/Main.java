package com.ketai.reader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.ketai.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity
{
	private ListView m_list = null;
	private RatingBar newRatingBar;
	private String appName; 
	private ListAdapter adapter;
	private int flag=0;
	private String[] info= null;
	private ArrayList<String[]> infoall = new ArrayList<String[]>();
	private ArrayList<Integer> infoflag = new ArrayList<Integer>();
	private ArrayList<String> address = new ArrayList<String>();
	private String[] currentAppInfo;
	private FileOutputStream outputStream;
	private EditText newCheckTxt;
	private String check;
	String g_path;
	
	private void book_delete() {
		for(int i=0;i<infoall.size();i++)
		{
			for(int j=0;j<infoflag.size();j++)
			{
				Integer currentPosition = infoflag.get(j);
				address.add(infoall.get(i)[0]);
				if((""+currentPosition.intValue()).equals(infoall.get(i)[3]))
				{
					infoall.remove(i);
					infoflag.remove(j);
					i=-1;
					break;
				}
			}
		}
		adapter.notifyDataSetChanged();
		
		
		for(int i=0;i<address.size();i++)
		{
			File f = new File(g_path+address.get(i)+".txt");
			f.delete();
		}
		try {
			outputStream = new FileOutputStream(g_path+"file.txt");
			
			for(int i=0;i<infoall.size();i++)
			{
				info = new String[3];
				info=infoall.get(i);
				outputStream.write((info[0]+"\r\n").getBytes());
				outputStream.write((info[1]+"\r\n").getBytes());
				outputStream.write((info[2]+"\r\n").getBytes());
			}	
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void my_book_quety() {
		Builder builder = new Builder(Main.this);
		builder.setIcon(R.drawable.check);
		builder.setTitle("查找");
		builder.setMessage("输入你要查找的内容");
		LinearLayout ratingLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.note_check, null);
		newCheckTxt = (EditText)ratingLayout.findViewById(R.id.newcheckTxt);
		builder.setView(ratingLayout);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
			public void onClick(DialogInterface dialog, int which) {
				check = newCheckTxt.getText().toString();
				adapter.notifyDataSetChanged();
				Intent intent = new Intent(Main.this,NoteCheck.class);	
				intent.putExtra("checktxt", check);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
		adapter.notifyDataSetChanged();
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
    {
    	
    	MenuItem delMenu = menu.add(R.string.del_but);   
    	delMenu.setIcon(R.drawable.del);
    	delMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			public boolean onMenuItemClick(MenuItem item) 
			{
				book_delete();
				return false;
			}
		});
    	MenuItem checkMenu = menu.add(R.string.check_but);
    	checkMenu.setIcon(R.drawable.check);
    	checkMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
    		public boolean onMenuItemClick(MenuItem item) 
    		{
    			my_book_quety();
    			return false;
			}
		});
    	MenuItem welcomeMenu = menu.add(R.string.about_but);
    	welcomeMenu.setIcon(R.drawable.welcome);
    	welcomeMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {	
			public boolean onMenuItemClick(MenuItem item) 
			{
				Toast.makeText(Main.this, "**我的笔记本**", Toast.LENGTH_LONG)
				.show();
				return false;
			}
		});
		MenuItem newNoteMenu = menu.add(R.string.newaNote_but);
		newNoteMenu.setIcon(R.drawable.add);
		newNoteMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(Main.this,NoteNew.class);	
				startActivity(intent);
				return false;
			}
		});
		MenuItem exitMenu = menu.add(R.string.exit_but);
		exitMenu.setIcon(R.drawable.exit);
		return true;
	}

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybook);
		m_list = (ListView) findViewById(R.id.mybooklist);
		m_list.setBackgroundResource(R.drawable.image1);
		g_path = android.os.Environment.getExternalStorageDirectory()
				.toString()+"/Download/Weixin/";
		getdata();
		adapter = new ListAdapter();
		m_list.setAdapter(adapter); // setListAdapter(adapter);
		
		//ListView lv = getListView();
		// listView的项的点击事件
		m_list.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
			    CheckBox checkBox =(CheckBox)arg1.findViewById(R.id.checkBox);
				if(checkBox.isChecked())
				{
					checkBox.setChecked(false);
					for(int i=0;i<infoflag.size();i++)
					{
						if(infoflag.get(i)==arg2)
						{
							infoflag.remove(i);
						}
					}
				}
				else
				{			
					checkBox.setChecked(true);			
					infoflag.add(arg2);
				}
			}		
		});
		m_list.setOnItemLongClickListener(new OnItemLongClickListener() 
		 {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				   Intent intent = new Intent(Main.this,NoteRead.class);
				   info=infoall.get(arg2);
				   intent.putExtra("name", info[0]);
				   intent.putExtra("flag", arg2);
				   startActivity(intent);
				   return false;
				}
			});
		
		
		findViewById(R.id.btn_my_delete).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				book_delete();
			}
		});
		
		findViewById(R.id.btn_my_query).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				my_book_quety() ;
			}
		});

		findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Main.this, "**我的笔记本**", Toast.LENGTH_LONG)
				.show();
			}
		});
		
		findViewById(R.id.btn_newnote).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Main.this,NoteNew.class);	
				startActivity(intent);
			}
		});


    }
	public void getdata()
	{
		  try 
			{
				FileInputStream inputStream = new FileInputStream(g_path+"file.txt");
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
					position++;
					infoall.add(info);
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