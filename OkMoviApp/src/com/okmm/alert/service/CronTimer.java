package com.okmm.alert.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.constant.Config;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class CronTimer extends BroadcastReceiver {    
   
  @Override
  public void onReceive(Context context, Intent intent) {   
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
    wl.acquire();
    callWSCampaigns(context);
    wl.release();
  }

  public void SetAlarm(Context context){
    AlarmManager am =( AlarmManager)context.getSystemService(Context
    		                                        .ALARM_SERVICE);
    Intent i = new Intent(context, CronTimer.class);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    		                                   , Config.TIMER_LAP, pi);
  }
  
  private void callWSCampaigns(Context ctx){
	RequestParams params = new RequestParams(); 
	params.put("id_user", SettingsHelper.getUserId(ctx));
	RestClient.get("camps", params, new RestResponseHandler(ctx, false) {
	  @Override
	  public void onSuccess(JSONObject response) throws JSONException {
			
	  }    
	});
  }
}