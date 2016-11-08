package com.maverick.assampoll2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Verification extends ActionBarActivity {

	int otp_n;
	EditText otp;
	SQLiteDatabase db;
	TextView timer;
	Boolean istimerfinished;
	String get_phone_number_s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_verification);

		
		getActionBar().setDisplayShowHomeEnabled(false);
		
		//color to action bar
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));
		

		StrictMode.enableDefaults();
		istimerfinished = false;
		
		 //open or create database
	      db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
		
		otp = (EditText)findViewById(R.id.eotp);
		timer = (TextView)findViewById(R.id.timer);
		
		//Get the bundle
  		Bundle bun = getIntent().getExtras();

  		//Extract the data… 
  		String otp_s = bun.getString(" "); 
  		otp_n = Integer.parseInt(otp_s);
  		
  		new CountDownTimer(60000, 1000) {

  		     public void onTick(long millisUntilFinished) {
  		         timer.setText("Time left : " + millisUntilFinished / 1000 + " seconds" );
  		     }

  		     public void onFinish() {
  		    	 istimerfinished = true;
  		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
  		     }
  		  }.start();
  		 
  	}
	
	public void ok(View v){
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent == false){
			
			  
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
		        toast.show();	      	  			
		}
		else{
		
		String typed_otp = otp.getText().toString();
		
		
		if(typed_otp.matches("")){
			  
            LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Please type the 4 digit code.!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	      	  			
		}
		else{
		
			int typed_otp_n = Integer.parseInt(typed_otp);
			if(typed_otp_n == otp_n && istimerfinished==false ){
			
			Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
			if(c.moveToFirst())
			{
				db.execSQL("UPDATE student SET phoneverification='"+1+"' WHERE id='"+1+"'");
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
			}
			
			
			
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
			
			
			
			Intent intent = new Intent(this, FlashAd.class);
			startActivity(intent);
			finish();
		}
		else if(typed_otp_n!=otp_n){
			  
            LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Please enter the correct code");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	      	  			
		}
		else if(typed_otp_n!=otp_n){
			  
            LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Click on resend OTP to get a new OTP!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	      	  			
		}
		else{
			
			 LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Click on resend OTP to get a new OTP!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	      			}
		
		}
		
		}
		}

	
	public void resend(View v){
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent == false){
			
			Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
			
		}
		else{
		
		
		
        Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
		if(c.getCount() == 0)
		{
		  return;
		}
		while(c.moveToNext()){
			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
		   get_phone_number_s = c.getString(1);
		}
		
		new UpdateInfoAsyncTask(get_phone_number_s).execute();
		
		}
		/*
		Random random = new Random();
		int r = random.nextInt(9000)+1001;
		String otp_s = Integer.toString(r);

		//int r=1234;
		// sendSMS(get_phone_number_s, "Welcome to ASSAM-POLL-2016 . Your confirmation code is  "+ r);
		Toast.makeText(getApplicationContext(), "" + r, Toast.LENGTH_SHORT).show();
		 
		Intent intent = new Intent(Verification.this, Verification.class);
         
    	 //Create the bundle
         Bundle bun = new Bundle();

         //Add your data to bundle
       bun.putString(" ",otp_s);

         
         //Add the bundle to the intent
         intent.putExtras(bun);
         
         startActivity(intent);
         
    	 finish();
		*/
		
	}
	
	private void sendSMS(String phoneNumber, String message)
    {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber, null, message, null, null);
    } 
	
	
	
	
	
	public class UpdateInfoAsyncTask extends AsyncTask<Void, Void, Void>{
   	 
		 
  	  String phone_no_s1;
  	  char check_char;
  	  int done;
  	  
  	  Random random = new Random();
		 int r = random.nextInt(9000)+1001;
		String otp_s = Integer.toString(r);
  	  
        ProgressDialog dialog = new ProgressDialog(Verification.this);
  	   
  	  public UpdateInfoAsyncTask(String phone_no_s1){
  	     
  	      this.phone_no_s1 = phone_no_s1;
  	     
  	  }

  	  @Override
  	  protected void onPreExecute() {
  	   
  		  // TODO Auto-generated method stub
  		
  		 dialog.setMessage("Please wait..."); 
  		 dialog.setCanceledOnTouchOutside(false);
  		 dialog.show();
  		  
         done = 0;
         
         
  	  }

   @Override
  	  protected Void doInBackground(Void... params) {
  	   
  		  
  		  //-------------------
  		//  String result = "";
		    	 
  		//  InputStream isr = null;
  			    	
  			try{
  			    	 
  			   HttpClient httpclient = new DefaultHttpClient();
  			    	 
  			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/RESENDOTP.php"); //YOUR PHP SCRIPT ADDRESS
  			    	 
  			   // HttpPost httppost = new HttpPost("http://172.23.193.32/elift-test/myfile.php"); //YOUR PHP SCRIPT ADDRESS
  			    	 
  			    	
  			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
  			    	
  			 
  			  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_no_s1));
  			  nameValuePairs.add(new BasicNameValuePair("OTP",otp_s));

  			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			    	
  			    	
  			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			 String response = httpclient.execute(httppost, responseHandler);
  			 check_char = response.charAt(0);
  			 done = Character.getNumericValue(check_char);
  			  //HttpResponse response = httpclient.execute(httppost);
  			    	 
  			//  HttpEntity entity = response.getEntity();
  			    	 
  			 // isr = entity.getContent();
  			   	
  		 }catch(Exception e){
  			   
  			}  	
  		
  	    
  	       return null;
  	  }
  	  protected void onPostExecute(Void unused) {
  		 //Toast.makeText(getApplicationContext(), ""+done, Toast.LENGTH_SHORT).show();
  		  dialog.dismiss();
  		  
  		  if(done == 1){
		    		/*
		    		 Random random = new Random();
		    		 
		    		int r = random.nextInt(9000)+1001;
		    		String otp_s = Integer.toString(r);
                    */
		    		//int r=1234;
		    		 //sendSMS(phone_no_s1, "Welcome to ASSAM-POLL-2016 . Your confirmation code is  "+ r);
		    	//Toast.makeText(getApplicationContext(), "" + r, Toast.LENGTH_SHORT).show();
		    		
		    		// save number in database
		    		
		    	
		    		
          
		    		
	        	
  			Intent intent = new Intent(Verification.this, Verification.class);
  	         
  	    	 //Create the bundle
  	         Bundle bun = new Bundle();

  	         //Add your data to bundle
  	         bun.putString(" ",otp_s);

  	         
  	         //Add the bundle to the intent
  	         intent.putExtras(bun);
  	         
  	         startActivity(intent);
  	         
  	    	 finish();
  		  
  		  
  		  
  		  
  	  }
  	  
  	}
	}
	
	
}
