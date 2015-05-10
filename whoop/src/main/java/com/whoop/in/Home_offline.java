package com.whoop.in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Home_offline extends Activity



{	ProgressDialog pDialog;
	private ListView listView;
	//String URL="https://www.pocketad.in/webservices/processes.php?tag=ngolist";
	//https://www.pocketad.in/webservices/index.php?tag=appsDownload&imei=359299051262694
	String url="http://www.pocketad.in/webservices/index.php?tag=game_list&imei=";
	String URL;
    static GridView grid;
	ArrayList<DataModelClass> messagelist = new ArrayList<DataModelClass>();
	String gameImage=null,gameImagePath=null,imei;
    TextView title,test_1;
    ImageView test_2,img_swipe;
    LinearLayout main;
    String fontPath = "fonts/LHF_Cosmic_Cursive.ttf";
    Typeface tf;


    private ShakeListener mShaker;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_offline);

        listView=(ListView) findViewById(R.id.listview);
        tf=Typeface.createFromAsset(this.getAssets(), fontPath);
        title=(TextView) findViewById(R.id.title);
        title.setTypeface(tf);
        main=(LinearLayout) findViewById(R.id.main);
        TelephonyManager telephonyManager = null;
        telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        //trackDonateFragment();



       /* mShaker = new ShakeListener(Home_offline.this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibrate(100);
                Intent i = new Intent(Home_offline.this, PlayActivity.class);
                startActivity(i);
                finish();
            }
        });*/






        Home_offline_games_adapter _adapter = new Home_offline_games_adapter(Home_offline.this);
        _adapter.notifyDataSetChanged();
        listView.setAdapter(_adapter);


    }



    public boolean getNotificationCount()
    {	Home_offline_games_Helper nh=new Home_offline_games_Helper(Home_offline.this);
        int count=nh.getNotificationCount();
        if(count==0)
        {
            Log.v("rb","///////////////////////////////////////00000000000000000000000000000000000000000///////////////////////");
            //nonotificationlayout.setVisibility(View.VISIBLE);

            return false;
        }
        return true;
    }


    public final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                vibrate(50);
                return true;
            } else {
                return false;
            }
        }
    }
    class MyDragListener implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    test_2.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    test_2.setVisibility(View.GONE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    class MyDragListener1 implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    test_2.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();

                    view.setVisibility(View.VISIBLE);
                    test_2.setVisibility(View.GONE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    class MyDragListener2 implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    test_2.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    /*ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                    view.setVisibility(View.GONE);
                    test_2.setVisibility(View.GONE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }



    public void vibrate(int vibrationtime)
    {	 Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(vibrationtime);
    }
    /*public void trackDonateFragment()//Sending info to Google Analytics
	{	try
		{	Tracker t=((Analytics_PocketAd)getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("Play_new_2");
			t.send(new HitBuilders.AppViewBuilder().build());
		}catch(Exception e){Log.v("trackDonateFragment()",e.toString());}
	}*/
	private class SendData extends AsyncTask<String, String, String> 
	{	@Override
		protected void onPreExecute() 
		{	super.onPreExecute();
            //imei="359299051262694";
			URL=url+imei;
		}
        @Override
        protected String doInBackground(String... params)
        {	try
        {	DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
            HttpResponse response = httpclient.execute(httpget);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null)
            {	sb.append(line + NL);
            }
            in.close();
            messagelist.clear();
            String result = sb.toString();
            System.out.println(result);
            try
            {	JSONObject obj = new JSONObject(result);
                JSONArray _jarray = obj.getJSONArray("GAME_LIST");
                for (int _i = 0; _i < _jarray.length(); _i++)
                {	JSONObject _jobj = _jarray.getJSONObject(_i);
                    String extr=_jobj.getString("ALL_GAME");
                    String extr2=_jobj.getString("GAME_ID");
                    String a = _jobj.getString("GAME_NAME");
                    String b = _jobj.getString("GAME_CODE");
                    gameImage = _jobj.getString("GAME_ICON");
                    String imageUrl ="http://www.pocketad.in/webservices/game_icon/"+gameImage;
                    if(!checkFile())
                        downloadImage(imageUrl);
                    DataModelClass dmc = new DataModelClass();
                    dmc.setPlay_1_catid(a);
                    dmc.setPlay_1_catname(b);
                    dmc.setPlay_1_catimg(gameImage);
                    // adding HashList to ArrayList
                    messagelist.add(dmc);
                }
            }
            catch (Exception e)
            {	e.printStackTrace();
            }
        }
        catch (Exception e)
        {	e.printStackTrace();
        }
            return null;
        }
		@Override
		protected void onPostExecute(String result)
		{
			Home_online_games_adapter _adapter = new Home_online_games_adapter(Home_offline.this, messagelist);
			_adapter.notifyDataSetChanged();
			listView.setAdapter(_adapter);
		/*	Animation animation;
			try {
				animation = AnimationUtils.loadAnimation(getActivity(),  R.anim.push_up);
				listView.startAnimation(animation);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			listView.setAdapter(_adapter);*/
			
		}
	}
	public void downloadImage(String imageUrl)
	{	try
		{	Log.v("Downloading..",gameImage);
			String imgPath=getImagePath();
			URL url=new URL(imageUrl);
			URLConnection ucon=url.openConnection();
			if(!(ucon instanceof HttpURLConnection))                    
				throw new IOException("Not an HTTP connection");
			InputStream is=ucon.getInputStream();
			BufferedInputStream bis=new BufferedInputStream(is);
			ByteArrayBuffer baf=new ByteArrayBuffer(50);
			int current=0;
			while((current=bis.read())!=-1)
			{	baf.append((byte) current);
			}
			FileOutputStream fos=new FileOutputStream(imgPath);
			fos.write(baf.toByteArray());
			fos.close();			
		}catch (Exception e)
		{	Log.v("GetImage:Err",e.toString());
		}
	}
	public boolean checkFile()
	{	String root=Environment.getExternalStorageDirectory().toString();
        gameImagePath=root+File.separator+".PocketAd"+File.separator+".GameImage"+File.separator+gameImage;
		File file=new File(gameImagePath);
		if(file.exists())
		{	Log.v("Already Exists",gameImage);
			return true;
		}
		return false;
	}
	public String getImagePath()
	{	String root=Environment.getExternalStorageDirectory().toString();
		//Log.v("Root",root);
		String subPath=".PocketAd"+File.separator+".GameImage";
		File myDir=new File(Environment.getExternalStorageDirectory(),subPath);
		myDir.mkdirs();
		String imgPath=root+File.separator+subPath+File.separator+gameImage;
		Log.v("Image Path",imgPath);
		return imgPath;
	}

}