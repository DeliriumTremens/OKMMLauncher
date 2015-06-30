package com.okmm.android.util;


import com.okmm.android.constant.Config;

import android.content.Context;
import android.widget.Toast;

public class ToastBuilder {
	  
  
  public static void show(Integer resId, Context ctx, Object ... var){
	show(ctx.getResources().getString(resId), ctx, var);
  }
	  
  public static void show(String message, Context ctx, Object ... var){
	Toast.makeText(ctx, String.format(message, var), Toast.LENGTH_SHORT).show();
  }
  
  public static void showError(String code, Context ctx, Object ... var){
	int resourceId = ctx.getResources().getIdentifier(Config
			     .MESSAGE_CODE_PREFFIX + code, "string", ctx.getPackageName());
	if(resourceId > 0){
	    show(resourceId, ctx, var);
	} else {
		show(code, ctx, var);
	}
  }

}
