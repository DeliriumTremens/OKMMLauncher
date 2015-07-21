package com.okmm.alert.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.JsonUtil;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.image.ImageLoader;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;
import com.okmm.alert.vo.bean.Campaign;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class Loader extends BroadcastReceiver {    
   
  @Override
  public void onReceive(Context context, Intent intent) {   
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
    wl.acquire();
    run(context);
    wl.release();
  }

  public void SetAlarm(Context context){
    AlarmManager am =( AlarmManager)context.getSystemService(Context
    		                                        .ALARM_SERVICE);
    Intent i = new Intent(context, Loader.class);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    		                                   , Config.LOADER_TIMER, pi);
  }
  
  private void run(Context ctx){
	CampaignDAO dao =  new CampaignDAO(ctx);
	if(dao.findActive() == null){
	  callWSCampaigns(ctx);
	}
  }
  
  public void callWSCampaigns(final Context ctx){
	RequestParams params = new RequestParams(); 
	Integer userId = SettingsHelper.getUserId(ctx);
	if(userId > 0){
	  params.put("id_user", userId);
	  RestClient.post("camps", params, new RestResponseHandler(ctx, false) {
	    @Override
	    public void onSuccess(final JSONObject response) throws JSONException {
	      Thread thread = new Thread(new Runnable(){
	    	@Override
	    	public void run() {
	    	  try {
	    	       Campaign campaign = JsonUtil.getCampaign(response);
	    	       CampaignDAO campaignDAO =  new CampaignDAO(ctx);
	    	  	   if(campaign != null){
	    	  		 campaign.setLoadedDate(new Date());
	    	  		 campaign.setStatus( Config.CAMPAIGN_STATUS.NEW.getId());
	    	  		 new ImageLoader(ctx).setFiles(campaign);
		    	  	 campaignDAO.truncate();
		    	  	 campaignDAO.insert(campaign);
	    	  	   }
	    	  	   
	    	   } catch (Exception e) {
	    	       e.printStackTrace();
	    	   }
	    	 }
	    });

	    	thread.start(); 
	    }    
	  });
	} else {
		new Registration(ctx).show();
	}
  }
}