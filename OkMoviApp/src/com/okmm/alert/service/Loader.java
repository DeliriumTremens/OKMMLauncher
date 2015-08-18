package com.okmm.alert.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import android.util.Log;

public class Loader extends BroadcastReceiver {    
   
  @Override
  public void onReceive(Context context, Intent intent) {   
	Log.i(Config.LOG_TAG, "Loader started");
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
    wl.acquire();
    run(context);
    wl.release();
  }

  public void start(Context context){
    AlarmManager am =( AlarmManager)context.getSystemService(Context
    		                                        .ALARM_SERVICE);
    Intent i = new Intent(context, Loader.class);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    		                                   , Config.LOADER_TIMER, pi);
  }
  
  private void run(Context ctx){
	CampaignDAO dao =  new CampaignDAO(ctx);
	Campaign campaign = dao.find();
	Calendar calendar = new GregorianCalendar();
	ServiceStatus serviceStatus = new ServiceStatus();
	if(isOutOfDate(campaign)){
		serviceStatus.start(ctx);
	} else {
		serviceStatus.stop(ctx);
	    if(campaign == null){
	     callWSCampaigns(ctx);	
	    } else {
	      calendar.setTime(campaign.getLoadedDate());
	      calendar.add(Calendar.MILLISECOND, Config.LOADER_PERIOD);
	      if((calendar.getTime().compareTo(new Date()) < 0) 
			 && (campaign.getStatus().equals(Config.CAMPAIGN_STATUS.DONE.getId()))){
		    callWSCampaigns(ctx);		
	      }
	    } 
	}
  }
  
  public void callWSCampaigns(final Context ctx){
	Log.i(Config.LOG_TAG, "Loader running");
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
	    		   CampaignDAO campaignDAO = new CampaignDAO(ctx);
	    		   Campaign campaign = campaignDAO.find();
	    		   ServiceStatus serviceStatus = new ServiceStatus();
	    		   String errorCode = null;
	    		   try{
	    			   errorCode = response.getString("errorcode");
	    		   } catch(JSONException je){}
	    		   if(errorCode == null){
	    			 serviceStatus.stop(ctx);
	    	         campaign = JsonUtil.getCampaign(response);
	    	         campaignDAO =  new CampaignDAO(ctx);
	    	  	     if(campaign != null){
	    	  		   campaign.setLoadedDate(new Date());
	    	  		   campaign.setStatus(Config.CAMPAIGN_STATUS.NEW.getId());
	    	  		   new ImageLoader(ctx).setFiles(campaign);
		    	  	   campaignDAO.truncate();
		    	  	   campaignDAO.insert(campaign);
	    	  	     }
	    		   } else{
	    			   if(errorCode.equals(Config.ERR_CODE_UNAVAILABLE)){
	    				 serviceStatus.start(ctx);
	    			   }
	    		   }
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
  
  private boolean isOutOfDate(Campaign campaign){
	Calendar minDate = new GregorianCalendar();
	minDate.add(Calendar.DAY_OF_YEAR, -1 * Config.UNAVAILABLE_PERIOD);
	return campaign.getLoadedDate().compareTo(minDate.getTime()) >= 0;
  }
}