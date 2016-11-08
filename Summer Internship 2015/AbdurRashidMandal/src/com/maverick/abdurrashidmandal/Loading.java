package com.maverick.abdurrashidmandal;

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


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.maverick.abdurrashidmandal.R;

public class Loading extends Activity {

	
	TextView oneN, twoN, threeN, fourN, fiveN,sixN,sevenN,eightN,nineN,tenN;
    TextView oneT, twoT, threeT, fourT, fiveT,sixT,sevenT,eightT,nineT,tenT;
	    
    TextView oneF, twoF, threeF, fourF, fiveF,sixF,sevenF,eightF,nineF,tenF;
    TextView oneR, twoR, threeR, fourR, fiveR,sixR,sevenR,eightR,nineR,tenR;
	    
	    
	    String Tweet[]=new String[10];
		 String Time[]=new String[10];
		 String Fav[]=new String[10];
		 String Re[]=new String[10];
		 
		 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		

        ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
		
		
		 StrictMode.enableDefaults();
		
		oneN =(TextView)findViewById(R.id.n1);
		oneT =(TextView)findViewById(R.id.t1);
		
		
		twoN =(TextView)findViewById(R.id.n2);
		twoT =(TextView)findViewById(R.id.t2);
		
		
		threeN =(TextView)findViewById(R.id.n3);
		threeT =(TextView)findViewById(R.id.t3);
		
		
		fourN =(TextView)findViewById(R.id.n4);
		fourT =(TextView)findViewById(R.id.t4);
		
		
		fiveN =(TextView)findViewById(R.id.n5);
		fiveT =(TextView)findViewById(R.id.t5);
		
		
		sixN =(TextView)findViewById(R.id.n6);
		sixT =(TextView)findViewById(R.id.t6);
		
		
		sevenN =(TextView)findViewById(R.id.n7);
		sevenT =(TextView)findViewById(R.id.t7);
		
		
		eightN =(TextView)findViewById(R.id.n8);
		eightT =(TextView)findViewById(R.id.t8);
		
		
		nineN =(TextView)findViewById(R.id.n9);
		nineT =(TextView)findViewById(R.id.t9);
		
		
		tenN =(TextView)findViewById(R.id.n10);
		tenT =(TextView)findViewById(R.id.t10);
		
		oneR =(TextView)findViewById(R.id.re1);
		oneF =(TextView)findViewById(R.id.fav1);
		
		
		twoR =(TextView)findViewById(R.id.re2);
		twoF =(TextView)findViewById(R.id.fav2);
		
		
		threeR =(TextView)findViewById(R.id.re3);
		threeF =(TextView)findViewById(R.id.fav3);
		
		
		fourR =(TextView)findViewById(R.id.re4);
		fourF =(TextView)findViewById(R.id.fav4);
		
		
		fiveR =(TextView)findViewById(R.id.re5);
		fiveF =(TextView)findViewById(R.id.fav5);
		
		
		sixR =(TextView)findViewById(R.id.re6);
		sixF =(TextView)findViewById(R.id.fav6);
		
		
		sevenR =(TextView)findViewById(R.id.re7);
		sevenF =(TextView)findViewById(R.id.fav7);
		
		
		eightR =(TextView)findViewById(R.id.re8);
		eightF =(TextView)findViewById(R.id.fav8);
		
		
		nineR =(TextView)findViewById(R.id.re9);
		nineF =(TextView)findViewById(R.id.fav9);
		
		
		tenR =(TextView)findViewById(R.id.re10);
		tenF =(TextView)findViewById(R.id.fav10);
		
		
		
		
		new GetDataad().execute();
		
		
		
		
	}
	
 public class GetDataad extends AsyncTask <Void, Void, Void>{
	   	 
	
		 String result = "";
	     InputStream isr = null;
	      	
		 
	   	ProgressDialog dialog = new ProgressDialog(Loading.this);

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
	          	HttpPost httppost = new HttpPost("http://www.spmaverick.com/MLAAPP/twitter/test2.php"); //YOUR PHP SCRIPT ADDRESS
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
	          	 
	          	 //Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	          	}catch(Exception e){
	          		 Toast.makeText(getApplicationContext(), "error one", Toast.LENGTH_SHORT).show();
	          	}
	   		// Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	  
	      
	      
	          try {
	          	JSONArray jArray = new JSONArray(result);
	   
	                 	
	         // 	Toast.makeText(getApplicationContext(), ""+jArray.length( ), Toast.LENGTH_SHORT).show();
	          	for( int i = 0 ; i < 10; i++ ){
	          	 
	          JSONObject json = jArray.getJSONObject(i);
	           Tweet[i] = json.getString("Tweet");
	           Time[i] = json.getString("Time");
	           Re[i] = json.getString("Retweet");
	           Fav[i] = json.getString("Favorite");
	           
	           
	     //      Toast.makeText(getApplicationContext(), ""+Tweet[i]+""+Time[i]+""+Re[i]+""+Fav[i], Toast.LENGTH_SHORT).show();
	           
	           
	           
	          	 }
	          	
	          	oneN.setText(Tweet[0]);
	          	twoN.setText(Tweet[1]);
	          	threeN.setText(Tweet[2]);
	          	fourN.setText(Tweet[3]);
	          	fiveN.setText(Tweet[4]);
	          	sixN.setText(Tweet[5]);
	          	sevenN.setText(Tweet[6]);
	          	eightN.setText(Tweet[7]);
	          	nineN.setText(Tweet[8]);
	          	tenN.setText(Tweet[9]);
	          	
	        	oneT.setText(Time[0]);
	          	twoT.setText(Time[1]);
	          	threeT.setText(Time[2]);
	          	fourT.setText(Time[3]);
	          	fiveT.setText(Time[4]);
	          	sixT.setText(Time[5]);
	          	sevenT.setText(Time[6]);
	          	eightT.setText(Time[7]);
	          	nineT.setText(Time[8]);
	          	tenT.setText(Time[9]);
	          	
	          	
	          	oneR.setText(Re[0]);
	          	twoR.setText(Re[1]);
	          	threeR.setText(Re[2]);
	          	fourR.setText(Re[3]);
	          	fiveR.setText(Re[4]);
	          	sixR.setText(Re[5]);
	          	sevenR.setText(Re[6]);
	          	eightR.setText(Re[7]);
	          	nineR.setText(Re[8]);
	          	tenR.setText(Re[9]);
	          	
	          	
	          	oneF.setText(Fav[0]);
	          	twoF.setText(Fav[1]);
	          	threeF.setText(Fav[2]);
	          	fourF.setText(Fav[3]);
	          	fiveF.setText(Fav[4]);
	          	sixF.setText(Fav[5]);
	          	sevenF.setText(Fav[6]);
	          	eightF.setText(Fav[7]);
	          	nineF.setText(Fav[8]);
	          	tenF.setText(Fav[9]);
	          	
	          	
	          	
	          	
	          	
	           
	          	} catch (Exception e) {
	         	    	
	         	 }   	  
	   	  }


	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
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
