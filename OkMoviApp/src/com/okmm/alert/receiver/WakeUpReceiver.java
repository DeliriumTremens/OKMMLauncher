package com.okmm.alert.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.okmm.alert.timer.EventTimer;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

public class WakeUpReceiver extends BroadcastReceiver
{   
	EventTimer alarm = new EventTimer();
    @Override
    public void onReceive(Context context, Intent intent)
    {   
        	if (SettingsHelper.getRegistration(context) == 0) {
    			try {
    				new Registration(context.getApplicationContext()).show();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}		
    		}
            alarm.SetAlarm(context);
    }
}
