package com.whoop.in;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Home_offline_games_adapter extends BaseAdapter
{	Context contaxt;
	LayoutInflater inflater;
	ArrayList<String> data;
	String gameImgPath;
	ProgressDialog pDialog;
	public Home_offline_games_adapter(Context contaxt)
	{	this.contaxt=contaxt;
		data=new ArrayList<String>();
		Home_offline_games_Helper nh=new Home_offline_games_Helper(contaxt);
		ArrayList<String> data1=nh.getNotification();
		for(int i=data1.size()-1;i>=0;i--)
		{	Log.v("Data",data1.get(i).toString());
			data.add(data1.get(i).toString());
		}
		gameImgPath=Environment.getExternalStorageDirectory()+ "/.PocketAd/.GameImage/";
	}
	@Override
	public int getCount()
	{	// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int position) 
	{	// TODO Auto-generated method stub
		return position;
	}
	@Override
	public long getItemId(int position)
	{	// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(final int position,View convertView,ViewGroup parent)
	{	inflater = (LayoutInflater) contaxt.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View itemView = inflater.inflate(R.layout.home_online_games_adapter,parent,false);
	    final String[] dm = data.get(position).split("::");


        final ImageView img = (ImageView)itemView.findViewById(R.id.img);
        final TextView gamename = (TextView)itemView.findViewById(R.id.gamename);
        final LinearLayout playoffline = (LinearLayout)itemView.findViewById(R.id.download_lin);
        final TextView descforapp= (TextView)itemView.findViewById(R.id.descforapp);
        img.setTag(position);
        playoffline.setTag(position);

        gamename.setText(dm[1]);
        descforapp.setText(dm[3]);

		  String s=dm[4];
		  Log.v("s",s);
		  String imgName=gameImgPath+dm[4];
		  File imgFile=new File(imgName);
		  Bitmap myBitmap=BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		  img.setImageBitmap(myBitmap);
		  playoffline.setOnClickListener(new OnClickListener()
		  {	@Override
				public void onClick(View arg0)
			   	{	try
					{	Intent intent=new Intent(contaxt,PlayActivity_offlinegames.class);
						String gamecode = dm[2];
						Log.v("gamecode",gamecode);
						intent.putExtra("gamecode", gamecode);
						contaxt.startActivity(intent);
			   		}catch(Exception e){}
		   		}
		   });
		  /*cleardata.setOnClickListener(new OnClickListener()
			  {	@Override
					public void onClick(View arg0)
				   	{	try
						{	try
							{	int nid=Integer.parseInt(dm[0].trim());
								pDialog = new ProgressDialog(contaxt);
								pDialog.setMessage("Removing the game from your phone. Please wait ...");
								pDialog.setIndeterminate(false);
								pDialog.setCancelable(false);
								pDialog.show();
								Handler handler = new Handler();
								handler.postDelayed(new Runnable()
								
								{	@Override
									public void run()
									{	
									pDialog.dismiss();
									}
								},2000);
								Play_new_3_Helper nh=new Play_new_3_Helper(contaxt);
				   				nh.delMessage(nid);
				   				Log.v("MessageShowAdapter","Updating after delete");
				   				data.remove(position);
				   				notifyDataSetChanged();
				   				
				   			}catch(Exception e){Log.v("MessageShowAdapter",e.toString());}
				   		}catch(Exception e){}
			   		}
			   });*/



			return itemView;
	}
}