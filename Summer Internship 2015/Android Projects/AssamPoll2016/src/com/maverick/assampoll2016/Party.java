package com.maverick.assampoll2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Party extends ActionBarActivity {
	
int check_which_party_clicked;
ImageView party_icon;
TextView party_title;
TextView party_content;

String fb_likes_s, talking_about_s, website_s, fb_link_s, contact_s, location_s, aboutparty_s;
TextView likes, talkingabout, website, contact, location, description;
Button fblink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_party);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		// If your minSdkVersion is 11 or higher use:
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//color to action bar
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));

		StrictMode.enableDefaults();
		//findviewbyy ids
  		party_icon = (ImageView)findViewById(R.id.partyphoto);
  		party_title = (TextView)findViewById(R.id.partytitle);
  		party_content = (TextView)findViewById(R.id.partycontent);
		
		likes = (TextView)findViewById(R.id.likes);
		talkingabout = (TextView)findViewById(R.id.talkingabout);
		//website = (TextView)findViewById(R.id.websitelink);
		fblink = (Button)findViewById(R.id.fblink);
		contact = (TextView)findViewById(R.id.contact);
		location = (TextView)findViewById(R.id.location);
	//	description = (TextView)findViewById(R.id.partycontent);
		
		
		//Get the bundle
  		Bundle bun = getIntent().getExtras();

  		//Extract the data… 
  		String s = bun.getString(" "); 
  		check_which_party_clicked = Integer.parseInt(s);
  		

  		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent==false){
			
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Party.this);
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
  		
  		
  		if( check_which_party_clicked == 1 ){
  			
  			party_icon.setImageResource(R.drawable.congresscolor);
  			party_title.setText("I N C");
  			
  			getActionBar().setSubtitle("Indian National Congress");
  			  			
  			new GetPartyDetails("CON").execute();
  			
  		//	party_content.setText(" The state unit of the INC is called the Assam Pradesh Congress Committee or APCC. It was formed in the year 1921 and is headquartered at Rajiv Bhavan, Guwahati. At the time of its formation in the state, Kuladhar Chaliha was the president.\n Currently, the Chairperson of the Assam Pradesh Congress Committee is Bhubaneshwar Kalita. It has many ancillary wings, such as the National Students' Union of India, the Assam Pradesh Youth Congress, the Assam Pradesh Mahila Congress, and the Indian National Trade Union Congress. On the political spectrum, the INC is believed to be centre-left.");
  		}
  		else if( check_which_party_clicked == 2 ){
  			
  			party_icon.setImageResource(R.drawable.bjpcolor);
  			party_title.setText("B J P");
  			
  			getActionBar().setSubtitle("Bharatiya Janata Party");
  			
  			new GetPartyDetails("BJP").execute();
  			
  		//	party_content.setText(" The state unit of the INC is called the Assam Pradesh Congress Committee or APCC. It was formed in the year 1921 and is headquartered at Rajiv Bhavan, Guwahati. At the time of its formation in the state, Kuladhar Chaliha was the president.\n Currently, the Chairperson of the Assam Pradesh Congress Committee is Bhubaneshwar Kalita. It has many ancillary wings, such as the National Students' Union of India, the Assam Pradesh Youth Congress, the Assam Pradesh Mahila Congress, and the Indian National Trade Union Congress. On the political spectrum, the INC is believed to be centre-left.");
  		}
        else if( check_which_party_clicked == 3 ){
  			
        	party_icon.setImageResource(R.drawable.aapcolor);
  			party_title.setText("A A P");
  			
  			getActionBar().setSubtitle("Aam Aadmi Party");
  			
  			new GetPartyDetails("AAP").execute();
  			
  		//	party_content.setText(" The state unit of the INC is called the Assam Pradesh Congress Committee or APCC. It was formed in the year 1921 and is headquartered at Rajiv Bhavan, Guwahati. At the time of its formation in the state, Kuladhar Chaliha was the president.\n Currently, the Chairperson of the Assam Pradesh Congress Committee is Bhubaneshwar Kalita. It has many ancillary wings, such as the National Students' Union of India, the Assam Pradesh Youth Congress, the Assam Pradesh Mahila Congress, and the Indian National Trade Union Congress. On the political spectrum, the INC is believed to be centre-left.");
  		}
        else if( check_which_party_clicked == 4 ){
		
        	party_icon.setImageResource(R.drawable.agpcolor);
  			party_title.setText("A G P");
  			
  			getActionBar().setSubtitle("Assam Gana Parishad");
		
		new GetPartyDetails("AGP").execute();
		
		//	party_content.setText(" The state unit of the INC is called the Assam Pradesh Congress Committee or APCC. It was formed in the year 1921 and is headquartered at Rajiv Bhavan, Guwahati. At the time of its formation in the state, Kuladhar Chaliha was the president.\n Currently, the Chairperson of the Assam Pradesh Congress Committee is Bhubaneshwar Kalita. It has many ancillary wings, such as the National Students' Union of India, the Assam Pradesh Youth Congress, the Assam Pradesh Mahila Congress, and the Indian National Trade Union Congress. On the political spectrum, the INC is believed to be centre-left.");
	   }
       else if( check_which_party_clicked == 5 ){
		
    	   party_icon.setImageResource(R.drawable.aiudfcolor);
 			party_title.setText("A I U D F");
 			
 			getActionBar().setSubtitle("All India United Democratic Front");
		
		new GetPartyDetails("AIUDF").execute();
		
		//	party_content.setText(" The state unit of the INC is called the Assam Pradesh Congress Committee or APCC. It was formed in the year 1921 and is headquartered at Rajiv Bhavan, Guwahati. At the time of its formation in the state, Kuladhar Chaliha was the president.\n Currently, the Chairperson of the Assam Pradesh Congress Committee is Bhubaneshwar Kalita. It has many ancillary wings, such as the National Students' Union of India, the Assam Pradesh Youth Congress, the Assam Pradesh Mahila Congress, and the Indian National Trade Union Congress. On the political spectrum, the INC is believed to be centre-left.");
	   }
		}
  		/*
       else if( check_which_party_clicked == 6 ){
		
		party_icon.setImageResource(R.drawable.otherscolor);
		party_title.setText("Bodoland People's Front");
		party_content.setText(" It is a state-level political party and is considerably strong in and around Kokrajhar and Autonomous District. During the 2009 general election, Sansuma Khunggur Bwiswmuthiary became its first MP, elected from Kokrajhar. In 2008, Biswajit Daimary was the first person to be elected from the Rajya Sabha. In the 12th Assam Legislative Assembly, the Bodoland People's Front won 10 assembly seats.\n Currently, it is a constituent of the ruling coalition government of Assam. It won 12 seats in 2011 Assam Assembly election.");
	}*/
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	    
	    	 Intent parentIntent = new Intent(this,AssamPoliticalParties.class);
		      startActivity(parentIntent);
		      
		         overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

		      finish();
	    	 
	    	 return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
	
	
	public void findonfb(View v){
		
		Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
        myWebLink.setData(Uri.parse("" + fb_link_s));
        startActivity(myWebLink);
		
	}
	
	public void location(View v){
		
		String uri = String.format(Locale.ENGLISH, "" + location_s);
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	this.startActivity(intent);
	}
	
	//get party info
	 public class GetPartyDetails extends AsyncTask <Void, Void, Void>{
	 
 	  String party_name_s1;
 	  InputStream isr = null;
 	  String result = "";
 	 
 	  
   	ProgressDialog dialog = new ProgressDialog(Party.this);

 	    public GetPartyDetails(String party_name_s1){
 	      this.party_name_s1 = party_name_s1;
 	      
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
			    	 
			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/FBPARTYDETAILS.php"); //YOUR PHP SCRIPT ADDRESS
			  			    	 
			    	
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			    	
			  
			  nameValuePairs.add(new BasicNameValuePair("PARTYNAME", party_name_s1));
			  

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
    	     
    		 fb_likes_s = json.getString("LIKES");
    		 talking_about_s = json.getString("TALKABT");
    		// website_s = json.getString("WEBSITE");
    		 fb_link_s = json.getString("FBLINK");
    		 contact_s = json.getString("CONTACT");
    		 location_s = json.getString("LOCATION");
    		 aboutparty_s = json.getString("ABOUT");
    		 
    		 likes.setText("" + fb_likes_s + " Likes");
    		 talkingabout.setText("" + talking_about_s + " people talking about this.");
    		// website.setText("" + website_s);
    		// fblink.setText("On FB: " + fb_link_s);
    		 contact.setText("" + contact_s);
    		 party_content.setText("" + aboutparty_s);
    		// location.setText("" + location_s);
    	//	 Toast.makeText(getApplicationContext(), "Likes " + json.getString("LIKES") + "\n" + json.getString("TALKABT") + "talking about this\nWEBSITE " + json.getString("WEBSITE") + "\nfb " +  json.getString("FBLINK") + "\n\n" + "contact" + json.getString("CONTACT") ,Toast.LENGTH_LONG ).show(); 
      		}
    	 }	    
		  
       }catch(Exception e){
		
    	   Toast.makeText(getApplicationContext(), "Sorry, error loading data. Please try again.", Toast.LENGTH_SHORT).show();
	   }
    }
}
	
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		 if (id == android.R.id.home) {
			 
			 Intent parentIntent = new Intent(this,AssamPoliticalParties.class);
		      startActivity(parentIntent);
		      
		         overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

		      finish();
		 }
		return super.onOptionsItemSelected(item);
	}
}
