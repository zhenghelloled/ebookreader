package com.ketai.reader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ketai.readdal.ReadSqliteOpenHelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.ketai.R;
public class RecentlyRead extends Activity 
{
	private SQLiteDatabase sqLiteDatabase;
	private ReadSqliteOpenHelper myOpenHelper;
	private GridView imageGridView = null;
	private ImageView imageView = null;
	private BaseAdapter booksAdapter = null;
	private ArrayList<byte[]> images = new ArrayList<byte[]>();
	private ArrayList<String> pathes = new ArrayList<String>();
	private ArrayList<String> names = new ArrayList<String>();
	
	private ContentResolver contentResolver;
	private  final static String CONTENT_URI_EBOOK = "content://com.ketai.readdal.MyContentProvider/ebook"; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem newWBMenu = menu.add("写书");
		newWBMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(RecentlyRead.this,WriteBook.class);	
				startActivity(intent);
				return false;
			}
		});
		MenuItem labelMenu = menu.add("查看书签");
		labelMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(RecentlyRead.this,LabelList.class);
				startActivity(intent);
				return false;
			}
		});
		MenuItem returnMenu =menu.add("返回");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recentlyread);
		
		contentResolver = getContentResolver();
		myOpenHelper = new ReadSqliteOpenHelper(this,null,null,1);
		sqLiteDatabase = myOpenHelper.getWritableDatabase();
        
		updateimageGridView();
		
		imageGridView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				Intent intent = new Intent(RecentlyRead.this,ReadBook.class);
				intent.putExtra("path", pathes.get(arg2));
				startActivity(intent);
			}
		});
		imageGridView.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{
				Intent intent = new Intent(RecentlyRead.this,ReadBook.class);
				intent.putExtra("path", pathes.get(arg2));
				startActivity(intent);
			}

			public void onNothingSelected(AdapterView<?> arg0) {				
			}
		});
		
		findViewById(R.id.btn_write_book).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecentlyRead.this,WriteBook.class);	
				startActivity(intent);
			}
		});
		
		findViewById(R.id.btn_book_mark).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecentlyRead.this,LabelList.class);
				startActivity(intent);
			}
		});

	}
	public void updateimageGridView()
	{
//		Cursor cursor=sqLiteDatabase.query
//		(
//	      "ebook",
//	      new String[]{"name","path","image"},
//	      null,null,null,null,"time desc"
//		);
//		while(cursor.moveToNext())
//		{	
//			String name = cursor.getString(cursor.getColumnIndex("name"));
//			String path = cursor.getString(cursor.getColumnIndex("path"));
//			byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
//			names.add(name);
//			images.add(image);
//			pathes.add(path);  
//		}
		Uri uri = Uri.parse(CONTENT_URI_EBOOK);
		Cursor cursor = contentResolver.query
		(
			uri,
			new String[]{"name","path","image"}, null, null, "time desc"
		);
		while(cursor.moveToNext())
		{	
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String path = cursor.getString(cursor.getColumnIndex("path"));
			byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
			names.add(name);
			images.add(image);
			pathes.add(path);
		}
		cursor.close();
		imageGridView = (GridView)findViewById(R.id.imageGridView);
		imageGridView.setNumColumns(3);
		booksAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				LinearLayout listviewItem = (LinearLayout)getLayoutInflater().inflate(R.layout.gridview_subitem, null);
				ImageView image = (ImageView)listviewItem.findViewById(R.id.imageItem);
				TextView name = (TextView)listviewItem.findViewById(R.id.textItem);
				Bitmap bitmap = BitmapFactory.decodeByteArray(images.get(position), 0, images.get(position).length, null);
				image.setImageBitmap(bitmap);
				name.setText(names.get(position));
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
				return images.size();
			}
		};
		imageGridView.setAdapter(booksAdapter);
	}

}
