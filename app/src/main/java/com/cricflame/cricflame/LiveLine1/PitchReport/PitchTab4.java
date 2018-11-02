package com.cricflame.cricflame.LiveLine1.PitchReport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

public class PitchTab4 extends Fragment {

    private DatabaseReference mRootref;
    private DatabaseReference mRootref1;
    private DatabaseReference df_series,df_match;
    private TextView val_series,name_team1_series,value_team1_series,name_team2_series,value_team2_series,val_draw_series;
    private TextView val_match,name_team1_match,value_team1_match,name_team2_match,value_team2_match,val_match_draw;
    private TextView val1_HSC,val2_HSC,val1_LSD,val2_LSD,val1_MR,val2_MR,val2_HS,val1_HAS,val2_HAS,val1_HSR,val2_HSR,val1_M100,val2_M100,val1_M50,val2_M50,val1_MD,val2_MD,val1_M6,val2_M6,val1_M4,val2_M4,val1_MRS,val2_MRS,val1_MW,val2_MW,val1_BER,val2_BER,val1_M5W,val2_M5W,val1_MS,val2_MS,val1_10WI,val2_10WI;
    private TextView val1_HS;
    String Key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Key = getActivity().getIntent().getExtras().getString("key");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pitch_tab_layout4, container, false);
        try{
            mRootref1= FirebaseUtils.getSecondaryDatabase(getActivity()).getReference();
            mRootref = mRootref1.child("Record").child(Key);

        df_series = mRootref.child("Series");
        df_match = mRootref.child("Match");
        init(view);
        assignValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
    private void assignValue() {
        df_series.child("total_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_series.child("team1_name_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name_team1_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_series.child("team1_score_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value_team1_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_series.child("team2_name_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name_team2_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_series.child("team2_score_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value_team2_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_series.child("total_draw_series").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val_draw_series.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        df_match.child("total_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val_match.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_match.child("team1_name_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name_team1_match.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_match.child("team1_score_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value_team1_match.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_match.child("team2_name_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name_team2_match.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_match.child("team2_score_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value_team2_match.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        df_match.child("total_draw_match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val_match_draw.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        mRootref.child("BER").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_BER.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("BER").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_BER.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HAVG").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_HAS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HAVG").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_HAS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HS").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_HS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HS").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_HS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HSC").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_HSC.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HSC").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_HSC.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("LSD").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_LSD.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("LSD").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_LSD.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HSR").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_HSR.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("HSR").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_HSR.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M100").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_M100.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M100").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_M100.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M50").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_M50.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M50").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_M50.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M4").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_M4.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M4").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_M4.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mRootref.child("M5W").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_M5W.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M5W").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_M5W.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M6").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_M6.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("M6").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_M6.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MD").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_MD.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MD").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_MD.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MR").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_MR.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MR").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_MR.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mRootref.child("MRS").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_MRS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MRS").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_MRS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MST").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_MS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MST").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_MS.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MW").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_MW.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("MW").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_MW.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("WII").child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val1_10WI.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mRootref.child("WII").child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val2_10WI.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void init(View view) {

        val_series = (TextView) view.findViewById(R.id.val_series);
        name_team1_series = (TextView) view.findViewById(R.id.name_team1_series);
        value_team1_series = (TextView) view.findViewById(R.id.value_team1_series);
        name_team2_series = (TextView) view.findViewById(R.id.name_team2_series);
        value_team2_series = (TextView) view.findViewById(R.id.value_team2_series);
        val_draw_series = (TextView) view.findViewById(R.id.val_draw_series);
        val_match = (TextView) view.findViewById(R.id.val_match);
        name_team1_match = (TextView) view.findViewById(R.id.name_team1_match);
        value_team1_match = (TextView) view.findViewById(R.id.value_team1_match);
        name_team2_match = (TextView) view.findViewById(R.id.name_team2_match);
        value_team2_match = (TextView) view.findViewById(R.id.value_team2_match);
        val_match_draw = (TextView) view.findViewById(R.id.val_match_draw);

        val1_HSC = (TextView) view.findViewById(R.id.val1_HSC);
        val2_HSC = (TextView) view.findViewById(R.id.val2_HSC);
        val1_LSD = (TextView) view.findViewById(R.id.val1_LSD);
        val2_LSD = (TextView) view.findViewById(R.id.val2_LSD);
        val1_MR = (TextView) view.findViewById(R.id.val1_MR);
        val2_MR = (TextView) view.findViewById(R.id.val2_MR);
        val1_HS = (TextView) view.findViewById(R.id.val1_HS);
        val2_HS = (TextView) view.findViewById(R.id.val2_HS);
        val1_HAS = (TextView) view.findViewById(R.id.val1_HAS);
        val2_HAS = (TextView) view.findViewById(R.id.val2_HAS);
        val1_HSR = (TextView) view.findViewById(R.id.val1_HSR);
        val2_HSR = (TextView) view.findViewById(R.id.val2_HSR);
        val1_M100 = (TextView) view.findViewById(R.id.val1_M100);
        val2_M100 = (TextView) view.findViewById(R.id.val2_M100);
        val1_M50 = (TextView) view.findViewById(R.id.val1_M50);
        val2_M50 = (TextView) view.findViewById(R.id.val2_M50);
        val1_MD = (TextView) view.findViewById(R.id.val1_MD);
        val2_MD = (TextView) view.findViewById(R.id.val2_MD);
        val1_M6 = (TextView) view.findViewById(R.id.val1_M6);
        val2_M6 = (TextView) view.findViewById(R.id.val2_M6);
        val1_M4 = (TextView) view.findViewById(R.id.val1_M4);
        val2_M4 = (TextView) view.findViewById(R.id.val2_M4);
        val1_MRS = (TextView) view.findViewById(R.id.val1_MRS);
        val2_MRS = (TextView) view.findViewById(R.id.val2_MRS);
        val1_MW = (TextView) view.findViewById(R.id.val1_MW);
        val2_MW = (TextView) view.findViewById(R.id.val2_MW);
        val1_BER = (TextView) view.findViewById(R.id.val1_BER);
        val2_BER = (TextView) view.findViewById(R.id.val2_BER);
        val1_M5W = (TextView) view.findViewById(R.id.val1_M5W);
        val2_M5W = (TextView) view.findViewById(R.id.val2_M5W);
        val1_MS = (TextView) view.findViewById(R.id.val1_MS);
        val2_MS = (TextView) view.findViewById(R.id.val2_MS);
        val1_10WI = (TextView) view.findViewById(R.id.val1_10WI);
        val2_10WI = (TextView) view.findViewById(R.id.val2_10WI);

    }
}
