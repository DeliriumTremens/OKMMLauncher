package com.okmm.alert.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.WallpaperManager;
import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.StaticsDAO;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;
import com.okmm.alert.vo.bean.Campaign;
import com.okmm.alert.vo.bean.Statics;

public class Wallpaper {
	
  private Context ctx = null;
	
  public Wallpaper(Context ctx){
	this.ctx = ctx;
  }
	
  public void run(Campaign campaign){
	WallpaperManager wpm = WallpaperManager.getInstance(ctx);
	InputStream ins = null;
	try {
		 wpm = WallpaperManager.getInstance(ctx);
		 ins = new FileInputStream(campaign.getBackground());
		 wpm.setStream(ins);  
		 callWSStatics(campaign);
	} catch (Exception e) {
		e.printStackTrace();
	} finally{
		try {
			 ins.close();
		} catch (IOException ignored) {}
	}
  }
  
  private void upsertStatics(Campaign campaign){
	Statics statics = new Statics();
	statics.setCampaignId(campaign.getId());
	statics.setActionId(Config.ACTION_ID.SHOW.getId());
	statics.setTypeId(Config.ELEMENT_TYPE.WALLPER.getId());
	new StaticsDAO(ctx).insert(statics);
	callWSStatics(campaign);
  }
  
  public void callWSStatics(Campaign campaign){
	RequestParams params = new RequestParams(); 
	Integer userId = SettingsHelper.getUserId(ctx);
	if(userId > 0){
	  params.put("id_user", userId);
	  params.put("id_camp", campaign.getId());
	  params.put("elemento", Config.ELEMENT_TYPE.WALLPER.getId());
	  params.put("accion ", Config.ACTION_ID.SHOW.getClass());
	  RestClient.post("camp_stat", params, new RestResponseHandler(ctx, false) {
		@Override
		public void onSuccess(final JSONObject response) throws JSONException {
		   System.out.println("Statics OK");
		}    
	  });
	} else {
		new Registration(ctx).show();
	}
  }

}
