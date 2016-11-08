package com.maverick.assampoll2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class News extends ActionBarActivity {
	
    TextView oneH, twoH, threeH, fourH, fiveH;
    TextView oneN, twoN, threeN, fourN, fiveN;
    TextView oneT, twoT, threeT, fourT, fiveT;
    
    String h1 = "h1", h2 = "h2", h3 = "h3", h4 = "h4", h5 = "h5";
    String n1 = "n1", n2 = "n2", n3 = "n3", n4 = "n4", n5 = "n5";
    String t1 = "t1", t2 = "t2", t3 = "t3", t4 = "t3", t5 = "t5";
    
    int change1 = 1, change2 = 1, change3 = 1, change4 = 1, change5 = 1;
    
    
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_news);
		
		 StrictMode.enableDefaults(); //STRICT MODE ENABLED
		
		getActionBar().setDisplayShowHomeEnabled(false);
		// If your minSdkVersion is 11 or higher use:
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//color to action bar
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));
	

		StrictMode.enableDefaults();
		
		oneH = (TextView)findViewById(R.id.h1);
		oneN =(TextView)findViewById(R.id.n1);
		oneT =(TextView)findViewById(R.id.t1);
		
		twoH = (TextView)findViewById(R.id.h2);
		twoN =(TextView)findViewById(R.id.n2);
		twoT =(TextView)findViewById(R.id.t2);
		
		threeH = (TextView)findViewById(R.id.h3);
		threeN =(TextView)findViewById(R.id.n3);
		threeT =(TextView)findViewById(R.id.t3);
		
		fourH = (TextView)findViewById(R.id.h4);
		fourN =(TextView)findViewById(R.id.n4);
		fourT =(TextView)findViewById(R.id.t4);
		
		fiveH = (TextView)findViewById(R.id.h5);
		fiveN =(TextView)findViewById(R.id.n5);
		fiveT =(TextView)findViewById(R.id.t5);
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent==false){
			
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(News.this);
     	      alertDialog.setTitle("Error");
     	      alertDialog.setMessage("Connect to Internet");     
     	      
     	                
     	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
     	                      
     	            public void onClick(DialogInterface dialog, int which) {
     	            	
    	  	
     	            	startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
     	            	//Toast.makeText(getApplicationContext(), "ok ok", Toast.LENGTH_SHORT).show();
     	            	finish();
    	         	  
     	              } }); 

     	      alertDialog.show();
			
		}
		else{
  		
  		
		
		new Get_News().execute();
      	
		}  
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
	
	public void one(View v){
		
		if(change1 == 0)
		{
			oneN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
			oneN.setText(n1.substring(0, 10) + "...");
			change1 = 1;
		}
		else{
			
			oneN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
			oneN.setText(n1);
			change1 = 0;
			
			}
	}

   public void two(View v){
	
	if(change2 == 0)
	{
		twoN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		twoN.setText(n2.substring(0, 10) + "...");
		change2 = 1;
	}
	else{
		twoN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		twoN.setText(n2);
		change2 = 0;
		
		}
   }

   public void three(View v){
	
	if(change3 == 0)
	{
		threeN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		threeN.setText(n3.substring(0, 10) + "...");
		change3 = 1;
	}
	else{

		threeN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		threeN.setText(n3);
		change3 = 0;
		}
   }

   public void four(View v){
	
	if(change4 == 0)
	{
		fourN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		fourN.setText(n4.substring(0, 10) + "...");
		change4 = 1;
	}
	else{

		fourN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
		fourN.setText(n4);
		change4 = 0;
		}
   }
	
   public void five(View v){
		
		if(change5 == 0)
		{
			fiveN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
			fiveN.setText(n5.substring(0, 10) + "...");
			change5 = 1;
		}
		else{

			fiveN.startAnimation(AnimationUtils.loadAnimation(News.this, android.R.anim.fade_in));
			fiveN.setText(n5);
			change5 = 0;
			}
	}

	public class Get_News extends AsyncTask<Void, Void, Void>{
	   	 
		 String result = "";
	     InputStream isr = null;
	      	
		 
	   	ProgressDialog dialog = new ProgressDialog(News.this);

	   	  @Override
	   	  protected void onPreExecute() {
	   		dialog.setMessage("Please wait..."); 
	   		dialog.setCanceledOnTouchOutside(false);
			dialog.show();

	   	  }

	   	  @Override
	   	  protected Void doInBackground(Void... params) {
	   		  
	   		try{
	         	 
	          	HttpClient httpclient = new DefaultHttpClient();
	          	HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/NEWS.php"); //YOUR PHP SCRIPT ADDRESS
	          	
	          	          	        	
	          	//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	          	//for posting
	          	//nameValuePairs.add(new BasicNameValuePair("action", id));
	          	//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	          	
	          	//for response
	          	HttpResponse response = httpclient.execute(httppost);
	          	HttpEntity entity = response.getEntity();
	          	isr = entity.getContent();
	          	 
	          	}catch(Exception e){
	          	//Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
	          	}
	   		return null;
	}
	   	  protected void onPostExecute(Void unused) {

	   		  dialog.dismiss();
	   		 
	   		 try{
		          	
	          	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
	          	 StringBuilder sb = new StringBuilder();
	          	 String line = null;
	          	 
	          	 while ((line = reader.readLine()) != null) {
	          	 
	          		 sb.append(line + "\n");
	          	}
	          	 isr.close();
	          	 result = sb.toString();
	          //	 Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_SHORT).show();
	          	}catch(Exception e){
	          		Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
	          	}
	      
	     // Toast.makeText(getApplicationContext(), " " + result, Toast.LENGTH_LONG).show();
	      
	      
	          try {
	          	JSONArray jArray = new JSONArray(result);
	   
	          	
	          	for( int i = 0 ; i < jArray.length( ); i++ ){
	          	 
	          		JSONObject json = jArray.getJSONObject(i);
	          	 
	          		if(json.getString("NEWS_ID").equals("1")){
	          			
	          		h1 = json.getString("HEADING");
	          		n1 = json.getString("NEWS");
	          		t1 = json.getString("TIME");
	          		
	          		oneH.setText(h1);
		          	oneN.setText(n1.substring(0, 10) + "...");
		          	oneT.setText(t1);
	          		
	          	    }
	          		else if(json.getString("NEWS_ID").equals("2")){
	          			
	          			h2 = json.getString("HEADING");
		          		n2 = json.getString("NEWS");
		          		t2 = json.getString("TIME");
	          			
		          		twoH.setText(h2);
			          	twoN.setText(n2.substring(0, 10) + "...");
			          	twoT.setText(t2);
		          		
	          		}
	          		else if(json.getString("NEWS_ID").equals("3")){
	          			
	          			h3 = json.getString("HEADING");
		          		n3 = json.getString("NEWS");
		          		t3 = json.getString("TIME");
		          		
		          		threeH.setText(h3);
			          	threeN.setText(n3.substring(0, 10) + "...");
			          	threeT.setText(t3);
		          		
	          			
	          		}
	          		else if(json.getString("NEWS_ID").equals("4")){
	          			
	          			h4 = json.getString("HEADING");
		          		n4 = json.getString("NEWS");
		          		t4 = json.getString("TIME");
		          		

			           	fourH.setText(h4);
			          	fourN.setText(n4.substring(0, 10) + "...");
			          	fourT.setText(t4);
		          		
	          			
	          		}
	          		else if(json.getString("NEWS_ID").equals("5")){
	          			h5=json.getString("HEADING");
		          		n5 = json.getString("NEWS");
		          		t5 = json.getString("TIME");
	          			
		          		fiveH.setText(h5);
			          	fiveN.setText(n5.substring(0, 10) + "...");
			          	fiveT.setText(t5);	
			          		
		          		
	          		}
	          	   
	            	    
	          		
	         // Toast.makeText(getApplicationContext(),"Heading 1 :"+h2+"news :"+n2+"  time :"+t2, Toast.LENGTH_SHORT).show();
	              
	          /*	oneH.setText(h1);
	          	oneN.setText(n1.substring(0, 10) + "...");
	          	oneT.setText(t1);
	          	
	          	twoH.setText(h2);
	          	twoN.setText(n2.substring(0, 10) + "...");
	          	twoT.setText(t2);
	          	
	          	threeH.setText(h3);
	          	threeN.setText(n3.substring(0, 10) + "...");
	          	threeT.setText(t3);
	          	
	           	fourH.setText(h4);
	          	fourN.setText(n4.substring(0, 10) + "...");
	          	fourT.setText(t4);
	          		
	          		
	          	fiveH.setText(h5);
	          	fiveN.setText(n5.substring(0, 10) + "...");
	          	fiveT.setText(t5);	
	          		
	          	*/	
	          		
	          	
	          		
	               
	          	 }
	          	
	           
	          	} catch (Exception e) {
	          		Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_SHORT).show();
	         	 }   
	    // Toast.makeText(getApplicationContext(), "CON:"+congress_s+"BJP:"+bjp_s+"APP:"+aap_s, Toast.LENGTH_SHORT).show();    
	   	  
	   	  }
	   	  
	 }
	
	 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
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
