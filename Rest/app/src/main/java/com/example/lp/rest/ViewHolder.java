package com.example.lp.rest;

/**
 * Created by maxencebeno on 26/01/2016.
 */
public class ViewHolder {

    protected int position;
    protected Ville ville;
    private int color;

    public ViewHolder() {
        position = 0;
        color = 0xFFFFFFFF;
    }

    public int getColor() {
        return color;
    }

    public void setRunning(boolean running) {
        ville.setRuning(running);
        if (running) {
            color = 0xFFffffb6;
        } else {
            color = 0xFFFFFFFF;
        }
    }
}
