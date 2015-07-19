package com.okmm.alert.ui;

import com.okmm.alert.R;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.vo.bean.Campaign;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Popup {
	
  private View popupView = null;
  private Handler handler = new Handler();
  
  private Context ctx = null;
  private AlertDialog dialog = null;
  private Campaign campaign = null;
  
  
  public Popup(Context ctx, Campaign campaign){
	this.ctx = ctx;
	this.campaign = campaign;
	init();
  }
  
  private Runnable closeDisplayer = new Runnable() {
	public void run() {
	    ((ImageButton) popupView.findViewById(R.id.ibClose)).setVisibility(View.VISIBLE);
	}
  };
  
  public void show(){
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT
    		                 , ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	dialog.show();
	handler.postDelayed(closeDisplayer, Config.TIME_TO_CLOSE);
  }
  
  private void init(){
	bind();
	createDialog();
  }
  
  private void bind(){
	popupView = LayoutInflater.from(ctx).inflate(R.layout.popup, null);
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                                             .setCancelable(false);
    ImageView ivAdvertisment = (ImageView) popupView.findViewById(R.id.ivAdvertisment);
    ImageButton ibClose = (ImageButton) popupView.findViewById(R.id.ibClose);
    ibClose.setVisibility(View.GONE);
    ivAdvertisment.setImageBitmap(BitmapFactory.decodeFile(campaign.getPopup()));
    ibClose.setOnClickListener(new onClickIbClose());
    dialog = alertBuilder.setView(popupView).create(); 
  }
  
  private class onClickIbClose implements OnClickListener{
	@Override
	public void onClick(View v) {
	  campaign.setStatus(Config.CAMPAIGN_STATUS.DONE.getId());
	  new CampaignDAO(ctx).update(campaign);
	  dialog.dismiss();
	}
  }
  
}
