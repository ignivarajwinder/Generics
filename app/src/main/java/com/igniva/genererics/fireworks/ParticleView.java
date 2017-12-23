package com.igniva.genererics.fireworks;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.igniva.genererics.R;

import java.util.ArrayList;

/**
 * To class
 *
 * @author Administrator
 *
 */
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback , View.OnTouchListener {
    public static  int DIE_OUT_LINE = 0;// Screen under the boundary
    DrawThread dt;
    ParticleSet ps;
    ParticleThread pt;
    Context context;
    String fps = "FPS:N/A";// Frame rate
    Canvas mainCanvas;
    int startX,startY;

    int startPoint;


    public ParticleView(Context context) {
        super(context);
        this.context=context;
        this.getHolder().addCallback(this);
        this.setOnTouchListener(this);
        dt = new DrawThread(this, getHolder());
        ps = new ParticleSet(context);
        pt = new ParticleThread(this);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
         DIE_OUT_LINE = size.y;


        int mWidth= context.getResources().getDisplayMetrics().widthPixels;
        int mHeight= context.getResources().getDisplayMetrics().heightPixels;

        startPoint=width/4;



    }

   @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Start a thread



        if (!dt.isAlive()) {
            dt.start();
        }

        if (!pt.isAlive()) {
            pt.start();
        }

        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas(null);
            synchronized (holder) {
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        dt.flag = false;
        dt = null;
        pt.flag = false;
        pt = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mainCanvas=canvas;
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.trophy);

        int mWidth= this.getResources().getDisplayMetrics().widthPixels;
        int mHeight= this.getResources().getDisplayMetrics().heightPixels;

        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        Paint paintBitmap = new Paint();
        paintBitmap.setFilterBitmap(true);
//        canvas.drawBitmap(icon, null, dest, paintBitmap);
        canvas.drawBitmap(icon, (canvas.getWidth()/2-(icon.getWidth()/2)), (float) (canvas.getHeight() * .15), null);
    }

    public void doDraw(Canvas canvas) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.trophy);
        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        Paint paintBitmap = new Paint();
        paintBitmap.setFilterBitmap(true);
//        canvas.drawBitmap(icon, null, dest, paintBitmap);
        canvas.drawBitmap(icon, (canvas.getWidth()/2-(icon.getWidth()/2)), (float) (canvas.getHeight() * .15), null);

        canvas.drawColor(Color.TRANSPARENT);// Clear the screen
        ArrayList<Particle> particleSet = ps.particleSet;
        Paint paint = new Paint();
        for (int i = 0; i <particleSet.size(); i++) {
            Particle p = particleSet.get(i);
            paint.setColor(p.color);
            int tempX = p.x;
            int tempY = p.y;
            int tempRadius = (int)(p.r*1.7);

            Log.d("ParticleView","tempX-"+tempX+" tempY-"+tempY+" tempRadius-"+tempRadius);

            RectF oval = new RectF(tempX, tempY, tempX + 2 * tempRadius, tempY
                    + 2 * tempRadius);
            canvas.drawOval(oval, paint);// Ellipse particle

            RectF oval2 = new RectF(tempX+startPoint, tempY, (tempX+startPoint) + (2 * tempRadius), tempY
                    + 2 * tempRadius);
            canvas.drawOval(oval2, paint);// Ellipse particle

            RectF oval3 = new RectF(tempX+startPoint+startPoint, tempY, (tempX+startPoint+startPoint) + (2 * tempRadius), tempY
                    + 2 * tempRadius);
            canvas.drawOval(oval3, paint);// Ellipse particle
//
//            RectF oval4 = new RectF(tempX+600, tempY, tempX+600 + 2 * tempRadius, tempY
//                    + 2 * tempRadius);
//
//            canvas.drawOval(oval4, paint);// Ellipse particle

        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        canvas.drawText(fps, 50, 50, paint);// Frame rate

    }

    public void doDraw(Canvas canvas,int startX,int startY) {
        this.mainCanvas=canvas;
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.trophy);

        int mWidth= this.getResources().getDisplayMetrics().widthPixels;
        int mHeight= this.getResources().getDisplayMetrics().heightPixels;

        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        Paint paintBitmap = new Paint();
        paintBitmap.setFilterBitmap(true);
//        canvas.drawBitmap(icon, null, dest, paintBitmap);
        canvas.drawBitmap(icon, (canvas.getWidth()/2-(icon.getWidth()/2)), (float) (canvas.getHeight() * .15), null);


        canvas.drawColor(Color.TRANSPARENT);// Clear the screen
        ArrayList<Particle> particleSet = ps.particleSet;
        Paint paint = new Paint();
        for (int i = 0; i <particleSet.size(); i++) {
            Particle p = particleSet.get(i);
            paint.setColor(p.color);

            ps.tempX=startX;

//            p.x = startX;
//            p.y = startY;

            int tempX = p.x;
            int tempY = p.y;


            int tempRadius = (int)(p.r*1.7);
            RectF oval = new RectF(tempX, tempY, tempX + 2 * tempRadius, tempY
                    + 2 * tempRadius);
            canvas.drawOval(oval, paint);// Ellipse particle

//            RectF oval2 = new RectF(tempX+200, tempY, tempX+200 + 2 * tempRadius, tempY
//                    + 2 * tempRadius);
//            canvas.drawOval(oval2, paint);// Ellipse particle
//
//            RectF oval3 = new RectF(tempX+400, tempY, tempX+400 + 2 * tempRadius, tempY
//                    + 2 * tempRadius);
//
//            canvas.drawOval(oval3, paint);// Ellipse particle
//
//            RectF oval4 = new RectF(tempX+600, tempY, tempX+600 + 2 * tempRadius, tempY
//                    + 2 * tempRadius);
//
//            canvas.drawOval(oval4, paint);// Ellipse particle

        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        canvas.drawText(fps, 50, 50, paint);// Frame rate

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {


        dt.stopThread=false;
        pt.stopThread=false;

        this.onDraw(mainCanvas);

        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }

        startX=x;
        startY=y;
//        ps.tempX=x;
//        ps.tempY=y;
        Toast.makeText(context, ""+x+" "+ y, Toast.LENGTH_SHORT).show();


//        if (!dt.isAlive()) {
//            dt.start();
//        }
//
//        if (!pt.isAlive()) {
//            pt.start();
//        }

//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (dt.isAlive()) {
//                    dt.stopThread=true;
////                    dt.interrupt();
//                }
//
//                if (pt.isAlive()) {
//                    pt.stopThread=true;
////                    pt.interrupt();
//                }
//            }
//        },3000);
        return false;
    }

}