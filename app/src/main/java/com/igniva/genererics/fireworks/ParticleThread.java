package com.igniva.genererics.fireworks;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */


import java.util.ArrayList;

/**
 * Add particles and change the trajectory of thread
 *
 * @author Administrator
 *
 */
public class ParticleThread extends Thread {
    boolean flag;// Mark thread execution
    ParticleView father;// A reference to the ParticleView object
    int sleepSpan =30;// Thread to sleep time
    double time = 10;// The time axis of the physics engine
    double span = 0.9;// Each computational particle displacement time interval
    boolean stopThread;
    public ParticleThread(ParticleView father) {
        this.father = father;
        this.flag = true;
    }

    @Override
    public void run() {
        while (flag) {

            if (stopThread)
            {
                return;
            }

            father.ps.add(2, time);// Every time you add 2 particles
            // Gets the collection of particles
            ArrayList<Particle> tempSet = father.ps.particleSet;
            int count = tempSet.size();
            // Traversing the particle set to modify its trajectory
            for (int i = 0; i <count; i++) {
                Particle particle = tempSet.get(i);
                // Calculated from the start to now after a time
                double timeSpan = time - particle.startTime;
                // Calculation of X coordinates
                int tempx = (int) (particle.startX + particle.horizontal_v
                        * timeSpan);
                // Calculation of Y coordinates
                int tempy = (int) (particle.startY + 0.9 * timeSpan * timeSpan + particle.vertical_v
                        * timeSpan);
                if (tempy > ParticleView.DIE_OUT_LINE) {// If the particles over the screen edge
                    tempSet.remove(particle);// Remove
                    count = tempSet.size();// Reset particle number
                }
                // Modify the particle coordinates
                particle.x = tempx;
                particle.y = tempy;
            }
            time += span;// Extend the time
            try {
                Thread.sleep(sleepSpan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
