package com.whoop.in;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;











import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.Contacts.People;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.MediaStore;


public class MainActivity extends Activity
{
	 String fontPath = "fonts/ALWAYS IN MY HEART.TTF";
	 int timeforvibration=500;
	 LinearLayout soundlin,vibrationlin,sharelin,playlin,ratelin;
	 static ImageView soundim,vibrationim,greatersmallerthan,imgView1,imgView2,imgView3,imgView4;
	 static String vibrationtag="vibrationison",soundtag="soundison";
	 Typeface tf;
	 private static int RESULT_LOAD_IMAGE = 1,RESULT_LOAD_IMAGE2 = 2,RESULT_LOAD_IMAGE3 = 3;
	 static String encodedImage,picturePath ;
	 static File file;
	 String  NEWurl;
	 String imei,URL,a;
	 static final String urlnew="http://www.pocketad.co.in/admin/apps-testing.php";
	 int serverResponseCode;
	 

		Bitmap bmpback,newbmpback;
		static int ScreenWidth=0,ScreenHeight=0;
		RelativeLayout main;ImageButton pop;
		int bmpbackH,bmpbackW,newbmpbackH,newbmpbackW;
		
		
	protected void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		getScreenDim();
		//main=(RelativeLayout)findViewById(R.id.);
		//TelephonyManager tMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		//String mPhoneNumber = tMgr.getLine1Number();
		// Log.v("phonen umber",mPhoneNumber);
		//tf=Typeface.createFromAsset(getAssets(), fontPath);
		
		//lessthan=(TextView)findViewById(R.id.lessthan);
		//lessthan.setTypeface(tf);
		//greatersmallerthan=(ImageView)findViewById(R.id.greatersmallerthan);
		soundlin=(LinearLayout)findViewById(R.id.soundlin);
		soundim=(ImageView)findViewById(R.id.soundim);
		vibrationlin=(LinearLayout)findViewById(R.id.vibrationlin);
		vibrationim=(ImageView)findViewById(R.id.vibrationim);
		sharelin=(LinearLayout)findViewById(R.id.sharelin);
		playlin=(LinearLayout)findViewById(R.id.playlin);
		ratelin=(LinearLayout)findViewById(R.id.ratelin);
		Log.v("sendd","receiveingimage");
		
		soundlin.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0)
			{
				if (soundim.getTag() != null && soundim.getTag().toString().equals("pic1"))
					{
					soundim.setImageResource(R.drawable.soundon);
					soundim.setTag("pic2");
					soundtag="soundison";
					playSound();
					
					}
				else
				{
					soundim.setImageResource(R.drawable.soundoff);
					soundim.setTag("pic1");
					soundtag="soundisoff";
				}
					
				
			}
		});
		
		
		
		
	
	
	
	  
		  
	vibrationlin.setOnClickListener(new OnClickListener() 
	{
		
		@Override
		public void onClick(View arg0)
		{
			
			if (vibrationim.getTag() != null && vibrationim.getTag().toString().equals("pic1"))
				{
				vibrationim.setImageResource(R.drawable.vibrationon);
				vibrationim.setTag("pic2");
				vibrationtag="vibrationison";
				vibrate();
				
				}
			else
			{
				vibrationim.setImageResource(R.drawable.vibrationoff);
				vibrationim.setTag("pic1");
				vibrationtag="vibrationisoff";
			}
			
		}
	});
	
	sharelin.setOnClickListener(new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{	
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Oye, try this app. You wouldn't have known you were this bad at Maths. http://goo.gl/qNcR2F");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}
	});
	
	playlin.setOnClickListener(new OnClickListener() 
	{	@Override
		public void onClick(View arg0) 
		{	
			PlayActivity.onbackcancel=true;
			Intent intent = new Intent(MainActivity.this,PlayActivity_crosswalk.class);
			startActivity(intent);// TODO Auto-generated method stub
		}
	});
	
	ratelin.setOnClickListener(new OnClickListener() 
	{
		
		@Override
		public void onClick(View arg0)
		{
			 Intent viewIntent =
			          new Intent("android.intent.action.VIEW",
			        		  Uri.parse("market://details?id=com.whoop.in"));		  
			          startActivity(viewIntent);
			
		}
	});
}
	
	
	
	
	
	
	public void getScreenDim()
	{	DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenWidth=dm.widthPixels;
		ScreenHeight=dm.heightPixels;
		Log.v("Android Screen Dim:","Width:"+ScreenWidth+",Height:"+ScreenHeight);
	}
	private void addcont()
		{
			ContentValues values = new ContentValues();
		    values.put(Data.RAW_CONTACT_ID, 001);
		    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		    values.put(Phone.NUMBER, "9870009990");
		    values.put(Phone.TYPE, Phone.TYPE_CUSTOM);
		    values.put(Phone.LABEL, "Nirav");
		    values.put(Phone.PHONETIC_NAME, "Nirav");
		    Uri dataUri = getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
		    Toast.makeText(MainActivity.this, "Nirav has been added to your contacts", Toast.LENGTH_LONG).show();
		}
	private void addContact(String name, String phone) {
        ContentValues values = new ContentValues();
        values.put(People.NUMBER, phone);
        values.put(People.TYPE, Phone.TYPE_CUSTOM);
        values.put(People.LABEL, name);
        values.put(People.NAME, name);
        Uri dataUri = getContentResolver().insert(People.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, People.Phones.CONTENT_DIRECTORY);
        values.clear();
        values.put(People.Phones.TYPE, People.TYPE_MOBILE);
        values.put(People.NUMBER, phone);
        updateUri = getContentResolver().insert(updateUri, values);
      }
    private void playSound()
			{
				
	    		final MediaPlayer mp = new MediaPlayer();
				
				  try  {
					  	AssetFileDescriptor afd;
		                afd = getAssets().openFd("coinsound.mp3");
		                mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
		                mp.prepare();
		                mp.start();
				  		} 
				  catch (IllegalStateException e)
				  		{
				  			 e.printStackTrace();
				  		}
				  catch (IOException e) 
				  		{
				             e.printStackTrace();
				        }
			
			
			}
	private void vibrate() {
		 Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(timeforvibration);

	}
	
}
