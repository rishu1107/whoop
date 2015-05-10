package com.whoop.in;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.xwalk.core.XWalkView;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PlayActivity_crosswalk extends Activity {
	
	
	 private XWalkView mXWalkView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play_crosswalk_test);
		mXWalkView = (XWalkView) findViewById(R.id.activity_play_crosswalk_test);
		
			//mXWalkView.load("http://hexgl.bkcore.com/play/", null);
			//mXWalkView.load("http://games.softgames.de/candy-rain-2/?p=pub-10154-10154&", null);
		
			//mXWalkView.load("file:///"+ Environment.getExternalStorageDirectory().toString() +File.separator+ "Encrypt_chk_rishabh"+File.separator+"index.html", null);
			//mXWalkView.load("http://get.webgl.org", null);
			
			mXWalkView.load("http://td2tl.com/codecanyon/popup/", null);
			//mXWalkView.load("file:///android_asset/game/greater-smaller/index_swappinggreatersmaller.html", null);
			
		
		/*
		try {
			InputStream is = getAssets().open("game/greater-smaller/index_swappinggreatersmaller.html");
			int size = is.available();

			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			String str = new String(buffer);
			str = str.replace("greater", "bre");
			mXWalkView.load("file:///android_asset/game/greater-smaller/index_swappinggreatersmaller.html", str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
			*/
			
		
			
			
		//	 mXWalkView.load("http://games.softgames.de/fruit-cut-ninja/?locale=en&p=unknown", null);
			
	   // mXWalkView.load("http://battlefish.clay.io/game/battlefish", null);
	    //mXWalkView.load("https://ibaskethtml5.ludei.com/", null);
		//mXWalkView.load("http://www.goodboydigital.com/pixijs/bunnymark/", null);
		
		//mXWalkView.load("http://www.pocketad.co.in/webservices/index.php?tag=game_view&imei=355666051144664&game_code=TOSSB1TIMET818", null);
		
		
		//onbackcancel =true;
		
		
		//wv.setWebChromeClient(new WebChromeClient());
		/*************wv.addJavascriptInterface(new WebAppInterface(this,this),"Android");**********/
		//wv.loadUrl("file:///android_asset/game/greater-smaller/index_swappinggreatersmaller.html");
		//wv.loadUrl("file:///android_asset/game/greater-smaller/rb.html");
		
	}
	
	
	
	
}
