package com.maverick.tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.maverick.tracker.R;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	PendingIntent pendingIntent1,pendingIntent2;
	AlarmManager alarmManager1 ;
	SQLiteDatabase db;
	 
	String username_s, contactno_s, designation_s, empid_s, place_s, imagename_s, password_s, starttime_s, endtime_s;

	TextView username_t, contactno_t, designation_t, empid_t, place_t, imagename_t, password_t, starttime_t, endtime_t;
		
	ImageView mainImageView ;
	String UserName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
    	// for full screen
        //   requestWindowFeature(Window.FEATURE_NO_TITLE);
       
		setContentView(R.layout.activity_main);
		
        //color to action bar
	    getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
	
        StrictMode.enableDefaults();
		
		username_t = (TextView)findViewById(R.id.usernametext);
		contactno_t = (TextView)findViewById(R.id.contactnotext);
		designation_t = (TextView)findViewById(R.id.designationtext);
		empid_t = (TextView)findViewById(R.id.empIDtext);
		place_t = (TextView)findViewById(R.id.placetext);
		password_t = (TextView)findViewById(R.id.passwordtext);
		starttime_t = (TextView)findViewById(R.id.starttimetext);
		endtime_t = (TextView)findViewById(R.id.endtimetext);
		
		
		
		 db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
		  //checking if signed in and then checking if already voted
	   	Cursor c1 = db.rawQuery("SELECT * FROM user", null);
	      if(c1.getCount() == 0)
	   		{
	   			return;
	   		}
	   	  while(c1.moveToNext())
	   		{
	   			//check if new user to register
	   			UserName=c1.getString(1);
	   		
	   			}
	   		
		
		
		new getprofileuserdetails(UserName).execute();
        
        
        db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
       
        db.execSQL("CREATE TABLE IF NOT EXISTS log(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, status VARCHAR);");

       
        //checking for pending intents
         Intent checkIntent = new Intent(getApplicationContext(),receiver.class);
	      checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      boolean k= (PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, checkIntent, PendingIntent.FLAG_NO_CREATE)!=null);
	      if(k == true)
	      {
	         
	    	  Intent intent2 = new Intent(this, receiver.class);
		        pendingIntent2 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent2, 0);
		        pendingIntent2.cancel();
	      }
        
         Calendar cal = Calendar.getInstance();
         int hourofday = cal.get(Calendar.HOUR_OF_DAY);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(System.currentTimeMillis());
         calendar.set(Calendar.HOUR_OF_DAY, hourofday);
         calendar.set(Calendar.MINUTE, 03);

  	        //for setting pending intents for 5 min starting from next hour
  	        Intent intent1 = new Intent(this, receiver.class);
	        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
	        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
	        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 1000*20, pendingIntent1);
  	
  	  
        
  
    }

    public void change(View v){
    	
    	 v.startAnimation(buttonClick);
    	 Intent checkIntent = new Intent(getApplicationContext(),receiver.class);
	      checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      boolean k= (PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, checkIntent, PendingIntent.FLAG_NO_CREATE)!=null);
	      if(k == true)
	      {
	         
	    	  Intent intent1 = new Intent(this, receiver.class);
		        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
		        pendingIntent1.cancel();
	    	  
	    	  //Toast.makeText(getApplicationContext(), "running", Toast.LENGTH_SHORT).show();
	      //  change.setBackgroundResource(R.drawable.play);
	       // t.setText("Tap to start giving your location");
	        
	        
	        
	      }
	      else
	      {

	    	  Calendar cal = Calendar.getInstance();
	          int hourofday = cal.get(Calendar.HOUR_OF_DAY);
	          
	    	  Calendar calendar = Calendar.getInstance();
	          calendar.setTimeInMillis(System.currentTimeMillis());
	           calendar.set(Calendar.HOUR_OF_DAY, hourofday);
	           calendar.set(Calendar.MINUTE, 30);

	    	  /*
	    	  Intent intent1 = new Intent(this, receiver.class);
		        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
		        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
		        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000*10*6*60, pendingIntent1);
	    	
	    	  */
	    	  
	    	 // change.setBackgroundResource(R.drawable.stop);
	    	 //t.setText("Tap to stop giving your location");
	    	 // Toast.makeText(getApplicationContext(), " not running", Toast.LENGTH_SHORT).show();
	      }
	      
    	
    	
    	
    	
    	
    }
