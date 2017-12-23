package com.example.roomexample;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Biri Infotech on 12/16/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public CustomLinearLayout customLinearLayout;
    private UpdateReceiver receiver;

    public abstract void setUplayout();
    public abstract void setDataInVIewObjects();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custum_layout_sample);
        customLinearLayout = (CustomLinearLayout) findViewById(R.id.cll_main_second);

//        receiver = new UpdateReceiver(this); // Create the receiver
//
//        registerReceiver(receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")); // Register receiver
//
//        this.sendBroadcast(new Intent("android.net.conn.CONNECTIVITY_CHANGE")); // Send an example Intent

//        Intent intent = new Intent();
//        intent.setAction("android.net.conn.CONNECTIVITY_CHANGE"); sendBroadcast(intent);


        registerReceiver(
                new UpdateReceiver(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

    }


    public void setContentView(int layout, OnLayoutSet onLayoutSet)
    {
        customLinearLayout.setLayout(layout, onLayoutSet);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
