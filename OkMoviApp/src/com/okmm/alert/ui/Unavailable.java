package com.okmm.alert.ui;

import com.okmm.alert.R;
import com.okmm.alert.util.SettingsHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Unavailable {
	
  private View unavailableView = null;
  private static Unavailable singleton = null;
  
  private static Context ctx = null;
  private static AlertDialog dialog = null;
  
  private static EditText etPassword = null;
  private static Button bnAccept = null;
  private static TextView tvErrorMessage = null;
  
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
	dialog.dismiss();
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
    etPassword = (EditText) dialog.findViewById(R.id.etPassword);
    bnAccept = (Button) dialog.findViewById(R.id.bnAccept);
    tvErrorMessage = (TextView) dialog.findViewById(R.id.tvErrorMessage);
    bnAccept.setOnClickListener(new OnClickListener(){
	  @Override
	  public void onClick(View v) {
		String pass = etPassword.getText().toString();
		if(pass != null && pass.equals(SettingsHelper.getUserToken(ctx))){
		  dimiss();
		} else {
			tvErrorMessage.setText(ctx.getString(R.string.errPIN));
		}
	  }
    });
    etPassword.setOnFocusChangeListener(new OnFocusChangeListener(){
	  @Override
	  public void onFocusChange(View v, boolean hasFocus) {
	    if(hasFocus){
		  tvErrorMessage.setText("");
		}	
	  }
    });
  }

}
