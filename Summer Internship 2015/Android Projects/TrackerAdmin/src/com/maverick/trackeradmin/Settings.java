package com.maverick.trackeradmin;

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
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends ActionBarActivity {
	
int intervalchoice = -1;
TextView intervaltext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
		 StrictMode.enableDefaults();
		 
		 intervaltext = (TextView)findViewById(R.id.intervaltext);
		 
		new GETINTERVAL().execute();
	}
	
	
	public void addmember(View v){
		
		Intent i = new Intent(Settings.this, AddUser.class);
		startActivity(i);
	}
	
	public void interval(View v){	
		final CharSequence[] interval = {"Today","This week", "This month"};

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Select one");

		alert.setSingleChoiceItems(interval, intervalchoice , new DialogInterface.OnClickListener()

		{
		    @Override
		    public void onClick(DialogInterface dialog, int which) 
		    {

          	  if(which == 0)

		        {
          		    intervalchoice = 0;
		        }

		        else if (which == 1)

		        {
		        	intervalchoice = 1;
		        	
		        }
		        else if (which == 2)

		        {
		        	intervalchoice = 2;		        
		        }
            
		        }

		});
		  alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
              
	            public void onClick(DialogInterface dialog, int whichok) {
	            	
	            	new SETINTERVAL(Integer.toString(intervalchoice+1)).execute();
	            	/*if(intervalchoice == 0)
			        {
	            		new SETINTERVAL()
			            Toast.makeText(getApplicationContext(), "today", Toast.LENGTH_SHORT).show();
			        }

			        else if (intervalchoice == 1)
			        {
			        	Toast.makeText(getApplicationContext(), "this week", Toast.LENGTH_SHORT).show();
			        }
			        else if (intervalchoice == 2)
			        {
			        	Toast.makeText(getApplicationContext(), "this month", Toast.LENGTH_SHORT).show();
			        }
	            	*/
	            	
	            } }); 
		  
		alert.show();
		
		
	}
	
	public void aboutus(View v){
		
		Intent i = new Intent(Settings.this, AboutUs.class);
	    startActivity(i);
	}
	
	public class GETINTERVAL extends AsyncTask <Void, Void, Void>{
	   	  InputStream isr = null;
	   	  String result = "";	
	   	  String UserName;
	     	ProgressDialog dialog = new ProgressDialog(Settings.this);

	   	    public GETINTERVAL(){ 
	   	    //	this.UserName=UserName;
	   	    	
	   	    }

	   	    @Override
	   	    protected void onPreExecute() {
	   	      dialog.setMessage("Loading...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/Settings.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("Interval","0"));
				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				  HttpResponse response = httpclient.execute(httppost);
				  HttpEntity entity = response.getEntity();
				  isr = entity.getContent(); 	
				}catch(Exception e){
				}  	
			return null;
	   }
	   	 protected void onPostExecute(Void unused) {
	         dialog.dismiss();
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
	  
			 
			 char check_char = result.charAt(0);
			 int done = Character.getNumericValue(check_char);
	        // Toast.makeText(getApplicationContext(), "" + done, Toast.LENGTH_LONG).show();
		      setinterval(done);
	   	 
	   	     }
		  }

	
	public class SETINTERVAL extends AsyncTask <Void, Void, Void>{
	   	  InputStream isr = null;
	   	  String result = "";	
	   	  String Interval;
	     	ProgressDialog dialog = new ProgressDialog(Settings.this);

	   	    public SETINTERVAL(String Interval){ 
	   	    	this.Interval=Interval;
	   	    	
	   	    }

	   	    @Override
	   	    protected void onPreExecute() {
	   	      dialog.setMessage("Loading...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/Settings.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("Interval",Interval));
				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				  HttpResponse response = httpclient.execute(httppost);
				  HttpEntity entity = response.getEntity();
				  isr = entity.getContent(); 	
				}catch(Exception e){
				}  	
			return null;
	   }
	   	 protected void onPostExecute(Void unused) {
	         dialog.dismiss();
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
	  
	        // Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
			 char check_char = result.charAt(0);
			 int done = Character.getNumericValue(check_char);
	        // Toast.makeText(getApplicationContext(), "" + done, Toast.LENGTH_LONG).show();
		      setinterval(done);
	         
	         
	         
		}
		  }

	
	
	public void setinterval( int i){
		
		if( i == 1){
			intervaltext.setText("Today");
			intervalchoice =0;
		}
		else if( i == 2){
			intervaltext.setText("This week");
			intervalchoice=1; 
		}
		else if( i == 3){
			intervaltext.setText("This month");
			intervalchoice =2;
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
