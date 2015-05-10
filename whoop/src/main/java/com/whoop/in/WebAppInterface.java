package com.whoop.in;

import java.io.IOException;









import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Contacts.People;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WebAppInterface 
{	Context context;PlayActivity pa;
	String currentscorefromgame;
	static String diplayad="rb",wvsecondurl;
	static String openpopup="open";
	WebAppInterface(Context c,PlayActivity pa)
	
		{	
			context=c;this.pa=pa;
		}
	
	
		@JavascriptInterface
	    public void playSoundgs(String aud)
		
		{ if(MainActivity.soundtag.equals("soundison"))
			{
				Log.v("aud",aud);
				
				
				
				final MediaPlayer mp;
				
				  try  {
					  AssetFileDescriptor fileDescriptor = 
					  		context.getAssets().openFd(aud);
		       	                  mp = new MediaPlayer();
		       	                  mp.setDataSource(fileDescriptor.getFileDescriptor(), 
		       	                  fileDescriptor.getStartOffset(), 
		       	                  fileDescriptor.getLength());
		       	                  fileDescriptor.close();
		       	                  mp.prepare();
		       	                  mp.start();
		       	                  mp.setOnCompletionListener(new OnCompletionListener() {
		       	                	  public void onCompletion(MediaPlayer mp) {
		       	                    mp.release();
		       	                	};
		       	               });
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
			
			
			
	    }
		
		@JavascriptInterface
		public void instructions() 
		{
			 PlayActivity.highscorefromgame="1";
			 PlayActivity.sharedprefcomparehighscore();
			 Intent intent = new Intent(context,PlayActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 			 context.startActivity(intent);
	    }
		
		@JavascriptInterface
		public void googlebanneradgs() 
		{
			diplayad="yrv";
	    }
		

		@JavascriptInterface
	    public void font()
		{
			String fontPath = "fonts/ALWAYS IN MY HEART.TTF";
			Typeface tf;
			tf=Typeface.createFromAsset(context.getAssets(), fontPath);
		}
		@JavascriptInterface
		public void vibrate(int vibrationtime) 
		{
			if(MainActivity.vibrationtag.equals("vibrationison"))
			{
				 Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
				 // Vibrate for 500 milliseconds
				 v.vibrate(vibrationtime);
			}
			
		}
		
		
		@JavascriptInterface
		public void checkhighscore(String currentscore) 
		{
			Log.v("currentscore",currentscore);
			PlayActivity.highscorefromgame=currentscore;
			PlayActivity.sharedprefcomparehighscore();
			currentscorefromgame = currentscore;
			Log.v("currentscorefromgame",currentscorefromgame);
		}
		@JavascriptInterface
		public void addContact(String name, String phone) {
			Log.v("name",name);
			Log.v("name",phone);
	        ContentValues values = new ContentValues();
	        values.put(People.NUMBER, phone);
	        values.put(People.TYPE, Phone.TYPE_CUSTOM);
	        values.put(People.LABEL, name);
	        values.put(People.NAME, name);
	        Uri dataUri = context.getContentResolver().insert(People.CONTENT_URI, values);
	        Uri updateUri = Uri.withAppendedPath(dataUri, People.Phones.CONTENT_DIRECTORY);
	        values.clear();
	        values.put(People.Phones.TYPE, People.TYPE_MOBILE);
	        values.put(People.NUMBER, phone);
	        updateUri = context.getContentResolver().insert(updateUri, values);
	      }
		@JavascriptInterface
		public void popup()
		 {		
				
				final Dialog dialog = new Dialog(pa);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.popup);
				dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
				dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.setCancelable(true);
				//dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
				dialog.setCanceledOnTouchOutside(false);
				String fontPath = "fonts/ALWAYS IN MY HEART.TTF";
				Typeface tf;
				tf=Typeface.createFromAsset(context.getAssets(), fontPath);
				TextView scoretex,highscoretex;
				LinearLayout sharelin,replaylin,ratelin;
				final String highscorevisibletouser=PlayActivity.highscore;
				sharelin=(LinearLayout)dialog.findViewById(R.id.sharepopuplin);
				ratelin=(LinearLayout)dialog.findViewById(R.id.ratepopuplin);
				replaylin=(LinearLayout)dialog.findViewById(R.id.replaypopuplin);
				scoretex=(TextView)dialog.findViewById(R.id.scoretex);
				highscoretex=(TextView)dialog.findViewById(R.id.highscoretex);
				scoretex.setTypeface(tf);
				scoretex.setText("Score: "+currentscorefromgame);
				highscoretex.setTypeface(tf);
				highscoretex.setText("Highscore: "+highscorevisibletouser);
				sharelin.setOnClickListener(new OnClickListener()
				{	@Override
					public void onClick(View v)
					{	//dialog.dismiss();
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(Intent.EXTRA_TEXT, "Oye, try this app. You wouldn't have known you were this bad at Maths. http://goo.gl/qNcR2F");
						sendIntent.setType("text/plain");
						context.startActivity(sendIntent);
						
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
						 			  context. startActivity(viewIntent);
						
					}
				});
				replaylin.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View arg0)
					{
						 Intent intent = new Intent(context,PlayActivity.class);
	                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	     				 intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	     				 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	     				 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		     			 context.startActivity(intent);
						
					}
				});
				 dialog.setOnKeyListener(new Dialog.OnKeyListener() {

		             @Override
		             public boolean onKey(DialogInterface arg0, int keyCode,
		                     KeyEvent event) {
		                 // TODO Auto-generated method stub
		                 if (keyCode == KeyEvent.KEYCODE_BACK) {
		                	
		                     dialog.dismiss();
		                     Intent intent = new Intent(context,MainActivity.class);
		                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		     				 intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		     				 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		     				 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			     			 context.startActivity(intent);
		                 }
		                 return true;
		             }
		         });

				dialog.show();
		 }
		
		@JavascriptInterface
		public void getRunningAppList()
		{	ActivityManager am=(ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE);
			List list=(List)am.getRunningAppProcesses();
			for(int i=0;i<list.size();i++)
				Log.v("Process Running:"+i,list.get(i).toString());
		}
		
}
