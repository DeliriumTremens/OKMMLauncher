package com.okmm.alert.service;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.okmm.alert.constant.Config;
import com.okmm.alert.util.SettingsHelper;

public class WakeUpReceiver extends BroadcastReceiver {   
  
  @Override
  public void onReceive(Context ctx, Intent intent){   
	Displayer displayer = new Displayer();
	Loader loader = new Loader();
	AuthorityManager authority = new AuthorityManager();
	setFilesystem();
    if(SettingsHelper.getUserId(ctx) == 0) {
    	authority.start(ctx);
    } else {
      loader.start(ctx);
      displayer.start(ctx);
    }
  }
    
  private void setFilesystem(){
    File file = new File(Config.IMG_LOCAL_PATH);
    file.mkdirs();
    file = new File(Config.BACKUP_LOCAL_PATH);
    file.mkdirs();
  }
}
