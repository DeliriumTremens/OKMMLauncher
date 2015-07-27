package com.okmm.alert.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.okmm.alert.constant.Config;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

public class AuthorityManager extends BroadcastReceiver {
	
  private PendingIntent pi  = null;
	
  @Override
  public void onReceive(Context ctx, Intent intent){   
	Registration registration = null;
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
		 if(pi != null){
			((AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE))
			                                                .cancel(pi);
			pi.cancel();
		 }
	 }
   }
  
  
  public void setAlarm(Context context){
	AlarmManager am =( AlarmManager)context.getSystemService(Context
	    		                                    .ALARM_SERVICE);
	pi = PendingIntent.getBroadcast(context, 0, new Intent(context, AuthorityManager
			                                                     .class), 0);
	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	    		                              , Config.AUTH_TIMER, pi);
  }
}
