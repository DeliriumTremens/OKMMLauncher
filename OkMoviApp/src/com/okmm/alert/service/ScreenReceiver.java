package com.okmm.alert.service;

import com.okmm.alert.constant.Config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
         {
        	 Log.i(Config.LOG_TAG, "Screen ON");     
         }
    }
}