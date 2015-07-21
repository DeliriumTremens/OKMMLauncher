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
  public void onReceive(Context ctx, Intent intent){   
	Displayer displayer = new Displayer();
	Loader loader = new Loader();
	Registration registration = null;
	setFilesystem();
    if(SettingsHelper.getUserId(ctx) == 0) {
      try{
    	  registration = Registration.getInstance(ctx);
  		  if(! registration.isShowing()){
  		    registration.show();
  		  }
      } catch (Exception e) {
    	  e.printStackTrace();
      }		
    } else {
      loader.SetAlarm(ctx);
      displayer.SetAlarm(ctx);
    }
  }
    
  private void setFilesystem(){
    File file = new File(Config.IMG_LOCAL_PATH);
    file.mkdirs();
  }
}
