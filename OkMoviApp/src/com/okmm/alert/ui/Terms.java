package com.okmm.alert.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.R;
import com.okmm.alert.service.AuthorityManager;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class Terms {
	
  private View registrationView = null;
  private Button bnAccept = null;
  private static TextView tvDescription = null;
  private static Terms singleton = null;
  
  private static Context ctx = null;
  private static AlertDialog dialog = null;
  
  private Terms(){
	bind();
	createDialog();
  }
  
  public static Terms getInstance(Context ctx){
	if(singleton == null){
	  Terms.ctx = ctx;
	  singleton = new Terms();
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
  
  public void dimiss(){
	dialog.dismiss();
  }
  
  public boolean isShowing(){
	return dialog.isShowing();
  }
  
  private void bind(){
	registrationView = LayoutInflater.from(ctx).inflate(R.layout.terms, null); 
	bnAccept = (Button) registrationView.findViewById(R.id.bnAccept);
	tvDescription = (TextView) registrationView.findViewById(R.id.tvDescription);
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                  .setCancelable(false);
    dialog = alertBuilder.setView(registrationView).create(); 
    bnAccept.setOnClickListener(onClickListener);
  }

  private void callPrivacyService(){
	RequestParams params = new RequestParams();
	RestClient.post("terminos", params, new RestResponseHandler(ctx, false) {
	  @Override
	  public void onSuccess(JSONObject response) throws JSONException {
		 try{
		     tvDescription.setText(response.getString("texto"));
		     bnAccept.setEnabled(true);
		 } catch(Exception e){
			 e.printStackTrace();
			 tvDescription.setText(ctx.getString(R.string.errUnavailable));
		 }
	  }    
	});
  }


}
