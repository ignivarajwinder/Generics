package com.igniva.genererics;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by ignivaandroid23 on 30/10/17.
 */

public class ContentView extends LinearLayout {


    private  Context context;
    private  LayoutInflater inflater;

    public ContentView(Context context) {
        super(context);
        this.context=context;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.activity_splash_main, this);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
}
