package com.maverick.abdurrashidmandal;

import com.maverick.abdurrashidmandal.R;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends ActionBarActivity {
	
	   //for slider layout
		LinearLayout ll;
		float startY;
		String[] menu;
	    DrawerLayout dLayout;
	    RelativeLayout dRelativeLayout;
	    ListView dList;
	    ArrayAdapter<String> adapter;
	    ActionBarDrawerToggle mDrawerToggle;
		//
		
	    Button reg, all;
	    Button nat, state, loc, fb, tw, vid, events, webnews, canpro, track;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ActionBar bar = getActionBar();
		    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
		    bar.setTitle(Html.fromHtml("<font color='#ffffff'>Abdur Rashid Mandal</font>"));
	      
		    
		    reg = (Button)findViewById(R.id.reg);
		    all = (Button)findViewById(R.id.all);
		   	nat = (Button)findViewById(R.id.nat);
		    state = (Button)findViewById(R.id.state);
		    loc = (Button)findViewById(R.id.loc);
		    fb = (Button)findViewById(R.id.fb);
		    tw = (Button)findViewById(R.id.tw);
		    vid = (Button)findViewById(R.id.vid);
		    events = (Button)findViewById(R.id.events);
		    webnews = (Button)findViewById(R.id.webnews);
		    canpro = (Button)findViewById(R.id.canpro);
		    track = (Button)findViewById(R.id.track);
		    
		    
		
	    }
		
		 
      public void nat(View v){
			 
			 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
             nat.startAnimation(animFadein); 
		 }
 
     public void state(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     state.startAnimation(animFadein); 
         }
 
     public void loc(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     loc.startAnimation(animFadein); 
         }
 
     public void fb(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     fb.startAnimation(animFadein); 
         }
 
     public void tw(View v){
	 
/*	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     tw.startAnimation(animFadein); 
 */
 
	 Intent intent4 = new Intent(getApplicationContext(), Loading.class);
    	 startActivity(intent4);
    
     }
 
     public void vid(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     vid.startAnimation(animFadein); 
    }
 
     public void webnews(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     webnews.startAnimation(animFadein); 
     }
 
     public void events(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     events.startAnimation(animFadein); 
 }
 

 
     public void canpro(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     canpro.startAnimation(animFadein); 
   }
 
    public void track(View v){
	 
	 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
     track.startAnimation(animFadein); 
     }
		 
		 
		 public void reg(View v){
			 
			 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
             reg.startAnimation(animFadein); 
		 }
		 
		 public void all(View v){
			 
			 Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
             all.startAnimation(animFadein); 
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
	        	
	        	Intent i = new Intent(this, SlidingDrawer.class);
	        	startActivity(i);
	        	//getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
	        	
	        }
	     
	        return super.onOptionsItemSelected(item);
	    }
	    
	    
	    
	
	}
