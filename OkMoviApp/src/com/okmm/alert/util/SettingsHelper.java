package com.okmm.alert.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

public class SettingsHelper {
	
  private static final String LOCAL_PREFERENCES = "com.okmm.alert";
	
  public static int getUserId(Context context) {
	SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
			                                     , Context.MODE_PRIVATE);
	int newD = sp.getInt("okmmId", 0);
	return newD;
  }
  
  public static void setUserId(Context context, Integer userId){
	SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
			                                     , Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = sp.edit();
	editor.putInt("okmmId", userId);
	editor.commit();
  }
  
//  public static void setBackgroundDrawable(Context context, Drawable drawable){
//	  SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
//              , Context.MODE_PRIVATE);
//SharedPreferences.Editor editor = sp.edit();
//editor.putInt("okmmId", userId);
//editor.put
//editor.commit();
//  }

}
