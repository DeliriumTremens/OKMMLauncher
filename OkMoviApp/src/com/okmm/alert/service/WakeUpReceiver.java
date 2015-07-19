package com.okmm.alert.service;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.okmm.alert.constant.Config;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

public class WakeUpReceiver extends BroadcastReceiver {   
  
  @Override
  public void onReceive(Context context, Intent intent){   
	CampaignDisplayer displayer = new CampaignDisplayer();
	CampaignLoader loader = new CampaignLoader();
	setFilesystem();
    if(SettingsHelper.getUserId(context) == 0) {
      try{
    	  new Registration(context).show();
      } catch (Exception e) {
    	  e.printStackTrace();
      }		
    }
    loader.SetAlarm(context);
    displayer.SetAlarm(context);
  }
    
    private void setFilesystem(){
    	File file = new File(Config.IMG_LOCAL_PATH);
    	file.mkdirs();
      }
}
