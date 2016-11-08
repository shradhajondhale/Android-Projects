package com.maverick.assampoll2016;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GcmActivity extends Activity {

	ShareExternalServer appUtil;
	String regId;
	AsyncTask<Void, Void, String> shareRegidTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_gcm);
		
		appUtil = new ShareExternalServer();

		regId = getIntent().getStringExtra("regId");
		Log.d("MainActivity", "regId: " + regId);

		final Context context = this;
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			ProgressDialog dialog = new ProgressDialog(GcmActivity.this);
	
			@Override
	   	    protected void onPreExecute() {
	   	      dialog.setMessage("Please wait...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				String result = appUtil.shareRegIdWithAppServer(context, regId);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				dialog.dismiss();
				shareRegidTask = null;
				//Toast.makeText(getApplicationContext(), result,	Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GcmActivity.this, SignUp.class);
		         startActivity(intent);
		        // overridePendingTransition(android.R.anim., R.anim.fadein);
		       	 finish();
			}

		};
		shareRegidTask.execute(null, null, null);
	}

}

