package at.htl.schichtbetrieb.services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends IntentService {


    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Service","Service gebunden");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String milliseconds = intent.getDataString();
        long newMilliseconds = Long.decode(milliseconds);
        while(true) {
            newMilliseconds += 60000;
            Intent localIntent = new Intent("TimerBroadcast").putExtra("Time", newMilliseconds);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}