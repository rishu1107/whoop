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

public class home_online_grid_adapter_delete_Async extends AsyncTask<String, String, String>
{	Context context;String responseString=null;
	String gamecode,gameimagepath,gamename,gamedesc;
	public home_online_grid_adapter_delete_Async(Context context, String gamecode_delete)
	{	this.context=context;this.gamecode= gamecode_delete;

	}
	
	ProgressDialog pDialog;
	static ProgressBar pdbar;
	private ListView listView;
	//String URL="https://www.pocketad.in/webservices/st_game.php?tag=restore&gcd=YRVSO1HEXTR189&imei_no=911343705959197";
	String url="http://www.pocketad.in/webservices/st_game.php?tag=restore&gcd=";
	//String url="http://pocketad.co.in/webservices/st_game.php?gcd=";
	String URL;
	ArrayList<DataModelClass> messagelist = new ArrayList<DataModelClass>();
	String gameImage=null,gameImagePath=null,imei;
	String gamecodereceived;
	
		@Override
		protected void onPreExecute() 
		{	super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Deleting the game from your phone. Please wait ...");
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
					JSONArray _jarray = obj.getJSONArray("RESTORE_GAME");
					for (int _i = 0; _i < _jarray.length(); _i++)
					{	JSONObject _jobj = _jarray.getJSONObject(_i);
						gameImage = _jobj.getString("IMAGE_NAME");

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
		{	//pDialog.dismiss();

		}
	

	

	public void getimei()
	{	
		TelephonyManager telephonyManager = null;
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		Log.v("ImEi in home_grid_delete async task",imei);
	}
}