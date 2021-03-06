package com.okmm.alert.util.ws;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.okmm.alert.R;
import com.okmm.alert.constant.Config;
import com.okmm.alert.util.ToastBuilder;

public abstract class RestResponseHandler extends JsonHttpResponseHandler {
	
  private Context ctx = null;
  private boolean validateErrorCode = true;
  
  public RestResponseHandler (Context ctx, boolean validateErrorCode) {
	this(ctx);
	this.validateErrorCode = validateErrorCode;
  }
  
  public RestResponseHandler (Context ctx){
	this.ctx = ctx;
  }
	
  public void onSuccess(JSONObject response)  throws JSONException{}
  public void onSuccess(JSONArray response)  throws JSONException{}
  
  
  @Override
  public final void onStart(){
  }
  
  @Override
  final public void onSuccess(int statusCode, Header[] headers
		                              , JSONObject response) {
	String code = null;
	try {
		 try{
		     code = response.getString("errorcode");
		 } catch(JSONException ignored){}
		 if((! validateErrorCode) 
				      || (code == null) || (code.equals(Config.WS_STATUS_OK))){
		   onSuccess(response); 
		 } else {
			 System.out.println("Error: " + response);
			 ToastBuilder.showError(code, ctx, Gravity.TOP);
			 
		 }
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Error: " + e);
		ToastBuilder.show(R.string.errUnexpected, ctx, Gravity.TOP);
	} 
  }
  
  @Override
  public final void onSuccess(int statusCode, Header[] headers
		                         , JSONArray response) {
	try{
		onSuccess(response);
	} catch(Exception e) {
		e.printStackTrace();
		System.out.println("Error: " + e);
		ToastBuilder.show(R.string.errUnexpected, ctx, Gravity.TOP);
	} 
  }

  @Override
  public final void onFailure(int statusCode, Header[] headers
		       , String responseString, Throwable throwable) {
	 ToastBuilder.show(R.string.errCommunications, ctx, String.valueOf(responseString));
  }  
  
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONObject response) {
	    if(response != null){
		  ToastBuilder.show(response.toString(),ctx, Gravity.TOP , statusCode);
	    } else {
		   ToastBuilder.show(R.string.errNull, ctx, Gravity.TOP);
        }
  }
  
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONArray  response) {
	  if(response != null){
		ToastBuilder.show(response.toString(), ctx, Gravity.TOP, statusCode);
	  } else {
		ToastBuilder.show(R.string.errNull, ctx, Gravity.TOP);
	  }
  }

}
