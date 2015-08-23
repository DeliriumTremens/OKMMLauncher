package com.okmm.alert.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.okmm.alert.constant.Config;
import com.okmm.alert.ui.Privacy;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.ui.Terms;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.Utilities;

public class AuthorityManager extends BroadcastReceiver {
	
  private static int stage = 1;
  private static Privacy privacy = null;
  private static Registration registration = null;
  private static Terms terms = null;

  private void init(Context ctx){
	privacy = Privacy.getInstance(ctx);
	registration = Registration.getInstance(ctx);
	terms = Terms.getInstance(ctx);
  }
  
  public static void advance(){
	stage++;
	show();
  }
  
  @Override
  public void onReceive(Context ctx, Intent intent){   
	Log.i(Config.LOG_TAG, "AuthorityManager started");
	init(ctx);
	System.out.println("userId => " + SettingsHelper.getUserId(ctx));
	if(SettingsHelper.getUserId(ctx) == 0) {
	  try{
		  if(Utilities.isNetworkAvailable(ctx)){
			show();
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
  
  public static void show(){
    switch(stage){
	  case 1:  if(! privacy.isShowing()){
	    	     privacy.show();
               }
	           break;
	   case 2: if(! terms.isShowing()){
		         terms.show();
               }
               break;
	   case 3: if(! registration.isShowing()){
		         registration.show();
               }
               break;
	 } 
  }
}
