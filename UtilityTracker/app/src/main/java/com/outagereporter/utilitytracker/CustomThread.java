package com.outagereporter.utilitytracker;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Miles on 12/3/2014.
 */
public class CustomThread extends Thread {
    private int position;
    private View convertView;
    private ViewGroup parent;


    CustomThread(int position, View convertView, ViewGroup parent){
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;

    }
    public void run(){}

}
