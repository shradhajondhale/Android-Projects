package com.maverick.assampoll2016;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//Toast.makeText(context, "got messege", Toast.LENGTH_SHORT).show();
		ComponentName comp = new ComponentName(context.getPackageName(),
				GCMNotificationIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
		

		
		
	}
}
