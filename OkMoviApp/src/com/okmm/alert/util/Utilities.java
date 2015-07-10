package com.okmm.alert.util;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.Locale;



import com.okmm.alert.constant.Config;

import android.text.TextUtils;

public class Utilities {
	
  public final static boolean isValidEmail(CharSequence target) {
	return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS
			                                     .matcher(target).matches();
  }
  
  public final static boolean isValidPhone(CharSequence target) {
	return !TextUtils.isEmpty(target) && android.util.Patterns.PHONE
			                             .matcher(target).matches();
  }
  
  public static Integer booleanToInt(Boolean input){
	return input ? 1 : 0 ;
  }
  
  public static Boolean stringToBoolean(String input){
	return input != null && input.equalsIgnoreCase("true");
  }
  
  public static Boolean intToBoolean(Integer input){
	return input == 1;
  }
  
  public static Integer parseInt(String input){
	Integer output = null;
	try{
		output = Integer.valueOf(input);
	} catch(Exception unparsableIgnored){
	}
	return output;
  }
  
  public static String dateToString(Date input){
	String output = "";
	if(input != null){
	  output = Config.DATE_FORMAT.format(input);
	}
	return output;
  }
  
  public static Date stringToDate(String input){
	Date output = null;
	if(input != null){
	  try{
		  output = Config.DATE_FORMAT.parse(input);
	  } catch(Exception unparsableIgnored){}
	}
	return output;
  }
  
  public String getNotNullString(String input){
	String output = "";
	if((input != null)){
		output = input;
	}
	return output;
  }
  
  public static String getMonth(int month) {
	return firstToUpper(new DateFormatSymbols(Locale.getDefault())
	                                       .getMonths()[month-1]);
  }
  
  
  public static String firstToUpper(String input){
	String output = input;
	if(input != null && input.length() > 1){
	  output = output.substring(0,1).toUpperCase(Locale.getDefault()) 
			 + output.substring(1).toLowerCase(Locale.getDefault());
	}
	return output;
  }

}
