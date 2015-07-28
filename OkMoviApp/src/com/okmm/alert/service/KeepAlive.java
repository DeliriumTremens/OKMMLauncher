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
import android.util.Log;

public class KeepAlive extends BroadcastReceiver {
	
  private static final String TAG =  KeepAlive.class.getName();
  
  private Context ctx = null;
	
  @Override
  public void onReceive(Context ctx, Intent intent){   
	System.out.println("KeepAlive => run");
	callKeepAliveService();
  }
  
  public void start(Context context){
	AlarmManager am =( AlarmManager)context.getSystemService(Context
		    		                                .ALARM_SERVICE);
	PendingIntent pi = PendingIntent.getBroadcast(context, 0
			     , new Intent(context, KeepAlive.class), 0);
	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
		    		                    , Config.KEEP_ALIVE_TIMER, pi);
  }
  
  private void callKeepAliveService(){
	RequestParams params = new RequestParams();
	params.put("no_sim", SettingsHelper.getSimNumber(ctx));
	params.put("imei ", SettingsHelper.getImeiNumber(ctx));
	RestClient.post("status", params, new RestResponseHandler(ctx, false) {
	  @Override
	  public void onSuccess(JSONObject response) throws JSONException {
	  	Log.i(TAG, "keepAlive");
	  }    
	});
  }

}
