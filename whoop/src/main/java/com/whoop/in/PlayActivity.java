package com.whoop.in;


import java.util.Timer;
import java.util.TimerTask;


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


public class PlayActivity extends Activity {
	
	public static WebView wv;
	boolean doubleBackToExitPressedOnce=false;
	WebSettings webSettings;static Context context;
	String GamePlayUrl;
	static String highscorefromshared="0",highscore,highscorefromgame;
	public static final String HIGH_SCORES = "HighScores";
	private SharedPreferences prefs;
	static int highscorefromgameint,highscorefromsharedint=0;
	String onbackcancelhandler="rb",adnotrunning="adnotrunning";
	static String greatgod="hg";
	static boolean onbackcancel =true;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play);
		

		//onbackcancel =true;
		
		context=this.getApplicationContext();
		wv=(WebView)findViewById(R.id.webview);
		
		WebSettings webSettings=wv.getSettings();
		wv.getSettings().setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		//wv.setWebChromeClient(new WebChromeClient());
		wv.addJavascriptInterface(new WebAppInterface(this,this),"Android");
		//wv.loadUrl("file:///android_asset/game/greater-smaller/index_swappinggreatersmaller.html");
		//wv.loadUrl("file:///android_asset/game/greater-smaller/rb.html");
		sharedprefgethighscore();
	}
	
	
	@JavascriptInterface
	public static void sharedprefgethighscore() 
	{
		try
		{
			SharedPreferences prefs = context.getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
			 highscorefromshared = prefs.getString("High_Score", highscore);
			 if (highscorefromshared!=null) 
			 {
				 Log.v("highscorefromshared",highscorefromshared); 
				 wv.loadUrl("file:///android_asset/stickhero/index.html"); 
				 //wv.loadUrl("https://html5test.com");
				//wv.loadUrl("http://games.softgames.de/the-tower/?locale=en&p=unknown");
		 	 }
			
			 else
			 {
				 highscorefromshared="0";
				 //sharedprefgethighscore();
				 Log.v("highscorefromshared","null");
				 wv.loadUrl("file:///android_asset/game/greater-smaller/index_instructions.html");
				 
				 
			 }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 
		 
	}
	
	
	@JavascriptInterface
	public static void sharedprefsendhighscore() 
	{
			
 			SharedPreferences prefs = context.getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString("High_Score", highscore);
		    Log.v("highscorefromgamesenttoshared",highscorefromgame);
		    editor.commit();
	 	
	}
	
	@JavascriptInterface
	 public static void sharedprefcomparehighscore() 
	 {
		 
		
		 	try 
			{
				
			 highscorefromgameint=Integer.parseInt(highscorefromgame);
			 highscorefromsharedint=Integer.parseInt(highscorefromshared);
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		 	
		 	try
		 	{
		 		if (highscorefromgameint>highscorefromsharedint)
			 	{
		 			highscore=highscorefromgame;
		 			Log.v("highscorefromgame>highscorefromshared",highscore);
		 			sharedprefsendhighscore();
			 	}
		 		else 
		 		{
		 			highscore=highscorefromshared;
		 			Log.v("highscorefromgame<highscorefromshared",highscore);
		 		}
		 	}
		 	catch (Exception e)
			{
				e.printStackTrace();
			}
		 	
			 	
	 }
		
	

	
	

	

	
	
	@Override
	public void onBackPressed() {
		
		if(WebAppInterface.openpopup.equalsIgnoreCase("open"))
		{
			
			super.onBackPressed();
			onbackcancel=false;
			onbackcancelhandler="finish";
			WebAppInterface.diplayad="rb";
			Log.v("onback_valueofonbackcancelhandler",onbackcancelhandler);
			finish();
		}
		else
		{
			if (doubleBackToExitPressedOnce) {
		        super.onBackPressed();
		        return;
		    }
	
		    this.doubleBackToExitPressedOnce = true;
		    Toast.makeText(this, "Press again to exit Play", Toast.LENGTH_SHORT).show();
	
		    new Handler().postDelayed(new Runnable() {
	
		        @Override
		        public void run() {
		            doubleBackToExitPressedOnce=false;                       
		        }
		    }, 2000);
		}
		
		   
	} 
	
}
