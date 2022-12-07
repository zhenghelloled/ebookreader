package com.ketai.reader;

import java.io.File;
import java.util.ArrayList;

import com.ketai.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LabelList extends Activity
{
	private SQLiteDatabase sqliteDatabase=null;
	private ContentResolver contentResolver,contentResolver2;
	private  final static String CONTENT_URI_EBOOK = "content://com.ketai.readdal.MyContentProvider/ebook"; 
	private  final static String CONTENT_URI_MARK = "content://com.ketai.readdal.MyContentProvider/bookmark"; 
	private ListView labelList;
	private BaseAdapter labelBase;
	private ArrayList<String> novelName = new ArrayList<String>();;
	private ArrayList<String> novelPath = new ArrayList<String>();;
	private ArrayList<Integer> novelmark = new ArrayList<Integer>();
	private int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.labellist);
		setTitle("书签管理");
		labelList = (ListView)findViewById(R.id.labelList); 
		contentResolver = getContentResolver();
		updateListView();
		
		labelList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("请选择:");    
			    menu.add(0,1, Menu.NONE, "阅读该书");    
			    menu.add(0,2, Menu.NONE,"删除该书签");   	
			}
		});
		labelList.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		labelList.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					flag=position;
					return false;
				}
			});



	}
	 @Override    
	 public boolean onContextItemSelected(MenuItem item) {    
		 Intent intent = null;
		 switch (item.getItemId()) {   
		 case 1:    
			 intent = new Intent(LabelList.this,ReadBook.class);
			 intent.putExtra("path", novelPath.get(flag));
			 intent.putExtra("mark", novelmark.get(flag));
			 startActivity(intent);
		     break;    
		 case 2:    
		     intent = new Intent(LabelList.this,LabelList.class);
		     String sql = "delete from bookmark where path='"+novelPath.get(flag)+"'";
		     sqliteDatabase.execSQL(sql);
		     new File(novelPath.get(flag)).delete();
			 startActivity(intent);
		         break;    
		   
		     default:    
		         break;    
		     }    
		     return super.onContextItemSelected(item);    
		 }  

	public void updateListView()
	{
		sqliteDatabase = openOrCreateDatabase("ebook.db", MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE, null);
		
		String sql2 = "select ebook.name,ebook.path,bookmark.mark from ebook,bookmark where bookmark.path=ebook.path";
		Cursor cursor = sqliteDatabase.rawQuery(sql2,null);
		
//		Uri uri = Uri.parse(CONTENT_URI_EBOOK);
//		Uri uri2 = Uri.parse(CONTENT_URI_MARK);
//		
//		Cursor cursor = contentResolver.query
//		(
//			uri uri2,
//			new String[]{"name","path","image"}, null, null, "time desc"
//		);
		while(cursor.moveToNext())
		{
			String novelName2=cursor.getString(cursor.getColumnIndex("name"));
			String novelPath2=cursor.getString(cursor.getColumnIndex("path"));
			int novelLabel2 = cursor.getInt(cursor.getColumnIndex("mark"));
			novelName.add(novelName2);
			novelPath.add(novelPath2);
			novelmark.add(novelLabel2);
		}
		cursor.close();
		labelBase = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				LinearLayout listviewItem = (LinearLayout)getLayoutInflater().inflate(R.layout.labellist_item, null);
				TextView name = (TextView)listviewItem.findViewById(R.id.NameTxt);
				TextView path = (TextView)listviewItem.findViewById(R.id.PathTxt);
				TextView label = (TextView)listviewItem.findViewById(R.id.LabelTxt);
				System.out.println(novelName.get(position));
				System.out.println(novelPath.get(position));
				System.out.println(novelmark.get(position));
				name.setText(novelName.get(position));
				path.setText(novelPath.get(position));
				label.setText(novelmark.get(position).toString());
				return listviewItem;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				
				return null;
			}
			
			@Override
			public int getCount() {
				return novelName.size();
			}
		};
		labelList.setAdapter(labelBase);
	}
}
