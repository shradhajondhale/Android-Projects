package com.maverick.tracker;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;
import android.view.View;
import android.widget.Toast;

public class receiver extends BroadcastReceiver {
	
	SQLiteDatabase db;
	
	private static final int NOTIFY_ME_ID=1337;
	@Override
	
	
	public void onReceive(Context context, Intent arg1) {
		Calendar cal1 = Calendar.getInstance(); 
		int min_c = cal1.get(Calendar.MINUTE);
      	 int ho_c = cal1.get(Calendar.HOUR_OF_DAY);
    //  Toast.makeText(context, "receiver called ", Toast.LENGTH_SHORT).show();
      	 if(ho_c==10 && min_c==0){ 
      		setlocation(context);
      	 }
      	 else if(ho_c==10 && min_c==30){ 
      		setlocation(context);
      	 }
      	 else if( ho_c==11 && min_c==0 ){
      		setlocation(context);
      	 }
      	 else if(ho_c==11 && min_c==30){ 
      		setlocation(context);
      	 }
      	 else if(ho_c==12 && min_c==0){
      		setlocation(context);
      	 }
      	 else if(ho_c==12 && min_c==30){ 
      		setlocation(context);
      	 }
      	 else if(ho_c==13 && min_c==0){
      		setlocation(context);
      	 }
      	 else if(ho_c==13 && min_c==30){ 
      		setlocation(context);
      	 }
      	 else if(ho_c==14 && min_c==0){
      		setlocation(context);
      	 }
      	 else if(ho_c==14 && min_c==30){
      		setlocation(context);
      	 }
      	 else if(ho_c==15 && min_c==0){
      		setlocation(context);
      	 }
      	 else if(ho_c==15 && min_c==30){
      		setlocation(context);
      	 }
      	 else if(ho_c==16 && min_c==0){
       		setlocation(context);
       	 }
       	 else if(ho_c==16 && min_c==30){
       		setlocation(context);
       	 }
       	 else if(ho_c==17 && min_c==0){
       		setlocation(context);
       	 }
       	 else if(ho_c==17 && min_c==30){
       		setlocation(context);
       	 }
       	 else if(ho_c==18 && min_c==0){
       		setlocation(context);
       	 }
       	 else if(ho_c==18 && min_c==30){
       		setlocation(context);
       	 }
       	 else if(ho_c==19 && min_c==0){
       		setlocation(context);
       	 }
    	 else if(ho_c==19 && min_c==30){
        		setlocation(context);
        	 }
    	 else if(ho_c==20 && min_c==0){
        		setlocation(context);
        	 }
    	 else if(ho_c==20 && min_c==30){
     		setlocation(context);
     	 }
    	 else if(ho_c==21 && min_c==0){
        		setlocation(context);
        	 }
    	 else if(ho_c==21 && min_c==30){
        		setlocation(context);
        	 }
    	 else if(ho_c==22 && min_c==0){
     		setlocation(context);
     	 }
 	   else if(ho_c==22 && min_c==30){
     		setlocation(context);
     	 }
 	   else if(ho_c==23 && min_c==0){
 		setlocation(context);
 	 }
	   else if(ho_c==23 && min_c==30){
 		setlocation(context);
 	 }
	   else if(ho_c==0 && min_c==0){
	 		setlocation(context);
	 	 }
      else if(ho_c==0 && min_c==30){
	 		setlocation(context);
	 	 }
      else if(ho_c==1 && min_c==0){
	 		setlocation(context);
	 	 }
    else if(ho_c==1 && min_c==30){
	 		setlocation(context);
	 	 }
    else if(ho_c==2 && min_c==0){
 		setlocation(context);
 	 }
  else if(ho_c==2 && min_c==30){
 		setlocation(context);
 	 }
  else if(ho_c==3 && min_c==0){
		setlocation(context);
	 }
else if(ho_c==3 && min_c==30){
		setlocation(context);
	 }
else if(ho_c==4 && min_c==0){
		setlocation(context);
	 }
else if(ho_c==4 && min_c==30){
		setlocation(context);
	 }
else if(ho_c==5 && min_c==0){
		setlocation(context);
	 }
else if(ho_c==5 && min_c==30){
		setlocation(context);
	 }
else if(ho_c==6 && min_c==0){
		setlocation(context);
	 }
else if(ho_c==6 && min_c==30){
		setlocation(context);
	 }
else if(ho_c==7 && min_c==0){
		setlocation(context);
	 }
else if(ho_c == 7 && min_c == 30){
		setlocation(context);
	 } 
else if(ho_c == 8 && min_c == 0){
	 		setlocation(context);
	 	 }
else if(ho_c == 8 && min_c == 30){
	 		setlocation(context);
	 	 }
else if(ho_c == 9 && min_c == 0){
		setlocation(context);
	 }
else if(ho_c == 9 && min_c == 30){
		setlocation(context);
	 }

      	 
      	 
      	 
	      	
      	
      	
		
	}
	
