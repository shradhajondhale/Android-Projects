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
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

public class uploaddata1 extends AsyncTask <Void, Void, Void>{
    String[] lon=new String[]{};
    String[] lat=new String[]{};
    String[] time=new String[]{};
    String[] date=new String[]{};
    String[] month=new String[]{};
    String[] year=new String[]{};
    Context context;
    String User;
    int i;
    SQLiteDatabase db;
	  InputStream isr = null;
	  String result = "";	 
	  
  	//ProgressDialog dialog = new ProgressDialog(MainActivity.this);

	    public uploaddata1(Context context,String[] time,String[] date,String[] month ,String[] year,String[] lon, String[] lat,String User){
	      this.lon = lon;
	      this.lat = lat;
	      this.time = time;
	      this.User=User;
	      this.context=context;
	      
	   this.date = date;
	      this.month = month;
	      this.year = year;
	      
	  }

	    @Override
	    protected void onPreExecute() {
	     // dialog.setMessage("Loading...");
		  //dialog.setCanceledOnTouchOutside(false);
		  //dialog.show();

	  }

	    @Override
	    protected Void doInBackground(Void... params) {
		   //-------------------
		
			    	
			try{
			    	 
			  HttpClient httpclient = new DefaultHttpClient();
			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/text.php"); //YOUR PHP SCRIPT ADDRESS
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			  nameValuePairs.add(new BasicNameValuePair("count",""+lat.length));
			  nameValuePairs.add(new BasicNameValuePair("User",User));
			  for(i=0;i<lat.length;i++){
			  nameValuePairs.add(new BasicNameValuePair("time" + i,time[i]));
			  nameValuePairs.add(new BasicNameValuePair("date" + i,date[i]));
			  nameValuePairs.add(new BasicNameValuePair("month" + i,month[i]));
			  nameValuePairs.add(new BasicNameValuePair("year" + i,year[i]));
			  nameValuePairs.add(new BasicNameValuePair("lon" + i, lon[i]));
			  nameValuePairs.add(new BasicNameValuePair("lat" + i, lat[i]));
			  }
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  HttpResponse response = httpclient.execute(httppost);
			    	 
			  HttpEntity entity = response.getEntity();
			    	 
			  isr = entity.getContent();
			   	
			}catch(Exception e){
			   
			}  	
		return null;
}
	 protected void onPostExecute(Void unused) {

      //dialog.dismiss();
		 db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
	    db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
		 db.delete("location", null, null);
	    db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
		 
		 try{
       	
   	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
   	 StringBuilder sb = new StringBuilder();
   	 String line = null;
   	 
   	 while ((line = reader.readLine()) != null) {
   	 
   		 sb.append(line + "\n");
   	}
   	 isr.close();
   	 result = sb.toString();
   	 
   	}catch(Exception e){
   	 
   	}

      Toast.makeText(context.getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();

	  }
}
