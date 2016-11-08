package com.maverick.assampoll2016;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomePage extends ActionBarActivity   {

	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	 SQLiteDatabase db, db1, dbweekcheck;
	 LinearLayout up1, lp1, up2, lp2, up3, lp3, up4, lp4, up5, lp5, up6, lp6;
	
	 Button votecongress, votebjp, voteaap, voteagp, voteaiudf, voteothers;
	
	 // TextView prev_votes;
	
	 Boolean isInternetPresent;
	 ConnectionDetector cd;
	 
	 Button slideButton, b1, b2, b3, b4;
	 
	 TextView congressvotes, bjpvotes, aapvotes, agpvotes, aiudfvotes, othersvotes; 
	 TextView fbcongress, fbbjp, fbaap, fbagp, fbaiudf;
	 TextView prev_votes_con, prev_votes_bjp, prev_votes_aap, prev_votes_agp, prev_votes_aiudf, prev_votes_others;
	 
	 
	 ViewFlipper flippy;
	
	// int checkflipper;
	 
	 int congressvotes_n, bjpvotes_n, aapvotes_n, agpvotes_n, aiudfvotes_n, othersvotes_n; 
     int totalvotes_n;
	 
     int check_present_vote = 0;
     String check_present_vote_s;
     String get_phone_number_s, weekdata_s, yeardata_s;
	    
        String congress_s = "1";
		String bjp_s = "1";
		String aap_s = "1";
		String agp_s = "1";
		String aiudf_s = "1";
		String others_s = "1";
		
		String congress_s_week = "1";
		String bjp_s_week = "1";
		String aap_s_week = "1";
		String agp_s_week = "1";
		String aiudf_s_week = "1";
		String others_s_week = "1";
	 
		String congress_s_fb = "1";
		String bjp_s_fb = "1";
		String aap_s_fb = "1";
		String agp_s_fb = "1";
		String aiudf_s_fb = "1";
		
		String congress_week = "0";
		
		Handler mHandler;
		
	 	String adlink = "http://spmamaverick.com";
		
	 	String weeknumber_s;
	 	
	 	int weeknumber_n;
	 	int weekofyear;
	 	int weekcheck = 0;
	 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_home_page);
		getActionBar().setDisplayShowHomeEnabled(false);
		//color to action bar
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));


		StrictMode.enableDefaults();
		
		flippy =(ViewFlipper)findViewById(R.id.viewFlipper1);
		
	    flippy.setFlipInterval(5000);
	   
	   //  checkflipper = 1;
		
	    //for ad image
	    
	    
	    
		
		
	    
	    String imageInSD = Environment.getExternalStorageDirectory().getAbsolutePath() +"/myAppDir/myImagesad.png";
        if(imageInSD != null){
	    Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
        Drawable drawable = new BitmapDrawable(getResources(),bitmap);
        Button myImageView = (Button)findViewById(R.id.imageView1);
        myImageView.setBackgroundDrawable(drawable);
	    
        }
		
	    View content = findViewById(R.id.graphlayout);
        content.setDrawingCacheEnabled(true);
		
		
		
		up1 = (LinearLayout)findViewById(R.id.up1);
        lp1 = (LinearLayout)findViewById(R.id.lp1);
        
        up2 = (LinearLayout)findViewById(R.id.up2);
        lp2 = (LinearLayout)findViewById(R.id.lp2);
        
        up3 = (LinearLayout)findViewById(R.id.up3);
        lp3 = (LinearLayout)findViewById(R.id.lp3);
        
        up4 = (LinearLayout)findViewById(R.id.up4);
        lp4 = (LinearLayout)findViewById(R.id.lp4);
        
        up5 = (LinearLayout)findViewById(R.id.up5);
        lp5 = (LinearLayout)findViewById(R.id.lp5);
        
        up6 = (LinearLayout)findViewById(R.id.up6);
        lp6 = (LinearLayout)findViewById(R.id.lp6);
        
        
        votecongress = (Button)findViewById(R.id.bcon);
        votebjp = (Button)findViewById(R.id.bbjp);
        voteaap = (Button)findViewById(R.id.baap);
        voteagp = (Button)findViewById(R.id.bagp);
        voteaiudf = (Button)findViewById(R.id.baiudf);
        voteothers = (Button)findViewById(R.id.bothers);
        
        fbcongress = (TextView)findViewById(R.id.fbcon);
        fbbjp = (TextView)findViewById(R.id.fbbjp);
        fbaap = (TextView)findViewById(R.id.fbaap);
        fbagp = (TextView)findViewById(R.id.fbagp);
        fbaiudf = (TextView)findViewById(R.id.fbaiudf);
        
        
        congressvotes = (TextView)findViewById(R.id.vcon);
        bjpvotes = (TextView)findViewById(R.id.vbjp);
        aapvotes = (TextView)findViewById(R.id.vaap);
        agpvotes = (TextView)findViewById(R.id.vagp);
        aiudfvotes = (TextView)findViewById(R.id.vaiudf);
        othersvotes = (TextView)findViewById(R.id.vothers);
    
       // prev_votes = (TextView)findViewById(R.id.prevday);
        
        
        prev_votes_con = (TextView)findViewById(R.id.prevdaycon);
        prev_votes_bjp = (TextView)findViewById(R.id.prevdaybjp);
        prev_votes_aap = (TextView)findViewById(R.id.prevdayaap);
        prev_votes_agp = (TextView)findViewById(R.id.prevdayagp);
        prev_votes_aiudf = (TextView)findViewById(R.id.prevdayaiudf);
        prev_votes_others = (TextView)findViewById(R.id.prevdayothers);
       
        
       
        
       //open or create database
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        
        Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
		if(c.getCount() == 0)
		{
		   return;
		}
		while( c.moveToNext() ){
			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
		   get_phone_number_s = c.getString(1);
		}
	
		
		
		
		 db1 = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
		    Cursor c2 = db1.rawQuery("SELECT * FROM ad WHERE id='"+1+"'", null);
			if(c2.getCount() == 0)
			{
			   return;
			}
			while( c2.moveToNext() ){
				//constituency_s = c1.getString(1);
				adlink = c2.getString(1);
		//	Toast.makeText(getApplicationContext(), " "+c2.getString(1) , Toast.LENGTH_SHORT).show();
			  
			}
		
		
			

			 dbweekcheck = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
			    Cursor c3 = dbweekcheck.rawQuery("SELECT * FROM weekcheck WHERE id='"+1+"'", null);
				if(c3.getCount() == 0)
				{
				   return;
				}
				while( c3.moveToNext() ){
					//constituency_s = c1.getString(1);
					weeknumber_s = c3.getString(1);
					weeknumber_n = Integer.parseInt(weeknumber_s);
	//Toast.makeText(getApplicationContext(), " " + weeknumber_s , Toast.LENGTH_SHORT).show();
				  
				}
			
		
				
		
		
		
		
		/*if(get_phone_number_s.equals("0")){
			
			Toast.makeText(getApplicationContext(), "Your phone is not regstred", Toast.LENGTH_SHORT).show();
		}
		else{
			//Toast.makeText(getApplicationContext(), "Your phone is not regstred", Toast.LENGTH_SHORT).show();
*/
		
		//Toast.makeText(getApplicationContext(), "" + get_phone_number_s,Toast.LENGTH_LONG).show();
		
		Calendar cal = Calendar.getInstance();
		
		weekofyear = cal.get(Calendar.WEEK_OF_YEAR);
		 
		int year = cal.get(Calendar.YEAR);
		
		weekdata_s = Integer.toString(weekofyear);
		//Toast.makeText(getApplicationContext(), ""+weekdata_s, Toast.LENGTH_LONG).show();
		yeardata_s = Integer.toString(year);
		
		
		if( weeknumber_n == 10 || weekofyear == ( weeknumber_n + 1 ) ){
			
			weekcheck = 1;
			
		}
		else{
			
			weekcheck = 0;
		}
		//Toast.makeText(getApplicationContext(), ""+weekcheck, Toast.LENGTH_SHORT).show();
		
		
		
		cd = new ConnectionDetector(getApplicationContext());
		 
		isInternetPresent = cd.isConnectingToInternet(); 
		         
		if( isInternetPresent == false ){
			
			 //Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
			 AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
	         builder.setMessage("No Internet Connection.");
	         builder.setTitle("Error");
	         builder.setPositiveButton("OK",
	    			
					new DialogInterface.OnClickListener() {
			
				public void onClick(DialogInterface dialog,int which) {
			
					startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					/* Intent intent = new Intent(getApplicationContext(), HomePage.class);
					 startActivity(intent);
			           finish();
			           */
			   }
			});
			
			builder.show();
	        
	        
			
		}
		else{
		
			new GetAll(get_phone_number_s, weekdata_s).execute();
		}
		
		
	//prev_votes.setText( Html.fromHtml("CON : " + "<b><font color =#000000>" + "5678" + "</font></b>"));
		
		this.mHandler = new Handler();
        m_Runnable.run();
	}
		
	
	
	
	
	
	//async task for getall
	 public class GetAll extends AsyncTask <Void, Void, Void>{
		
      String week_number_s1; 
   	  String phone_number_s1;
   	  InputStream isr = null;
   	  String result = "";
   	 
   	  
     	ProgressDialog dialog = new ProgressDialog(HomePage.this);
		

   	    public GetAll(String phone_number_s1, String week_number_s1){
   	      this.phone_number_s1 = phone_number_s1;
   	      this.week_number_s1 = week_number_s1;
   	      
   	  }

   	    @Override
   	    protected void onPreExecute() {
   	      dialog.setMessage("Loading...");
   		  dialog.setCanceledOnTouchOutside(false);
		  dialog.show();

   	  }

   	    @Override
   	    protected Void doInBackground(Void... params) {
   		   //-------------------
		
			    	
			try{
			    	 
			  HttpClient httpclient = new DefaultHttpClient();
			    	 
			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/REFRESH.php"); //YOUR PHP SCRIPT ADDRESS
			  			    	 
			    	
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			    	
			  
			  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s1));
			  nameValuePairs.add(new BasicNameValuePair("Week", week_number_s1));


			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    	
			
			  HttpResponse response = httpclient.execute(httppost);
			    	 
			  HttpEntity entity = response.getEntity();
			    	 
			  isr = entity.getContent();
			   	
			}catch(Exception e){
			   
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
      	 
      	}catch(Exception e){
      	 
      	}
  
       //  Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
  
  
      try {
      	JSONArray jArray = new JSONArray(result);

             	
      	for( int i = 0 ; i < jArray.length( ); i++ ){
      	 
      		JSONObject json = jArray.getJSONObject(i);
      	 
      		if(json.getString("ID").equals("1")){
      		  
      		//  Toast.makeText(getApplicationContext(), "PV= "+json.getString("PRESENT_VOTE") ,Toast.LENGTH_LONG ).show();
      	   
      		
      		
      		  
      		 congress_s = json.getString("CON");
      		 bjp_s = json.getString("BJP");
      		 aap_s = json.getString("AAP");
      		 agp_s = json.getString("AGP");
      		 aiudf_s = json.getString("AIUDF");
      		 others_s = json.getString("OTHERS");
      		 congress_s_week = json.getString("CONWEEK");
      		 bjp_s_week = json.getString("BJPWEEK");
      		 aap_s_week = json.getString("AAPWEEK");
      		 agp_s_week = json.getString("AGPWEEK");
      		 aiudf_s_week = json.getString("AIUDFWEEK");
      		 others_s_week = json.getString("OTHERSWEEK");
      		 check_present_vote_s = json.getString("PRESENT_VOTE");
      		  
      		  check_present_vote = Integer.parseInt(check_present_vote_s);
              congressvotes_n =  Integer.parseInt(congress_s);
              bjpvotes_n =  Integer.parseInt(bjp_s);
              aapvotes_n =  Integer.parseInt(aap_s);
              agpvotes_n =  Integer.parseInt(agp_s);
              aiudfvotes_n =  Integer.parseInt(aiudf_s);
              othersvotes_n =  Integer.parseInt(others_s);
               
              
              
               congressvotes.setText(""+congressvotes_n);
               bjpvotes.setText(""+bjpvotes_n);
               aapvotes.setText(""+aapvotes_n);
               agpvotes.setText(""+agpvotes_n);
               aiudfvotes.setText(""+aiudfvotes_n);
               othersvotes.setText(""+othersvotes_n);
               
            //   prev_votes.setText("CON : "+congressvotes_n);
               
             
                 congress_s_fb = json.getString("CONFB");
        		 bjp_s_fb = json.getString("BJPFB");
        		 aap_s_fb = json.getString("AAPFB");
        		 agp_s_fb = json.getString("AGPFB");
        		 aiudf_s_fb = json.getString("AIUDFFB");
        		 
        	//	 Toast.makeText(getApplicationContext(), "PV= "+json.getString("CONFB") ,Toast.LENGTH_LONG ).show();
        		 
        		 fbcongress.setText("" + congress_s_fb);
                 fbbjp.setText("" + bjp_s_fb);
                 fbaap.setText("" + aap_s_fb);
                 fbagp.setText("" + agp_s_fb);
                 fbaiudf.setText("" + aiudf_s_fb);
               
               
             
               totalvotes_n = congressvotes_n + bjpvotes_n + aapvotes_n + agpvotes_n + aiudfvotes_n + othersvotes_n;
        		
               
               
               float l1 = (float) congressvotes_n/totalvotes_n;
               float u1 = 1- l1;
               
               float l2 = (float) bjpvotes_n/totalvotes_n;
               float u2 = 1- l2;
               
               float l3 = (float) aapvotes_n/totalvotes_n;
               float u3 = 1- l3;
               
               float l4 = (float) agpvotes_n/totalvotes_n;
               float u4 = 1- l4;
               
               float l5 = (float) aiudfvotes_n/totalvotes_n;
               float u5 = 1- l5;
               
               float l6 = (float) othersvotes_n/totalvotes_n;
               float u6 = 1- l6;
               
              // Toast.makeText(getApplicationContext(), "  "+ l3+"    "+ u3, Toast.LENGTH_SHORT).show();

               LinearLayout.LayoutParams paramu1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u1);
               LinearLayout.LayoutParams paraml1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l1);
              
               LinearLayout.LayoutParams paramu2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u2);
               LinearLayout.LayoutParams paraml2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l2);
               
               LinearLayout.LayoutParams paramu3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u3);
               LinearLayout.LayoutParams paraml3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l3);
               
               LinearLayout.LayoutParams paramu4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u4);
               LinearLayout.LayoutParams paraml4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l4);
               
               LinearLayout.LayoutParams paramu5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u5);
               LinearLayout.LayoutParams paraml5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l5);
               
               LinearLayout.LayoutParams paramu6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u6);
               LinearLayout.LayoutParams paraml6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l6);
              
               up1.setLayoutParams(paramu1);
               lp1.setLayoutParams(paraml1);
               
               up2.setLayoutParams(paramu2);
               lp2.setLayoutParams(paraml2);
               
               up3.setLayoutParams(paramu3);
               lp3.setLayoutParams(paraml3);
               
               up4.setLayoutParams(paramu4);
               lp4.setLayoutParams(paraml4);
               
               up5.setLayoutParams(paramu5);
               lp5.setLayoutParams(paraml5);
               
               up6.setLayoutParams(paramu6);
               lp6.setLayoutParams(paraml6);
               
               update_button(check_present_vote);
             
               prev_votes_con.setText("INC : " + congress_s_week);
               prev_votes_bjp.setText("BJP : " + bjp_s_week);
               prev_votes_aap.setText("AAP : " + aap_s_week);
               prev_votes_agp.setText("AGP : " + agp_s_week);
               prev_votes_aiudf.setText("AIUDF : " + aiudf_s_week);
               prev_votes_others.setText("OTHERS : " + others_s_week);
               
               flippy.startFlipping();
      	  
      	}
      			
      			
      		}	    
		  
      	
      	/*
      	congressvotes_n =  Integer.parseInt(congress_s);
        bjpvotes_n =  Integer.parseInt(bjp_s);
        aapvotes_n =  Integer.parseInt(aap_s);
        agpvotes_n =  Integer.parseInt(agp_s);
        aiudfvotes_n =  Integer.parseInt(aiudf_s);
        othersvotes_n =  Integer.parseInt(others_s);
         
         congressvotes.setText(""+congressvotes_n);
         bjpvotes.setText(""+bjpvotes_n);
         aapvotes.setText(""+aapvotes_n);
         agpvotes.setText(""+agpvotes_n);
         aiudfvotes.setText(""+aiudfvotes_n);
         othersvotes.setText(""+othersvotes_n);

   	  */
    
     /*    
     congress_s="CON : "+congress_s;
     bjp_s="BJP : "+bjp_s;
     aap_s="AAP : "+aap_s;
     agp_s="AGP : "+agp_s;
     aiudf_s = "AIUDF : "+aiudf_s;
     others_s = "OTHERS : "+others_s; 
     */
   	
     	  
	}catch(Exception e){
		
		
	}
      
      
      
	  }
}

	 //-----------------------------------------------------------------------------------------------
	//async task for getinfo
		 public class Update extends AsyncTask <Void, Void, Void>{
		 

		  String week_number_s1;
	   	  String phone_number_s1;
	   	  String voted_to_s1;
	   	  InputStream isr = null;
	   	  String result = "";
	   	 
	   	  
	     	ProgressDialog dialog = new ProgressDialog(HomePage.this);

	   	    public Update(String phone_number_s1, String voted_to_s1, String week_number_s1){
	   	      this.phone_number_s1 = phone_number_s1;
	   	      this.voted_to_s1 = voted_to_s1;
	   	      this.week_number_s1 = week_number_s1;
	   	      
	   	  }

	   	    @Override
	   	    protected void onPreExecute() {
	   	      dialog.setMessage("Please wait...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				    	 
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/CHANGE_VOTE.php"); //YOUR PHP SCRIPT ADDRESS
				  			    	 
				    	
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				    	
				  
				  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s1));
				  nameValuePairs.add(new BasicNameValuePair("Vote", voted_to_s1));
				  nameValuePairs.add(new BasicNameValuePair("Week", week_number_s1));


				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				    	
				
				  HttpResponse response = httpclient.execute(httppost);
				    	 
				  HttpEntity entity = response.getEntity();
				    	 
				  isr = entity.getContent();
				   	
				}catch(Exception e){
				   
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
	      	 
	      	}catch(Exception e){
	      	 
	      	}
	  
	         //Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
	  
	  
	      try {
	      	JSONArray jArray = new JSONArray(result);

	             	
	      	for( int i = 0 ; i < jArray.length( ); i++ ){
	      	 
	      		JSONObject json = jArray.getJSONObject(i);
	      	 
	      		if(json.getString("ID").equals("1")){
	      		  
	      		//  Toast.makeText(getApplicationContext(), "PV= "+json.getString("PRESENT_VOTE") ,Toast.LENGTH_LONG ).show();
	      	   
	      		
	      		
	      		  
	      		 congress_s = json.getString("CON");
	      		 bjp_s = json.getString("BJP");
	      		 aap_s = json.getString("AAP");
	      		 agp_s = json.getString("AGP");
	      		 aiudf_s = json.getString("AIUDF");
	      		 others_s = json.getString("OTHERS");
	      		 congress_s_week = json.getString("CONWEEK");
	      		 bjp_s_week = json.getString("BJPWEEK");
	      		 aap_s_week = json.getString("AAPWEEK");
	      		 agp_s_week = json.getString("AGPWEEK");
	      		 aiudf_s_week = json.getString("AIUDFWEEK");
	      		 others_s_week = json.getString("OTHERSWEEK");
	      		 check_present_vote_s = json.getString("PRESENT_VOTE");
	      		  
	      		  check_present_vote = Integer.parseInt(check_present_vote_s);
	              congressvotes_n =  Integer.parseInt(congress_s);
	              bjpvotes_n =  Integer.parseInt(bjp_s);
	              aapvotes_n =  Integer.parseInt(aap_s);
	              agpvotes_n =  Integer.parseInt(agp_s);
	              aiudfvotes_n =  Integer.parseInt(aiudf_s);
	              othersvotes_n =  Integer.parseInt(others_s);
	               
	               congressvotes.setText("" + congressvotes_n);
	               bjpvotes.setText("" + bjpvotes_n);
	               aapvotes.setText("" + aapvotes_n);
	               agpvotes.setText("" + agpvotes_n);
	               aiudfvotes.setText("" + aiudfvotes_n);
	               othersvotes.setText("" + othersvotes_n);
	               
	          //     prev_votes.setText("CON : "+congressvotes_n);
	               
	                 congress_s_fb = json.getString("CONFB");
	        		 bjp_s_fb = json.getString("BJPFB");
	        		 aap_s_fb = json.getString("AAPFB");
	        		 agp_s_fb = json.getString("AGPFB");
	        		 aiudf_s_fb = json.getString("AIUDFFB");
	        		 
	        	//	 Toast.makeText(getApplicationContext(), "PV= "+json.getString("CONFB") ,Toast.LENGTH_LONG ).show();
	        		 
	        		 fbcongress.setText("" + congress_s_fb);
	                 fbbjp.setText("" + bjp_s_fb);
	                 fbaap.setText("" + aap_s_fb);
	                 fbagp.setText("" + agp_s_fb);
	                 fbaiudf.setText("" + aiudf_s_fb);
	                 
	               totalvotes_n = congressvotes_n + bjpvotes_n + aapvotes_n + agpvotes_n + aiudfvotes_n + othersvotes_n;
	        		
	               
	               
	               float l1 = (float) congressvotes_n/totalvotes_n;
	               float u1 = 1- l1;
	               
	               float l2 = (float) bjpvotes_n/totalvotes_n;
	               float u2 = 1- l2;
	               
	               float l3 = (float) aapvotes_n/totalvotes_n;
	               float u3 = 1- l3;
	               
	               float l4 = (float) agpvotes_n/totalvotes_n;
	               float u4 = 1- l4;
	               
	               float l5 = (float) aiudfvotes_n/totalvotes_n;
	               float u5 = 1- l5;
	               
	               float l6 = (float) othersvotes_n/totalvotes_n;
	               float u6 = 1- l6;
	               
	              // Toast.makeText(getApplicationContext(), "  "+ l3+"    "+ u3, Toast.LENGTH_SHORT).show();

	               LinearLayout.LayoutParams paramu1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u1);
	               LinearLayout.LayoutParams paraml1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l1);
	              
	               LinearLayout.LayoutParams paramu2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u2);
	               LinearLayout.LayoutParams paraml2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l2);
	               
	               LinearLayout.LayoutParams paramu3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u3);
	               LinearLayout.LayoutParams paraml3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l3);
	               
	               LinearLayout.LayoutParams paramu4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u4);
	               LinearLayout.LayoutParams paraml4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l4);
	               
	               LinearLayout.LayoutParams paramu5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u5);
	               LinearLayout.LayoutParams paraml5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l5);
	               
	               LinearLayout.LayoutParams paramu6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u6);
	               LinearLayout.LayoutParams paraml6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l6);
	              
	               up1.setLayoutParams(paramu1);
	               lp1.setLayoutParams(paraml1);
	               
	               up2.setLayoutParams(paramu2);
	               lp2.setLayoutParams(paraml2);
	               
	               up3.setLayoutParams(paramu3);
	               lp3.setLayoutParams(paraml3);
	               
	               up4.setLayoutParams(paramu4);
	               lp4.setLayoutParams(paraml4);
	               
	               up5.setLayoutParams(paramu5);
	               lp5.setLayoutParams(paraml5);
	               
	               up6.setLayoutParams(paramu6);
	               lp6.setLayoutParams(paraml6);
	               
	               update_button(check_present_vote);
	               
	               prev_votes_con.setText("INC : "+congress_s_week);
	               prev_votes_bjp.setText("BJP : "+bjp_s_week);
	               prev_votes_aap.setText("AAP : "+aap_s_week);
	               prev_votes_agp.setText("AGP : "+agp_s_week);
	               prev_votes_aiudf.setText("AIUDF : "+aiudf_s_week);
	               prev_votes_others.setText("OTHERS : "+others_s_week);
	               
	               flippy.startFlipping();
	      	  
	               
	               LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Congratulations! Your vote has been registered.");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();	      	  
	      	}
	      			
	      			
	      		}	    
			  
	      	
	      	/*
	      	congressvotes_n =  Integer.parseInt(congress_s);
	        bjpvotes_n =  Integer.parseInt(bjp_s);
	        aapvotes_n =  Integer.parseInt(aap_s);
	        agpvotes_n =  Integer.parseInt(agp_s);
	        aiudfvotes_n =  Integer.parseInt(aiudf_s);
	        othersvotes_n =  Integer.parseInt(others_s);
	         
	         congressvotes.setText(""+congressvotes_n);
	         bjpvotes.setText(""+bjpvotes_n);
	         aapvotes.setText(""+aapvotes_n);
	         agpvotes.setText(""+agpvotes_n);
	         aiudfvotes.setText(""+aiudfvotes_n);
	         othersvotes.setText(""+othersvotes_n);

	   	  */
	    
	     /*    
	     congress_s="CON : "+congress_s;
	     bjp_s="BJP : "+bjp_s;
	     aap_s="AAP : "+aap_s;
	     agp_s="AGP : "+agp_s;
	     aiudf_s = "AIUDF : "+aiudf_s;
	     others_s = "OTHERS : "+others_s; 
	     */
	   	
	     	  
		}catch(Exception e){
			
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Sorry!. Failed to change vote.");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();		
		        }
	      
	      
	      
		  }

		 
		 
		 
	 }
	      
	 
		 public class GetAllRefresh extends AsyncTask <Void, Void, Void>{
			 
			  String week_number_s1;
		   	  String phone_number_s1;
		   	  InputStream isr = null;
		   	  String result = "";	 
		   	  
		     	//ProgressDialog dialog = new ProgressDialog(HomePage.this);

		   	    public GetAllRefresh(String phone_number_s1, String week_number_s1){
		   	      this.phone_number_s1 = phone_number_s1;
		   	      this.week_number_s1 = week_number_s1;
		   	      
		   	  }

		   	    @Override
		   	    protected void onPreExecute() {
		   	      //dialog.setMessage("Loading...");
		   		  //dialog.setCanceledOnTouchOutside(false);
				//  dialog.show();

		   	  }

		   	    @Override
		   	    protected Void doInBackground(Void... params) {
		   		   //-------------------
				
					    	
					try{
					    	 
					  HttpClient httpclient = new DefaultHttpClient();
					    	 
					  HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/REFRESH.php"); //YOUR PHP SCRIPT ADDRESS
					  			    	 
					    	
					  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					    	
					  
					  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s1));
					  nameValuePairs.add(new BasicNameValuePair("Week", week_number_s1));


					  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					    	
					
					  HttpResponse response = httpclient.execute(httppost);
					    	 
					  HttpEntity entity = response.getEntity();
					    	 
					  isr = entity.getContent();
					   	
					}catch(Exception e){
					   
					}  	
				return null;
		   }
		   	 protected void onPostExecute(Void unused) {
		  		
		//  Toast.makeText(getApplicationContext(), "weeknumber " + weekdata_s , Toast.LENGTH_SHORT).show();
		        // dialog.dismiss();
				 
				 try{
		          	
		      	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
		      	 StringBuilder sb = new StringBuilder();
		      	 String line = null;
		      	 
		      	 while ((line = reader.readLine()) != null) {
		      	 
		      		 sb.append(line + "\n");
		      	}
		      	 isr.close();
		      	 result = sb.toString();
		      	 
		      	}catch(Exception e){
		      	 
		      	}
		  
		       //  Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
		  
		  
		      try {
		      	JSONArray jArray = new JSONArray(result);

		             	
		      	for( int i = 0 ; i < jArray.length( ); i++ ){
		      	 
		      		JSONObject json = jArray.getJSONObject(i);
		      	 
		      		if(json.getString("ID").equals("1")){
		      		  
		      		//  Toast.makeText(getApplicationContext(), "PV= "+json.getString("PRESENT_VOTE") ,Toast.LENGTH_LONG ).show();
		      	   
		      		
		      		
		      		  
		      		 congress_s = json.getString("CON");
		      		 bjp_s = json.getString("BJP");
		      		 aap_s = json.getString("AAP");
		      		 agp_s = json.getString("AGP");
		      		 aiudf_s = json.getString("AIUDF");
		      		 others_s = json.getString("OTHERS");
		      		 congress_s_week = json.getString("CONWEEK");
		      		 bjp_s_week = json.getString("BJPWEEK");
		      		 aap_s_week = json.getString("AAPWEEK");
		      		 agp_s_week = json.getString("AGPWEEK");
		      		 aiudf_s_week = json.getString("AIUDFWEEK");
		      		 others_s_week = json.getString("OTHERSWEEK");
		      		 check_present_vote_s = json.getString("PRESENT_VOTE");
		      		  
		      		  check_present_vote = Integer.parseInt(check_present_vote_s);
		              congressvotes_n =  Integer.parseInt(congress_s);
		              bjpvotes_n =  Integer.parseInt(bjp_s);
		              aapvotes_n =  Integer.parseInt(aap_s);
		              agpvotes_n =  Integer.parseInt(agp_s);
		              aiudfvotes_n =  Integer.parseInt(aiudf_s);
		              othersvotes_n =  Integer.parseInt(others_s);
		               
		              
		              
		               congressvotes.setText("" + congressvotes_n);
		               bjpvotes.setText("" + bjpvotes_n);
		               aapvotes.setText("" + aapvotes_n);
		               agpvotes.setText("" + agpvotes_n);
		               aiudfvotes.setText("" + aiudfvotes_n);
		               othersvotes.setText("" + othersvotes_n);
		               
		            //   prev_votes.setText("CON : "+congressvotes_n);
		               
		             
		                 congress_s_fb = json.getString("CONFB");
		        		 bjp_s_fb = json.getString("BJPFB");
		        		 aap_s_fb = json.getString("AAPFB");
		        		 agp_s_fb = json.getString("AGPFB");
		        		 aiudf_s_fb = json.getString("AIUDFFB");
		        		 
		        	//	 Toast.makeText(getApplicationContext(), "PV= "+json.getString("CONFB") ,Toast.LENGTH_LONG ).show();
		        		 
		        		 fbcongress.setText("" + congress_s_fb);
		                 fbbjp.setText("" + bjp_s_fb);
		                 fbaap.setText("" + aap_s_fb);
		                 fbagp.setText("" + agp_s_fb);
		                 fbaiudf.setText("" + aiudf_s_fb);
		               
		               
		             
		               totalvotes_n = congressvotes_n + bjpvotes_n + aapvotes_n + agpvotes_n + aiudfvotes_n + othersvotes_n;
		        		
		               
		               
		               float l1 = (float) congressvotes_n/totalvotes_n;
		               float u1 = 1- l1;
		               
		               float l2 = (float) bjpvotes_n/totalvotes_n;
		               float u2 = 1- l2;
		               
		               float l3 = (float) aapvotes_n/totalvotes_n;
		               float u3 = 1- l3;
		               
		               float l4 = (float) agpvotes_n/totalvotes_n;
		               float u4 = 1- l4;
		               
		               float l5 = (float) aiudfvotes_n/totalvotes_n;
		               float u5 = 1- l5;
		               
		               float l6 = (float) othersvotes_n/totalvotes_n;
		               float u6 = 1- l6;
		               
		              // Toast.makeText(getApplicationContext(), "  "+ l3+"    "+ u3, Toast.LENGTH_SHORT).show();

		               LinearLayout.LayoutParams paramu1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u1);
		               LinearLayout.LayoutParams paraml1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l1);
		              
		               LinearLayout.LayoutParams paramu2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u2);
		               LinearLayout.LayoutParams paraml2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l2);
		               
		               LinearLayout.LayoutParams paramu3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u3);
		               LinearLayout.LayoutParams paraml3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l3);
		               
		               LinearLayout.LayoutParams paramu4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u4);
		               LinearLayout.LayoutParams paraml4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l4);
		               
		               LinearLayout.LayoutParams paramu5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u5);
		               LinearLayout.LayoutParams paraml5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l5);
		               
		               LinearLayout.LayoutParams paramu6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, u6);
		               LinearLayout.LayoutParams paraml6 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, l6);
		              
		               up1.setLayoutParams(paramu1);
		               lp1.setLayoutParams(paraml1);
		               
		               up2.setLayoutParams(paramu2);
		               lp2.setLayoutParams(paraml2);
		               
		               up3.setLayoutParams(paramu3);
		               lp3.setLayoutParams(paraml3);
		               
		               up4.setLayoutParams(paramu4);
		               lp4.setLayoutParams(paraml4);
		               
		               up5.setLayoutParams(paramu5);
		               lp5.setLayoutParams(paraml5);
		               
		               up6.setLayoutParams(paramu6);
		               lp6.setLayoutParams(paraml6);
		               
		               update_button(check_present_vote);
		             
		               prev_votes_con.setText("INC : "+congress_s_week);
		               prev_votes_bjp.setText("BJP : "+bjp_s_week);
		               prev_votes_aap.setText("AAP : "+aap_s_week);
		               prev_votes_agp.setText("AGP : "+agp_s_week);
		               prev_votes_aiudf.setText("AIUDF : "+aiudf_s_week);
		               prev_votes_others.setText("OTHERS : "+others_s_week);
		               
		               flippy.startFlipping();
		      	  
		      	}
		      			
		      			
		      		}	    
				  
		      	
		      	/*
		      	congressvotes_n =  Integer.parseInt(congress_s);
		        bjpvotes_n =  Integer.parseInt(bjp_s);
		        aapvotes_n =  Integer.parseInt(aap_s);
		        agpvotes_n =  Integer.parseInt(agp_s);
		        aiudfvotes_n =  Integer.parseInt(aiudf_s);
		        othersvotes_n =  Integer.parseInt(others_s);
		         
		         congressvotes.setText(""+congressvotes_n);
		         bjpvotes.setText(""+bjpvotes_n);
		         aapvotes.setText(""+aapvotes_n);
		         agpvotes.setText(""+agpvotes_n);
		         aiudfvotes.setText(""+aiudfvotes_n);
		         othersvotes.setText(""+othersvotes_n);

		   	  */
		    
		     /*    
		     congress_s="CON : "+congress_s;
		     bjp_s="BJP : "+bjp_s;
		     aap_s="AAP : "+aap_s;
		     agp_s="AGP : "+agp_s;
		     aiudf_s = "AIUDF : "+aiudf_s;
		     others_s = "OTHERS : "+others_s; 
		     */
		   	
		     	  
			}catch(Exception e){
				
				
			}
		      
		      
		      
			  }
		}
		
		 
		 
		 
		 
		 
		 
	 
	 //----------------------------------------------------------------------------------------------------
	 
	 //to set the button as voted one
	 public void update_button(int curr_vote){
			// flippy.stopFlipping();
			// Toast.makeText(getApplicationContext(), ""+curr_vote, Toast.LENGTH_SHORT).show();
			 
			 if(curr_vote == 1){
				 
				
				 
				 votecongress.setBackgroundResource(R.drawable.congresscolor);
				 votebjp.setBackgroundResource(R.drawable.bjpbnw);
				 voteaap.setBackgroundResource(R.drawable.aapbnw);
				 voteagp.setBackgroundResource(R.drawable.agpbnw);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfbnw);
				 voteothers.setBackgroundResource(R.drawable.othersbnw);
			 }
			 else if(curr_vote == 2){
				 
				 votecongress.setBackgroundResource(R.drawable.congressbnw);
				 votebjp.setBackgroundResource(R.drawable.bjpcolor);
				 voteaap.setBackgroundResource(R.drawable.aapbnw);
				 voteagp.setBackgroundResource(R.drawable.agpbnw);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfbnw);
				 voteothers.setBackgroundResource(R.drawable.othersbnw);
			 }
			 else if(curr_vote == 3){
				 
				 votecongress.setBackgroundResource(R.drawable.congressbnw);
				 votebjp.setBackgroundResource(R.drawable.bjpbnw);
				 voteaap.setBackgroundResource(R.drawable.aapcolor);
				 voteagp.setBackgroundResource(R.drawable.agpbnw);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfbnw);
				 voteothers.setBackgroundResource(R.drawable.othersbnw);
			 }
			 else if(curr_vote == 4){
				 
				 votecongress.setBackgroundResource(R.drawable.congressbnw);
				 votebjp.setBackgroundResource(R.drawable.bjpbnw);
				 voteaap.setBackgroundResource(R.drawable.aapbnw);
				 voteagp.setBackgroundResource(R.drawable.agpcolor);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfbnw);
				 voteothers.setBackgroundResource(R.drawable.othersbnw);
				 
			 }
			 else if(curr_vote == 5){
				 
				 votecongress.setBackgroundResource(R.drawable.congressbnw);
				 votebjp.setBackgroundResource(R.drawable.bjpbnw);
				 voteaap.setBackgroundResource(R.drawable.aapbnw);
				 voteagp.setBackgroundResource(R.drawable.agpbnw);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfcolor);
				 voteothers.setBackgroundResource(R.drawable.othersbnw);
			 }
			 else if(curr_vote == 6){
				 
				 votecongress.setBackgroundResource(R.drawable.congressbnw);
				 votebjp.setBackgroundResource(R.drawable.bjpbnw);
				 voteaap.setBackgroundResource(R.drawable.aapbnw);
				 voteagp.setBackgroundResource(R.drawable.agpbnw);
				 voteaiudf.setBackgroundResource(R.drawable.aiudfbnw);
				 voteothers.setBackgroundResource(R.drawable.otherscolor);
			 }
			 
		 }
	 
	
	 
	 //this are the party  buttons
	 public void votecon(final View v){
		 
		 v.startAnimation(buttonClick);
		 
		 check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
			 
		if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();		}
		else{
		 if(check_present_vote == 1){
			 
	
			 
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Already Voted!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();		
		        
		        
		 }else{
			 //flippy.stopFlipping();
			 
			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
    	      alertDialog.setTitle("Confirm Vote");
    	      alertDialog.setIcon(R.drawable.confirm);
    	      alertDialog.setMessage("Are you sure you want to vote for Congress ? Once voted you cannot change your vote till next week.");     
    	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	                	
    	          public void onClick(DialogInterface dialog, int which) {
                        
    	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
    	       	               
    	           } }); 
    	                
    	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
    	                      
    	            public void onClick(DialogInterface dialog, int which) {
    	            	
   	  		
    	            	new Update(get_phone_number_s, "CON", weekdata_s).execute();
    	            	Update_week_check();
    	            	
    	          
   	         	  
    	              } }); 

    	      alertDialog.show();

    	         };
    	 
        
		
		
		
		}
		
		
		 
		 }
		 
		}
	
	 public void votebjp(final View v){
		 
		 v.startAnimation(buttonClick);
		 
     check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
		 
		 if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();			}
			else{
		 
              if(check_present_vote == 2){
            	  
			 
            	  LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Already voted!");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();		 }else{
			 //flippy.stopFlipping();
			 
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Confirm Vote");
   	      alertDialog.setIcon(R.drawable.confirm);
   	      alertDialog.setMessage("Are you sure you want to vote for Bharatiya Janata Party ? Once voted you cannot change your vote till next week. ");     
   	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
   	                      
   	            public void onClick(DialogInterface dialog, int which) {
   	            	
  	  		
   	            	new Update(get_phone_number_s,"BJP", weekdata_s).execute();
   	            	
   	              	   	 	    
   	             Update_week_check();
   	             
   	     	
  	         	  
   	              } }); 

   	      alertDialog.show();

   	         };
   	 
       
			}
		
		 }
	 }
	 public void voteaap(final View v){
		 
		 v.startAnimation(buttonClick);
		 
 check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
		 
		 if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();			}
			else{
		 
       if(check_present_vote == 3){
			
    	   LayoutInflater inflater = getLayoutInflater();

	        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

	        //Set text
	        TextView text = (TextView) layout.findViewById(R.id.tv);
	        text.setText("Already voted!");

	        //Toast
	        Toast toast = new Toast(getApplicationContext());
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.setDuration(Toast.LENGTH_SHORT);
	        toast.setView(layout);
	        toast.show();		 }else{
			 //flippy.stopFlipping();
			
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Confirm Vote");
   	      alertDialog.setIcon(R.drawable.confirm);
   	      alertDialog.setMessage("Are you sure you want to vote for Aam Aadmi Party ? Once voted you cannot change your vote till next week.");   
   	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
   	                      
   	            public void onClick(DialogInterface dialog, int which) {
   	            	
  	  		
   	            	new Update(get_phone_number_s, "AAP", weekdata_s).execute();
   	             
   	          
   	   	 	       	            	
   	            	Update_week_check();
  	         	  
   	              } }); 

   	      alertDialog.show();

   	         };
   	 
       
			}
		
		 }
	 }
	 public void voteagp(View v){
		 
		 v.startAnimation(buttonClick);
            
 check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
		 
		 if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();			}
			else{
		 
		 if(check_present_vote == 4){
		
			 
			 
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Already voted!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();		 }else{
			 //flippy.stopFlipping();
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Confirm Vote");
   	      alertDialog.setIcon(R.drawable.confirm);
   	      alertDialog.setMessage("Are you sure you want to vote for Assam Gana Parishad ? Once voted you cannot change your vote till next week.");      
   	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
   	                      
   	            public void onClick(DialogInterface dialog, int which) {
   	            	
  	  		
   	            	new Update(get_phone_number_s, "AGP", weekdata_s).execute();
   	            	
   	             Update_week_check();
   	              } }); 

   	      alertDialog.show();

   	         };
   	 
       
		}
		 
		
		 }
	 }
	 public void voteaiudf(View v){
		 
		 v.startAnimation(buttonClick);
		 
 check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
		 
		 if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();			}
			else{    
		 
		 if(check_present_vote == 5){
			 
			 
			 
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Already voted!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();		 }else{
			 //flippy.stopFlipping();
			
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Confirm Vote");
   	      alertDialog.setIcon(R.drawable.confirm);
   	      alertDialog.setMessage("Are you sure you want to vote for All India United Democratic Front ? Once voted you cannot change your vote till next week.");     
   	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
   	                      
   	            public void onClick(DialogInterface dialog, int which) {
   	            	
  	  		
   	            	new Update(get_phone_number_s, "AIUDF", weekdata_s).execute();
   	             Update_week_check();
  	         	  
   	              } }); 

   	      alertDialog.show();

   	         };
   	 
       
		}
		 
		 
		 }
	 }
	 
	
	 public void voteoth(View v){
		 
		 v.startAnimation(buttonClick);
		 
 check_week();
		 
		 if(weekcheck == 0){
			

			  AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Sorry!");
   	      alertDialog.setMessage("You cannot vote right now. You have to wait till next week");     
   	      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	   alertDialog.show();
		 }
		 else{ 
		 
		 if(checknet()==0){
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();			}
			else{
		 
            if(check_present_vote == 6){
            	
            	
            	  LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Already voted!");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();		 }else{
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(HomePage.this);
   	      alertDialog.setTitle("Confirm Vote");
   	      alertDialog.setIcon(R.drawable.confirm);
   	      alertDialog.setMessage("Are you sure you want to vote for Other Parties ? Once voted you cannot change your vote till next week.");    
   	      alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   	                	
   	          public void onClick(DialogInterface dialog, int which) {
                       
   	            //   Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
   	       	               
   	           } }); 
   	                
   	      alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
   	                      
   	            public void onClick(DialogInterface dialog, int which) {
   	            	
  	  		
   	            	new Update(get_phone_number_s, "OTHERS", weekdata_s).execute();
   	             Update_week_check();
  	         	  
   	              } }); 

   	      alertDialog.show();

   	         };
   	 
       
		}
		
		 
		 }
		}
	 
	 
	 
	 
	 
	 
	 //share votes onclick
   public void sharevotes(View v){
	
	   getScreen();
	   
	   /*
	   try
       { Intent i = new Intent(Intent.ACTION_SEND);  
         i.setType("text/plain");
         i.putExtra(Intent.EXTRA_SUBJECT, "Assam Poll 2016 Votes");
         String sAux = "Assam Poll 2016 Statistics are : \nCON : " + congressvotes_n +  ", BJP : " + bjpvotes_n + ", AAP : " + aapvotes_n + ", AGP : " + agpvotes_n + ", AIUDF : " + aiudfvotes_n + ", OTHERS : "  + othersvotes_n + "\n" ;
        // sAux = sAux + "https://play.google.com/store/apps/details?id=com.viewpoint.rankpredictor\n";
         i.putExtra(Intent.EXTRA_TEXT, sAux);   
         startActivity(Intent.createChooser(i, "Share stat's via"));
       }
       catch(Exception e)
       { //e.toString();
       }   */
	 }
	
	 public void adclick(View v){
		 
		 
		 Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	        myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
	        myWebLink.setData(Uri.parse(adlink));
	        startActivity(myWebLink);
		 
	 }
	 
	 //my constituency page onclick
	 public void myareapage(View v){
		 
		 Intent intent = new Intent(this, MyAreaPage.class);
		 startActivity(intent);
         overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

         finish();
	 }
	 
	 
	 /*
	 public void setflip(){
			prev_votes_con.setText(""+congress_s);
          	prev_votes_bjp.setText(""+bjp_s);
          	prev_votes_aap.setText(""+aap_s);
          	prev_votes_agp.setText(""+agp_s);
          	prev_votes_aiudf.setText(""+aiudf_s);
          	prev_votes_others.setText(""+others_s);
          	
         
          	//flippy.startFlipping();
		 
		 
		 
	 }
	 */
	 
	 
	 


   int checknet(){
	  
	  cd = new ConnectionDetector(getApplicationContext());
		 
		isInternetPresent = cd.isConnectingToInternet(); 
	 
	  if( isInternetPresent == false ){
			
			//Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
			 AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
	       builder.setMessage("No Internet Connection");
	        builder.setTitle("Error");
	        builder.setPositiveButton("OK",
	    			
					new DialogInterface.OnClickListener() {
			
				public void onClick(DialogInterface dialog,int which) {
			
					
			
			}
			});
			
			builder.show();
			return 0;
			}
	  else{
		  return 1;
		  
	  }
	  
	  
  }

  //refresh function
    int checknetforref(){
	  
	  cd = new ConnectionDetector(getApplicationContext());
		 
		isInternetPresent = cd.isConnectingToInternet(); 
	 
	  if( isInternetPresent == false ){
			
			
			return 0;
			}
	  else{
		  return 1;
		  
	  }
    }


  

  private final Runnable m_Runnable = new Runnable()
  {
      public void run()

      {
    	if( checknetforref()==1 ){
    	  
    	  
    	  new GetAllRefresh(get_phone_number_s, weekdata_s).execute();
         
          
    	}
    	else{
    		//Toast.makeText(getApplicationContext(), "connect to net", Toast.LENGTH_SHORT).show();
    		
    	}
    	 HomePage.this.mHandler.postDelayed(m_Runnable, 60000); 
    	
      }

  };



