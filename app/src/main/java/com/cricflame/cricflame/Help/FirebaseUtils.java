package com.cricflame.cricflame.Help;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by yashlam on 2/5/2018.
 */

    public class FirebaseUtils {
        private static FirebaseDatabase mDatabase;
        private static FirebaseApp secondApp;
        private static FirebaseDatabase secondaryDatabase;
        /*private static DatabaseReference mRootref;*/

        public static FirebaseDatabase getInstance() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabase.setPersistenceEnabled(true);
            }
            return mDatabase;
        }


        public static FirebaseDatabase getSecondaryDatabase(Context context){

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:943109609317:android:95d03a9cf07b9d1a") // Required for Analytics.
                    .setApiKey("AIzaSyD1lEyygYMEiV1HFKWlvU3TsVEyNSRktBo") // Required for Auth.
                    .setDatabaseUrl("https://cricflame-8rde2-cb4a5.firebaseio.com/") // Required for RTDB.
                    .build();

            Long tsLong = System.currentTimeMillis() / 1000;
            Random r = new Random();
            int num = (r.nextInt(80) + 50);
            String ts = tsLong.toString()+num+"";


            //DB REFERENCE//
            secondApp = FirebaseApp.initializeApp(context, options, ts);
            secondaryDatabase = FirebaseDatabase.getInstance(secondApp);
            return  secondaryDatabase;
        }

        public static FirebaseDatabase getThirdDatabase(Context context){
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:943109609317:android:95d03a9cf07b9d1a") // Required for Analytics.
                    .setApiKey("AIzaSyD1lEyygYMEiV1HFKWlvU3TsVEyNSRktBo") // Required for Auth.
                    .setDatabaseUrl("https://cricflame-8rde2-68b5b.firebaseio.com/") // Required for RTDB.
                    .build();


            Long tsLong = System.currentTimeMillis() / 1000;
            Random r = new Random();
            int num = (r.nextInt(80) + 60);
            String ts = tsLong.toString()+num+"";


            //DB REFERENCE//
            secondApp = FirebaseApp.initializeApp(context, options, ts);
            secondaryDatabase = FirebaseDatabase.getInstance(secondApp);
            return  secondaryDatabase;
        }




    }
