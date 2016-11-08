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

public class uploaddata2 extends AsyncTask <Void, Void, Void>{
    String[] status=new String[]{};
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

	    public uploaddata2(Context context,String[] time,String[] date,String[] month ,String[] year,String[] status,String User){
	      this.status = status;
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
			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/ADDLOG.php"); //YOUR PHP SCRIPT ADDRESS
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			  nameValuePairs.add(new BasicNameValuePair("count","" + status.length));
			  nameValuePairs.add(new BasicNameValuePair("User",User));
			  for(i=0;i<status.length;i++){
			  nameValuePairs.add(new BasicNameValuePair("time" + i, time[i]));
			  nameValuePairs.add(new BasicNameValuePair("date" + i, date[i]));
			  nameValuePairs.add(new BasicNameValuePair("month" + i, month[i]));
			  nameValuePairs.add(new BasicNameValuePair("year" + i, year[i]));
			  nameValuePairs.add(new BasicNameValuePair("status" + i, status[i]));
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
	     db.execSQL("CREATE TABLE IF NOT EXISTS log(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, status VARCHAR);");
		 
		 db.delete("log", null, null);
	     db.execSQL("CREATE TABLE IF NOT EXISTS log(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, status VARCHAR);");
		 
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

   //   Toast.makeText(context.getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();

	  }
}
