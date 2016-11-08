package com.maverick.tracker;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

public class GPSStatusBroadcastReceiver extends BroadcastReceiver {
	  public static final String GPS_ENABLED_CHANGE_ACTION = "android.location.GPS_ENABLED_CHANGE";
      String Status="";
      SQLiteDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	 final LocationManager manager = (LocationManager)context.getSystemService( Context.LOCATION_SERVICE );

    	    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
    	   // Toast.makeText(context, "GPS is turned off", Toast.LENGTH_SHORT).show();  
    	        Status = "OFF";
    	        
    	    }
    	    else{
    	    //	Toast.makeText(context, "GPS is turned on", Toast.LENGTH_SHORT).show();  
    	    	Status = "ON";
    	    	
    	    	
    	    }
    	    
    	  
    	    db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);	 
           	   // Toast.makeText(context, "Recording", Toast.LENGTH_SHORT).show();
           	  Cursor c = db.rawQuery("SELECT * FROM log", null);
          	 	 int count =c.getCount() + 1;
          	 	
           //Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
          	 
          	 	
          	 	 Calendar cal = Calendar.getInstance(); 
          	     int minutes = cal.get(Calendar.MINUTE);
            	 int time_hour = cal.get(Calendar.HOUR_OF_DAY);
            	 int time_minutes = minutes % 60;
           	 
          	 	 String time = "" + time_hour + " : " + time_minutes ;
          	 	 String date = Integer.toString(cal.get( Calendar.DATE ));
          	 	 String month=Integer.toString( cal.get( Calendar.MONTH ) + 1 );
          	 	 String year=Integer.toString( cal.get( Calendar.YEAR ) );
          
          	 	db.execSQL("INSERT INTO log VALUES("+count+", '"+time+"','"+date+"','"+month+"','"+year+"','"+Status+"');"); //no initial phone no and not registered  
      //    Toast.makeText(context, "entered", Toast.LENGTH_SHORT).show();
          	 	// Toast.makeText(context, "Latitude:- " + MyLocationListener.latitude + "Longitude:- " + MyLocationListener.longitude, Toast.LENGTH_SHORT).show();     
          	 
  
          }
    	    
    	    
    }
