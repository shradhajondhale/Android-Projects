package com.maverick.abdurrashidmandal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.maverick.abdurrashidmandal.R;

public class SlidingDrawer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
		setContentView(R.layout.activity_sliding_drawer);
	}
	
	public void home(View v){}
	public void addmember(View v){}
	public void chat(View v){}
	public void contactus(View v){}
	public void partyagenda(View v){}
	public void faq(View v){}
	public void aboutus(View v){
		
		Intent i = new Intent(this, AboutUs.class);
		startActivity(i);
		finish();
		
	}
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();

	    View view = getWindow().getDecorView();
	    WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
	    lp.gravity = Gravity.RIGHT | Gravity.TOP;
	   // lp.x = 100;
	    lp.dimAmount = 0.5f;  
	    
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
