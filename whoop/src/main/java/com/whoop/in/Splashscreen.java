package com.whoop.in;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Splashscreen extends Activity  {
	String imei,URL,a;
	String url="http://www.pocketad.co.in/webservices/index.php?tag=appsUNotifi&imei=";

	String newurl="http://www.pocketad.in/webservices/login-userV2.php?imei=";
	String NEWurl;

	private ConnectionDetector cd;

	String fontPath = "fonts/chunkfive.ttf";
    Typeface tf;

    String fontPath2 = "fonts/OpenSans-Semibold.ttf";
    Typeface tf2;
    TextView no_internet;
    ImageView no_internet_refresh;
	static String bal_draw,noti_draw;

	public static AnimationDrawable animation;
	public static ImageView rocket;


	int currentVersion=0;


	@Override
	protected void onCreate(Bundle savedInstanceState)

	{	requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		cd = new ConnectionDetector(getApplicationContext());
		
		
		/*tf=Typeface.createFromAsset(this.getAssets(), fontPath);
		TextView wallet=(TextView)findViewById(R.id.wallet_name);
		wallet.setTypeface(tf);*/



	    if(cd.isConnectingToInternet())
	    {
            Log.v("connected","connected");
            Intent i = new Intent(getApplicationContext(), Home_online.class);

            startActivity(i);
            finish();


	    }
	    else
	    {
            Log.v("not connected","not connected");
            Intent i = new Intent(getApplicationContext(), Home_offline.class);
            startActivity(i);
            finish();

		}



    }



}
