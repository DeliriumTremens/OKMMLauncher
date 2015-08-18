package com.okmm.alert.util;



import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
	
  private static final String LOCAL_PREFERENCES = "com.okmm.alert";
	
  public static Integer getUserId(Context context) {
	try{
	    return Integer.valueOf(get(context, "okmm_userId").toString());
	} catch(Exception ignored){}
	return 0;
  }
  
  public static void setUserId(Context context, Integer userId){
	set(context, "okmm_userId", userId);
  }
  
  public static void setSimNumber(Context context, String simNumber){
	set(context, "okmm_simNumber", simNumber);
  }
  
  public static void setUserToken(Context context, String userToken){
	set(context, "okmm_userToken", userToken);
  }
  
  public static String getSimNumber(Context context){
	try{
	   return get(context, "okmm_simNumber").toString();
	} catch(Exception ignored){}
	return null;
  }
  
  public static String getUserToken(Context context){
	try{
	   return get(context, "okmm_userToken").toString();
	} catch(Exception ignored){}
	return null;
  }
  
  public static void setImeiNumber(Context context, String imeiNumber){
	set(context, "okmm_imeiNumber", imeiNumber);
  }
  
  public static String getImeiNumber(Context context){
	try{
	   return get(context, "okmm_imeiNumber").toString();
	} catch(Exception ignored){}
	return null;
  }
  
  private static void set(Context context, String name, Object value){
	SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
                                                 , Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    if(value instanceof Integer){
       editor.putInt(name, Integer.valueOf(value.toString()));
    } else if (value instanceof String){
    	editor.putString(name, value.toString());
    }
    editor.commit();
  }
  
  public static Object get(Context context, String name) {
    SharedPreferences sp = context.getSharedPreferences(LOCAL_PREFERENCES
				                                  , Context.MODE_PRIVATE);
    return sp.getAll().get(name);
  }
  

}
