package com.igniva.genererics.fireworks;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Responsible for manage and add the particle object
 *
 * @author Administrator
 *
 */
public class ParticleSet {
    ArrayList<Particle> particleSet;// The collection object storage particle
    public int tempX = 260;
    Context context;
    int tempY = (int) (100 - 10 * (Math.random()));
    public ParticleSet() {
        particleSet = new ArrayList<Particle>();
    }

    public ParticleSet(Context context) {
        this.context=context;
        particleSet = new ArrayList<Particle>();

        int mWidth= context.getResources().getDisplayMetrics().widthPixels;
        int mHeight= context.getResources().getDisplayMetrics().heightPixels;

        tempX=mWidth/4;
        tempY=260;

        Log.d("ParticleSet","tempX-"+tempX+" tempY-"+tempY);



    }

    /**
     * Adds the specified number of particle to particle set object
     *
     * @param count
     * @param startTime
     */
    public void add(int count, double startTime) {
        for (int i = 0; i <count; i++) {
            int tempColor = this.getColor(i);
            int tempR = 1;
            // Randomly generated particles vertical velocity
            double tempv_v = -30 + 10 * (Math.random());
            // Randomly generated particles of horizontal velocity
            double tempv_h = 10 - 20 * (Math.random());

            // Randomly generated particles Y coordinates, 90~100

            // Create particle object
            Particle particle = new Particle(tempColor, tempR, tempv_v,
                    tempv_h, tempX, tempY, startTime);
            particleSet.add(particle);// Added to the collection
        }
    }

    /**
     * According to the index of different colors
     *
     * @param i
     * @return
     */
    public int getColor(int i) {
        int color = Color.RED;
        switch (i % 4) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.GRAY;
                break;
        }
        return color;
    }
}
