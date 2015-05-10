package com.whoop.in;


import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Home_online_games_adapter extends BaseAdapter
{

	ProgressDialog pDialog;
	DataModelClass dmc;
	RelativeLayout bkimg;
	TextView donatedesc,seemore,donatetext;
	ImageView img1;
	String a,b,c,d;
	String url="https://www.pocketad.in/webservices/processes.php?tag=viewngo&ngo_id=";
    String URL;
    String getdesurl="https://www.pocketad.in/webservices/processes.php?tag=viewngo&ngo_id=";//10&viewngodesc=1";
    String GETDESURL;
    String package_name = "com.whoop.in",app_id_tobesent="1";
    String Url_redirected="market://details?id=com.whoop.in";
    String app_name_pop;
    String app_img_pop;
    String v1_pop="1",v2_pop,v3_pop,v4_pop,v5_pop;
    String hhgtest="1";
    Bitmap bitmap_logo;
	int ss;

    String gameImagePath;
	int i=0,ii=0,iii=0;
	String istr="",iistr,iiistr;
	String fontPath = "fonts/HelveticaNeue-Light.ttf";
    Typeface tf;


	boolean checkingon=true;
	Context _ctxx;
	LayoutInflater inflater;
	public ArrayList<DataModelClass> data ;

	public Home_online_games_adapter(Context c, ArrayList<DataModelClass> _arraylist)
	{
		this._ctxx = c;
		this.data = _arraylist;
		
		
		
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		inflater = (LayoutInflater) _ctxx.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View itemView = inflater.inflate(R.layout.home_online_games_adapter,parent,false);
	    
	    final ImageView img = (ImageView)itemView.findViewById(R.id.img);
	    final TextView gamename = (TextView)itemView.findViewById(R.id.gamename);
	    final LinearLayout download = (LinearLayout)itemView.findViewById(R.id.download_lin);
	    final TextView descforapp= (TextView)itemView.findViewById(R.id.descforapp);
	    img.setTag(position);
	    download.setTag(position);
	    
	    tf=Typeface.createFromAsset(_ctxx.getAssets(), fontPath);
	    
	    DataModelClass dm = data.get(position);
        gamename.setText(dm.getPlay_1_catname());
	    gamename.setTypeface(tf);

	    descforapp.setText(dm.getPlay_1_catdesc());
	    descforapp.setTypeface(tf);

        String 	gameImgPath= Environment.getExternalStorageDirectory()+ "/.PocketAd/.GameImage/";
        String imgchk=dm.getPlay_1_catimg();
        if(imgchk.equalsIgnoreCase(""))
        {
            gameImagePath=gameImgPath+"1423466998DTH-redeem-successful.png";
        }
        else
        {
            gameImagePath=gameImgPath+dm.getPlay_1_catimg();
        }


        File gameImage=new File(gameImagePath);
        Bitmap bitmap=BitmapFactory.decodeFile(gameImage.getAbsolutePath());
        img.setImageBitmap(bitmap);

	    /*String ngoImagePath=dm.getPlay_2_catimg();
	    File ngoImage=new File(ngoImagePath);
	    bitmap_logo=BitmapFactory.decodeFile(ngoImage.getAbsolutePath());
	    img.setImageBitmap(bitmap_logo);*/



       // download.setOnTouchListener(new MyTouchListener());






        download.setOnClickListener(new OnClickListener()
		  {

				@Override
				public void onClick(View vr)
				{
					int gamecod=(Integer) vr.getTag();
					int gameimg=(Integer) vr.getTag();
                    int gamenam=(Integer) vr.getTag();
                    int gamedes=(Integer) vr.getTag();
                    int gameide=(Integer) vr.getTag();
					DataModelClass cna=data.get(gamecod);
					DataModelClass cnaa=data.get(gameimg);
                    DataModelClass cnaaa=data.get(gamenam);
                    DataModelClass cnaaaa=data.get(gamedes);
                    DataModelClass cnaaaaa=data.get(gameide);
					String gamecode=cna.getPlay_1_catcode();
					String gameimagepath=cnaa.getPlay_1_catimg();
                    String gamename=cnaaa.getPlay_1_catname();
                    String gamedesc=cnaaaa.getPlay_1_catdesc();
                    String gameid=cnaaaa.getPlay_1_catid();
					Log.v("gamecode",gamecode);
					Log.v("gameimagepath",gameimagepath);
                    Log.v("gamename",gamename);
                    Log.v("gamedesc",gamedesc);
                    Log.v("gameid",gameid);


					home_online_games_adapter_Async cf=new home_online_games_adapter_Async(_ctxx, gameid, gamecode, gameimagepath, gamename, gamedesc);
					cf.execute();

                    Intent intent=new Intent(_ctxx,PlayActivity_onlinegames.class);
                    String gamecodesent = gamecode;
                    Log.v("gamecodesent",gamecodesent);
                    intent.putExtra("gamecodesent", gamecodesent);
                    _ctxx.startActivity(intent);

				}
		  });

		
		return itemView;
	}
    public final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


	 
	 private boolean checkAppOnPhoneOrNot(String packageName) {
			
		    PackageManager pm = _ctxx.getPackageManager();
		    boolean installed = false;
		    try {
			       pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			       installed = true;
			       
			       if(ii>1)/////////////////this is done, because unistalling and then installing would lead to manipulated value of iii.
			       {
			    	   iii++;
			       }
		       
		       	   Log.v("true","true:present");
		    	} catch (PackageManager.NameNotFoundException e) {
		       installed = false;
		       ii++;
		       Log.v("false","false:notpresent");
		    }
		    return installed;
		}
		
		public void AppInstallationCheck(){
			
			final ActivityManager am = (ActivityManager) _ctxx.getSystemService(_ctxx.ACTIVITY_SERVICE);
			checkAppOnPhoneOrNot(package_name);
			/*Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			
			{	@Override
				public void run()
				{	
					AppInstallationCheck();
				}
			},4000);*/
		}
		public void checkappthroughloop(){
			Intent viewIntent = new Intent("android.intent.action.VIEW",
			        		  Uri.parse(Url_redirected));	  
			          _ctxx.startActivity(viewIntent);
					for(int i=0;i<10;i++)
					{
						 try {
		                        Thread.sleep(1000);
		                    } catch (InterruptedException e) {
		                    }
						istr= "endloop"+i;
						Log.v("a",istr);
						AppInstallationCheck();
					}
					
					//Toast.makeText(_ctxx,"Seems like something is right,",Toast.LENGTH_SHORT).show();
		}
		

}

