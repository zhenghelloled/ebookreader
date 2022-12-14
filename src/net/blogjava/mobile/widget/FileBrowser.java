package net.blogjava.mobile.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileBrowser extends ListView implements
		android.widget.AdapterView.OnItemClickListener
{
	private final String namespace = "http://mobile.blogjava.net";
	private String sdcardDirectory;
	private List<File> fileList = new ArrayList<File>();
	private Stack<String> dirStack = new Stack<String>();
	private FileListAdapter fileListAdapter;
	private OnFileBrowserListener onFileBrowserListener;
	private int folderImageResId;
	private int otherFileImageResId;
	private Map<String, Integer> fileImageResIdMap = new HashMap<String, Integer>();
	private boolean onlyFolder = false;
	private int g_pick;

	public FileBrowser(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//sdcardDirectory = android.os.Environment.getExternalStorageDirectory()
		//		.toString()+"/zhihu";
		sdcardDirectory = android.os.Environment.getExternalStorageDirectory()
				.toString()+"/Download/Weixin/";
		Toast.makeText(context, "-->" + sdcardDirectory, Toast.LENGTH_SHORT).show();
		setOnItemClickListener(this);
		setBackgroundColor(android.graphics.Color.BLACK);

		folderImageResId = attrs.getAttributeResourceValue(namespace,
				"folderImage", 0);
		otherFileImageResId = attrs.getAttributeResourceValue(namespace,
				"otherFileImage", 0);
		onlyFolder = attrs.getAttributeBooleanValue(namespace, "onlyFolder",
				false);
		int index = 1;
		
		while (true)
		//for(int i=0; i<15; i++)
		{
			String extName = attrs.getAttributeValue(namespace, "extName"
					+ index);
			int fileImageResId = attrs.getAttributeResourceValue(namespace,
					"fileImage" + index, 0);

			if ("".equals(extName) || extName == null || fileImageResId == 0)
			{
				break;
			}
			fileImageResIdMap.put(extName, fileImageResId);
			index++;
			Log.i("FileBrowser", "FileBrowser,index:"+index);
		}

		dirStack.push(sdcardDirectory);
		addFiles();
		
		

		fileListAdapter = new FileListAdapter(getContext());
		setAdapter(fileListAdapter);
		
	}

	private void addFiles()
	{
		fileList.clear();
		String currentPath = getCurrentPath();
		File[] files = new File(currentPath).listFiles();
		if (dirStack.size() > 1)
			fileList.add(null);
		int i=0;
		for (File file : files)
		{
			Log.i("FileBrowser", "FileBrowser,index:"+i);
			if (onlyFolder)
			{
				if (file.isDirectory()) {
					fileList.add(file);
					i++;
				}
			}
			else
			{
				fileList.add(file);
				i++;
			}
			if(i > 20) {
				break;
			}
		}
	}

	private String getCurrentPath()
	{
		String path = "";
		for (String dir : dirStack)
		{
			path += dir + "/";
		}
		path = path.substring(0, path.length() - 1);
		return path;
	}

	private String getExtName(String filename)
	{

		int position = filename.lastIndexOf(".");
		if (position >= 0)
			return filename.substring(position + 1);
		else
			return "";
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		if (fileList.get(position) == null)
		{
			dirStack.pop();
			addFiles();
			fileListAdapter.notifyDataSetChanged();
			if (onFileBrowserListener != null)
			{
				onFileBrowserListener.onDirItemClick(getCurrentPath());
			}
		}
		else if (fileList.get(position).isDirectory())
		{
			dirStack.push(fileList.get(position).getName());
			addFiles();
			fileListAdapter.notifyDataSetChanged();
			if (onFileBrowserListener != null)
			{
				onFileBrowserListener.onDirItemClick(getCurrentPath());
			}
		}
		else
		{
			if (onFileBrowserListener != null)
			{
				String filename = getCurrentPath() + "/"
						+ fileList.get(position).getName();
				g_pick = position;
				fileListAdapter.notifyDataSetChanged(); // 2022-12-3 15:57:04
				onFileBrowserListener.onFileItemClick(filename);
			}
		}

	}

	private class FileListAdapter extends BaseAdapter
	{
		private Context context;

		public FileListAdapter(Context context)
		{
			this.context = context;
		}

		@Override
		public int getCount()
		{
			return fileList.size();
		}

		@Override
		public Object getItem(int position)
		{
			return fileList.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LinearLayout fileLayout = new LinearLayout(context);
			fileLayout.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			fileLayout.setOrientation(LinearLayout.HORIZONTAL);
			fileLayout.setPadding(5, 10, 0, 10);
			ImageView ivFile = new ImageView(context);
			ivFile.setLayoutParams(new LayoutParams(48, 48));
			TextView tvFile = new TextView(context);
			//if(g_pick == position) {
			//	tvFile.setTextColor(android.graphics.Color.YELLOW);
			//}else {
			//	tvFile.setTextColor(android.graphics.Color.WHITE);
			//}
			tvFile.setTextAppearance(context,
					android.R.style.TextAppearance_Large);

			tvFile.setPadding(5, 5, 0, 0);
			if (fileList.get(position) == null)
			{

				if (folderImageResId > 0)
					ivFile.setImageResource(folderImageResId);
				tvFile.setText(". .");
			}
			else if (fileList.get(position).isDirectory())
			{
				if (folderImageResId > 0)
					ivFile.setImageResource(folderImageResId);
				tvFile.setText(fileList.get(position).getName());
			}
			else
			{
				if(g_pick == position) {
					tvFile.setBackgroundColor(Color.parseColor("#F7f709"));
					tvFile.setTextColor(Color.parseColor("#000000"));
					tvFile.setText(fileList.get(position).getName());
				}else {
					tvFile.setText(fileList.get(position).getName());
				}
				Integer resId = fileImageResIdMap.get(getExtName(fileList.get(
						position).getName()));
				int fileImageResId = 0;
				if (resId != null)
				{
					if (resId > 0)
					{
						fileImageResId = resId;
					}

				}
				if (fileImageResId > 0)
					ivFile.setImageResource(fileImageResId);
				else if (otherFileImageResId > 0)
					ivFile.setImageResource(otherFileImageResId);
			}

			tvFile.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			fileLayout.addView(ivFile);
			fileLayout.addView(tvFile);
			return fileLayout;
		}
	}

	public void setOnFileBrowserListener(OnFileBrowserListener listener)
	{
		this.onFileBrowserListener = listener;
		if(fileList.size() > 0) {
			String filename = getCurrentPath() + "/"
					+ fileList.get(0).getName();
			//fileListAdapter.notifyDataSetChanged(); // 2022-12-3 15:57:04
			onFileBrowserListener.onFileItemClick(filename);
		}
	}
}