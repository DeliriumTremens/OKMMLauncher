package com.okmm.alert.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.Context;

import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.StaticsDAO;
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
		 upsertStatics(campaign);
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
  }

}
