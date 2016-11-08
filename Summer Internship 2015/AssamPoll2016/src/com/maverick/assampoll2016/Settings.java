package com.maverick.assampoll2016;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

public class Settings extends ActionBarActivity {

	SQLiteDatabase db, db2;
	String count_s;
	int count_n;
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
         setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		// If your minSdkVersion is 11 or higher use:
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//color to action bar
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));
		
		setContentView(R.layout.activity_settings);
		
		//open or create database
	      db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
	      
	      db2 = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);

	      Cursor c1 = db2.rawQuery("SELECT * FROM del WHERE id='"+1+"'", null);
			if(c1.getCount() == 0)
			{
			   return;
			}
			while( c1.moveToNext() ){
				count_s = c1.getString(1);
				count_n = Integer.parseInt(""+count_s);
				//Toast.makeText(getApplicationContext(), "count "+count_n , Toast.LENGTH_SHORT).show();
			  
			}
			
	      
	//   db.execSQL("CREATE TABLE IF NOT EXISTS student(id VARCHAR, phone VARCHAR, phoneverification VARCHAR, constituency VARCHAR);");
	//   db.execSQL("INSERT INTO student VALUES('"+ 1 +"' , '0' , '0', '0');"); //no initial phone no and not registered
	      
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
	
	/*
	public void deleteacccount(View v){
	
		 v.startAnimation(buttonClick);
		 
		 	
		 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Settings.this);
  	      alertDialog.setTitle("DELETE ACCOUNT");
  	      alertDialog.setIcon(R.drawable.confirm);
  	   // alertDialog.setCancelable(false);
  	      alertDialog.setMessage("Are you sure you want to delete your account ? You can delete it " + (3 - count_n) + " more times.");     
  	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
  	                	
  	          public void onClick(DialogInterface dialog, int which) {
                      
  	            //Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
  	       	               
  	           } }); 
  	                
  	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
  	                      
  	            public void onClick(DialogInterface dialog, int which) {
  	            	
  	            	count_n = count_n  + 1;
  	            	
  	            	

  	            	Cursor c1 = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
  	      		if(c1.moveToFirst())
  	      		{
  	      			 db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
  	      		
  	      			 db.execSQL("UPDATE del SET delcount ='"+count_n+"' WHERE id='"+ 1 +"'");
   	      		//Toast.makeText(getApplicationContext(), "done ", Toast.LENGTH_SHORT).show();

  	      		}	
  	      	 else
  	      		{
  	      			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
  	      		}	
  	            	
  	            	
  	            if(count_n < 3){	
  	           	Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
  	      		if(c.moveToFirst())
  	      		{
  	      			 db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
  	      			db.execSQL("UPDATE student SET phone ='0', phoneverification ='0', constituency='0' WHERE id='"+ 1 +"'");
  	      		//	Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
  	      		
  	      		
  	      		}
  	      		else
  	      		{
  	      			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
  	      		}
  	      		
	      	Toast.makeText(getApplicationContext(), "Successfully deleted you account", Toast.LENGTH_SHORT).show();

  	      		
  	      		
  	      		Intent i = new Intent(Settings.this, FlashActivity.class );
  	      		startActivity(i);
  	      		finish();
 	  		
  	            	//new Update(get_phone_number_s, "OTHERS").execute();
  	            	
  	            }
  	            else{
  	            	
  	            	
  	            	Toast.makeText(getApplicationContext(), "Sorry. You have exceeded the maximum limit of deleting your account. ", Toast.LENGTH_SHORT).show();
  	            	
  	            }
  	            
  	              } }); 

  	      alertDialog.show();
		
		
		
		
		
	//	Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
	}
	
	
	*/
	
	
		
    public void help(View v){
		
    	 v.startAnimation(buttonClick);
    	 Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
         myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
         myWebLink.setData(Uri.parse("http://spmaverick.com"));
         startActivity(myWebLink);
         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

    	 
    	 /*
    	String[] TO = {"arpit.padwekara@gmail.com"};
	      String[] CC = {""};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");
	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "HELP FOR ASSAMPOLL ANDROID APP");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your Problem here");
	      
	      try {
	         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	         finish();
	         
	      }
	      catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(Settings.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   
    	
	//	Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
	
	*/
	}
	
	

	
	
	
	public void termsandcon(View v){
		
		
		 v.startAnimation(buttonClick);
		 //Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
		 AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setMessage("\nPlease read these terms and conditions carefully before using Assam Poll 2016.\n\nThe Application is currently made available to you free of charge for non-commercial use. You will not, nor allow third parties on your behalf to (i) make and distribute copies of the Application (ii) attempt to copy, reproduce, alter, modify, reverse engineer, disassemble, decompile, transfer, exchange or translate the Application; or (iii) create derivative works of the Application of any kind whatsoever. Please acknowledge the Application is provided over the internet and mobile networks and so the quality and availability of the Application may be affected by factors outside our reasonable control.\n");
        builder.setTitle("Terms and Conditions");
        builder.setPositiveButton("OK",
   			
				new DialogInterface.OnClickListener() {
		
			public void onClick(DialogInterface dialog,int which) {
		
			
		   }
		});
		
		builder.show();
       
	}
	
	
	
	public void aboutus(View v){
		
		
		 v.startAnimation(buttonClick);
		Intent i = new Intent(this, AboutUs.class );
		startActivity(i);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

		
	}
	
	

	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == android.R.id.home) {
				 
				 Intent parentIntent = new Intent(this, HomePage.class);
			      startActivity(parentIntent);
			         overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

			      finish();
			 }
			
			return super.onOptionsItemSelected(item);
		}
	
}
