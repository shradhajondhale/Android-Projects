package com.maverick.tracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;
 
public class NetworkUtil {
     
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    static SQLiteDatabase db;
     
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        } 
        return TYPE_NOT_CONNECTED;
    }
     
    public static String getConnectivityStatusString(final Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
     
        if (conn == NetworkUtil.TYPE_WIFI) {
        	
        	
        	//for the locations data
        	  db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
              db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
         
              Cursor c = db.rawQuery("SELECT * FROM location", null);
       	   
      	    if (c.getCount() == 0 )
      		{
      	    	//Toast.makeText(context, "no data present here for location", Toast.LENGTH_SHORT).show();
      		//return;
      		}
      	    else{
      	    String User;
      	    String[] time = new String [c.getCount()];
      	    String[] date = new String [c.getCount()];
      	    String[] month = new String [c.getCount()];
      	    String[] year = new String [c.getCount()];
      	    String[] lon = new String [c.getCount()];
      	    String[] lat = new String [c.getCount()];
      	    
             int i = 0;
         
      	    User = "";
      	    
      		StringBuffer buffer=new StringBuffer();
      		
      		while(c.moveToNext())
      		{

      			time[i] = c.getString(1);
      			date[i] = c.getString(2);
      			month[i] = c.getString(3);
      			year[i] = c.getString(4);
      			lon[i] = c.getString(5);
      			lat[i] = c.getString(6);
      			//Toast.makeText(getApplicationContext(), "time= "+time[i]+"date = "+date[i]+"month ="+month[i]+"year = "+year[i]+"lon ="+lon[i]+"lat="+lat[i], Toast.LENGTH_SHORT).show();

      	         i++;
      		}
      		
      		
      	     //open or create database
              db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
              
              Cursor c1 = db.rawQuery("SELECT * FROM user WHERE id='"+1+"'", null);
      		if(c1.getCount() == 0)
      		{
      		  
      		}
      		while( c1.moveToNext() ){
      			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
      		   User = c1.getString(1);
      		}
      		//Toast.makeText(context, ""+User, Toast.LENGTH_SHORT).show();
      	new uploaddata1(context,time,date,month,year,lon,lat,User).execute();
            //status = "Wifi enabled";
      	   }

      	    
      	   
      	    
        	//for the log data
        	  db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
     	     db.execSQL("CREATE TABLE IF NOT EXISTS log(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, status VARCHAR);");
         
              Cursor c2 = db.rawQuery("SELECT * FROM log", null);
       	  // Toast.makeText(context, ""+c2.getCount(),Toast.LENGTH_SHORT).show();
      	    if (c2.getCount() == 0 )
      		{
      	    	Toast.makeText(context, "no data present here for log", Toast.LENGTH_SHORT).show();
      		//return;
      		}
      	    else{
      	    String User_log;
      	    String[] time_log = new String [c2.getCount()];
      	    String[] date_log = new String [c2.getCount()];
      	    String[] month_log = new String [c2.getCount()];
      	    String[] year_log = new String [c2.getCount()];
      	    String[] status_log = new String [c2.getCount()];
      	    
             int j = 0;
             
             
             
        
      	    User_log = "";
      	    
      		StringBuffer buffer=new StringBuffer();
      		
      		while(c2.moveToNext())
      		{

      			time_log[j] = c2.getString(1);
      			date_log[j] = c2.getString(2);
      			month_log[j] = c2.getString(3);
      			year_log[j] = c2.getString(4);
      			status_log[j] = c2.getString(5);
      	
      			//Toast.makeText(context, "time= "+time_log[j]+"date = "+date_log[j]+"month ="+month_log[j]+"year = "+year_log[j], Toast.LENGTH_SHORT).show();

      	         j++;
      		}
      		
      		
      	     //open or create database
              db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
              
              Cursor c3 = db.rawQuery("SELECT * FROM user WHERE id='"+1+"'", null);
      		if(c3.getCount() == 0)
      		{
      		  
      		}
      		while( c3.moveToNext() ){
      			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
      		   User_log = c3.getString(1);
      		}
      		//Toast.makeText(context, ""+User, Toast.LENGTH_SHORT).show();
   	new uploaddata2(context,time_log,date_log,month_log,year_log,status_log,User_log).execute();
            //status = "Wifi enabled";
      	    } 
        
      	    
      	    status = "Wifi enabled";    
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            
        	status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }



    //async task to upload data to server
    
 	
    public void deleteall(){
	    db.delete("location", null, null);
	    
        db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
	}	
	
}