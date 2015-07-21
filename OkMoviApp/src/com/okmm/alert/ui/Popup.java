package com.okmm.alert.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.R;
import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.db.dao.core.StaticsDAO;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;
import com.okmm.alert.vo.bean.Campaign;
import com.okmm.alert.vo.bean.Statics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
  
  public Popup(Context ctx){
	this.ctx = ctx;
	init();
  }
  
  public boolean isShowing(){
	return dialog.isShowing();
  }
  
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
  
  public void run(Campaign campaign){
	ImageView ivAdvertisment = (ImageView) popupView.findViewById(R.id.ivAdvertisment);
	ImageButton ibClose = (ImageButton) popupView.findViewById(R.id.ibClose);
	ivAdvertisment.setOnClickListener(new onClickIvAdvertisment());
	ivAdvertisment.setImageBitmap(BitmapFactory.decodeFile(campaign.getPopup()));
	ibClose.setVisibility(View.GONE);
	this.campaign = campaign;
	campaign.setStatus(Config.CAMPAIGN_STATUS.DISPLAYED.getId());
	new CampaignDAO(ctx).update(campaign);
	handler.postDelayed(closeDisplayer, Config.TIME_TO_CLOSE);
	dialog.show();
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
    ImageButton ibClose = (ImageButton) popupView.findViewById(R.id.ibClose);
    ibClose.setOnClickListener(new onClickIbClose());
    dialog = alertBuilder.setView(popupView).create(); 
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT
    		                 , ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  }
  
  private class onClickIbClose implements OnClickListener{
	@Override
	public void onClick(View v) {
	  campaign.setStatus(Config.CAMPAIGN_STATUS.DONE.getId());
	  new CampaignDAO(ctx).update(campaign);
	  dialog.dismiss();
	  callWSStatics(campaign, Config.ACTION_ID.CLOSED.getId());
	}
  }
  
  private class onClickIvAdvertisment implements OnClickListener{
	@Override
	public void onClick(View v) {
	  Intent browserIntent = null;
	  campaign.setStatus(Config.CAMPAIGN_STATUS.DONE.getId());
	  new CampaignDAO(ctx).update(campaign);
	  dialog.dismiss();
	  callWSStatics(campaign, Config.ACTION_ID.LINKED.getId());
	  if(campaign.getLink() != null && !campaign.getLink().isEmpty()){
		if(! campaign.getLink().startsWith("http")){
			campaign.setLink("http://" + campaign.getLink());
		}
		browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(campaign.getLink()));
		browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(browserIntent);
	  }
	}
  }
  
  private void upsertStatics(Campaign campaign, Integer statusId){
	Statics statics = null;
	StaticsDAO StaticsDAO = new StaticsDAO(ctx);
	if((statics = StaticsDAO.find(campaign.getId(), Config.ELEMENT_TYPE.POPUP
			                                            .getId())) == null) {
	  statics = new Statics();
	}
	statics.setCampaignId(campaign.getId());
	statics.setActionId(statusId);
	statics.setTypeId(Config.ELEMENT_TYPE.POPUP.getId());
	new StaticsDAO(ctx).upsert(statics);
	callWSStatics(campaign, statusId);
  }
  
  public void callWSStatics(Campaign campaign, Integer eventType){
	RequestParams params = new RequestParams(); 
	Integer userId = SettingsHelper.getUserId(ctx);
	if(userId > 0){
	  params.put("id_user", userId);
	  params.put("id_camp", campaign.getId());
	  params.put("elemento", Config.ELEMENT_TYPE.POPUP.getId());
	  params.put("action", eventType);
	  RestClient.post("camp_stat", params, new RestResponseHandler(ctx, false) {
		@Override
		public void onSuccess(final JSONObject response) throws JSONException {
		  System.out.println("Statics OK");
		}    
	  });
	} 
  }
  
}