	private void showNotification(Context context) {
	   /* PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
	            new Intent(context, receiver.class), 0);

	    NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(context)
	            .setSmallIcon(0)
	            .setContentTitle("My notification")
	            .setContentText("Hello World!");
	    mBuilder.setContentIntent(contentIntent);
	    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
	    mBuilder.setAutoCancel(true);
	    NotificationManager mNotificationManager =
	        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(1, mBuilder.build());
*/
		
		 final NotificationManager mgr=
		            (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		        Notification note=new Notification(R.drawable.ic_launcher,"Tracker!",System.currentTimeMillis());
		         
		        // This pending intent will open after notification click
		        PendingIntent i=PendingIntent.getActivity(context, 0,new Intent(context, NotifyGPSNotPresent.class),0);
		         note.setLatestEventInfo(context, "Tracker","Please turn on your GPS!!", i);
		         
		        //After uncomment this line you will see number of notification arrived
		        note.number=2;
		        mgr.notify(NOTIFY_ME_ID, note);
	}  
	public void setlocation( Context context){
//		Toast.makeText(context, "toast", Toast.LENGTH_LONG).show();
	 db = context.openOrCreateDatabase("track", Context.MODE_PRIVATE, null);	 
		 LocationManager mlocManager=null;  
        LocationListener mlocListener;  
        mlocManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  
        mlocListener = new MyLocationListener();  
       mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
 
       if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
           if(MyLocationListener.latitude>0)  
           {  
        	   
        	   
        	   
        	  // Toast.makeText(context, "Recording", Toast.LENGTH_SHORT).show();
        	  Cursor c=db.rawQuery("SELECT * FROM location", null);
       	 	 int count =c.getCount() + 1;
        
       	 	Calendar cal = Calendar.getInstance(); 
        	 
        	 
       	 	
       	 	int minutes = cal.get(Calendar.MINUTE);
       	 int time_hour = cal.get(Calendar.HOUR_OF_DAY);
        	// int time_hour = minutes/60;
        	 int time_minutes = minutes % 60;
        	 
       	 	 String time = "" + time_hour + " : " + time_minutes ;
       	 	 String date = Integer.toString(cal.get(Calendar.DATE));
       	 	 String month=Integer.toString( cal.get(Calendar.MONTH)+1);
       	 	String year=Integer.toString(cal.get(Calendar.YEAR));
       	 	
       	 	int check=0;
       	 while(c.moveToNext())
   		{

   			if((c.getString(2).equals(date)) && (c.getString(4).equals(year))&& (c.getString(1).equals(time)) ){
   				check=1;
   			}
   		}
       	 	if(check==0){
       	 	db.execSQL("INSERT INTO location VALUES("+count+", '"+time+"','"+date+"','"+month+"','"+year+"','"+MyLocationListener.longitude+"', '"+MyLocationListener.latitude+"');"); //no initial phone no and not registered  
        	// Toast.makeText(context, "Latitude:- " + MyLocationListener.latitude + "Longitude:- " + MyLocationListener.longitude, Toast.LENGTH_SHORT).show();     
            } 
       	 	
           }
            else  
            { // Toast.makeText(	context, "wait", Toast.LENGTH_SHORT).show(); 
             }  
         } else { 
        	 //for showing the notification
        	 showNotification(context);
       	  //Toast.makeText(context, "GPS is not turned on...", Toast.LENGTH_SHORT).show();   
         }  

	
       }
	}
	
	
	
		 
