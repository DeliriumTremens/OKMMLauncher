package com.okmm.alert.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.ui.Popup;
import com.okmm.alert.ui.Wallpaper;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.vo.bean.Campaign;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class Displayer extends BroadcastReceiver {    
	   
  private static Popup popup = null;
  private static Wallpaper wallpaper = null;
  
  @Override
  public void onReceive(Context context, Intent intent) {
	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
	init(context);
	run(context);
	wl.acquire();
	wl.release();
  }
  
  private void init(Context context){
	if(popup == null){
	  popup = new Popup(context);
	}
	if(wallpaper == null){
	  wallpaper = new Wallpaper(context);
	}
  }
  
  private void run(Context ctx){
	Integer userId = SettingsHelper.getUserId(ctx);
	Campaign campaign = null;
	if(userId > 0){
	  campaign =  new CampaignDAO(ctx).findActive();
	  if(campaign != null) {
		if(! popup.isShowing()){
	      wallpaper.run(campaign);
	      popup.run(campaign);
		}
	  }
	}
  }
  
  public void SetAlarm(Context context){
	AlarmManager am =( AlarmManager)context.getSystemService(Context
	    		                                    .ALARM_SERVICE);
	Intent i = new Intent(context, Displayer.class);
	PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	    		                         , Config.DISPLAYER_TIMER, pi);
  }

  private void displayWallper(Context ctx, String filePath){
	WallpaperManager wpm = WallpaperManager.getInstance(ctx);
	InputStream ins = null;
	try {
		 wpm = WallpaperManager.getInstance(ctx);
		 ins = new FileInputStream(filePath);
		 wpm.setStream(ins);  
	} catch (Exception e) {
		e.printStackTrace();
	} finally{
		try {
			ins.close();
		} catch (IOException ignored) {}
	}
	  
  }
  
  private void displayPopup(Context ctx, Campaign campaign){
	popup.run(campaign);
  }
}