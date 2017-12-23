package com.igniva.genererics.custum_layout_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UpdateReceiver extends BroadcastReceiver {

    Context mContext;
    public UpdateReceiver() {

    }
    public UpdateReceiver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof BaseActivity) {
//            boolean isVisible = MyApplication.isActivityVisible();// Check if
////            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);
//            // If it is visible then trigger the task else do nothing
//            if (isVisible == true)
//
//            {

                if (isInternetConnection(context)) {
                    ((BaseActivity)context).customLinearLayout.setLayout();
                } else {
                    ((BaseActivity)context).customLinearLayout.setNoInternetConnectionLayout(context);
                }
//            }
        }



    }

    public boolean isInternetConnection(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        try {

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getType() == ConnectivityManager.TYPE_WIFI)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedWifi = true;
                if (ni.getType() == ConnectivityManager.TYPE_MOBILE)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HaveConnectedWifi || HaveConnectedMobile;
    }

}