/*
    public void start(View v){
    	 v.startAnimation(buttonClick);
    	  Intent intent1 = new Intent(this, receiver.class);
	        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
	        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
	        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10, pendingIntent1);
    	
    }
    
   public void stop(View v){
	   v.startAnimation(buttonClick);
 	  Intent intent1 = new Intent(this, receiver.class);
	        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
	        pendingIntent1.cancel();
    }
    
   public void check(View v){
   	
	   Intent checkIntent = new Intent(getApplicationContext(),receiver.class);
	      checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      boolean k= (PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, checkIntent, PendingIntent.FLAG_NO_CREATE)!=null);
	      if(k==true)
	      {
	         
	    	  Toast.makeText(getApplicationContext(), "running", Toast.LENGTH_SHORT).show();
	      
	      }
	      else
	      {
	    	 
	    	  Toast.makeText(getApplicationContext(), " not running", Toast.LENGTH_SHORT).show();
	      }
	      
	    }
   */
   /*
   public void upload(View v){
   	
	   Cursor c = db.rawQuery("SELECT * FROM location", null);
	   
	    if (c.getCount() == 0 )
		{
	    	Toast.makeText(getApplicationContext(), "no data present here", Toast.LENGTH_SHORT).show();
		return;
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
        db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
        
        Cursor c1 = db.rawQuery("SELECT * FROM user WHERE id='"+1+"'", null);
		if(c1.getCount() == 0)
		{
		  
		}
		while( c1.moveToNext() ){
			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
		   User = c1.getString(1);
		}
	//	Toast.makeText(getApplicationContext(), ""+User, Toast.LENGTH_SHORT).show();
	new uploaddata(time,date,month,year,lon,lat,User).execute();
		
		}
		
		 
		
	//deleteall();	//for deleting all rows from database
		
	   
	
	
 	}
 */
   public void deleteall(){
	    db.delete("location", null, null);
	    
        db.execSQL("CREATE TABLE IF NOT EXISTS location(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, time VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR, lon VARCHAR, lat VARCHAR);");
	}
   
  
   /*
   //async task to upload data to server
   public class uploaddata extends AsyncTask <Void, Void, Void>{
	       String[] lon=new String[]{};
	       String[] lat=new String[]{};
	       String[] time=new String[]{};
	       String[] date=new String[]{};
	       String[] month=new String[]{};
	       String[] year=new String[]{};
	       String User;
	       int i;
	   	  InputStream isr = null;
	   	  String result = "";	 
	   	  
	     	ProgressDialog dialog = new ProgressDialog(MainActivity.this);

	   	    public uploaddata(String[] time,String[] date,String[] month ,String[] year,String[] lon, String[] lat,String User){
	   	      this.lon = lon;
	   	      this.lat = lat;
	   	      this.time = time;
	   	      this.User=User;
	   	      
	   	   this.date = date;
	   	      this.month = month;
	   	      this.year = year;
	   	      
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
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/text.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("count",""+lat.length));
				  nameValuePairs.add(new BasicNameValuePair("User",User));
				  for(i=0;i<lat.length;i++){
				  nameValuePairs.add(new BasicNameValuePair("time"+i,time[i]));
				  nameValuePairs.add(new BasicNameValuePair("date"+i,date[i]));
				  nameValuePairs.add(new BasicNameValuePair("month"+i,month[i]));
				  nameValuePairs.add(new BasicNameValuePair("year"+i,year[i]));
				  nameValuePairs.add(new BasicNameValuePair("lon"+i, lon[i]));
				  nameValuePairs.add(new BasicNameValuePair("lat"+i, lat[i]));
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
	 
	         dialog.dismiss();
	         deleteall(); 
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
	  
	         Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
	 
		  }
	}
	*/
   
   
   
  
	
	
	
	public class getprofileuserdetails extends AsyncTask<Void, Void, Void>{
		
	  InputStream isr = null;
	  String result = "";	 
	   	 
	  String username_s;
 	      
     ProgressDialog dialog = new ProgressDialog(MainActivity.this);
 	   
 	  public getprofileuserdetails(String username_s ){
 	      
 		this.username_s = username_s;	
 		
 	  }

 	  @Override
 	  protected void onPreExecute() {
 	   
 		 // TODO Auto-generated method stub
 		  
 		 dialog.setMessage("Please wait..."); 
 		 dialog.setCanceledOnTouchOutside(false);
 		 dialog.show();
 		  
                
        
 	  }

     @Override
 	  protected Void doInBackground(Void... params) {
 	   
 		    	
 			try{
 			    	 
 			  HttpClient httpclient = new DefaultHttpClient();
 			    	 
 			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GETPROFILEUSER.php"); //YOUR PHP SCRIPT ADDRESS
 						    	 
 			    	
 			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
 			    	
 			  nameValuePairs.add(new BasicNameValuePair("UserName", username_s));
 		 	
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
	  
	     // Toast.makeText(getApplicationContext(), "Result" + result, Toast.LENGTH_LONG).show();
	  
	         char check_char = result.charAt(0);
			 int result_n = Character.getNumericValue(check_char);
		     if(result_n == 0){
			 		  
			 	//Toast.makeText(getApplicationContext(), "Data of this user is not present", Toast.LENGTH_SHORT).show();
			 }
			 else{
			 	
				 // Toast.makeText(getApplicationContext(), "User data is present", Toast.LENGTH_SHORT).show();

			 		  
				 try {
				      	JSONArray jArray = new JSONArray(result);

				             	
				      	for( int i = 0 ; i < jArray.length( ); i++ ){
				      	 
				      		JSONObject json = jArray.getJSONObject(i);
				      			  
				      		 username_s = json.getString("NAME");
				      		 password_s = json.getString("PASSWORD");
				      		 contactno_s = json.getString("CONTACTNO");
				      		 designation_s = json.getString("DESIGNATION");				      		 
				      		 empid_s = json.getString("EMPID");
				      		 place_s = json.getString("PLACE");
				      		 starttime_s = json.getString("STARTTIME");
				      		 endtime_s = json.getString("ENDTIME");
				      		 imagename_s = json.getString("IMAGENAME");
	      	
	      	 	   // Toast.makeText(getApplicationContext(), "Image: " + imagename_s , Toast.LENGTH_SHORT).show();	
	      	 	    
	      	 	mainImageView = (ImageView) findViewById(R.id.imageView);
	 	        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
	 	        String imageurl = "http://www.spmaverick.com/TrackMe/uploadedimages/" + imagename_s;
	 	        
	 	        
	 	        username_t.setText("Name: " + username_s);
	 	        password_t.setText("" + password_s);
	 	        contactno_t.setText("" + contactno_s);
	 	        designation_t.setText("Designation: " + designation_s);
	 	        empid_t.setText("EMP CODE: " + empid_s);
	 	        place_t.setText("" + place_s);
	 	        starttime_t.setText("" + starttime_s);
  		        endtime_t.setText("" + endtime_s);
	 
	 	        
	 	        ImageDownloadMessageHandler imageDownloadMessageHandler1= new ImageDownloadMessageHandler(progressBar, mainImageView);
	 	        ImageDownlaodThread imageDownlaodThread = new ImageDownlaodThread(imageDownloadMessageHandler1,imageurl);
	 	        imageDownlaodThread.start();
	 	        
	 	        
	      	 	    
		    	}
	      	
		     }catch(Exception e){
			
	      	   Toast.makeText(getApplicationContext(), "Error Loading", Toast.LENGTH_SHORT).show();	

		}
	      
	  }
 	  }  
	
}
	
	 class ImageDownlaodThread extends Thread {
		 
	        ImageDownloadMessageHandler imageDownloadMessageHandler;
	        String imageUrl;

	        public ImageDownlaodThread(ImageDownloadMessageHandler imageDownloadMessageHandler, String imageUrl) {
	         this.imageDownloadMessageHandler = imageDownloadMessageHandler;
	         this.imageUrl = imageUrl;
	        }

	        @Override
	        public void run() {
	         Drawable drawable = LoadImageFromWebOperations(imageUrl);
	         Message message = imageDownloadMessageHandler.obtainMessage(1, drawable);
	         imageDownloadMessageHandler.sendMessage(message);
	         System.out.println("Message sent");
	        }

	       }
	

   class ImageDownloadMessageHandler extends Handler {
    ProgressBar progressBar;
    View imageTextView;

    public ImageDownloadMessageHandler(ProgressBar progressBar, View imageTextView) {
     this.progressBar = progressBar;
     this.imageTextView = imageTextView;
    }

    @Override
    public void handleMessage(Message message) {
     progressBar.setVisibility(View.GONE);
     imageTextView.setBackgroundDrawable(((Drawable) message.obj));
     imageTextView.setVisibility(View.VISIBLE);
    }

   }
	  Drawable LoadImageFromWebOperations(String url) {
	        Drawable d = null;
	        InputStream is = null;
	        try {
	         is = (InputStream) new URL(url).getContent();
	         d = Drawable.createFromStream(is, "src name");
	        } catch (MalformedURLException e) {
	         e.printStackTrace();
	        } catch (IOException e) {
	         e.printStackTrace();
	        }
	        return d;
	       }


	
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Toast.makeText(getApplicationContext(), "settings clicked", Toast.LENGTH_SHORT).show();

           	/*
     	   Cursor c = db.rawQuery("SELECT * FROM location", null);
     	   
     	    if (c.getCount() == 0 )
     		{
     	    	Toast.makeText(getApplicationContext(), "no data present here", Toast.LENGTH_SHORT).show();
     		
     		}
     	    else{
     	    String User="";
     	    String[] time = new String [c.getCount()];
     	    String[] date = new String [c.getCount()];
     	    String[] month = new String [c.getCount()];
     	    String[] year = new String [c.getCount()];
     	    String[] lon = new String [c.getCount()];
     	    String[] lat = new String [c.getCount()];
     	    
            int i = 0;
       
            
            //open or create database
             db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
             
             Cursor c1 = db.rawQuery("SELECT * FROM user WHERE id='"+1+"'", null);
     		if(c1.getCount() == 0)
     		{
     		  
     		}
     		while( c1.moveToNext() ){
     			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
     		   User = c1.getString(1);
     		}
     	
     	    //  User = "local";//database warun username ghe
     		StringBuffer buffer = new StringBuffer();
     		
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
     		
     	 new uploaddata(time,date,month,year,lon,lat,User).execute();
     		
     		}
     		
     		 
     		
     	//deleteall();	//for deleting all rows from database
     		
     	   */
     	
     	
      	
            return true;
        }
        if (id == R.id.action_aboutus) {
        	
        	Intent i = new Intent(MainActivity.this, AboutUs.class);
        	startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
