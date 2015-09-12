package com.example.den.hw19.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.den.hw19.MainActivity;
import com.example.den.hw19.utils.NotificationsUtils;
import com.example.den.hw19.db.DataBaseHelper;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Den on 10.09.15.
 */
public class MyGcmListenerService extends GcmListenerService{

    private DataBaseHelper mDataBaseHelper;
    private NotificationsUtils mNotificationsUtils;
    public static final String NEW_NOTIFICATION = "com.example.den.hw19.NEW_NOTIFICATIONS";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(MainActivity.TAG, "Got message: " + data.getString("message"));
        showNotification(data);
        addToDB(data);
        pushIntent();
    }
    public void showNotification(Bundle bundle) {
        String message = bundle.getString("message");
        String title = bundle.getString("title");
        String subtitle = bundle.getString("subtitle");
        String tickerText = bundle.getString("tickerText");

        mNotificationsUtils = new NotificationsUtils();
        mNotificationsUtils.createNotification(this,message, title, subtitle, tickerText);
    }

    private void pushIntent() {
        Intent intent = new Intent(NEW_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void addToDB(Bundle bundle) {
        mDataBaseHelper = new DataBaseHelper(getApplicationContext());
        mDataBaseHelper.addNewNotification(bundle.getString("message"),
                                           bundle.getString("title"),
                                           bundle.getString("subtitle"),
                                           bundle.getString("tickerText"));
    }


}
