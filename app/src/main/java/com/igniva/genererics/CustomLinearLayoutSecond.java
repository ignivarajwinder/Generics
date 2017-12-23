
package com.igniva.genererics;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ignivaandroid23 on 30/10/17.
 */

public class CustomLinearLayoutSecond extends LinearLayout implements View.OnClickListener{
    private String layoutType = null;
    LayoutInflater inflater;
    Context context;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    private int mBackgroundColor = Color.rgb(200, 115, 205);

    public CustomLinearLayoutSecond(Context context) {
        super(context);
    }

    public CustomLinearLayoutSecond(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        inflater = LayoutInflater.from(context);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.yourView);
            mBackgroundColor=a.getColor(R.styleable.yourView_default_layout_background, mBackgroundColor);
            layoutType=a.getString(R.styleable.yourView_layoutType);
            a.getResourceId(R.styleable.yourView_layoutTypeInt,0);
            a.recycle();
        }
//            layoutType="TextView";
            setLayoutOnInternetConnectionActivated(context,layoutType);
    }

    public CustomLinearLayoutSecond(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public CustomLinearLayoutSecond(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
    }

    private void setLayoutOnInternetConnectionActivated(Context context, String layoutType) {

        if (isInternetConnection(context))
        {
        switch (layoutType)
        {
            case "TextView":
                removeAllViews();
                setLayoutWithTextView(context);
                break;
            case "RecyclerView":
                setLayoutWithRecyclerView(context);
                break;
            default:
                removeAllViews();
                setLayoutWithTextView(context);
                break;
        }

        }
        else
        {
            Toast.makeText(context, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            setNoInternetConnectionLayout(context);

        }

    }

    private void setLayoutWithRecyclerView(Context context) {
        view=inflater.inflate(R.layout.layout_recycler_view, this);
    }

    private void setLayoutWithTextView(final Context context) {
        view=inflater.inflate(R.layout.layout_main, this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                removeAllViews();
                setLayoutOnInternetConnectionActivated(context,"TextView");

            }


        });

    }

    private void setNoInternetConnectionLayout(Context context) {
        removeAllViews();
        view=inflater.inflate(R.layout.no_internet_connection, this);
        Button buttonRetry=(Button)view.findViewById(R.id.retry);
        buttonRetry.setOnClickListener(this);
        TextView textViewWifi=(TextView) view.findViewById(R.id.tv_wifi);
        textViewWifi.setOnClickListener(this);
        TextView textViewCellularData=(TextView)view.findViewById(R.id.tv_cellular_data);
        textViewCellularData.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.retry:
                setLayoutOnInternetConnectionActivated(context,"TextView");
                break;

            case R.id.tv_wifi:
                /**
                 * Intent action to open wifi screen
                 */
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.tv_cellular_data:
             openMobileDataScreen();
//                try {
 //                    setMobileDataEnabled(context,true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                break;
        }
    }

    private void openMobileDataScreen() {
        //                Intent intent = new Intent(Intent.ACTION_MAIN);
        //                intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
        //                context.startActivity(intent);



        /**
         * Intent action to open Mobile Data Screen
         */
                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(i);
    }

    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
        final ConnectivityManager conman =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(
                    iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
