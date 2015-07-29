package com.okmm.alert.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.okmm.alert.constant.Config;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

public class AuthorityManager extends BroadcastReceiver {

  @Override
  public void onReceive(Context ctx, Intent intent){   
	Log.i(Config.LOG_TAG, "AuthorityManager started");
	Registration registration = null;
	System.out.println("userId => " + SettingsHelper.getUserId(ctx));
	if(SettingsHelper.getUserId(ctx) == 0) {
	  try{
	  	  registration = Registration.getInstance(ctx);
	 	  if(! registration.isShowing()){
	  		registration.show();
	      }
	   } catch (Exception e) {
	      e.printStackTrace();
	   }		
	 } else {
		 stop(ctx);
	 }
   }
  
  public void stop(Context ctx){
	PendingIntent pi = null;
	AlarmManager am = null;
	am = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
	pi = PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, AuthorityManager
			                                                    .class), 0);  
	am.cancel(pi);
	pi.cancel();
  }
  
  public void start(Context ctx){
	PendingIntent pi = null;
	AlarmManager am = null;
	am = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
	pi = PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, AuthorityManager
			                                                    .class), 0);
	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	    		                              , Config.AUTH_TIMER, pi);
  }
}
