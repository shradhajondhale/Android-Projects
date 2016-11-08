package com.maverick.tracker;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class bootstart extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		//Toast.makeText(context, "tracker started", Toast.LENGTH_SHORT).show();
		
		Intent i=new Intent(context,FlashActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		/*
		 * Intent i=new Intent(context,MainActivity.class);
		 
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		
		*/
		
		 //checking for pending intents
     /*   Intent checkIntent = new Intent(context,receiver.class);
	      checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      boolean k= (PendingIntent.getBroadcast(context, 234324243, checkIntent, PendingIntent.FLAG_NO_CREATE)!=null);
	      if(k == true)
	      {
	         
	    	  Intent intent2 = new Intent(context, receiver.class);
		        pendingIntent2 = PendingIntent.getBroadcast(context, 234324243, intent2, 0);
		        pendingIntent2.cancel();
	      }
       
        Calendar cal = Calendar.getInstance();
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourofday);
        calendar.set(Calendar.MINUTE, 03);

 	        //for setting pending intents for 5 min starting from next hour
 	        Intent intent1 = new Intent(this, receiver.class);
	        pendingIntent1 = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent1, 0);
	        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
	        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 1000*20, pendingIntent1);
*/ 	
	}

}
