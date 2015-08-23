package com.okmm.alert.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.R;
import com.okmm.alert.constant.Config;
import com.okmm.alert.service.AuthorityManager;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class Privacy {
	
  private View privacyView = null;
  private Button bnAccept = null;
  private static TextView tvDescription = null;
  private static Privacy singleton = null;
  
  private static Context ctx = null;
  private static AlertDialog dialog = null;
  
  private Privacy(){
	bind();
	createDialog();
  }
  
  public static Privacy getInstance(Context ctx){
	if(singleton == null){
	  Privacy.ctx = ctx;
	  Log.i(Config.LOG_TAG, "ctx => " + ctx);
	  singleton = new Privacy();
	} 
	return singleton;
  }
  
  
  
  private View.OnClickListener onClickListener =  new View.OnClickListener(){            
    @Override
    public void onClick(View v){
    	AuthorityManager.advance();
    	dialog.dismiss();
    }
  };
  
  public void show(){
	callPrivacyService();
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	dialog.show();
  }
  
  public boolean isShowing(){
	return dialog.isShowing();
  }
  
  private void bind(){
	privacyView = LayoutInflater.from(ctx).inflate(R.layout.privacy, null); 
	bnAccept = (Button) privacyView.findViewById(R.id.bnAccept);
	tvDescription = (TextView) privacyView.findViewById(R.id.tvDescription);
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                  .setCancelable(false);
    dialog = alertBuilder.setView(privacyView).create(); 
    bnAccept.setOnClickListener(onClickListener);
  }

  private static void callPrivacyService(){
	RequestParams params = new RequestParams();
	RestClient.post("aviso", params, new RestResponseHandler(ctx, false) {
	  @Override
	  public void onSuccess(JSONObject response) throws JSONException {
		 try{
		     tvDescription.setText(response.getString("texto"));
		 } catch(Exception e){
			 e.printStackTrace();
			 tvDescription.setText(ctx.getString(R.string.errUnavailable));
		 }
	  }    
	});
  }


}
