package com.cricflame.cricflame;

import android.app.Activity;
import android.util.Log;


import com.cricflame.cricflame.Model.InterruptThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Deepak Sharma on 05/10/2017.
 */

public class Global {

    public static String BETFAIR_URL_TOKEN;
    public static String BETFAIR_URL_GETLAYS;
    public static String URL;
    public static String BETFAIR_URL;
    public static String VIDEODATA_URL;
    public static String WEB_URL;
    public static String PHOTO_URL;
    public static String TIPS_IMAGE_URL;
    public static String PLAYER_URL;
    public static String ANALYSIS_IMAGE_URL;
    public static String livelineteam1;
    public static String IMG_URL;
    public static String IMG_VENUE_URL;
    public static String STATS_URL;

    public static String Tour_Id="";
    public static String Tour_name="";
    public static String SCHEDULE_ID="";
    public final static int CONNECTION_TIME_OUT = 10000 * 10;
    public static String DUMMY1_BOOK_URL;
    public static String DUMMY1_SESSION_URL;
    public static String DUMMY2_BOOK_URL;
    public static String DUMMY2_SESSION_URL;
    public static String DUMMY3_BOOK_URL;
    public static String DUMMY3_SESSION_URL;
    public static String IPL_URL;
    public static Long Version_Number= 0l;
    public static String Comman_Url;
    public static String RESULT_URL;
    public static String LIVEMATCH_URL;
    public static String JAVA_URL;
    public static String CRICK_EXCHANGE_MATCH;
    public static String SCORE_CARD_URL;
    public static String LOTUS_URL;
    public static String CRICBUZZ_POINTS_TABLE;
    public static String BETFAIR_RATE_URL;
    public static String BETFAIR_API_KEY;

    public interface ResultCallBack {
        void onSuccess(int responseCode, String strResponse);

        void onCancelled();

        void onFailure(int responseCode, String strResponse);
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    public static void HandleError(final Activity context, String error) {
        try {

            if (error != null)
                if (error.contains("No authentication")) {
                }

        } catch (Exception e) {
            e.printStackTrace();
            try {

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            if (context != null) {
//                FunctionUtils.ShowToast(context, error);
            }

        }
    }


    public static void callGet(String methodName, boolean isJWTNeeded, ResultCallBack resultCallBack) {
        java.net.URL url;
        HttpURLConnection urlConnection = null;
        int responseCode = 0;
        try {
            url = new URL(URL + methodName);
            Log.d("Get :", url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIME_OUT);
            urlConnection.setReadTimeout(CONNECTION_TIME_OUT);
            new InterruptThread(new Thread(),urlConnection);

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Language", "en-US");
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");

            responseCode = urlConnection.getResponseCode();

            resultCallBack.onSuccess(responseCode, getResponseText(urlConnection.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
            String abc = e.getLocalizedMessage();
            if (abc == null) {
                abc = "";
            }
            resultCallBack.onFailure(responseCode, abc);
        }
    }
    private static String getResponseText(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        if (is != null) {


            int line;
            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.read()) != -1) {
                    sb.append((char) line);
                }
            } catch (IOException e) {
                StringBuilder error = new StringBuilder();
                error.append("Message = ").append(e.getMessage()).append("Cause = ").append(e.getCause());
                sb = error;
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        StringBuilder error = new StringBuilder();
                        error.append("Message = ").append(e.getMessage()).append("Cause = ").append(e.getCause());
                        sb = error;
                    }
                }
            }
        }
        Log.d("Resposnse", sb.toString());
        return sb.toString();
    }

}

