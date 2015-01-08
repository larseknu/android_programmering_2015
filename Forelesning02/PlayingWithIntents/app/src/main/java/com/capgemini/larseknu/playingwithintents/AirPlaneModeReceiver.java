package com.capgemini.larseknu.playingwithintents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AirPlaneModeReceiver extends BroadcastReceiver {
    public AirPlaneModeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean modeIsOn = intent.getBooleanExtra("state", false);
        Log.i("AirplaneModeReceiver", "Mode is " + (modeIsOn ? "on" : "off"));
    }
}
