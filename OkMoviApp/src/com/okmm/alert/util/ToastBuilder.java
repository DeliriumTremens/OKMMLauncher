package com.okmm.alert.util;


import com.okmm.alert.constant.Config;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastBuilder {
	  
  
	public static void show(Integer resId, Context ctx, Integer gravity, Object ... var){
		show(ctx.getResources().getString(resId), ctx, gravity, var);
	  }
	
  public static void show(Integer resId, Context ctx, Object ... var){
	show(ctx.getResources().getString(resId), ctx, Gravity.CENTER , var);
  }
	  
  public static void show(String message, Context ctx, Integer gravity,  Object ... var){
	Toast toast = Toast.makeText(ctx, String.format(message, var), Toast.LENGTH_SHORT);
	toast.setGravity(gravity, 0, 0);
	toast.show();
  }
  
  public static void showError(String code, Context ctx, Object ... var){
	int resourceId = ctx.getResources().getIdentifier(Config
			     .MESSAGE_CODE_PREFFIX + code, "string", ctx.getPackageName());
	if(resourceId > 0){
	    show(resourceId, ctx, var);
	} else {
		show(code, ctx, Gravity.CENTER, var);
	}
  }

}
