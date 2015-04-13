package com.tagit.checkinternet_demo;

import java.net.URL;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private EditText url;
	private Button search;
	private WebView web;
	
	@Override
	protected void onResume() {

		super.onResume();
		checkInternet();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		url = (EditText)findViewById(R.id.editTextUrl);
		search = (Button)findViewById(R.id.btnSearch);
		web  = (WebView)findViewById(R.id.webViewPage);
		
		checkInternet();
//		Set the webView
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
				view.loadUrl(url);
				return false;
			}
		});
		
//		Search button click event handler
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(url.getText().toString().length()<1)
				{
					web.loadUrl("http://www.google.com/");
				}else{
					web.loadUrl(url.getText().toString());
				}
				url.setText("http://www.");
				
			}
		});
		
//		When click enter on keyboard , also enable search
		url.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER)
				{
					web.loadUrl(url.getText().toString());
					url.setText("http://www.");
					return true;
				}
				return false;
			}
			
		});
	}
	
	public void checkInternet()
	{
		ConnectivityManager check = (ConnectivityManager)this.getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(check != null)
		{
			NetworkInfo[] info = check.getAllNetworkInfo();
			if(info!=null)
			{
				for(int i=0;i<info.length;i++)
				{
					if(info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
					}
				}
			}
		}else
		{
			Toast.makeText(getBaseContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
		}
	}

	
}
