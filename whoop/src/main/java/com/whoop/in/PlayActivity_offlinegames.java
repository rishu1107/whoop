package com.whoop.in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.xwalk.core.XWalkView;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity_offlinegames extends Activity 
{	
	public static WebView wv;PopupWindow pw;int ScreenWidth,ScreenHeight;
	static String startTime=null,skipUrl=null,nextUrl=null,stopTime=null,description="Wait..",status="Skip",gameCode="",runUrl=null;
	RelativeLayout main;ImageButton pop;//static ProgressBar progress;
	static ImageButton b1;
	ImageButton b2;
	private Bundle bundle;
	boolean doubleBackToExitPressedOnce=false;
	Bitmap small;static int game_no=0;
	static Bitmap big1;
	Bitmap big2;static boolean confirmGame=false;
	Timer timer;TimerTask tt;static long startTimerTime=0L;
	static Timer skipTimer,confirmTimer;static TimerTask skipEnabler,confirmTask;static boolean skipEnable=false;
	int smallW,smallInitialW,smallFinalW,smallH,big1W,big1InitialW,big1FinalW,big1H,big2W,big2InitialW,big2FinalW,big2H;
	boolean dragOpen=true;
	boolean initPopup=true;
	boolean popDrag=true,b1Drag=true,b2Drag=true,dragging=false;
	static TextView textwebvi;
	boolean createBig1=true,removeBig1=true,createBig2=true,removeBig2=true;
	int dragPixel=10;static ProgressDialog progressDialog;

	static boolean onbackcancel =true;
	static boolean snAdshow=true,snAdshow1=true;
	static String snAdshow2="true";
	String onbackcancelhandler="rb",adnotrunning="adnotrunning";
	//String url="https://www.pocketad.in/webservices/index.php?imei=";
	String url="http://www.pocketad.co.in/webservices/index.php?tag=game_view&imei=";//911343705913699&game_code=YRVSO1GAZE2351
	String imei,RefreshURL,Gameurl=null;
	WebSettings webSettings;static Context context;
	int Play_1_id;
	String Play_1_name;

    private XWalkView mXWalkView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_crosswalk_test);
        mXWalkView = (XWalkView) findViewById(R.id.activity_play_crosswalk_test);
		bundle = getIntent().getExtras();
		Play_1_name = bundle.getString("gamecode");
		context=this.getApplicationContext();
		getScreenDim();
		TelephonyManager telephonyManager=null;
	    telephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
	    imei=telephonyManager.getDeviceId();//device Id
		

		
		
		
	    //Gameurl=url+imei;
	    //Gameurl=url+imei+"&game_code="+Play_1_name;
	    //Gameurl="file:///android_asset/game/greater-smaller/index_swappinggreatersmaller.html";
		
		
		
	    Gameurl="file:///"+ Environment.getExternalStorageDirectory().toString() +File.separator+ ".PocketAd"+File.separator+"."+Play_1_name+File.separator+"index.html";
	  
		
		
	   /* Storage storage = null;
	    storage = SimpleStorage.getExternalStorage();
	 // set encryption
	    String IVX = "abcdefghijklmnop"; // 16 lenght - not secret
	    String SECRET_KEY = "secret1234567890"; // 16 lenght - secret

	    // build configuratio
	    SimpleStorageConfiguration configuration = new SimpleStorageConfiguration.Builder()
	        .setEncryptContent(IVX, SECRET_KEY)
	        .build();

	    // configure the simple storage
	    SimpleStorage.updateConfiguration(configuration);
	    String content = storage.readTextFile("Encrypt_chk_rishabh", "chk_enc.html");
		Log.v("content",content);
	    
	    */
		
		// Gameurl="file:///"+ Environment.getExternalStorageDirectory().toString() +File.separator+ "Encrypt_chk_rishabh"+File.separator+"index.html";
        mXWalkView.load(Gameurl,null);
	    
	    	
	  
	}
	
	
	
	public void getScreenDim()
	{	DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenWidth=dm.widthPixels;
		ScreenHeight=dm.heightPixels;
		Log.v("Android Screen Dim:","Width:"+ScreenWidth+",Height:"+ScreenHeight);
	}
	
	public String getCurTime()
	{	Calendar c=Calendar.getInstance();
		String t=String.valueOf(c.getTimeInMillis());
		return t;
	}
	
	
	
	

	public static void enableSkipTimer()
	{	try
		{	startTimerTime=Calendar.getInstance().getTimeInMillis();
			Log.v("skipTimer","Started");
			skipTimer=new Timer();
			skipEnabler=new TimerTask()
			{	@Override
				public void run()
				{	skipEnable=true;
					Log.v("skipEnable","Yes");
					//Log.v("Status",status);
				}
			};
			skipTimer.schedule(skipEnabler,15000L);
		}catch(Exception e){Log.v("SkipTimer",e.toString());}
	}


	


	/*	@Override
	public void onBackPressed()
	{	//Log.v("Back Button","Pressed..");
		super.onBackPressed();
		
		finish();
		
	}*/
	@Override
	public void onBackPressed() 
	{Log.v("Back Button","Pressed..");
		
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	      
	        finish();
	       
			
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
	
	/*public void addPopButton()
	{	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		pop=new ImageButton(this);
		pop.setPadding(0,0,0,0);
		pop.setBackgroundColor(Color.TRANSPARENT);
		pop.setId(1);
		pop.setImageBitmap(setSmallButton());
		pop.setLayoutParams(lp);
		pop.setOnClickListener(this);
		main.addView(pop);
	}
	public void createBig1()
	{	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		b1=new ImageButton(this);
		b1.setOnClickListener(this);
		b1.setBackgroundColor(Color.TRANSPARENT);
		b1.setPadding(0,0,0,0);
		b1.setId(2);
		b1.setLayoutParams(lp);
		lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.LEFT_OF,b1.getId());
		pop.setLayoutParams(lp);
		main.addView(b1);
		createBig1=false;
	}
	public void createBig2()
	{	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		b2=new ImageButton(this);
		b2.setOnClickListener(this);
		b2.setBackgroundColor(Color.TRANSPARENT);
		b2.setPadding(0,0,0,0);
		b2.setId(3);
		b2.setLayoutParams(lp);
		lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.LEFT_OF,b2.getId());
		b1.setLayoutParams(lp);
		lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.LEFT_OF,b1.getId());
		pop.setLayoutParams(lp);
		main.addView(b2);
		createBig2=false;
	}
	public void getImageDim()
	{	small=BitmapFactory.decodeResource(getResources(),R.drawable.quick);
		big1=BitmapFactory.decodeResource(getResources(),R.drawable.liveimage0);
		big1W=big1.getWidth();
		big1H=big1.getHeight();
		big1InitialW=1;
		big1FinalW=big1W;
		big2=BitmapFactory.decodeResource(getResources(),R.drawable.skip_unavailable2);
		big2W=big2.getWidth();
		big2H=big2.getHeight();
		big2InitialW=1;
		big2FinalW=big1W;
	}
	
	public void dragImage()
	{	try
		{	if(!dragging)
			{	dragging=true;
				timer=new Timer();
				if(dragOpen)
					setDragOpen();
				else
					setDragClose();
			}
		}catch(Exception e){Log.v("Error",e.toString());}
	}
	public void setDragOpen()
	{	popDrag=true;b1Drag=true;createBig1=true;createBig2=true;
		tt=new DragForward();
		timer.schedule(tt,0L,10L);
	}
	public void setDragClose()
	{	removeBig2=true;removeBig1=true;
		big2FinalW=big2W;big1FinalW=big1W;
		if(pw!=null)
			pw.dismiss();
		tt=new DragBack();
		timer.schedule(tt,0L,10L);
	}
	public Bitmap setSmallButton()
	{	smallW=small.getWidth();
		smallH=small.getHeight();
		smallInitialW=smallW;
		smallFinalW=smallW;
		Bitmap newBitmap=Bitmap.createBitmap(small,0,0,smallInitialW,smallH,null,false);
		return newBitmap;
	}
	class DragBack extends TimerTask
	{	String cur;
		@Override
		public void run()
		{	runOnUiThread(new Runnable()
			{	public void run()
				{	try
					{	//Log.v("dragImage()","Called..");
						if(removeBig2)
						{	cur="removeBig2";
							if(big2FinalW<dragPixel)
							{	removeBig2();
								removeBig2=false;
							}
							else
							{	big2FinalW-=dragPixel;
								Bitmap bg2=Bitmap.createBitmap(big2,0,0,big2FinalW,big2H,null,false);
								b2.setImageBitmap(bg2);
							}
						}
						if(!removeBig2)
						{	if(removeBig1)
							{	cur="removeBig1";
								if(big1FinalW<dragPixel)
								{	removeBig1();
									removeBig1=false;
								}
								else
								{	big1FinalW-=dragPixel;
									Bitmap bg1=Bitmap.createBitmap(big1,0,0,big1FinalW,big1H,null,false);
									b1.setImageBitmap(bg1);
								}
							}
							if(!removeBig1)
							{	if(smallFinalW>smallInitialW+dragPixel)
								{	Bitmap sm=Bitmap.createBitmap(small,0,0,smallFinalW,smallH,null,false);
									pop.setImageBitmap(sm);
									smallFinalW-=dragPixel;
								}
								else
								{	dragOpen=true;
									small=BitmapFactory.decodeResource(getResources(),R.drawable.quick);
									pop.setImageBitmap(setSmallButton());
									timer.cancel();
									dragging=false;
								}
							}
						}
					}catch(Exception e){Log.v(cur,e.toString());}
				}
			});
		}		
	}
	public void removeBig2()
	{	main.removeView(b2);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		b1.setLayoutParams(lp);
	}
	public void removeBig1()
	{	main.removeView(b1);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		pop.setLayoutParams(lp);
	}
	class DragForward extends TimerTask
	{	@Override
		public void run()
		{	runOnUiThread(new Runnable()
			{	public void run()
				{	try
					{	//Log.v("dragImage()","Called..");
						small=BitmapFactory.decodeResource(getResources(),R.drawable.close2);
						pop.setImageBitmap(setSmallButton());
						smallFinalW=smallW;
						if(popDrag)
						{	Bitmap pop1=Bitmap.createBitmap(small,0,0,smallInitialW,smallH,null,false);
							pop.setImageBitmap(pop1);
							//Log.v("smallInitialW",":"+smallInitialW);
							smallInitialW+=dragPixel;
							if(smallInitialW+dragPixel>=(smallFinalW))
								popDrag=false;
						}
						if(!popDrag)
						{	if(b1Drag)
							{	if(createBig1)
								{	createBig1();
									big1InitialW=1;
								}
								Bitmap bg=Bitmap.createBitmap(big1,0,0,big1InitialW,big1H,null,false);
								b1.setImageBitmap(bg);
								if(big1InitialW+dragPixel<big1W)
									big1InitialW+=dragPixel;
								else
									b1Drag=false;
							}
							if(!b1Drag)
							{	if(createBig2)
								{	createBig2();
									big2InitialW=1;
								}
								Bitmap bg2=Bitmap.createBitmap(big2,0,0,big2InitialW,big2H,null,false);
								b2.setImageBitmap(bg2);
								if(big2InitialW+dragPixel<big2W)
									big2InitialW+=dragPixel;
								else
								{	smallInitialW=smallW;
									dragOpen=false;
									timer.cancel();
									dragging=false;
								}
							}
						}
					}catch(Exception e){Log.v("Err:",e.toString());}
				}
			});
		}		
	}
	public static void setBig1(String imgNo)
	{	try
		{	int bno=Integer.parseInt(imgNo.trim());
			game_no=bno;
			int[] d=new int[51];
			d[0]=R.drawable.liveimage0;
			d[1]=R.drawable.liveimage1;
			d[2]=R.drawable.liveimage2;
			d[3]=R.drawable.liveimage3;
			d[4]=R.drawable.liveimage4;
			d[5]=R.drawable.liveimage5;
			d[6]=R.drawable.liveimage6;
			d[7]=R.drawable.liveimage7;
			d[8]=R.drawable.liveimage8;
			d[9]=R.drawable.liveimage9;
			d[10]=R.drawable.liveimage10;
			d[11]=R.drawable.liveimage11;
			d[12]=R.drawable.liveimage12;
			d[13]=R.drawable.liveimage13;
			d[14]=R.drawable.liveimage14;
			d[15]=R.drawable.liveimage15;
			d[16]=R.drawable.liveimage16;
			d[17]=R.drawable.liveimage17;
			d[18]=R.drawable.liveimage18;
			d[19]=R.drawable.liveimage19;
			d[20]=R.drawable.liveimage20;
			d[21]=R.drawable.liveimage21;
			d[22]=R.drawable.liveimage22;
			d[23]=R.drawable.liveimage23;
			d[24]=R.drawable.liveimage24;
			d[25]=R.drawable.liveimage25;
			d[26]=R.drawable.liveimage26;
			d[27]=R.drawable.liveimage27;
			d[28]=R.drawable.liveimage28;
			d[29]=R.drawable.liveimage29;
			d[30]=R.drawable.liveimage30;
			d[31]=R.drawable.liveimage31;
			d[32]=R.drawable.liveimage32;
			d[33]=R.drawable.liveimage33;
			d[34]=R.drawable.liveimage34;
			d[35]=R.drawable.liveimage35;
			d[36]=R.drawable.liveimage36;
			d[37]=R.drawable.liveimage37;
			d[38]=R.drawable.liveimage38;
			d[39]=R.drawable.liveimage39;
			d[40]=R.drawable.liveimage40;
			d[41]=R.drawable.liveimage41;
			d[42]=R.drawable.liveimage42;
			d[43]=R.drawable.liveimage43;
			d[44]=R.drawable.liveimage44;
			d[45]=R.drawable.liveimage45;
			d[46]=R.drawable.liveimage46;
			d[47]=R.drawable.liveimage47;
			d[48]=R.drawable.liveimage48;
			d[49]=R.drawable.liveimage49;
			d[50]=R.drawable.liveimage50;
			big1=BitmapFactory.decodeResource(context.getResources(),d[bno]);
			if(b1!=null)
				b1.invalidate();
		}catch(Exception e){Log.v("setBig1",e.toString());}
	}
	public void initPopup()
	{	try
		{	String html="<u>Instructions</u>";
			LayoutInflater layoutInflater=(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
			View popupView=layoutInflater.inflate(R.layout.popup,null);
			TextView ptitle=(TextView)popupView.findViewById(R.id.ptitle);
			ptitle.setText(Html.fromHtml(html));
			TextView ptext=(TextView)popupView.findViewById(R.id.description);
			ptext.setText(description);
			pw=new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//pw=new PopupWindow(popupView,250,150,false);
			pw.showAsDropDown(pop,0,0);
			//pw.showAtLocation(pop,Gravity.CENTER_HORIZONTAL,0,0);
		}catch(Exception e){Log.v("PW Err:",e.toString());}
	}*/
}