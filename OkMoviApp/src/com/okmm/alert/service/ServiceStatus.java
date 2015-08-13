package com.okmm.alert.service;

import com.okmm.alert.constant.Config;
import com.okmm.alert.ui.Unavailable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class ServiceStatus extends BroadcastReceiver {    
	   
  private static Unavailable unavailable = null;
  private PowerManager pm = null;
  private static boolean isRunning = false;
  
  @Override
  public void onReceive(Context context, Intent intent) {
	Log.i(Config.LOG_TAG, "ServiceStatus started");
	if(pm == null){
		pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	}
	PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
	init(context);
	run(context);
	wl.acquire();
	wl.release();
  }
  
  private void init(Context ctx){
	if(unavailable == null){
	  unavailable = Unavailable.getInstance(ctx);
	}
  }
  
  private void run(Context ctx){
	if(! unavailable.isShowing()){
	  unavailable.show();
    }	
  }
  
  public void start(Context context){
	AlarmManager am = null;
    Intent i = null;
	if(!isRunning){
	  am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
      i = new Intent(context, ServiceStatus.class);
      PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
      am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                                         , Config.UNAVAILABLE_TIMER, pi);
      isRunning = true;
	}
  }
  
  public void stop(Context ctx){
	PendingIntent pi = null;
	AlarmManager am = null;
	if(isRunning){
	  am = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
	  unavailable.dimiss();
	  pi = PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, AuthorityManager
					                                              .class), 0);  
	  am.cancel(pi);
	  pi.cancel();	
	  isRunning = false;
	}
  }
}