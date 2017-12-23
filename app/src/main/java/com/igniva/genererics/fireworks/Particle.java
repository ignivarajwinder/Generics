package com.igniva.genererics.fireworks;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */

/**
 * Solid particles
 *
 * @author Administrator
 *
 */
public class Particle {
    int color;// Color
    int r;// Radius
    double vertical_v;// Vertical velocity
    double horizontal_v;// The horizontal velocity
    int startX;// The initial x coordinates
    int startY;// The initial y coordinates
    int x;// Real time x coordinates
    int y;// Real time y coordinates
    double startTime;// Start time

    public Particle(int color, int r, double vertical_v, double horizontal_v,
                    int x, int y, double startTime) {
        this.color = color;
        this.r = r;
        this.vertical_v = vertical_v;
        this.horizontal_v = horizontal_v;
        this.startX = x;
        this.startY = y;
        this.x = x;
        this.y = y;
        this.startTime = startTime;
    }
}
