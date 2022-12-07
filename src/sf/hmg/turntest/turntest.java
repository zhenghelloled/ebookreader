package sf.hmg.turntest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ketai.R;
import com.ketai.reader.ReadBook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class turntest extends Activity 
{
	private SQLiteDatabase sqliteDatabase;
	private String path=""; 
	private MenuItem fontsizeMenu=null;
	private MenuItem fontcolorMenu=null;
	private MenuItem linedistanceMenu=null;
	private MenuItem fontlightMeun=null;
	private MenuItem backpicMeun=null;
	private MenuItem savebookmarkMenu=null;
	private int sorce = 20;
	private int sorce1 = 0,position1;
	private int colorsvalue = Color.BLACK;
	private PageWidget mPageWidget;
	private Bitmap mCurPageBitmap, mNextPageBitmap;
	private Canvas mCurPageCanvas, mNextPageCanvas;
	private BookPageFactory pagefactory;
	private int redCol,greenCol,blueCol;
	private Gallery gallery =null;
	private Integer[] views = new Integer[]{R.drawable.bg,R.drawable.green,R.drawable.back,R.drawable.bg,R.drawable.blue};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.readbook);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget);
		mCurPageBitmap = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);

		Bundle bundle = getIntent().getExtras();
		path = bundle.getString("path");
		init();
		mPageWidget.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent e) 
			{
				boolean ret = false;
				if (v == mPageWidget) 
				{
					if (e.getAction() == MotionEvent.ACTION_DOWN) 
					{
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) 
						{
							try
							{
								pagefactory.prePage();
							} 
							catch (IOException e1) 
							{
								e1.printStackTrace();
							}
							if (pagefactory.isfirstPage())
							return false;
							pagefactory.onDraw(mNextPageCanvas);
						} 
						else 
						{
							try 
							{
								pagefactory.nextPage();
							}
							catch (IOException e1) 
							{
								e1.printStackTrace();
							}
							if (pagefactory.islastPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}
		});
	}
	public void init()
	{
		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(colorsvalue,sorce);
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				turntest.this.getResources(), views[position1]));
		try
		{
			pagefactory.openbook(path);
			pagefactory.onDraw(mCurPageCanvas);
			mPageWidget.invalidate();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(this, "电子书不存在,请将《test.txt》放在SD卡目录下",Toast.LENGTH_SHORT).show();
		}
		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		fontsizeMenu=menu.add(1, 1, 1, "滚动阅读模式");
		fontcolorMenu=menu.add(1, 2, 2, "字体设置");
		MyMenuItemClickListener listener=new MyMenuItemClickListener();
		fontsizeMenu.setOnMenuItemClickListener(listener);
		fontcolorMenu.setOnMenuItemClickListener(listener);
		return super.onCreateOptionsMenu(menu);
	}
	class MyMenuItemClickListener implements OnMenuItemClickListener
	{

		@Override
		public boolean onMenuItemClick(MenuItem item) 
		{
			if(item == fontsizeMenu)
			{
				Intent intent = new Intent(turntest.this,ReadBook.class);
				intent.putExtra("path", path);
				startActivity(intent);
			}
			else if(item==fontcolorMenu)
			{
				LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.textsettings, null);
				final SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekBar1);
				SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.seekBar2);
				SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.seekBar3);
				SeekBar seekBar4 = (SeekBar) view.findViewById(R.id.seekBar4);
				final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
				final TextView textView2 = (TextView) view.findViewById(R.id.textView2);
				final TextView textView3 = (TextView) view.findViewById(R.id.textView3);
				final TextView textView4 = (TextView) view.findViewById(R.id.textView4);
				final TextView textView5 = (TextView) view.findViewById(R.id.textView5);
				seekBar1.setProgress(20);
				textView1.setText(""+20);
				textView2.setText(""+redCol);
				textView3.setText(""+greenCol);
				textView4.setText(""+blueCol);
				textView5.setTextSize(20);
				seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {			
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						textView1.setText(""+progress);
						textView5.setTextSize(progress);					}
				});
				seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						redCol = progress;
						textView2.setText(""+progress);
						textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
					}
				});
				seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						greenCol = progress;
						textView3.setText(""+progress);
						textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
					}
				});
				seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						blueCol = progress;
						textView4.setText(""+progress);
						textView5.setTextColor(Color.argb(255, redCol, greenCol, blueCol));
					}
				});
				AlertDialog.Builder builder = new AlertDialog.Builder(turntest.this);
				builder.setTitle("字体设置");
				builder.setView(view);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sorce = seekBar1.getProgress();
						colorsvalue = Color.argb(255, redCol, greenCol, blueCol);
						init();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
			return false;
	}
	
	}


	public String convertCodeAndGetText(String path)
	{
		File file = new File(path);
		BufferedReader reader;
		String text = "";
		try 
		{
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fis);
			in.mark(4);
			byte[] first3bytes = new byte[3];
			in.read(first3bytes);
			in.reset();
			if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF)
			{
				reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			}
			else if (first3bytes[0] == (byte) 0xFF&& first3bytes[1] == (byte) 0xFE)
			{
				reader = new BufferedReader(new InputStreamReader(in, "unicode"));
			}
			else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF)
			{
				reader = new BufferedReader(new InputStreamReader(in,  "utf-16be"));
			}
			else if (first3bytes[0] == (byte) 0xFF  && first3bytes[1] == (byte) 0xFF)
			{
				reader = new BufferedReader(new InputStreamReader(in,  "utf-16le"));
			}
			else
			{
				reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			}
			String str = reader.readLine();
			while (str != null)
			{
				text = text + str + "\n";
				str = reader.readLine();
			}
			reader.close();
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return text;
	}

}
