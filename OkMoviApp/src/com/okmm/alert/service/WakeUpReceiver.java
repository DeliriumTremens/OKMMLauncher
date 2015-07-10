package com.okmm.alert.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

public class WakeUpReceiver extends BroadcastReceiver
{   
	CronTimer alarm = new CronTimer();
    @Override
    public void onReceive(Context context, Intent intent)
    {   
        	if (SettingsHelper.getUserId(context) == 0) {
    			try {
    				new Registration(context).show();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}		
    		}
            alarm.SetAlarm(context);
    }
}
