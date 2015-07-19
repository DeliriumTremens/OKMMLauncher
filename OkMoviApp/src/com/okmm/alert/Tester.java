package com.okmm.alert;

import com.okmm.alert.service.Loader;
import com.okmm.alert.service.WakeUpReceiver;
import com.okmm.alert.util.SettingsHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Tester extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingsHelper.setUserId(this, 1);
		new WakeUpReceiver().onReceive(this, new Intent(this, Tester.class));
		
		//new CampaignLoader().callWSCampaigns(this);
	}

}
