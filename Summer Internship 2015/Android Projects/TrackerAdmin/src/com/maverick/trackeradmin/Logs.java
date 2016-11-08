package com.maverick.trackeradmin;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class Logs extends ActionBarActivity {

	ListView loglistView; 
	ArrayList<String> dataItems = new ArrayList<String>();	
	SQLiteDatabase db; 
    String UserName;
    String[] Id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logs);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));

		
		loglistView = (ListView) findViewById(R.id.loglist);
		
		ArrayList<String> logslist = new ArrayList<String>();
		
		UserName = getIntent().getStringExtra("UserName");
		 
		getActionBar().setTitle("GPS logs of " + UserName);

			
		 db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
		
		 int i = 0;
		 
		 Cursor c = db.rawQuery("SELECT * FROM log", null);
		 
		 Id = new String[c.getCount()];
		
		 
		   if( c.getCount() == 0 )
			{
				Toast.makeText(getApplicationContext(), "The user is not using this app.", Toast.LENGTH_SHORT).show();
		      // 	return;
			}
		   
		  //	StringBuffer buffer = new StringBuffer();
			
			while(c.moveToNext())
			{
				//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
				 
		        logslist.add(c.getString(0)) ;
		         i++;
			}
		
		
				
		LogsCustomAdapter logadapter = new LogsCustomAdapter(logslist, this);
        loglistView.setAdapter(logadapter);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logs, menu);
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
