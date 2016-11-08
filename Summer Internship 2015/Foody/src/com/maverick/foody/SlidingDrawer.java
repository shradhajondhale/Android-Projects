package com.maverick.foody;

import com.maverick.foody.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SlidingDrawer extends Activity {

	int actionbarheight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    	
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
    	
		setContentView(R.layout.activity_sliding_drawer);
		
        actionbarheight = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height);

	}
	

	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();

	    View view = getWindow().getDecorView();
	    WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
	    lp.gravity = Gravity.RIGHT | Gravity.TOP;
	   	lp.dimAmount = 0.1f;  
	    lp.y = actionbarheight;
	    
	    getWindowManager().updateViewLayout(view, lp);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sliding_drawer, menu);
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
