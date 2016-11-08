package com.maverick.assampoll2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

public class More extends Activity {
	
	AlphaAnimation buttonClick = new AlphaAnimation(0.3F, 0.3F);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        
		setContentView(R.layout.activity_more);
	}
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();

	    View view = getWindow().getDecorView();
	    WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
	    lp.gravity = Gravity.RIGHT | Gravity.TOP;
	   // lp.x = 100;
	    lp.dimAmount = 0.5f;  
	    lp.y = 100;
	    
	    getWindowManager().updateViewLayout(view, lp);
	}
	
	public void politicalparties(View v){
		
		
		 v.startAnimation(buttonClick);
		
		Intent i = new Intent(More.this, AssamPoliticalParties.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
      

		finish();
	}
	
  public void updates(View v){
	
	 v.startAnimation(buttonClick);
		
		Intent i = new Intent(More.this, News.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
		finish();
	}

  public void survey(View v){
	  
	  v.startAnimation(buttonClick);
	
	Intent i = new Intent(More.this, Survey.class);
	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	startActivity(i);
	finish();
}

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
}

public void aboutus(View v){
	
	 v.startAnimation(buttonClick);
	
	Intent i = new Intent(More.this, AboutUs.class);
	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	startActivity(i);
	finish();
}

/*
   public void settings(View v){
	
	 v.startAnimation(buttonClick);
	
	Intent i = new Intent(More.this, Settings.class);
	startActivity(i);
	finish();
   }

*/
	
}
