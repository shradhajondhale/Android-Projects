package com.maverick.foody;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.maverick.foody.R;


public class SplashActivity extends ActionBarActivity {
ProgressBar progress;
ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//
        
        setContentView(R.layout.activity_splash);
        
            loading = (ImageView) findViewById(R.id.image);
			Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.loading);
			loading.startAnimation(hyperspaceJump);
        
    	new CountDownTimer(2000, 1000) {
            
   			public void onTick(long millisUntilFinished) {
   				   				
   			}

     	    public void onFinish() {
     	    	
     	    	loading.clearAnimation();
     	    	loading.setVisibility(View.GONE);
     		    Intent intent = new Intent(SplashActivity.this, FoodAroundMe.class);
     		    
     		    startActivity(intent);
     		    finish();
     		    	
     		    	
     		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
     		  }
      }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
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
