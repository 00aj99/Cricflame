package com.cricflame.cricflame.Help;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yashlam on 2/5/2018.
 */

public class FirebaseQueries {
    private static DatabaseReference mRootRef;


    public FirebaseQueries(Context context) {
        mRootRef = FirebaseUtils.getSecondaryDatabase(context).getReference();
    }

    public static String findById(String str){
        String returnId="0";
        int id=0;
        if(str != null){
              id = convertToInteger(str);
        }

        DatabaseReference abc = mRootRef.child("make_league");
        final int finalId = id;
        abc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    long str1 = (long) snapshot.child("league_id").getValue();
                    int str = (int) str1;
                    if (str == finalId) {
                       // returnId = (String) snapshot.child("id").getValue();

                        // Toast.makeText(getActivity(), "make_league_id:: " + snapshot.child("id").getValue(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return returnId;
    }

    private static int convertToInteger(String str) {
        final int _createLeagueId = Integer.parseInt(str);
        return _createLeagueId;
    }
}
