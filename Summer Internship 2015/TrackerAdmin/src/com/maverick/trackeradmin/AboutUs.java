package com.maverick.trackeradmin;

import java.util.Locale;
import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class AboutUs extends ActionBarActivity {
	
AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
	}
	
	
	//share app button onclick
	public void shareapp(View v){
		
		 v.startAnimation(buttonClick);
		 
   	 try
	    { Intent i = new Intent(Intent.ACTION_SEND);  
	      i.setType("text/plain");
	      i.putExtra(Intent.EXTRA_SUBJECT, "Tracker Admin");
	      String sAux = "\nDownload this application\n\n";
	      sAux = sAux + "https://play.google.com/store/apps/details?id=com.maverick.trackeradmin\n";
	      i.putExtra(Intent.EXTRA_TEXT, sAux);  
	      startActivity(Intent.createChooser(i, "Share this app via"));
	    }
	    catch(Exception e)
	    { //e.toString();
	    }   
		 finish();

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
        myWebLink.setData(Uri.parse("http://spmaverick.com"));
        startActivity(myWebLink);
		
	}
	
	
	//location button onclick
	public void map(View v){
		
		 v.startAnimation(buttonClick);
			
		String uri = String.format(Locale.ENGLISH, "geo:0,0?q=Maverick IT, 4th floor, Ivory Enclave, tiniali near usha court, Zoo Narengi Rd, Guwahati, Assam 781021");
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	this.startActivity(intent);
	
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
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
