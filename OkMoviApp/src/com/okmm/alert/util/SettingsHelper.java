package com.okmm.alert.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
	
  private static final String LOCAL_PREFERENCES = "com.okmm.alert";
	
  public static int getRegistration(Context context) {
	SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
			                                     , Context.MODE_PRIVATE);
	int newD = sp.getInt("okmmId", 0);
	return newD;
  }

}
