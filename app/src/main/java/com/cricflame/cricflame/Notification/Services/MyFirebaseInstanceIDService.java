package com.cricflame.cricflame.Notification.Services;

/**
 * Created by Deepak Sharma on 31/10/2017.
 */

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Global;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import com.cricflame.cricflame.Model.SharedPrefManager;
import com.cricflame.cricflame.Notification.Model.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Belal on 03/11/16.
 */


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeToken(refreshedToken);
        sendRegistrationToServer(refreshedToken);
        FirebaseMessaging.getInstance().subscribeToTopic("shashi");


        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }



    public  void sendRegistrationToServer(final String token) {
        // sending fcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        StringRequest request = new StringRequest(Request.Method.POST, (Global.Comman_Url+"saveDeviceToken"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    System.out.print(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("devicetoken", token);
                params.put("devicename",getDeviceName());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(MyFirebaseInstanceIDService.this).add(request);

    }


    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}