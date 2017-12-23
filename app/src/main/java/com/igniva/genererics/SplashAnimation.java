package com.igniva.genererics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ignivaandroid23 on 30/10/17.
 */

public class SplashAnimation extends AppCompatActivity {
    TextView textViewIndividual, textViewInstitution;
    LinearLayout ll_bottom, linearLayout;
    private static final String TAG = "MainActivityMultipleTables";
    private static final boolean DO_XML = false;

    private ViewGroup mMainView;
    private SplashView mSplashView;
    private View mContentView;
    private Handler mHandler = new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        linearLayout=(LinearLayout)findViewById(R.id.ll_main);
//        LayoutTransition layoutTransition=new LayoutTransition();
//        layoutTransition.setDuration(500);
//        linearLayout.setLayoutTransition(layoutTransition);
//
//        textViewIndividual=(TextView)findViewById(R.id.tv_individual);
//        textViewInstitution=(TextView)findViewById(R.id.tv_institution);
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                textViewIndividual.setVisibility(View.VISIBLE);
//            }
//        },2000);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                textViewInstitution.setVisibility(View.VISIBLE);
//            }
//        },1000);


        // change the DO_XML variable to switch between code and xml
        if(DO_XML){
            // inflate the view from XML and then get a reference to it
            setContentView(R.layout.activity_main);
            mMainView = (ViewGroup) findViewById(R.id.main_view);
            mSplashView = (SplashView) findViewById(R.id.splash_view);
        } else {
            // create the main view and it will handle the rest
            mMainView = new MainView(getApplicationContext());
            mSplashView = ((MainView) mMainView).getSplashView();
            setContentView(mMainView);
        }

        // pretend like we are loading data
        startLoadingData();
    }

    private void startLoadingData(){
        // finish "loading data" in a random time between 1 and 3 seconds
        Random random = new Random();
        mHandler.postDelayed(new Runnable(){
            @Override
            public void run(){
                onLoadingDataEnded();
            }
        }, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded(){
        Context context = getApplicationContext();
        // now that our data is loaded we can initialize the content view
        mContentView = new ContentView(context);
        // add the content view to the background
        mMainView.addView(mContentView, 0);

        // start the splash animation
        mSplashView.splashAndDisappear(new SplashView.ISplashListener(){
            @Override
            public void onStart(){
                // log the animation start event
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction){
                // log animation update events
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd(){
                // log the animation end event
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                mSplashView = null;
                if(!DO_XML){
                    // if inflating from code we will also have to free the reference in MainView as well
                    // otherwise we will leak the View, this could be done better but so far it will suffice
                    ((MainView) mMainView).unsetSplashView();
                }
            }
        });
    }

}
