package com.cricflame.cricflame.Model;

import com.cricflame.cricflame.Global;

import java.net.HttpURLConnection;
import java.net.URLConnection;

/**
 * Created by Deepak Sharma on 24/11/2017.
 */

public class InterruptThread implements Runnable {
    Thread parent;
    URLConnection con;
    Global.ResultCallBack resultCallBack;

    public InterruptThread(Thread parent, URLConnection con) {
        this.parent = parent;
        this.con = con;
    }

    public void run() {
        try {
            Thread.sleep(Global.CONNECTION_TIME_OUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Timer thread forcing parent to quit connection");
        ((HttpURLConnection) con).disconnect();
        System.out.println("Timer thread closed connection held by parent, exiting");
    }
}
