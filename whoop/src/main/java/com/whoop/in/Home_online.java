package com.whoop.in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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



public class Home_online extends Activity



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
    LinearLayout grid_layout;
    LinearLayout main;
    String fontPath = "fonts/LHF_Cosmic_Cursive.ttf";
    Typeface tf;
    ProgressBar progress;




    ImageButton backfromsecondwebview;
    Bitmap bmpback,newbmpback;
    static int ScreenWidth=0,ScreenHeight=0;
    RelativeLayout main_rel;ImageButton pop;
    int bmpbackH,bmpbackW,newbmpbackH,newbmpbackW;


    private ShakeListener mShaker;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_online);

        listView=(ListView) findViewById(R.id.listview);
        progress=(ProgressBar) findViewById(R.id.progress);
        tf=Typeface.createFromAsset(this.getAssets(), fontPath);
        title=(TextView) findViewById(R.id.title);
        title.setTypeface(tf);
        grid_layout=(LinearLayout) findViewById(R.id.grid_layout);
        main=(LinearLayout) findViewById(R.id.main);
        main_rel=(RelativeLayout) findViewById(R.id.main_rel);
        TelephonyManager telephonyManager = null;
        telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();

        new SendData().execute();


        //getScreenDim();
        //addPopButton();

        mShaker = new ShakeListener(Home_online.this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibrate(100);
                /*Intent i = new Intent(Home_online.this, Home_online.class);
                startActivity(i);
                finish();*/
                new SendData_shuffle().execute();
            }
        });



        //grid_layout.setOnDragListener(new MyDragListener());
        //test_4.setOnDragListener(new MyDragListener1());
        //taunnt_title.setOnTouchListener(new MyTouchListener());



        if(getNotificationCount())
        {
            grid_layout.setVisibility(View.VISIBLE);
            home_online_grid_adapter _adapter=new home_online_grid_adapter(Home_online.this);
            _adapter.notifyDataSetChanged();
            grid=(GridView)findViewById(R.id.grid);
            grid.setAdapter(_adapter);
	   		/*Animation animation;
			try {
				animation = AnimationUtils.loadAnimation(getActivity(),  R.anim.push_up);
				listViewcheck.startAnimation(animation);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			listViewcheck.setAdapter(_adapter);*/
        }

    }


    public void addPopButton()
    {	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        pop=new ImageButton(this);
        pop.setPadding(0,0,0,0);
        pop.setBackgroundColor(Color.TRANSPARENT);
        pop.setImageBitmap(setSmallButton());
        pop.setLayoutParams(lp);
        int wc= ScreenWidth/5;
        int hc= ScreenHeight/ScreenWidth*wc;
        pop.setX(5*wc/6);
        pop.setY(hc/6);
        pop.setOnClickListener(new View.OnClickListener()
        {	@Override
             public void onClick(View v)
            {	//dialog.dismiss();
                finish();

            }
        });
        main_rel.addView(pop);
    }
    public Bitmap setSmallButton()
    {	bmpback= BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bmpbackW=bmpback.getWidth();
        bmpbackH=bmpback.getHeight();
        newbmpbackW=ScreenWidth/7;
        newbmpbackH=ScreenHeight/ScreenWidth*newbmpbackW;
        newbmpback=Bitmap.createBitmap(bmpback,0,0,bmpbackW,bmpbackH,null,false);
        return newbmpback.createScaledBitmap(bmpback,newbmpbackW, newbmpbackH,true);//you can give any size to newbmpbackW and newbmpbackH bcoz they are scaled.
    }																				//change the left,top,right,bottom padding through pop.setPadding

    public void getScreenDim()
    {	DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ScreenWidth=dm.widthPixels;
        ScreenHeight=dm.heightPixels;
        Log.v("Android Screen Dim:","Width:"+ScreenWidth+",Height:"+ScreenHeight);
    }
    public boolean getNotificationCount()
    {	Home_offline_games_Helper nh=new Home_offline_games_Helper(Home_online.this);
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
                    String a=_jobj.getString("GAME_ID");
                    String b = _jobj.getString("GAME_NAME");
                    String c = _jobj.getString("GAME_CODE");
                    String d = _jobj.getString("GAME_DESC");
                    gameImage = _jobj.getString("GAME_ICON");
                    String imageUrl ="http://www.pocketad.in/webservices/game_icon/"+gameImage;
                    if(!checkFile())
                        downloadImage(imageUrl);
                    DataModelClass dmc = new DataModelClass();
                    dmc.setPlay_1_catid(a);
                    dmc.setPlay_1_catname(b);
                    dmc.setPlay_1_catcode(c);
                    dmc.setPlay_1_catdesc(d);
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
            progress.setVisibility(View.GONE);
			Home_online_games_adapter _adapter = new Home_online_games_adapter(Home_online.this, messagelist);
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
	}private class SendData_shuffle extends AsyncTask<String, String, String>
{	@Override
     protected void onPreExecute()
{	super.onPreExecute();
    //imei="359299051262694";
    progress.setVisibility(View.VISIBLE);
    listView.setVisibility(View.GONE);
    url="http://www.pocketad.in/webservices/index.php?tag=Rgame_list&imei=";
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
                String a=_jobj.getString("GAME_ID");
                String b = _jobj.getString("GAME_NAME");
                String c = _jobj.getString("GAME_CODE");
                String d = _jobj.getString("GAME_DESC");
                gameImage = _jobj.getString("GAME_ICON");
                String imageUrl ="http://www.pocketad.in/webservices/game_icon/"+gameImage;
                if(!checkFile())
                    downloadImage(imageUrl);
                DataModelClass dmc = new DataModelClass();
                dmc.setPlay_1_catid(a);
                dmc.setPlay_1_catname(b);
                dmc.setPlay_1_catcode(c);
                dmc.setPlay_1_catdesc(d);
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
        progress.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        Home_online_games_adapter _adapter = new Home_online_games_adapter(Home_online.this, messagelist);
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

    public class OnSwipeTouchListener implements OnTouchListener {

        public final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }

}