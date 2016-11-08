package com.maverick.assampoll2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Survey extends ActionBarActivity {
  
	RadioGroup options1, options2, options3, options4, options5;
    RadioButton opt11, opt12, opt13, opt21, opt22, opt23, opt31, opt32, opt33, opt41, opt42, opt43, opt51, opt52, opt53 ;
    TextView question1, question2, question3, question4, question5;

    
    String q1o1, q1o2, q1o3, q2o1, q2o2, q2o3 ,q3o1, q3o2, q3o3, q4o1, q4o2, q4o3, q5o1, q5o2, q5o3;
    String res1, res2, res3, res4, res5;
    String q1, q2, q3, q4, q5;
    
   
    int check_selected_option1, check_selected_option2, check_selected_option3, check_selected_option4, check_selected_option5 ;
    int q_no = 1;
    String selected_option_is = "0";
    
    SQLiteDatabase db;
    String get_phone_number_s;
    
    Button submit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_survey);
		getActionBar().setDisplayShowHomeEnabled(false);
		// If your minSdkVersion is 11 or higher use:
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//color to action bar
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009966")));


		StrictMode.enableDefaults();
		
		question1 = (TextView)findViewById(R.id.question1);
		question2 = (TextView)findViewById(R.id.question2);
		question3 = (TextView)findViewById(R.id.question3);
		question4 = (TextView)findViewById(R.id.question4);
		question5 = (TextView)findViewById(R.id.question5);
		
		options1 = (RadioGroup) findViewById(R.id.radioGroup1);                   //findViewById
		options2 = (RadioGroup) findViewById(R.id.radioGroup2);
		options3 = (RadioGroup) findViewById(R.id.radioGroup3);
		options4 = (RadioGroup) findViewById(R.id.radioGroup4);
		options5 = (RadioGroup) findViewById(R.id.radioGroup5);
		
		submit = (Button) findViewById(R.id.submit);  
		 
        opt11 = (RadioButton) findViewById(R.id.option11);                          //findViewById
        opt12 = (RadioButton) findViewById(R.id.option12);
        opt13 = (RadioButton) findViewById(R.id.option13);                        //findViewById
        
        
        opt21 = (RadioButton) findViewById(R.id.option21);                          //findViewById
        opt22 = (RadioButton) findViewById(R.id.option22);
        opt23 = (RadioButton) findViewById(R.id.option23);     
        
        opt31 = (RadioButton) findViewById(R.id.option31);                          //findViewById
        opt32 = (RadioButton) findViewById(R.id.option32);
        opt33 = (RadioButton) findViewById(R.id.option33);     
        
        opt41 = (RadioButton) findViewById(R.id.option41);                          //findViewById
        opt42 = (RadioButton) findViewById(R.id.option42);
        opt43 = (RadioButton) findViewById(R.id.option43);     
        
        opt51 = (RadioButton) findViewById(R.id.option51);                          //findViewById
        opt52 = (RadioButton) findViewById(R.id.option52);
        opt53 = (RadioButton) findViewById(R.id.option53);     
        
        
        //get phone number from databasse
        
      //open or create database
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        
        Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+ 1 +"'", null);
		if(c.getCount() == 0)
		{
		   return;
		}
		while( c.moveToNext() ){
			//Toast.makeText(getApplicationContext(), "Phone No"+c.getString(1) , Toast.LENGTH_SHORT).show();
		   get_phone_number_s = c.getString(1);
		}
	//	Toast.makeText(getApplicationContext(), "" + get_phone_number_s, Toast.LENGTH_SHORT).show();
		//get phone number from databasse END

		//survey set questions if not submitted
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent==false){
			
			 AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Survey.this);
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
  		
  		
	new survey_set(get_phone_number_s).execute();
		}
		}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	   
	    	 
			 Intent parentIntent = new Intent(this,HomePage.class);
		      startActivity(parentIntent);
		      finish();
	    	 
	    	 return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
	
	//setting question and options
	public void surveyqna(String ques, String option1, String option2, String option3){
		
		   question1.setText(ques);
		   
		   opt11.setText(option1);
		   opt12.setText(option2);
		   opt13.setText(option3);
		  
	}//setting question and options END

	
	
	// check selected option of the question
	
	public void submit(View v){
		
		
		check_selected_option1 = options1.getCheckedRadioButtonId();
		check_selected_option2 = options2.getCheckedRadioButtonId();
		check_selected_option3 = options3.getCheckedRadioButtonId();
		check_selected_option4 = options4.getCheckedRadioButtonId();
		check_selected_option5 = options5.getCheckedRadioButtonId();
		
	       //radioButton = (RadioButton) findViewById(selectedId);

		
		  
		   if( check_selected_option1 == R.id.option11 )
			   res1 = "1";
		   else if(check_selected_option1 == R.id.option12)
			   res1 = "2";
		   else if(check_selected_option1 == R.id.option13)
			   res1 = "3";
		  
		
     	
		 
			  
			  if( check_selected_option2 == R.id.option21 )
				   res2 = "1";
			   else if(check_selected_option2 == R.id.option22)
				   res2 = "2";
			   else if(check_selected_option2 == R.id.option23)
				   res2 = "3";
			
		
        	 if( check_selected_option3 == R.id.option31 )
  			   res3 = "1";
  		   else if(check_selected_option3 == R.id.option32)
  			   res3 = "2";
  		   else if(check_selected_option3 == R.id.option33)
  			   res3 = "3";
			  
      
			  
        	 if( check_selected_option4 == R.id.option41 )
  			   res4 = "1";
  		   else if(check_selected_option4 == R.id.option42)
  			   res4 = "2";
  		   else if(check_selected_option4 == R.id.option43)
  			   res4 = "3";
			  
        
        	 if( check_selected_option5 == R.id.option51 )
  			   res5 = "1";
  		   else if( check_selected_option5 == R.id.option52 )
  			   res5 = "2";
  		   else if(check_selected_option5 == R.id.option53)
  			   res5 = "3";
			  
        	 
        	 new sendOptions(get_phone_number_s, res1, res2, res3, res4, res5).execute();
        	// Toast.makeText(getApplicationContext(), ""+res1+""+res2+""+res3+""+res4+""+res5, Toast.LENGTH_LONG).show();
        	 
          
		  
		 //  Toast.makeText(getApplicationContext(),selected_option_is, Toast.LENGTH_SHORT).show();
		
	}// check selected option of the question END
	

	

	
	
	//async task for checking survey already submitted
	 public class survey_set extends AsyncTask<Void, Void, Void>{
		                                                                  //set survey Async Task
		 String result = "";
	     InputStream isr = null;                                        //set survey Async Task
	      	
   	  
	     String phone_number_s1;
   	   
   	  
	     char check_char;
   	  
	     String check_response_submission_s;
                                                                         //set surveyAsync Task
   	  
     	ProgressDialog dialog = new ProgressDialog(Survey.this);           //set survey Async Task

   	    public survey_set(String phone_number_s1){
   	      this.phone_number_s1 = phone_number_s1;
   	                                                                   //set survey Async Task
   	  }

   	    @Override                                                         //set survey Async Task
   	    protected void onPreExecute() {
   	      dialog.setMessage("Please wait...");
   		  dialog.setCanceledOnTouchOutside(false);
		  dialog.show();                                             //set survey Async Task

   	  }

   	    @Override
   	    protected Void doInBackground(Void... params) {                     //set survey
   		   //-------------------
		    
			    	
			try{
			    	 
			   HttpClient httpclient = new DefaultHttpClient();                    //set survey
			    	 
			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/SURVEY.php"); //set survey
			    	 
			    	 
			    	
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			    	
			  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s1));       //set survey Async Task
			  
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    	
			//  ResponseHandler<String> responseHandler = new BasicResponseHandler();       //set survey Async Task
			//	 String response = httpclient.execute(httppost, responseHandler);
			//	  check_char = response.charAt(0);
			//	  check_response_submission = Character.getNumericValue(check_char);
				 
			                                                                        //set survey Async Task
			  //for response of questions
			 HttpResponse response = httpclient.execute(httppost);
			    	 
			 HttpEntity entity = response.getEntity();
			    	 
			isr = entity.getContent();                                      //set survey Async Task
			   	
			}catch(Exception e){
				                                                            //set survey Async Task
			}  	
		return null;
}//end background
   	    
   	  protected void onPostExecute(Void unused) {                               //set survey Async Task
   		//new Get_Question().execute();
   		   		
   		dialog.dismiss();
  		 //-------------
   		//JSON take questions options check _sbmitted
   		
   	 
  		 try{
	          	
         	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
         	 StringBuilder sb = new StringBuilder();
         	 String line = null;
         	 
         	 while ((line = reader.readLine()) != null) {
         	 
         		 sb.append(line + "\n");
         	}
         	 isr.close();
         	 result = sb.toString();
         	// Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_SHORT).show();
         	}catch(Exception e){
         		
         		Toast.makeText(getApplicationContext(), "getting result error 1", Toast.LENGTH_SHORT).show();
         	}
     
         // Toast.makeText(getApplicationContext(), " " + result, Toast.LENGTH_LONG).show();
     
     
         try {
        	// Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_SHORT).show();
         	JSONArray jArray = new JSONArray(result);
  
         	
         	for( int i = 0 ; i < jArray.length( ); i++ ){
         	 
         		JSONObject json = jArray.getJSONObject(i);
         		
         	 if(json.getString("ID").equals("1")){
         		 
         		 check_response_submission_s = json.getString("DONE");
         	
         		   int check_submission = Integer.parseInt(check_response_submission_s);
         		   
         		// Toast.makeText(getApplicationContext(), ""+check_submission, Toast.LENGTH_SHORT).show();
         		
         		 if(check_submission == 0){
            		
         		//	 Toast.makeText(getApplicationContext(), " You have not submitted respsonse yet " + check_response_submission_s, Toast.LENGTH_SHORT).show();

            	 q1 = json.getString("QUESTION1");
         		q1o1 = json.getString("OPTION11");
         		q1o2 = json.getString("OPTION12");
         		q1o3 = json.getString("OPTION13");
         	   
         			 q2 = json.getString("QUESTION2");
         			q2o1 = json.getString("OPTION21");
	          		q2o2 = json.getString("OPTION22");
	          		q2o3 = json.getString("OPTION23");
         	
         			 q3 = json.getString("QUESTION3");
         			q3o1 = json.getString("OPTION31");
	          		q3o2 = json.getString("OPTION32");
	          		q3o3 = json.getString("OPTION33");
         			
         	
         			 q4 = json.getString("QUESTION4");
         			q4o1 = json.getString("OPTION41");
	          		q4o2 = json.getString("OPTION42");
	          		q4o3 = json.getString("OPTION43");
         			
         		
         			 q5 = json.getString("QUESTION5");
         			q5o1 = json.getString("OPTION51");
	          		q5o2 = json.getString("OPTION52");
	          		q5o3 = json.getString("OPTION53");
         			
         		
         	   
         	question1.setText("" + q1);
         	opt11.setText("" + q1o1);
         	opt12.setText("" + q1o2);
         	opt13.setText("" + q1o3);
         	
         	question2.setText("" + q2);
         	opt21.setText("" + q2o1);
         	opt22.setText("" + q2o2);
         	opt23.setText("" + q2o3);
         	
         	question3.setText("" + q3);
         	opt31.setText("" + q3o1);
         	opt32.setText("" + q3o2);
         	opt33.setText("" + q3o3);
         	
         	question4.setText("" + q4);
         	opt41.setText("" + q4o1);
         	opt42.setText("" + q4o2);
         	opt43.setText("" + q4o3);
         	
         	question5.setText("" + q5);
         	opt51.setText("" + q5o1);
         	opt52.setText("" + q5o2);
         	opt53.setText("" + q5o3);
         	
         	
         		
         		//Toast.makeText(getApplicationContext(),"Question 1 :"+q1+"options are :"+ q1o1+" ,"+q1o2+","+q1o3, Toast.LENGTH_SHORT).show();
         		
         		 }	
         		 else if(check_submission ==1){

      			 
       			AlertDialog.Builder builder = new AlertDialog.Builder(Survey.this);
       			builder.setCancelable(false);
             	builder.setTitle("Sorry!");
     			builder.setMessage(" You already have submitted the response. Wait till our next survey starts.");
     			builder.setPositiveButton("OK",
     			
     					new DialogInterface.OnClickListener() {
     			
     				public void onClick(DialogInterface dialog,int which) {
     			
     					Intent intent = new Intent(getApplicationContext(), HomePage.class);
     					 startActivity(intent);
     					 finish();
     			}
     			});
     			
     			builder.show();
         	 }//end else  
         	} //end if id ==1
         	
                
         }
       
        } catch (Exception e) {
         		Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_SHORT).show();
        	 }   
   // Toast.makeText(getApplicationContext(), "CON:"+congress_s+"BJP:"+bjp_s+"APP:"+aap_s, Toast.LENGTH_SHORT).show();    
  	  
  	  
   		
  		
                                                               //Set survey Async Task
   	}//end postexecute
}  //set survey Async Task END
          
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
		//async task for sending options
	 public class sendOptions extends AsyncTask<Void, Void, Void>{
		                                                                  //send options Async Task
		 String result = "";
	     InputStream isr = null;                                        //send options Async Task
	      	
	     String phone_number_s1, res1_s1, res2_s1, res3_s1, res4_s1, res5_s1;;
	   	 char check_char;
	   	 int check_submitted;
                                                                         //send options Async Task
   	  
     	ProgressDialog dialog = new ProgressDialog(Survey.this);           //send options Async Task
     	
     	public sendOptions(String phone_number_s1, String res1_s1, String res2_s1, String res3_s1, String res4_s1, String res5_s1){
      	    
     		this.phone_number_s1 = phone_number_s1;
      	    this.res1_s1 = res1_s1;
      	    this.res2_s1 = res2_s1;
         	this.res3_s1 = res3_s1;
        	this.res4_s1 = res4_s1;
      	    this.res5_s1 = res5_s1;
      	                                                                  
      	  }
   	 
   	    @Override                                                         //send options Async Task
   	    protected void onPreExecute() {
   	      dialog.setMessage("Please wait...");
   		  dialog.setCanceledOnTouchOutside(false);
		  dialog.show();                                             //send options Async Task

   	  }

   	    @Override
   	    protected Void doInBackground(Void... params) {                     //send options Async Task
   		   //-------------------
		    
			    	
			try{
			    	 
			   HttpClient httpclient = new DefaultHttpClient();                    //send optiions Async Task
			    	 
			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/SET_RESULTS.php"); //YOUR PHP SCRIPT ADDRESS
			    	 
			   // HttpPost httppost = new HttpPost("http://172.23.193.32/elift-test/myfile.php"); //YOUR PHP SCRIPT ADDRESS
			    	 
			    	
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			   
			  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s1));
			  nameValuePairs.add(new BasicNameValuePair("Answer1", res1_s1));
			  nameValuePairs.add(new BasicNameValuePair("Answer2", res2_s1));
			  nameValuePairs.add(new BasicNameValuePair("Answer3", res3_s1));
			  nameValuePairs.add(new BasicNameValuePair("Answer4", res4_s1));
			  nameValuePairs.add(new BasicNameValuePair("Answer5", res5_s1));  //send options Async Task
			  
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
    	
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();       //send options Async Task
			 String response = httpclient.execute(httppost, responseHandler);
			  check_char = response.charAt(0);
			  check_submitted = Character.getNumericValue(check_char);
			 
			  //HttpResponse response = httpclient.execute(httppost);
			                                                                        //send options Async Task
			  //  HttpEntity entity = response.getEntity();
			    	 
			  // isr = entity.getContent();
			                                                                      //send options Async Task
			}catch(Exception e){
			   
			}  	
		return null;                                                   //send options Async Task

}
   	  protected void onPostExecute(Void unused) {
   		
   	   		
   		dialog.dismiss();
  		 
  		 if(check_submitted == 0){
  			 
  			//Toast.makeText(getApplicationContext(), "Failed to submit response." + check_submitted +"  " + res1_s1 +" "+ res2_s1, Toast.LENGTH_SHORT).show();
  			  
             LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Failed to submit response!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	      	  
  			Intent i = new Intent(Survey.this, HomePage.class);
   			startActivity(i);
   			finish();
  		 }
  		 else{
   		//	Toast.makeText(getApplicationContext(), "Submitted successfully." + check_submitted +"  " + res1_s1 +" "+ res2_s1, Toast.LENGTH_SHORT).show();
  			  
             LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Submitted successfully!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();	
		        
   			Intent i = new Intent(Survey.this, HomePage.class);
   			startActivity(i);
   			finish();
  		 }
   	  }
   	  //Toast.makeText(getApplicationContext(), ""+check_present_vote, Toast.LENGTH_SHORT).show();
 }  //send options Async Task END
	 
	
	
	 
	 
	 /*
	 //for geeting question 
     //get data
	 public class Get_Question extends AsyncTask<Void, Void, Void>{
	   	 
		 String result = "";
	     InputStream isr = null;
	      	
		 
	   	ProgressDialog dialog = new ProgressDialog(Survey.this);

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
	          	HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/GET_QUESTION_SET.php"); //YOUR PHP SCRIPT ADDRESS
	          	
	          	//HttpPost httppost = new HttpPost("http://127.0.0.1/localhost/checkpro/parties.php");
	          	        	
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
	          	// Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_SHORT).show();
	          	}catch(Exception e){
	          		Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
	          	}
	      
	     // Toast.makeText(getApplicationContext(), " " + result, Toast.LENGTH_LONG).show();
	      
	      
	          try {
	          	JSONArray jArray = new JSONArray(result);
	   
	          	
	          	for( int i = 0 ; i < jArray.length( ); i++ ){
	          	 
	          		JSONObject json = jArray.getJSONObject(i);
	          	 
	          		if(json.getString("QUESTION").equals("Q1")){
	          		 q1=json.getString("GET_QUEST");
	          		q1o1 = json.getString("OPTION_1");
	          		q1o2 = json.getString("OPTION_2");
	          		q1o3 = json.getString("OPTION_3");
	          	    }
	          		else if(json.getString("QUESTION").equals("Q2")){
	          			 q2=json.getString("GET_QUEST");
	          			q2o1 = json.getString("OPTION_1");
		          		q2o2 = json.getString("OPTION_2");
		          		q2o3 = json.getString("OPTION_3");
	          			
	          		}
	          		else if(json.getString("QUESTION").equals("Q3")){
	          			 q3=json.getString("GET_QUEST");
	          			q3o1 = json.getString("OPTION_1");
		          		q3o2 = json.getString("OPTION_2");
		          		q3o3 = json.getString("OPTION_3");
	          			
	          		}
	          		else if(json.getString("QUESTION").equals("Q4")){
	          			 q4=json.getString("GET_QUEST");
	          			q4o1 = json.getString("OPTION_1");
		          		q4o2 = json.getString("OPTION_2");
		          		q4o3 = json.getString("OPTION_3");
	          			
	          		}
	          		else if(json.getString("QUESTION").equals("Q5")){
	          			 q5=json.getString("GET_QUEST");
	          			q5o1 = json.getString("OPTION_1");
		          		q5o2 = json.getString("OPTION_2");
		          		q5o3 = json.getString("OPTION_3");
	          			
	          		}
	          	   
	            	    
	          		question.setText(""+q1);
	          		opt1.setText(""+q1o1);
	          		opt2.setText(""+q1o2);
	          		opt3.setText(""+q1o3);
	          		question_no.setText("1/5");
	          		//Toast.makeText(getApplicationContext(),"Question 1 :"+q1+"options are :"+ q1o1+" ,"+q1o2+","+q1o3, Toast.LENGTH_SHORT).show();
	                 
	               
	          	 }
	          	
	           
	          	} catch (Exception e) {
	          		Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_SHORT).show();
	         	 }   
	    // Toast.makeText(getApplicationContext(), "CON:"+congress_s+"BJP:"+bjp_s+"APP:"+aap_s, Toast.LENGTH_SHORT).show();    
	   	  
	   	  }
	   	  
	 }
	
	 */
	 
	 

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.survey, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			 
			 Intent parentIntent = new Intent(this,HomePage.class);
		      startActivity(parentIntent);
		      finish();
		 }
		return super.onOptionsItemSelected(item);
	}
}
