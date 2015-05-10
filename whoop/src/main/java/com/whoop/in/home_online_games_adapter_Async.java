package com.whoop.in;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;


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

public class home_online_games_adapter_Async extends AsyncTask<String, String, String>
{	Context context;String responseString=null;
	String gamecode,gameimagepath,gamename,gamedesc,gameid;
	public home_online_games_adapter_Async(Context context, String gameid, String gamecode, String gameimagepath, String gamename, String gamedesc)
	{	this.context=context;this.gamecode= gamecode;this.gamename= gamename;this.gamedesc= gamedesc;this.gameid= gameid;
		if(gameimagepath.equalsIgnoreCase(""))
		{
			this.gameimagepath="1423466998DTH-redeem-successful.png";
			Log.v("","gaem image path set t");
			
		}
		else
		{
			this.gameimagepath=gameimagepath;
		}
	}
	
	ProgressDialog pDialog;
	static ProgressBar pdbar;
	private ListView listView;
	//String URL="http://pocketad.co.in/webservices/st_game.php?gcd=BLUEH2DEMOG018&imei_no=911343705959197";
	String url="http://www.pocketad.in/webservices/st_game.php?gcd=";
	//String url="http://pocketad.co.in/webservices/st_game.php?gcd=";
	String URL;
	ArrayList<DataModelClass> messagelist = new ArrayList<DataModelClass>();
	String gameImage=null,gameImagePath=null,imei;
	String gamecodereceived;
	
		@Override
		protected void onPreExecute() 
		{	super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Transferring the game to your phone. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			//pDialog.show();
			getimei();
			URL=url+gamecode+"&imei_no="+imei;
			Log.v("URL",URL);
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
					JSONArray _jarray = obj.getJSONArray("IMAGE_LIST");
					for (int _i = 0; _i < _jarray.length(); _i++)
					{	JSONObject _jobj = _jarray.getJSONObject(_i);
						gameImage = _jobj.getString("IMAGE_NAME");
						String a = _jobj.getString("PHONE_GROUP");
						gamecodereceived = _jobj.getString("GAME_CODE");
						
						String imageUrl ="http://pocketad.in/webservices/game_images/"+gamecodereceived+"/"+a+"/"+gameImage;
						if(!checkFile())
							downloadImage(imageUrl);
						DataModelClass dmc = new DataModelClass();
                        dmc.setPlay_1_catid(a);
                        dmc.setPlay_1_catname(gamecodereceived);
                        dmc.setPlay_1_catimg(gameImagePath);
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
		{	pDialog.dismiss();
			
			
			Home_offline_games_Helper ph= new Home_offline_games_Helper(context);
			ph.addNotification(gameid, gamename, gamecode, gamedesc, gameimagepath);

            try {
                home_online_grid_adapter _adapter = new home_online_grid_adapter(context);
                _adapter.notifyDataSetChanged();
                Home_online.grid.setAdapter(_adapter);
            }catch (Exception e)
            {	Log.v("GetImage:Err",e.toString());
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
		gameImagePath=root+File.separator+".PocketAd"+File.separator+"."+gamecodereceived+File.separator+gameImage;
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
		String subPath=".PocketAd"+File.separator+"."+gamecodereceived;
		File myDir=new File(Environment.getExternalStorageDirectory(),subPath);
		myDir.mkdirs();
		String imgPath=root+File.separator+subPath+File.separator+gameImage;
		Log.v("Image Path",imgPath);
		return imgPath;
	}
	
	

	public void getimei()
	{	
		TelephonyManager telephonyManager = null;
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		Log.v("ImEi in play_new_1 adapter async task",imei);
	}
}