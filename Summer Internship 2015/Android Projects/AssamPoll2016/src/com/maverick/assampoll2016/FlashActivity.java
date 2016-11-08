package com.maverick.assampoll2016;


import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class FlashActivity extends Activity {

	//database
	SQLiteDatabase db, db1, db2, dbweekcheck;
	
	
	//GCM

	GoogleCloudMessaging gcm;
	Context context;
	String regId;
	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";
	static final String TAG = "Register Activity";
	
	
	//net connection
	Boolean isInternetPresent;
	ConnectionDetector cd;
	 
	
	int check_if_phone_not_registered = 0;
	
	int week_n;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	
    	// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_flash);

		StrictMode.enableDefaults();
		
        context = getApplicationContext();
        
        
        
if( checknetforref() == 1 ){
        
      //open or create database
      db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
      		
      db1 = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
    
      db2 = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
      
      dbweekcheck = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
  
      
      //Toast.makeText(getApplicationContext(), ""+weekdata_s, Toast.LENGTH_LONG).show();
	      
     
      
      //check if app running first time 
     if(!isFirstTime()){
    	
    	registerGCM();
     }
     else{
    	 

    //checking if signed in and then checking if already voted
   	Cursor c = db.rawQuery("SELECT * FROM student", null);
      if(c.getCount() == 0)
   		{
   			return;
   		}
   	  while(c.moveToNext())
   		{
   			//check if new user to register
   			if( (c.getString(2).equals("0")) ){
   			
   				check_if_phone_not_registered = 1;
   			    	
   			}
   		}
   		
   		
     // go to another activity after checking
   	///-------------------
   	if(check_if_phone_not_registered == 1){
   	    
   		new CountDownTimer(3000, 1000) {
             
   			public void onTick(long millisUntilFinished) { }

     	    public void onFinish() {
     	    	
     		    Intent intent = new Intent(FlashActivity.this, SignUp.class);
     		    startActivity(intent);
     		    finish();
     		    	
     		    	//Toast.makeText(getApplicationContext(), "Click On resend to get another one", Toast.LENGTH_SHORT).show();
     		  }
      }.start();
   			  
   			  
   	}//end if
    else if(check_if_phone_not_registered == 0){
   		
   		  Intent intent = new Intent(this, FlashAd.class);
          startActivity(intent);
       	  finish();
   		
   	}//end else if
   	   
  //----------------------------------
  }//end else
}
else{
        	
     AlertDialog.Builder alertDialog = new  AlertDialog.Builder(FlashActivity.this);
     alertDialog.setTitle("Error");
     alertDialog.setMessage("Connect to Internet");     
     alertDialog.setCancelable(false);
      	                
     alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      	                      
      public void onClick(DialogInterface dialog, int which) {
      	            	
     	 finish();
     	         	  
      	} }); 

     alertDialog.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
           
         public void onClick(DialogInterface dialog, int which) {
         	            	
        	  startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
         	  //Toast.makeText(getApplicationContext(), "ok ok", Toast.LENGTH_SHORT).show();
         	  finish();
        	         	  
         	} }); 
      alertDialog.show();
     
 }
    
} //oncreate
     

    
//function to check if the app is run first time to create database
private boolean isFirstTime() {
    	 
    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    boolean ranBefore = preferences.getBoolean("RanBefore", false);
         
    if (!ranBefore) {
       	 
       SharedPreferences.Editor editor = preferences.edit();
       editor.putBoolean("RanBefore", true);
       editor.commit(); 
           
       db.execSQL("CREATE TABLE IF NOT EXISTS student(id VARCHAR, phone VARCHAR, phoneverification VARCHAR, constituency VARCHAR);");
       db.execSQL("INSERT INTO student VALUES('"+ 1 +"' , '0' , '0', '0');"); //no initial phone no and not registered
         
       	
       db1.execSQL("CREATE TABLE IF NOT EXISTS ad(id VARCHAR, link VARCHAR);");
       db1.execSQL("INSERT INTO ad VALUES('"+ 1 +"' , 'http://www.spmaverick.com' );"); //initial link
       	
        
       db2.execSQL("CREATE TABLE IF NOT EXISTS del(id VARCHAR, delcount VARCHAR);");
       db2.execSQL("INSERT INTO del VALUES('"+ 1 +"' , '"+ 0 +"' );"); //initial 

        
      dbweekcheck.execSQL("CREATE TABLE IF NOT EXISTS weekcheck(id VARCHAR, week VARCHAR);");
      dbweekcheck.execSQL("INSERT INTO weekcheck VALUES('"+ 1 +"' , '"+ 10 +"' );"); //initial 

      }
         
             
   	  return ranBefore;
       
}
    
    
    //for gcm
    public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);
		
		if (TextUtils.isEmpty(regId)) {
		//	Toast.makeText(getApplicationContext(),	" RegId: " + regId,	Toast.LENGTH_LONG).show();
			registerInBackground();

		
		} else {
		//	Toast.makeText(getApplicationContext(),		"RegId already available. RegId: " + regId,	Toast.LENGTH_LONG).show();
		}
		return regId;
	}

    
    
	private String getRegistrationId(Context context) {
		
		final SharedPreferences prefs = getSharedPreferences(
				FlashActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");
	//	Toast.makeText(getApplicationContext(), "reg "+registrationId, Toast.LENGTH_SHORT).show();
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			//Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
		
	}

	
	
	private static int getAppVersion(Context context) {
		
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
		
	}

	
	
	private void registerInBackground() {
		
		new AsyncTask<Void, Void, String>() {
			String msg = "";
			@Override
			protected String doInBackground(Void... params) {
			//String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(Config.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					msg = regId;

					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
				
			}

			@Override
			protected void onPostExecute(String msg) {
				
		
						Intent i = new Intent(getApplicationContext(),
								GcmActivity.class);
						i.putExtra("regId", msg);
						Log.d("RegisterActivity",
								"onClick of Share: Before starting main activity.");
						startActivity(i);
						finish();
						Log.d("RegisterActivity", "onClick of Share: After finish.");
					
		         
				
				
			//	Toast.makeText(getApplicationContext(),		"Registered with GCM Server." + msg, Toast.LENGTH_LONG).show();
			}
		}.execute(null, null, null);
	}

	
	
	private void storeRegistrationId(Context context, String regId) {
		
		final SharedPreferences prefs = getSharedPreferences(
				FlashActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
		
	}

    
	
	
    
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

}
