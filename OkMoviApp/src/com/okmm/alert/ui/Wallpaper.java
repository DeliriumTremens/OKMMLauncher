package com.okmm.alert.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.image.ImageUtils;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;
import com.okmm.alert.vo.bean.Campaign;

public class Wallpaper {
		  
  private static Context ctx = null;
  private Boolean hasBeenChanged = false;
  private static Wallpaper singleton = null;
  private Campaign campaign = null;
	
  private Wallpaper(Context ctx){
	Wallpaper.ctx = ctx;
  }
  
  public static Wallpaper getInstance(Context ctx){
	if(singleton == null){
	  singleton = new Wallpaper(ctx);
	}
	return singleton;
  }
	
  public void run(Campaign campaign){
    if((this.campaign == null) || (this.campaign.getBackground() == null) 
    		|| !campaign.getBackground().equals(this.campaign.getBackground())){
      setHasBeenChanged();
      if(hasBeenChanged && (this.campaign != null)){
  		 callWSStatics(this.campaign, Config.ACTION_ID.CHANGE.getId());
  	  }
	  changeWallpaper(campaign);
	  callWSStatics(campaign, Config.ACTION_ID.SHOW.getId());
	}
  }
  
  private void changeWallpaper(Campaign campaign){
	WallpaperManager wpm = null;
	InputStream ins = null;
	if(campaign.getBackground() != null && !campaign.getBackground().isEmpty()){
	  Log.i(Config.LOG_TAG, "Changing wallpaper");
	  try {
		   wpm = WallpaperManager.getInstance(ctx);
	       wpm.forgetLoadedWallpaper();
		   ins = new FileInputStream(campaign.getBackground());
		   wpm.setStream(ins);
		   this.campaign = campaign;
		   if(campaign.getPopup() == null || campaign.getPopup().isEmpty()){
			 campaign.setStatus(Config.CAMPAIGN_STATUS.DONE.getId());
			 new CampaignDAO(ctx).update(campaign);
		   }
	   } catch (Exception e) {
	 	   e.printStackTrace();
	   } finally{
		   try {
			 ins.close();
		   } catch (Exception ignored) {}
		   ImageUtils.drawableTofile(wpm.getDrawable()
				       , Config.WP_CURRENT_FILE_NAME);
		}
	  }
  }

  public void sendStatics(Campaign campaign){
	callWSStatics(campaign, Config.ACTION_ID.SHOW.getId());
	Log.i(Config.LOG_TAG, "Wallpaper changed :" + hasBeenChanged);
	if(hasBeenChanged){
		callWSStatics(campaign, Config.ACTION_ID.CHANGE.getId());
	}
  }
  
  public void callWSStatics(Campaign campaign, Integer eventType){
	RequestParams params = new RequestParams(); 
	Integer userId = SettingsHelper.getUserId(ctx);
	if(userId > 0){
	  params.put("id_user", userId);
	  params.put("id_camp", campaign.getId());
	  params.put("elemento", Config.ELEMENT_TYPE.POPUP.getId());
	  params.put("action", eventType);
	  RestClient.post("camp_stat", params, new RestResponseHandler(ctx, false) {
		@Override
		public void onSuccess(final JSONObject response) throws JSONException {
		  Log.i(Config.LOG_TAG, "Popup send statics : OK");
		}    
	  });
	} 
  }
  
  private void setHasBeenChanged(){
	File current = new File(Config.BACKUP_LOCAL_PATH + Config.WP_CURRENT_FILE_NAME);
	File last = null;
	hasBeenChanged = false;
	try{
		if(current != null && current.exists()){
		  ImageUtils.drawableTofile(WallpaperManager.getInstance(ctx).getDrawable()
				                                       , Config.WP_LAST_FILE_NAME);
		  last = new File(Config.BACKUP_LOCAL_PATH + Config.WP_LAST_FILE_NAME);
		  hasBeenChanged = ! (current.length() == last.length());
		}
	}catch(Exception e){
	   e.printStackTrace();
	}
  }

}
