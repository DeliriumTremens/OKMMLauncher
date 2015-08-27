package com.okmm.alert.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.ui.Popup;
import com.okmm.alert.ui.Unavailable;
import com.okmm.alert.ui.Wallpaper;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.vo.bean.Campaign;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class Displayer extends BroadcastReceiver {    
	   
  private static Popup popup = null;
  private static Wallpaper wallpaper = null;
  private PowerManager pm = null;
  private static Unavailable unavailable = null;
  
  @Override
  public void onReceive(Context context, Intent intent) {
	Log.i(Config.LOG_TAG, "Displayer started");
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
	if(popup == null){
	  popup = Popup.getInstance(ctx);
	}
	if(wallpaper == null){
	  wallpaper = Wallpaper.getInstance(ctx);
	}
	if(unavailable == null){
	  unavailable = Unavailable.getInstance(ctx);
	}
  }
  
  private void run(Context ctx){
	Integer userId = SettingsHelper.getUserId(ctx);
	Campaign campaign = null;
	if(userId > 0){
	  campaign =  new CampaignDAO(ctx).findActive();
	  if(campaign != null) {
		unavailable.dimiss();
		if(pm.isScreenOn()){
		  if(! popup.isShowing()){
		   popup.run(campaign);
		  }	
		} else {
			 popup.dimiss();
		}
		wallpaper.run(campaign);
		if(campaign.getStatus().equals(Config.CAMPAIGN_STATUS.NEW.getId())){
		  campaign.setStatus(Config.CAMPAIGN_STATUS.DISPLAYED.getId());
	      new CampaignDAO(ctx).update(campaign);
		}
	  } else{
		  if((campaign =  new CampaignDAO(ctx).find()) != null){
		    if(isOutOfDate(campaign)){
              if(! unavailable.isShowing()){
                unavailable.show();
              }
		    } else {
		    	unavailable.dimiss();
		    }
          } 
	  }
	}
  }
  
  public void start(Context context){
	AlarmManager am =( AlarmManager)context.getSystemService(Context
	    		                                    .ALARM_SERVICE);
	Intent i = new Intent(context, Displayer.class);
	PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	    		                         , Config.DISPLAYER_TIMER, pi);
  }
  
  private boolean isOutOfDate(Campaign campaign){
	Calendar minDate = new GregorianCalendar();
	Boolean isOutOfDate = false;
	if(campaign != null){
	  minDate.add(Calendar.DAY_OF_YEAR, -1 * Config.UNAVAILABLE_PERIOD_DAYS);
	  isOutOfDate = campaign.getLoadedDate().compareTo(minDate.getTime()) < 0;
	}
	return isOutOfDate;
  }
}