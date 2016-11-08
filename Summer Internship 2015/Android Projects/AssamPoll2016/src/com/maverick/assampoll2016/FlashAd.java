package com.maverick.assampoll2016;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class FlashAd extends ActionBarActivity {
	SQLiteDatabase db;
	String adlink_s ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);	
		StrictMode.enableDefaults();
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_flash_ad);
		
		//open or create database
	      db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
	   /*  db.execSQL("CREATE TABLE IF NOT EXISTS ad(id VARCHAR, link VARCHAR);");
	   db.execSQL("INSERT INTO ad VALUES('"+ 1 +"' , 'http://www.spmaverick.com' );"); //initial link

*/	      
	      
		  if(isNetworkAvailable()){
              /** Creating a new non-ui thread task */
              DownloadTask downloadTask = new DownloadTask();

              /** Starting the task created above */
          downloadTask.execute("http://www.spmaverick.com/AssamPoll/AD/ad.png");
          
		  
		  }else{
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
		        toast.show();          }
		
		
	}
	



	 
	 //get data
	 public class GetDataad extends AsyncTask <Void, Void, Void>{
	   	 
		 String result = "";
	     InputStream isr = null;
	      	
		 
	 //  	ProgressDialog dialog = new ProgressDialog(FlashAd.this);

	   	  @Override
	   	  protected void onPreExecute() {
	   		  
	 //       dialog.setMessage("Please wait..."); 
	 //  		dialog.setCanceledOnTouchOutside(false);
	//		dialog.show();

	   	  }

	   	  @Override
	   	  protected Void doInBackground(Void... params) {
	   		  
	   		try{
	         	 
	          	HttpClient httpclient = new DefaultHttpClient();
	          	HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/AD/AD.php"); //YOUR PHP SCRIPT ADDRESS
	          	
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
	          	
	          	}
	   		return null;
	}
	   	  protected void onPostExecute(Void unused) {
	   		 
	   		//  dialog.dismiss();
	  		 
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
	      
	     // Toast.makeText(getApplicationContext(), " " + result, Toast.LENGTH_LONG).show();
	      
	     
	          try {
	          	JSONArray jArray = new JSONArray(result);
	   
	                 	
	          	for( int i = 0 ; i < jArray.length( ); i++ ){
	          	 
	          		JSONObject json = jArray.getJSONObject(i);
	          	 
	          		if(json.getString("LINK_ID").equals("1")){
	          		  
	          		  adlink_s = json.getString("LINK");
	          	    }
	         //Toast.makeText(getApplicationContext(), "faw  "+adlink_s, Toast.LENGTH_SHORT).show();  
	         
	        // db.execSQL("INSERT INTO ad VALUES('"+1+"' , 'http://" + adlink_s +"');"); //initial link
	         
	         db.execSQL("UPDATE ad SET link='http://"+ adlink_s +"' WHERE id='"+ 1 +"'");
	        
	         
	       
	         Intent in = new Intent(FlashAd.this, HomePage.class);
        	 startActivity(in);
        	 finish();
        	 
        	
        	 
        	 
        	 //setflip();
	          	 }
	          	
	           
	          	} catch (Exception e) {
	         	    	
	         	 }   
	 //   Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();    
	   	  
	   	  }


	 }
    private boolean storeImage(Bitmap imageData, String filename) {
    	//get path to external storage (SD card)
    	String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/";
    	File sdIconStorageDir = new File(iconsStoragePath);

    	//create storage directories, if they don't exist
    	sdIconStorageDir.mkdirs();

    	try {
    		String filePath = sdIconStorageDir.toString() + filename;
    		FileOutputStream fileOutputStream = new FileOutputStream(filePath);

    		BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

    		//choose another format if PNG doesn't suit you
    		imageData.compress(CompressFormat.PNG, 100, bos);

    		bos.flush();
    		bos.close();

    	} catch (FileNotFoundException e) {
    		Toast.makeText(getApplicationContext(), "ERROR 3", Toast.LENGTH_SHORT).show();
    		Log.w("TAG", "Error saving image file: " + e.getMessage());
    		return false;
    	} catch (IOException e) {
    		Toast.makeText(getApplicationContext(), "ERROR 4", Toast.LENGTH_SHORT).show();
    		Log.w("TAG", "Error saving image file: " + e.getMessage());
    		return false;
    	}

    	return true;
    }
    
  
    
    
    private boolean isNetworkAvailable(){
        boolean available = false;
        /** Getting the system's connectivity service */
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
 
        /** Getting active network interface  to get the network's status */
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
 
        if(networkInfo !=null && networkInfo.isAvailable())
            available = true;
 
        /** Returning the status of the network */
        return available;
    }
 
    private Bitmap downloadUrl(String strUrl) throws IOException{
        Bitmap bitmap=null;
        InputStream iStream = null;
        try{
            URL url = new URL(strUrl);
            /** Creating an http connection to communcate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 
            /** Connecting to url */
            urlConnection.connect();
 
            /** Reading data from url */
            iStream = urlConnection.getInputStream();
 
            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
        }
        return bitmap;
    }
 
    private class DownloadTask extends AsyncTask<String, Integer, Bitmap>{
        Bitmap bitmap = null;
      /* // ProgressDialog  dialog = new ProgressDialog(FlashAd.this);

	   	  @Override
	   	  protected void onPreExecute() {
	   		  
	   	dialog.setMessage("Please wait..."); 
	   		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	   	  }*/        
        @Override
        protected Bitmap doInBackground(String... url) {
            try{
                bitmap = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return bitmap;
        }
 
        @Override
        protected void onPostExecute(Bitmap result) {
            /** Getting a reference to ImageView to display the
            * downloaded image
            */
         //  dialog.dismiss();
            if(storeImage(result,"ad.png")){
            	// Toast.makeText(getBaseContext(), "saved sucessfully", Toast.LENGTH_SHORT).show();
    
            	   new GetDataad().execute();
            	   
            	 
            	 
            }
            /** Showing a message, on completion of download process */
           // Toast.makeText(getBaseContext(), "Image downloaded successfully", Toast.LENGTH_SHORT).show();
            
         
        }
    }
    
	
		

}
