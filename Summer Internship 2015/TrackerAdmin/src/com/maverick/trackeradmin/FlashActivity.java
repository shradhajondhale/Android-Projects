package com.maverick.trackeradmin;


import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.provider.Settings;

public class FlashActivity extends ActionBarActivity {

	int check_if_registered = 1;
	SQLiteDatabase db;
	

	//net connection
	Boolean isInternetPresent;
	ConnectionDetector cd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.activity_flash);
		
		/*
		 
   		new CountDownTimer(2000, 1000) {
             
   			public void onTick(long millisUntilFinished) { }

     	    public void onFinish() {
     	    	

     		    Intent intent = new Intent(FlashActivity.this, MainActivity.class);
     		    startActivity(intent);
     		    finish();
     		    	
     		    	
     		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
     		  }
      }.start();
	
	*/
		
		
		if( checknetforref() == 1 ){
		
		//open or create database
	      db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
		
	      
	      
	      
	      isFirstTime();
	   
		
		
		  //checking if signed in and then checking if already voted
	   	Cursor c = db.rawQuery("SELECT * FROM user", null);
	      if(c.getCount() == 0)
	   		{
	   			return;
	   		}
	   	  while(c.moveToNext())
	   		{
	   			//check if new user to register
	   			if( (c.getString(1).equals("0")) ){
	   			
	   				check_if_registered = 0;
	   			    	
	   			}
	   		}
	   		
	   	  //Toast.makeText(getApplicationContext(), ""+check_if_registered, Toast.LENGTH_SHORT).show();
	   		
	     // go to another activity after checking
	   	///-------------------
	   	if(check_if_registered == 1){
	   	    
	   		new CountDownTimer(1500, 1000) {
	             
	   			public void onTick(long millisUntilFinished) { }

	     	    public void onFinish() {
	     	    	

	     		    Intent intent = new Intent(FlashActivity.this, MainActivity.class);
	     		    startActivity(intent);
	     		    finish();
	     		    	
	     		    	
	     		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
	     		  }
	      }.start();
	     	    	
	     		    
	     		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
	     		 
	   			  
	   			  
	   	}
	   	else if(check_if_registered == 0){
	   	 
	   		new CountDownTimer(1500, 1000) {
	             
	   			public void onTick(long millisUntilFinished) { }

	     	    public void onFinish() {
	     	    	
	   		  Intent intent = new Intent(FlashActivity.this, Login.class);
	          startActivity(intent);
	       	  finish();
	      	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
	     		  }
	      }.start();
	     	    	
	   	}//end else if
		
	   	
	   	
	   	
		}
		
		
else{
		
	
	  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(FlashActivity.this);
	     alertDialog.setTitle("Error");
	     alertDialog.setMessage("Connect to Internet");     
	     alertDialog.setCancelable(false);
	      	                
	     alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      	                      
	      public void onClick(DialogInterface dialog, int which) {
	      	            	
	     	 finish();
	     	         	  
	      	} }); 

	     alertDialog.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
	           
	         public void onClick(DialogInterface dialog, int which) {
	         	            	
	        	  startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	         	  //Toast.makeText(getApplicationContext(), "ok ok", Toast.LENGTH_SHORT).show();
	        	  finish();
	        	         	  
	         	} }); 
	      alertDialog.show();
	     
			
	}
		
}
	
	
	
	private boolean isFirstTime() {
	   	 
	    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	    boolean ranBefore = preferences.getBoolean("RanBefore", false);
	         
	    if (!ranBefore) {
	       	 
	       SharedPreferences.Editor editor = preferences.edit();
	       editor.putBoolean("RanBefore", true);
	       editor.commit(); 
	           
	       db.execSQL("CREATE TABLE IF NOT EXISTS user(id VARCHAR, username VARCHAR);");
	       db.execSQL("INSERT INTO user VALUES('1' , '0');"); //no initial phone no and not registered
	         
	    }
	    	  return ranBefore;
	        
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
		getMenuInflater().inflate(R.menu.flash, menu);
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
