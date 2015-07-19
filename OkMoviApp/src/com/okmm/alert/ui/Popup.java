package com.okmm.alert.ui;

import com.okmm.alert.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Popup {
	
  private View popupView = null;
  
  private Context ctx = null;
  private AlertDialog dialog = null;
  private String filePath = null;
  private String link = null;
  
  
  public Popup(Context ctx, String filePath, String link){
	this.ctx = ctx;
	this.filePath = filePath;
	this.link = link;
	init();
  }
  
  public void onClickIbClose(View clickedView){
	dialog.dismiss();
  }
  
  public void onClickIvAdvertisment(View clickedView){
  }
  
  public void show(){
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT
    		                 , ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
    ImageView ivAdvertisment = (ImageView) popupView.findViewById(R.id.ivAdvertisment);
    ImageButton ibClose = (ImageButton) popupView.findViewById(R.id.ibClose);
    ivAdvertisment.setImageBitmap(BitmapFactory.decodeFile(filePath));
    ibClose.setOnClickListener(new onClickIbClose());
    dialog = alertBuilder.setView(popupView).create(); 
  }
  
  private class onClickIbClose implements OnClickListener{
	@Override
	public void onClick(View v) {
		dialog.dismiss();
	}
  }
  
}
