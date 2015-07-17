package com.okmm.alert.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.vo.bean.Campaign;

import android.app.WallpaperManager;
import android.content.Context;

public class CampaignDisplayer {    
	   
  private Context ctx = null;
  
  public CampaignDisplayer(Context ctx){
	this.ctx = ctx; 
  }
  
  public  void run(Context ctx){
	Campaign campaign =  new CampaignDAO(ctx).findActive();
	displayWallper(campaign.getBackground());
  }
  
  
  private void displayWallper(String filePath){
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
}