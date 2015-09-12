package com.example.den.hw19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.den.hw19.adapters.NotificationsAdapter;
import com.example.den.hw19.db.DataBaseHelper;
import com.example.den.hw19.services.MyGcmListenerService;
import com.example.den.hw19.utils.NotificationsUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "Ololo";

    private ListView lvNotification;

    private NotificationsAdapter adapter;

    private DataBaseHelper mDataBaseHelper;

    private BroadcastReceiver broadcastReceiver;

    private IntentFilter mIntentFilter;

    private NotificationsUtils mNotificationsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToken();

        mDataBaseHelper = new DataBaseHelper(this);

        lvNotification = (ListView) findViewById(R.id.lvNotification_AM);
        lvNotification.setOnItemClickListener(this);

        adapter = new NotificationsAdapter(this, mDataBaseHelper.getAllNotifications());

        lvNotification.setAdapter(adapter);

        startReceiver();

    }

    private void getToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d(TAG, "Token: " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startReceiver(){

        broadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                switch (action) {
                    case MyGcmListenerService.NEW_NOTIFICATION:
                        lvNotification.setAdapter(new NotificationsAdapter(getApplicationContext(),
                                mDataBaseHelper.getAllNotifications()));
                        break;
                }
            }
        };
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(MyGcmListenerService.NEW_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, mIntentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mNotificationsUtils = new NotificationsUtils();
        mNotificationsUtils.createNotification(this,
                mDataBaseHelper.getAllNotifications().get(position).getMessage(),
                mDataBaseHelper.getAllNotifications().get(position).getTitle(),
                mDataBaseHelper.getAllNotifications().get(position).getSubtitle(),
                mDataBaseHelper.getAllNotifications().get(position).getTicketText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
