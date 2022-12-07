package com.ketai.reader;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ketai.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OnlineRead extends Activity 
{
	private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlineread);
        
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("http://www.qidian.com/");
		webView.getSettings().setJavaScriptEnabled(true); 
		
		webView.setWebViewClient(new WebViewClient()
		{
			@Override   
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{  
				view.loadUrl(url);
				return true;  
			}	 
		});
//		final Handler handler = new Handler();  
//		webView.addJavascriptInterface(new Object()
//		{
//			public void download(final int id)   
//			{
//				handler.post(new Runnable() {			
//					@Override
//					public void run() 
//					{
//						Toast.makeText(OnlineRead.this, ""+id, Toast.LENGTH_LONG).show();
////						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////						builder.setMessage("");
////						builder.show();
//						try  
//						{			
//							URL url2 = new URL("http://10.0.7.87:8080/MyWeb/"+id+".mp3");
//							HttpURLConnection urlCon = (HttpURLConnection)url2.openConnection();
//							urlCon.setDoInput(true);
//								InputStream is = urlCon.getInputStream();
//							FileOutputStream fos = new FileOutputStream("mnt/sdcard/"+id+"" +
//									"" +
//									".mp3");
//							byte[] bytes = new byte[1024];
//							int position = 0;
//							while((position = is.read(bytes))!=-1)
//							{
//								fos.write(bytes,0,position);
//							}
//							fos.flush();
//							fos.close();
//							is.close();		
//							
//							Toast.makeText(OnlineRead.this, "œ¬‘ÿ≥…π¶", Toast.LENGTH_LONG).show();
////							File file = new File("/mnt/sdcard/book.apk");
////							Intent intent = new Intent();
////							intent.setAction(Intent.ACTION_VIEW);
////							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////							intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
////							startActivity(intent);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});   
//			}
//		}, "androidObj");
    }
}