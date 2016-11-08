package com.maverick.trackeradmin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
EditText username, password;
String username_s, password_s;
SQLiteDatabase db;

   //net connection
	Boolean isInternetPresent;
	ConnectionDetector cd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_login);
		
		//open or create database
	     
	      
		
		username = (EditText)findViewById(R.id.editText1);		
		password = (EditText)findViewById(R.id.editText2);		
	}
	
	public void login(View v){
		
		if( checknetforref() == 1 ){
		
		 //get the details filled by user
    	username_s = username.getText().toString();

    	password_s = password.getText().toString();
		
		if (username_s.matches("")) {
  		 
		        Toast.makeText(getApplicationContext(), "enter username", Toast.LENGTH_SHORT).show();
  	}
  	else if(password_s.matches("")){
      	

        Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
				          
      }
	else{
		
		new UpdateInfoAsyncTask(username_s, password_s).execute();
		
	}
		}else{
			
			Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_SHORT).show();
		}
		
	}	
		
		
		//asytask to update details of user
		public class UpdateInfoAsyncTask extends AsyncTask<Void, Void, Void>{
	    	 
			  String username_s1;
	    	  String password_s1;
	    	 
	    	  
	    	  char check_char;
	    	  int done;
	    	  
	    	 
	    	  
	          ProgressDialog dialog = new ProgressDialog(Login.this);
	    	   
	    	  public UpdateInfoAsyncTask(String name_s1,String password_s1){
	    	      
	    		  this.username_s1 = name_s1;
	    	      this.password_s1 = password_s1;
	    	      
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
	    			    	 
	    			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/ADMINLOGINAU.php"); //YOUR PHP SCRIPT ADDRESS
	    			    	 
	    			    	
	    			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    			    	
	    			  nameValuePairs.add(new BasicNameValuePair("UserName", username_s));
	    			  nameValuePairs.add(new BasicNameValuePair("Password", password_s));
	    			 
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
	    		 
	    		  dialog.dismiss();
	    		  
	    		  if(done == 1){
			    		
	    			   //open or create database
	    		     db = openOrCreateDatabase("track", Context.MODE_PRIVATE, null);
			    		// save  in database
			    		
			    	Cursor c = db.rawQuery("SELECT * FROM user WHERE id='1'", null);
			    	if(c.moveToFirst())
			    		{
			    			db.execSQL("UPDATE user SET username='"+ username_s1 +"' WHERE id='1'");
			    			//Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
			    		}
			    		else
			    		{
			    		//Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
			    		}
			    		
			    		
	            
			    		
			    	
			    	//	Toast.makeText(getApplicationContext(), " My constituency is " + c.getString(3), Toast.LENGTH_SHORT).show();
		        	
			    		//calling intent ( activity1 )
		        	
			    	Intent	intent1 = new Intent(Login.this, MainActivity.class);
		            			    	
		            startActivity(intent1);
		             
			   	    finish();
	    		 
	    		  
	    	  }
	    	  
	    	  }
	    	  
	    	  
	    	  
	    
		}
		
		  
    	//check net connection
    		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
