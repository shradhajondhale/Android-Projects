package com.maverick.assampoll2016;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AssamPoliticalParties extends ActionBarActivity {
	
	Button congress, bjp, aap, agp, aiudf;
	String check_which_party;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_assam_political_parties);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		// If your minSdkVersion is 11 or higher use:
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//color to action bar
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));


		StrictMode.enableDefaults();
		
			//button party findviewby id
			congress = (Button)findViewById(R.id.con);
	        bjp = (Button)findViewById(R.id.bjp);
	        aap = (Button)findViewById(R.id.aap);
	        agp = (Button)findViewById(R.id.agp);
	        aiudf = (Button)findViewById(R.id.aiudf);
	        
	        
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
	
	//------------button party onclicks
		 public void con(View v){
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			 
				Boolean isInternetPresent = cd.isConnectingToInternet(); 
				         
				if(isInternetPresent==false){
					
					  LayoutInflater inflater = getLayoutInflater();

				        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

				        //Set text
				        TextView text = (TextView) layout.findViewById(R.id.tv);
				        text.setText("No Internet Connection!");

				        //Toast
				        Toast toast = new Toast(getApplicationContext());
				      //  toast.setGravity(Gravity.BOTTOM, 0, 0);
				        toast.setDuration(Toast.LENGTH_SHORT);
				        toast.setView(layout);
				        toast.show();
					
					
					
				}
				else{
			 
		    	check_which_party = "1";
		    	Intent intent = new Intent(this, Party.class);
		    	
		    	 //Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                 bun.putString(" ", check_which_party);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
	          
		    	startActivity(intent);
		    	
		         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
              finish();
				}	
		    }
		
		 
		 
		 public void bjp(View v){
			 
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			 
				Boolean isInternetPresent = cd.isConnectingToInternet(); 
				         
				if(isInternetPresent==false){
					
					  LayoutInflater inflater = getLayoutInflater();

				        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

				        //Set text
				        TextView text = (TextView) layout.findViewById(R.id.tv);
				        text.setText("No Internet Connection!");

				        //Toast
				        Toast toast = new Toast(getApplicationContext());
				    //    toast.setGravity(Gravity.BOTTOM, 0, 0);
				        toast.setDuration(Toast.LENGTH_SHORT);
				        toast.setView(layout);
				        toast.show();
					
										
				}
				else{
			 
		    	check_which_party = "2";
		    	Intent intent = new Intent(this, Party.class);
		    	
		    	//Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                bun.putString(" ", check_which_party);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
		    	startActivity(intent);
		         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

		         finish();
				}
		    }
		 
		 
		 public void aap(View v){
			 
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			 
				Boolean isInternetPresent = cd.isConnectingToInternet(); 
				         
				if(isInternetPresent==false){
					
					  LayoutInflater inflater = getLayoutInflater();

				        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

				        //Set text
				        TextView text = (TextView) layout.findViewById(R.id.tv);
				        text.setText("No Internet Connection!");

				        //Toast
				        Toast toast = new Toast(getApplicationContext());
				   //     toast.setGravity(Gravity.BOTTOM, 0, 0);
				        toast.setDuration(Toast.LENGTH_SHORT);
				        toast.setView(layout);
				        toast.show();
										
				}
				else{
		    	check_which_party = "3";
		    	Intent intent = new Intent(this, Party.class);
		    	
		    	//Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                bun.putString(" ", check_which_party);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
		    	startActivity(intent);
		         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

		         finish();
				}
		    }
		 
		 
		 public void agp(View v){
			 
			 
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			 
				Boolean isInternetPresent = cd.isConnectingToInternet(); 
				         
				if(isInternetPresent==false){
					
					  LayoutInflater inflater = getLayoutInflater();

				        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

				        //Set text
				        TextView text = (TextView) layout.findViewById(R.id.tv);
				        text.setText("No Internet Connection!");

				        //Toast
				        Toast toast = new Toast(getApplicationContext());
				   //     toast.setGravity(Gravity.BOTTOM, 0, 0);
				        toast.setDuration(Toast.LENGTH_SHORT);
				        toast.setView(layout);
				        toast.show();
										
				}
				else{
		    	check_which_party = "4";
		    	Intent intent = new Intent(this, Party.class);
		    	
		    	//Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                bun.putString(" ", check_which_party);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
		    	startActivity(intent);
		         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

		         finish();
				}
		    }
		 
		 
		 public void aiudf(View v){

			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			 
				Boolean isInternetPresent = cd.isConnectingToInternet(); 
				         
				if(isInternetPresent==false){
					
					  LayoutInflater inflater = getLayoutInflater();

				        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

				        //Set text
				        TextView text = (TextView) layout.findViewById(R.id.tv);
				        text.setText("No Internet Connection!");

				        //Toast
				        Toast toast = new Toast(getApplicationContext());
				  //      toast.setGravity(Gravity.BOTTOM, 0, 0);
				        toast.setDuration(Toast.LENGTH_SHORT);
				        toast.setView(layout);
				        toast.show();
										
				}
				else{
		    	check_which_party = "5";
		    	Intent intent = new Intent(this, Party.class);
		    	
		    	//Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                bun.putString(" ", check_which_party);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
	             
		    	startActivity(intent);
		         overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

		         finish();
		    	
		    }
		 }
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assam_political_parties, menu);
		return true;
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
