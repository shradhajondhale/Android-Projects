package com.maverick.assampoll2016;

import java.util.Locale;
import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

public class AboutUs extends ActionBarActivity {
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//screen awake
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//screen portrait
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_about_us);
		
		//action bar 
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//color to action bar
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));
	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     
	    	 Intent parentIntent = new Intent(this, HomePage.class);
		      startActivity(parentIntent);
		         overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		      finish();
	    	 
	    	 return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}

	//share app button onclick
	public void shareapp(View v){
		
		 v.startAnimation(buttonClick);
		 
   	 try
	    { Intent i = new Intent(Intent.ACTION_SEND);  
	      i.setType("text/plain");
	      i.putExtra(Intent.EXTRA_SUBJECT, "Assam Poll 2016");
	      String sAux = "\nDownload this application to vote for your favourite party in Assam elections, 2016.\n\n";
	      sAux = sAux + "https://play.google.com/store/apps/details?id=com.maverick.assampoll2016\n";
	      i.putExtra(Intent.EXTRA_TEXT, sAux);  
	      startActivity(Intent.createChooser(i, "Share this app via"));
	    }
	    catch(Exception e)
	    { //e.toString();
	    }   
		 //finish();

	}
	
	
	//go to facebook page maverick button onclick
	public void facebook(View v){
		
		 v.startAnimation(buttonClick);
		 
		Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
        myWebLink.setData(Uri.parse("https://www.facebook.com/pages/Maverick-IT/1043065389055366"));
        startActivity(myWebLink);
		
	}
	
	
	//go to maverick website button onclick
	public void website(View v){
		
		 v.startAnimation(buttonClick);
		 		
		Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
        myWebLink.setData(Uri.parse("http://www.spmaverick.com/AssamPoll/WEBPORTAL/"));
        startActivity(myWebLink);
		
	}
	
	
	//location button onclick
	public void map(View v){
		
		 v.startAnimation(buttonClick);
			
		String uri = String.format(Locale.ENGLISH, "geo:0,0?q=Maverick IT, 4th floor, Ivory Enclave, tiniali near usha court, Zoo Narengi Rd, Guwahati, Assam 781021");
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	this.startActivity(intent);
	
	}


	//go to twitter page maverick button onclick
		public void twitter(View v){
			
			 v.startAnimation(buttonClick);
			 
			Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	        myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
	        myWebLink.setData(Uri.parse("https://twitter.com/maverickitindia"));
	        startActivity(myWebLink);
			
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
