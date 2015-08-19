package com.okmm.alert.ui;

import java.util.Date;

import com.okmm.alert.R;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.vo.bean.Campaign;

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
	setBehavior();
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
	etPassword = (EditText) unavailableView.findViewById(R.id.etPassword);
    bnAccept = (Button) unavailableView.findViewById(R.id.bnAccept);
    tvErrorMessage = (TextView) unavailableView.findViewById(R.id.tvErrorMessage);
  }
  
  private void setBehavior(){
	bnAccept.setOnClickListener(new OnClickListener(){
	  @Override
	  public void onClick(View v) {
		String pass = etPassword.getText().toString();
		if(pass != null && pass.equals(SettingsHelper.getUserToken(ctx))){
		  CampaignDAO campaignDAO = new CampaignDAO(ctx);
	      Campaign campaign = campaignDAO.find();
	      if(campaign != null){
	    	campaign.setLoadedDate(new Date());
	    	campaignDAO.update(campaign);
	      }
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
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                                             .setCancelable(false);
    dialog = alertBuilder.setView(unavailableView).create(); 
  }

}
