package com.maverick.trackeradmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.maverick.trackeradmin.MainActivity.Plots;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserDetails extends ActionBarActivity {
	
	String username_s, contactno_s, designation_s, empid_s, place_s, imagename_s, password_s, starttime_s, endtime_s;

	TextView username_t, contactno_t, designation_t, empid_t, place_t, imagename_t, password_t, starttime_t, endtime_t;
	
	ImageView mainImageView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));

		StrictMode.enableDefaults();
		
		String UserName = getIntent().getStringExtra("UserName");
		
		username_t = (TextView)findViewById(R.id.usernametext);
		contactno_t = (TextView)findViewById(R.id.contactnotext);
		designation_t = (TextView)findViewById(R.id.designationtext);
		empid_t = (TextView)findViewById(R.id.empIDtext);
		place_t = (TextView)findViewById(R.id.placetext);
		password_t = (TextView)findViewById(R.id.passwordtext);
		starttime_t = (TextView)findViewById(R.id.starttimetext);
		endtime_t = (TextView)findViewById(R.id.endtimetext);
		
		
		new getprofileuserdetails(UserName).execute();
	}
	
	
	
	
	public class getprofileuserdetails extends AsyncTask<Void, Void, Void>{
		
	  InputStream isr = null;
	  String result = "";	 
	   	 
	  String username_s;
  	      
      ProgressDialog dialog = new ProgressDialog(UserDetails.this);
  	   
  	  public getprofileuserdetails(String username_s ){
  	      
  		this.username_s = username_s;	
  		
  	  }

  	  @Override
  	  protected void onPreExecute() {
  	   
  		 // TODO Auto-generated method stub
  		  
  		 dialog.setMessage("Please wait..."); 
  		 dialog.setCanceledOnTouchOutside(false);
  		 dialog.show();
  		  
                 
         
  	  }

      @Override
  	  protected Void doInBackground(Void... params) {
  	   
  		    	
  			try{
  			    	 
  			  HttpClient httpclient = new DefaultHttpClient();
  			    	 
  			  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GETPROFILEUSER.php"); //YOUR PHP SCRIPT ADDRESS
  						    	 
  			    	
  			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
  			    	
  			  nameValuePairs.add(new BasicNameValuePair("UserName", username_s));
  		 	
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
	  
	    //  Toast.makeText(getApplicationContext(), "Result" + result, Toast.LENGTH_LONG).show();
	  
	         char check_char = result.charAt(0);
			 int result_n = Character.getNumericValue(check_char);
			 
		     if(result_n == 0){
			 		  
			 	Toast.makeText(getApplicationContext(), "Data of this user is absent.", Toast.LENGTH_SHORT).show();
			 }
			 else{
			 	
				 // Toast.makeText(getApplicationContext(), "User data is present", Toast.LENGTH_SHORT).show();

			 		  
				 try {
				      	JSONArray jArray = new JSONArray(result);

				             	
				      	for( int i = 0 ; i < jArray.length( ); i++ ){
				      	 
				      		JSONObject json = jArray.getJSONObject(i);
				      			  
				      		 username_s = json.getString("NAME");
				      		 password_s = json.getString("PASSWORD");
				      		 contactno_s = json.getString("CONTACTNO");
				      		 designation_s = json.getString("DESIGNATION");				      		 
				      		 empid_s = json.getString("EMPID");
				      		 place_s = json.getString("PLACE");
				      		 starttime_s = json.getString("STARTTIME");
				      		 endtime_s = json.getString("ENDTIME");
				      		 imagename_s = json.getString("IMAGENAME");
	      	
	      	 	 //   Toast.makeText(getApplicationContext(), "Image: " + imagename_s , Toast.LENGTH_SHORT).show();	
	      	 	    
	      	 	mainImageView = (ImageView) findViewById(R.id.imageView);
	 	        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
	 	        String imageurl = "http://www.spmaverick.com/TrackMe/uploadedimages/" + imagename_s;
	 	        
	 	        
	 	        username_t.setText("" + username_s);
	 	        password_t.setText("" + password_s);
	 	        contactno_t.setText("" + contactno_s);
	 	        designation_t.setText("" + designation_s);
	 	        empid_t.setText("EMP CODE: " + empid_s);
	 	        place_t.setText("" + place_s);
	 	        starttime_t.setText("" + starttime_s);
   		        endtime_t.setText("" + endtime_s);
	 
	 	        
	 	        ImageDownloadMessageHandler imageDownloadMessageHandler1= new ImageDownloadMessageHandler(progressBar, mainImageView);
	 	        ImageDownlaodThread imageDownlaodThread = new ImageDownlaodThread(imageDownloadMessageHandler1,imageurl);
	 	        imageDownlaodThread.start();
	 	        
	 	        
	      	 	    
		    	}
	      	
		     }catch(Exception e){
			
	      	   Toast.makeText(getApplicationContext(), "Error Loading", Toast.LENGTH_SHORT).show();	

		}
	      
	  }
  	  }  
	
}
	
	 class ImageDownlaodThread extends Thread {
		 
	        ImageDownloadMessageHandler imageDownloadMessageHandler;
	        String imageUrl;

	        public ImageDownlaodThread(ImageDownloadMessageHandler imageDownloadMessageHandler, String imageUrl) {
	         this.imageDownloadMessageHandler = imageDownloadMessageHandler;
	         this.imageUrl = imageUrl;
	        }

	        @Override
	        public void run() {
	         Drawable drawable = LoadImageFromWebOperations(imageUrl);
	         Message message = imageDownloadMessageHandler.obtainMessage(1, drawable);
	         imageDownloadMessageHandler.sendMessage(message);
	         System.out.println("Message sent");
	        }

	       }
	

    class ImageDownloadMessageHandler extends Handler {
     ProgressBar progressBar;
     View imageTextView;

     public ImageDownloadMessageHandler(ProgressBar progressBar, View imageTextView) {
      this.progressBar = progressBar;
      this.imageTextView = imageTextView;
     }

     @Override
     public void handleMessage(Message message) {
      progressBar.setVisibility(View.GONE);
      imageTextView.setBackgroundDrawable(((Drawable) message.obj));
      imageTextView.setVisibility(View.VISIBLE);
     }

    }
	  Drawable LoadImageFromWebOperations(String url) {
	        Drawable d = null;
	        InputStream is = null;
	        try {
	         is = (InputStream) new URL(url).getContent();
	         d = Drawable.createFromStream(is, "src name");
	        } catch (MalformedURLException e) {
	         e.printStackTrace();
	        } catch (IOException e) {
	         e.printStackTrace();
	        }
	        return d;
	       }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_details, menu);
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
