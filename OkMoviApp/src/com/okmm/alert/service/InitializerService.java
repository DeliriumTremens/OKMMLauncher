package com.okmm.alert.service;

import com.okmm.alert.timer.EventTimer;
import com.okmm.alert.ui.Registration;
import com.okmm.alert.util.SettingsHelper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class InitializerService extends Service
{
	EventTimer alarm = new EventTimer();
    public void onCreate()
    {
        super.onCreate();       
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
        return START_STICKY;
    }

   @Override        
   public void onStart(Intent intent, int startId)
    {
       if (SettingsHelper.getRegistration(this) == 0) {
			try {
				new Registration(this).show();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
        alarm.SetAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
}
