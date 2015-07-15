package com.okmm.alert.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
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

public class CampaignLoader extends BroadcastReceiver {    
   
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
    Intent i = new Intent(context, CampaignLoader.class);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    		                                   , Config.LOADER_TIMER, pi);
  }
  
  public void callWSCampaigns(final Context ctx){
	RequestParams params = new RequestParams(); 
	Integer userId = SettingsHelper.getUserId(ctx);
	if(userId > 0){
	  //TODO
	  //params.put("id_user", userId);
	  params.put("id_user", 1);
	  RestClient.post("camps", params, new RestResponseHandler(ctx, false) {
	    @Override
	    public void onSuccess(final JSONObject response) throws JSONException {
	      Thread thread = new Thread(new Runnable(){
	    	    @Override
	    	    public void run() {
	    	        try {
	    	        	Campaign campaign = JsonUtil.getCampaign(response);
	    	  		  ImageLoader imageLoader = new ImageLoader(ctx);
	    	  		  CampaignDAO campaignDAO = new CampaignDAO(ctx);
	    	  		  CampaignDisplayer displayer = new CampaignDisplayer(ctx);
	    	  		  System.out.println("onSuccess");
	    	  		  if(campaign != null){
	    	  			campaign.setLoadedDate(new Date());
	    	  			campaign.setWatched(false);
	    	  		  }
	    	  		  imageLoader.setFiles(campaign);
	    	  		  campaignDAO.upsert(campaign);
	    	  		  displayer.run(ctx);
	    	        } catch (Exception e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
	    	});

	    	thread.start(); 
	    }    
	  });
	}
  }
}