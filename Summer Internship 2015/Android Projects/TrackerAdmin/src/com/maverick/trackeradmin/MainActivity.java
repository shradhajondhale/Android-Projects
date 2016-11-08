package com.maverick.trackeradmin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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



import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maverick.trackeradmin.R;


public class MainActivity extends ActionBarActivity {
String Users[];
ListView lv;
ArrayAdapter<String> adapter;

String[] Lon;
String[] Lat;
String[] Time;
String[] Address;
SQLiteDatabase db;

//net connection
Boolean isInternetPresent;
ConnectionDetector cd;


AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
  //      getActionBar().setTitle(Html.fromHtml("<font color='#00C0DE'>Tracker Admin</font>"));
        
        StrictMode.enableDefaults();
        
        //FOR CREATING DATABASE
        db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
        
       // db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Address VARCHAR);");
       // db.execSQL("INSERT INTO tracks VALUES('arpit','123','456','time','address');");
     
        db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Date VARCHAR,Address VARCHAR);");

  	
        
        //CREATED DATABASE
        lv = (ListView)findViewById(R.id.listView1);
     
    
        
   	   new GetUsersList().execute();
    
    
    
  
    //this is on item long click
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    	
	    @Override
	     public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, long id) {
	                
	       // TODO: Get the position of the item clicked. 
	       //       Delete it from your collection eg.ArrayList.
	       //       Call notifydatasetChanged so that it will refresh 
	       //       the views displaying updated list.
	            	
	      final String selectedValue = (String) lv.getItemAtPosition(pos);
	      
	      AlertDialog.Builder alertDialog = new  AlertDialog.Builder(MainActivity.this);
	      alertDialog.setTitle("Delete");
	      alertDialog.setMessage(selectedValue);     
	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                	
	          public void onClick(DialogInterface dialog, int which) {
                    
	              // Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
	       	               
	           } }); 
	                
	      alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
	                      
	            public void onClick(DialogInterface dialog, int which) {
	            	 // ListView Clicked item value
	         	     String  itemValue  = (String) lv.getItemAtPosition(pos);
	         	             
	         	   
	         	             
	         	// Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
	         	    
	              } }); 

	      alertDialog.show();
	         return true;}});
	 
}


    //async task to upload data to server
    public class GetUsersList extends AsyncTask <Void, Void, Void>{
 	   	 
    	InputStream isr = null;
 	   	  String result = "";	 
 	     	ProgressDialog dialog = new ProgressDialog(MainActivity.this);

 	   	    public GetUsersList(){ }

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
 				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GetAllUsers.php"); //YOUR PHP SCRIPT ADDRESS
 				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
 				  nameValuePairs.add(new BasicNameValuePair("admin","admin"));
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
 		       if(result_n==0){
 			 		  
 			 		  Toast.makeText(getApplicationContext(), "Track data of this user is absent.", Toast.LENGTH_SHORT).show();
 			 	  }
 			 	  else{
 			 		  //Toast.makeText(getApplicationContext(), "User daata is present", Toast.LENGTH_SHORT).show();

 			 		  
 	      try {
 	      	JSONArray jArray = new JSONArray(result);

 	      	Users = new String[jArray.length( )];
 	      	for( int i = 0 ; i < jArray.length( ); i++ ){
 	      	 
 	      		JSONObject json = jArray.getJSONObject(i);
 	      	 	 Users[i] = json.getString("NAME");		
 	      	 	 
 	      	//Toast.makeText(getApplicationContext(), Users[i], Toast.LENGTH_SHORT).show();	
 	      	}	  
 	      	
 	      	
 	       adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.userslist, R.id.userslisttext, Users);
 	        
 	        lv.setAdapter(adapter);
 	      	lv.setOnItemClickListener(new OnItemClickListener(){
 	      		

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					
					 view.startAnimation(buttonClick);
					
					// TODO Auto-generated method stub
					String UserName = (String)lv.getItemAtPosition(position);
				  // Toast.makeText(getApplicationContext(), "" +itemValue , Toast.LENGTH_SHORT).show();
                 

					if( checknetforref() == 1 ){
					
					new Plots(UserName).execute();
					}
					else{
						Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
						
					}
					/*
					Intent i = new Intent(MainActivity.this,MapPlot.class);
					i.putExtra("UserName", UserName);
					startActivity(i);
					
	*/				
				}
 	      	});
 			  
 	      	
 		}catch(Exception e){
 			
 			
 		}
 	      
 	   	 }
 	  
 		  }
    }
    
    
    
    
    

	int getweekno(int Day,int Month,int Year) {
		 
		if((Year%4)==0){
		 
		 if(Month==1){
		return (Day/7)+1;
		}
		else if(Month==2){
		return ((31+Day)/7)+1;
		} 
		else if(Month==3){
		return ((60+Day)/7)+1;
		} 
		else if(Month==4){
		return ((91+Day)/7)+1;
		} 
		else if(Month==5){
		return ((121+Day)/7)+1;
		} 
		else if(Month==6){
		return ((152+Day)/7)+1;
		} 
		else if(Month==7){
		return ((182+Day)/7)+1;
		} 
		else if(Month==8){
		return ((213+Day)/7)+1;
		} 
		else if(Month==9){
		return ((244+Day)/7)+1;
		} 
		else if(Month==10){
		return ((274+Day)/7)+1;
		} 
		else if(Month==11){
		return ((305+Day)/7)+1;
		} 
		else if(Month==12){
		return ((335+Day)/7)+1;
		} 

		}
		else{

		  if(Month==1){
		return (Day/7)+1;
		}

		else if(Month==2){
		return ((31+Day)/7)+1;
		} 
		else if(Month==3){
		return ((59+Day)/7)+1;
		} 
		else if(Month==4){
		return ((90+Day)/7)+1;
		} 
		else if(Month==5){
		return ((120+Day)/7)+1;
		} 
		else if(Month==6){
		return ((151+Day)/7)+1;
		} 
		else if(Month==7){
		return ((181+Day)/7)+1;
		} 
		else if(Month==8){
		return ((212+Day)/7)+1;
		} 
		else if(Month==9){
		return ((243+Day)/7)+1;
		} 
		else if(Month==10){
		return ((273+Day)/7)+1;
		} 
		else if(Month==11){
		return ((304+Day)/7)+1;
		} 
		else if(Month==12){
		return ((334+Day)/7)+1;
		} 

		}
		return 0;
		}
    
    
    public void adduser(View v){

		if( checknetforref() == 1 ){
		
    	Intent i =new Intent(this,AddUser.class);
       startActivity(i);
		}
		else{
	    	Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT).show();
		}
    	//Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
    }


    
    
    public class Plots extends AsyncTask <Void, Void, Void>{
    	
	   	  InputStream isr = null;
	   	  String result = "";	
	   	  String UserName;
	   	  
	   	String Date ;
  	 	String Month;
  	 	String Year;
  	    String Week;	
	   	  
	     	ProgressDialog dialog = new ProgressDialog(MainActivity.this);

	   	    public Plots(String UserName){ 
	   	    	
	   	    	this.UserName = UserName;
	   	    	
	   	    }

	   	    @Override
	   	    protected void onPreExecute() {
	   	    	
	   	    	
          	Calendar cal = Calendar.getInstance(); 
        	 
       	 	 Date = Integer.toString(cal.get(Calendar.DATE));
       	     Month = Integer.toString( cal.get(Calendar.MONTH)+1);
       	 	 Year = Integer.toString(cal.get(Calendar.YEAR));
	   	    // Week = Integer.toString(cal.get(Calendar.WEEK_OF_YEAR));
	   	    
        	//  Week = Integer.toString(cal.get(Calendar.WEEK_OF_YEAR));
     	 	  Week = Integer.toString(getweekno(cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)));	
	   	      dialog.setMessage("Loading...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GetLocationsOfUser.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("User",UserName));
				  nameValuePairs.add(new BasicNameValuePair("Year",Year));
				  nameValuePairs.add(new BasicNameValuePair("Month",Month));
				  nameValuePairs.add(new BasicNameValuePair("Day",Date));
				  nameValuePairs.add(new BasicNameValuePair("Week",Week));
				  
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
	       // Toast.makeText(getApplicationContext(), ""+UserName, Toast.LENGTH_SHORT).show();
	       //Toast.makeText(getApplicationContext(), "Result"+result, Toast.LENGTH_LONG).show();
		
	       
	  	 char check_char = result.charAt(0);
		 int result_n = Character.getNumericValue(check_char);
	       if(result_n==0){
		 		  
	    	   Intent i = new Intent(MainActivity.this, UserDetails.class);
				i.putExtra("UserName", UserName);
				startActivity(i);
	    	   
		 		  Toast.makeText(getApplicationContext(), "Track data of this user is absent.", Toast.LENGTH_SHORT).show();
		 	  }
		 	  else{
		 		  //Toast.makeText(getApplicationContext(), "User daata is present", Toast.LENGTH_SHORT).show();

		 		  
	  
	      try {
	      	JSONArray jArray = new JSONArray(result);

	      	Lon=new String[jArray.length( )];
	    	Lat=new String[jArray.length( )];
	    	Time=new String[jArray.length( )];
	    	Address=new String[jArray.length( )];
	    	
	    	 db.delete("tracks", null, null);
	    	   
	    	 db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
	         db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Date VARCHAR,Address VARCHAR);");
           // db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Address VARCHAR);");	      	
	    	 if( jArray.length( )==0){
	    		 
	    		 Toast.makeText(getApplicationContext(), "No data sent.", Toast.LENGTH_SHORT).show();
	    	 }
	    	 else{
	            for( int i = 0 ; i < jArray.length( ); i++ ){
	      	 
	      		JSONObject json = jArray.getJSONObject(i);
	      		 db.execSQL("INSERT INTO tracks VALUES('"+i+"','"+UserName+"','"+json.getString("LON")+"','"+json.getString("LAT")+"','"+json.getString("TIME")+"  "+json.getString("DATE")+"/"+json.getString("MONTH")+"/"+json.getString("YEAR")+"','"+json.getString("DATE")+"','"+json.getString("ADDRESS")+"');"); //no initial phone no and not registered   
	      		 
	      		
	      	}	
	    	 }
	      	
	     
	      	
			  
	      	
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
			
		}
	      
	     
	      
	        Intent i = new Intent(MainActivity.this, TabActivity.class);
			i.putExtra("UserName", UserName);
			MainActivity.this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
			startActivity(i);      
	      
	      
		  }
 }
    }
    

	//check net connection
	
	 int checknetforref(){
		  
		  cd = new ConnectionDetector(getApplicationContext());
		  isInternetPresent = cd.isConnectingToInternet(); 
		 
		  if( isInternetPresent == false ){
			
				return 0;
		  }
		  else{
			  return 1;
			  
		  }
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
        	
        	if( checknetforref() == 1 ){
				
        	Intent i = new Intent(MainActivity.this, Settings.class);
			startActivity(i);
        	}
        	else{
        		Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
        		
        	}
        }
       
        return super.onOptionsItemSelected(item);
    }
}
