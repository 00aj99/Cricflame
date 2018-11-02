package com.cricflame.cricflame;


import android.content.Context;

public class CheckProxy {
    //private static final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    private Context context;
    String proxyAddress;
    int proxyPort;
    boolean isProxy;

    public CheckProxy(Context context) {
        this.context = context;
    }

    public CheckProxy() {
        try {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
            if(proxyAddress.equals(null)){
                isProxy=true;
            }
            isProxy = false;
        }catch (NullPointerException e){
            isProxy = true;
        }
    }

    public Boolean isProxyDisabled(){
        return isProxy;
    }

}



