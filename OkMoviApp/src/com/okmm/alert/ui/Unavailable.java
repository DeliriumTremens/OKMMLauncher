package com.okmm.alert.ui;

import com.okmm.alert.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;


public class Unavailable {
	
  private View unavailableView = null;
  private static Unavailable singleton = null;
  
  private static Context ctx = null;
  private static AlertDialog dialog = null;
  
  private Unavailable(){
	bind();
	createDialog();
  }
  
  public static Unavailable getInstance(Context ctx){
	if(singleton == null){
	  Unavailable.ctx = ctx;
	  singleton = new Unavailable();
	} 
	return singleton;
  }
  
  public void show(){
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	dialog.show();
  }
  
  public void dimiss(){
	dialog.dismiss();;
  }
  
  public boolean isShowing(){
	return dialog.isShowing();
  }
  
  private void bind(){
	unavailableView = LayoutInflater.from(ctx).inflate(R.layout.unavailable
			                                                       , null);
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                                             .setCancelable(false);
    dialog = alertBuilder.setView(unavailableView).create(); 
  }

}
