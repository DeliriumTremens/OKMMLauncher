package com.okmm.alert;

import com.okmm.alert.service.CampaignLoader;
import com.okmm.alert.service.WakeUpReceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Tester extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new WakeUpReceiver().onReceive(this, new Intent(this, Tester.class));
		new CampaignLoader().callWSCampaigns(this);
	}

}
