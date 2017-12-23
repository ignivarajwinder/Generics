package com.igniva.genererics;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ignivaandroid23 on 30/10/17.
 */

public class CustomLinearLayout extends LinearLayout implements View.OnClickListener{
    private String layoutType = null;
    LayoutInflater inflater;
    Context context;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    Activity activity;
    private int mBackgroundColor = Color.rgb(200, 115, 205);
    private Utils.onCallBack onCallBack;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
         inflater = LayoutInflater.from(context);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.yourView);
            mBackgroundColor=a.getColor(R.styleable.yourView_default_layout_background, mBackgroundColor);
            layoutType=a.getString(R.styleable.yourView_layoutType);
            a.getResourceId(R.styleable.yourView_layoutTypeInt,0);
            a.recycle();
            this.setBackgroundColor(mBackgroundColor);
        }

        if (isInternetConnection(context))
        {
        setLayoutOnInternetConnectionActivated(context,layoutType);
        }
        else
        {
           setNoInternetConnectionLayout(context);

        }

    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;

//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.yourView);



    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
    }

    public  void setLayout(int layout)
    {
        removeAllViews();
        if (isInternetConnection(context)) {
            inflater.inflate(layout, this);
            this.setBackgroundColor(mBackgroundColor);
        }
        else
        {
            setNoInternetConnectionLayout(context);
        }
    }

    public  void setCustomColor(int color)
    {
        this.setBackgroundColor(color);
    }
    public void setContext(Activity activity, Utils.onCallBack onCallBack)
    {
        this.activity=activity;
        this.onCallBack=onCallBack;
    }

    private void setLayoutOnInternetConnectionActivated(Context context, String layoutType) {
        switch (layoutType)
        {
            case "TextView":
                view=inflater.inflate(R.layout.layout_main, this);
                setlayoutOnRefresh();
                break;
            case "RecyclerView":
                view=inflater.inflate(R.layout.layout_recycler_view, this);
                setlayoutOnRefresh();
                break;
        }


//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setColorSchemeResources(
//                R.color.colorPrimary, R.color.colorPrimaryDark,
//                R.color.colorAccent);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                setlayoutOnRefresh();
//
//            }
//
//
//        });

    }
    public void setlayoutOnRefresh()
    {
        if (onCallBack!=null) {
            CallBackPojo callBackPojo = new CallBackPojo<String>();
            callBackPojo.setValue("retry");
            onCallBack.onSuccess(callBackPojo);
        }
    }
    private void setNoInternetConnectionLayout(Context context) {

        view=inflater.inflate(R.layout.no_internet_connection, this);
        Button buttonRetry=(Button)view.findViewById(R.id.retry);
        buttonRetry.setOnClickListener(this);
    }


//    public void initCustomAttribute(AttributeSet attrs, Context context) {
//        layoutTypedArray = context.obtainStyledAttributes(attrs,
//                R.styleable.CustomLayout);
//        CharSequence layoutTypeCharSequence = layoutTypedArray
//                .getString(R.styleable.CustomLayout_layout_type);
//
//        if (layoutTypeCharSequence != null) {
//            activeLayout = layoutTypeCharSequence.toString();
//
//        }
//        Log.i("Check for Active layout", activeLayout);
//        if (activeLayout.equalsIgnoreCase("video")) {
//            //use your attribute the way you want
//
//        }
//        layoutTypedArray.recycle();
//    }


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
                    callApi();
                break;
        }
    }

    private void callApi() {
        CallBackPojo callBackPojo=new  CallBackPojo<String>();
        callBackPojo.setValue("Retry click ho gya");
        onCallBack.onSuccess(callBackPojo);

    }
}
