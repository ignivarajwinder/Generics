package com.igniva.genererics.fireworks;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Drawing the screen thread
 *
 * @author Administrator
 *
 */
public class DrawThread extends Thread {
    ParticleView pv;
    SurfaceHolder holder;
    boolean flag;
    int sleepSpan = 15;
    long start = System.nanoTime();
    int count = 0;// Record the variable used to calculate the number of frames, frame rate
    public boolean stopThread;
    public DrawThread(ParticleView pv, SurfaceHolder holder) {
        this.pv = pv;
        this.holder = holder;
        this.flag = true;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (flag) {
            try {
                canvas = holder.lockCanvas(null);
                synchronized (holder) {
                    pv.doDraw(canvas);
//                    pv.doDraw(canvas,pv.startX,pv.startY);

                }

                if (stopThread)
                {
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            this.count++;
            if (count == 20) {
                count = 0;
                long tempStamp = System.nanoTime();
                long span = tempStamp - start;
                start = tempStamp;
                // Calculation of frame rate
                double fps = Math.round(100000000000.0 / span * 20) / 100.0;
                pv.fps = "FPS:" + fps;
            }
            try {
                Thread.sleep(sleepSpan);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
