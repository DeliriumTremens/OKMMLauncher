package com.okmm.alert;

import com.okmm.alert.service.CronTimer;
import com.okmm.alert.service.WakeUpReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Tester extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new WakeUpReceiver().onReceive(this, new Intent(this, Tester.class));
		new CronTimer().callWSCampaigns(this);
	}

}
