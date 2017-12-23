package com.igniva.genererics.custum_layout_sample;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.igniva.genererics.R;

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
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_custum_layout_sample);
            customLinearLayout = (CustomLinearLayout) findViewById(R.id.cll_main_second);

            registerReceiver(
                    new UpdateReceiver(),
                    new IntentFilter(
                            ConnectivityManager.CONNECTIVITY_ACTION));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