//taking screenshot
  private void getScreen()
  {
  	 View content = findViewById(R.id.graphlayout);
  	    Bitmap bitmap = content.getDrawingCache();
    //  File file = new File( Environment.getExternalStorageDirectory() + "/test.png");
  	    File makefile = new File( Environment.getExternalStorageDirectory() + "/AssamPoll");
        makefile.mkdir();
        File file = new File( Environment.getExternalStorageDirectory() + "/AssamPoll/test.png");
      
      try 
      {
      	
      	//this is for making getting screen shot
          file.createNewFile();
          FileOutputStream ostream = new FileOutputStream(file);
          bitmap.compress(CompressFormat.PNG, 100, ostream);
          ostream.close();
          //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
          
     //this is for sharing
        
          
                    
          
          File myFile = new File(Environment.getExternalStorageDirectory() + "/AssamPoll/test.png");
          MimeTypeMap mime = MimeTypeMap.getSingleton();
          String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
          String type = mime.getMimeTypeFromExtension(ext);
          Intent sharingIntent = new Intent("android.intent.action.SEND");
          //  Intent(Intent.ACTION_SEND);
        		  
          sharingIntent.setType(type);
          sharingIntent.putExtra( "android.intent.extra.STREAM", Uri.fromFile(myFile));   
        		//  "android.intent.extra.STREAM"
       //   sharingIntent.putExtra(Intent.EXTRA_TITLE, "my awesome caption in thefield");

          
          startActivity(Intent.createChooser(sharingIntent,"Share using"));
       
          
      } 
      catch (Exception e) 
      {
          Toast.makeText(getApplicationContext(), "Not done", Toast.LENGTH_SHORT).show();
      }
      
      
      
    
    /*  
      try {
          File myFile = new File(Environment.getExternalStorageDirectory() + "/test.png");
          MimeTypeMap mime = MimeTypeMap.getSingleton();
          String ext=myFile.getName().substring(myFile.getName().lastIndexOf(".")+1);
          String type = mime.getMimeTypeFromExtension(ext);
          Intent sharingIntent = new Intent("android.intent.action.SEND");
          sharingIntent.setType(type);
          sharingIntent.putExtra("android.intent.extra.STREAM",Uri.fromFile(myFile));   
          startActivity(Intent.createChooser(sharingIntent,"Share using"));
      }
      catch(Exception e){
          Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();  
      } 
      
      */
      
      
  }
	
  
  public void check_week(){
	  
	  dbweekcheck = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
	    Cursor c3 = dbweekcheck.rawQuery("SELECT * FROM weekcheck WHERE id='"+1+"'", null);
		if(c3.getCount() == 0)
		{
		   return;
		}
		while( c3.moveToNext() ){
			//constituency_s = c1.getString(1);
			weeknumber_s = c3.getString(1);
			weeknumber_n = Integer.parseInt(weeknumber_s);
//Toast.makeText(getApplicationContext(), " " + weeknumber_s , Toast.LENGTH_SHORT).show();
		  
		}
	

		




/*if(get_phone_number_s.equals("0")){
	
	Toast.makeText(getApplicationContext(), "Your phone is not regstred", Toast.LENGTH_SHORT).show();
}
else{
	//Toast.makeText(getApplicationContext(), "Your phone is not regstred", Toast.LENGTH_SHORT).show();
*/

//Toast.makeText(getApplicationContext(), "" + get_phone_number_s,Toast.LENGTH_LONG).show();

Calendar cal = Calendar.getInstance();

weekofyear = cal.get(Calendar.WEEK_OF_YEAR);
int year = cal.get(Calendar.YEAR);

weekdata_s = Integer.toString(weekofyear);
//Toast.makeText(getApplicationContext(), ""+weekdata_s, Toast.LENGTH_LONG).show();
yeardata_s = Integer.toString(year);


if( weeknumber_n == 10 || weekofyear == ( weeknumber_n + 1 ) ){
	
	weekcheck = 1;
	
}
else{
	
	weekcheck = 0;
}
	  
  }
  
  
 public void Update_week_check(){
	  
	  dbweekcheck = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);

		 

		Cursor c = dbweekcheck.rawQuery("SELECT * FROM weekcheck WHERE id='"+1+"'", null);
		if(c.moveToFirst())
		{
			dbweekcheck.execSQL("UPDATE weekcheck SET week='"+ weekofyear+"' WHERE id='"+ 1 +"'");
			//Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
		}
		
		
		 
	  
  }
  
 
 
 @Override
 public void onBackPressed() {
     new AlertDialog.Builder(this)
         .setTitle("Really Exit?")
         .setMessage("Are you sure you want to exit?")
         .setNegativeButton(android.R.string.no, null)
         .setPositiveButton(android.R.string.yes, new OnClickListener() {

             public void onClick(DialogInterface arg0, int arg1) {
                 //HomePage.super.onBackPressed();
            	 finish();
                 System.exit(0);
             }
         }).create().show();
 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		
		if ( id == R.id.refresh ) {
			
			cd = new ConnectionDetector(getApplicationContext());
			 
			isInternetPresent = cd.isConnectingToInternet(); 
			
			if( isInternetPresent == false ){
				
				//Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
		       builder.setMessage("No Internet Connection");
		        builder.setTitle("Error");
		        builder.setPositiveButton("OK",
		    			
						new DialogInterface.OnClickListener() {
				
					public void onClick(DialogInterface dialog,int which) {
				
						
				}
				});
				
				builder.show();
		        
		        
				
			
				
			}
			else{
			
				  LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Refreshing...");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			     //   toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();						
			new GetAllRefresh(get_phone_number_s, weekdata_s).execute();
			}
			return true;
		}
		//share app
        if (id == R.id.more) {
        	
        	Intent intent = new Intent(this, More.class);
	    	startActivity(intent);
            
	      //this.overridePendingTransition(R.anim.anim_slide_in_left,
              //      R.anim.anim_slide_out_left);
	    	
	    	getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        	

          //  return true;
        }
   
		return super.onOptionsItemSelected(item);
	}
}
