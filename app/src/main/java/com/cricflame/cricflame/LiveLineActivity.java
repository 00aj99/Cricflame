package com.cricflame.cricflame;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.cricflame.cricflame.Model.LoadingDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.LiveLine1.LiveLine;


public class LiveLineActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tv_liveline_match2,tv_liveline_match1;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(com.cricflame.cricflame.R.layout.activity_live_line);
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        tv_liveline_match1 = (TextView) findViewById(com.cricflame.cricflame.R.id.liveline_match1);
        tv_liveline_match2 = (TextView) findViewById(com.cricflame.cricflame.R.id.liveline_match2);
        loadingDialog = new LoadingDialog(LiveLineActivity.this);


        loadingDialog.show();

        tv_liveline_match1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LiveLineActivity.this, LiveLine.class));
            }
        });

        tv_liveline_match2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LiveLineActivity.this, com.cricflame.cricflame.LiveLine2.LiveLine.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseUtils.getSecondaryDatabase(LiveLineActivity.this).getReference().child("Match").child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tv_liveline_match1.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try{
            FirebaseUtils.getThirdDatabase(LiveLineActivity.this).getReference().child("Match").child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        tv_liveline_match2.setText(dataSnapshot.getValue(String.class));
                        if(loadingDialog.isShowing()){
                            loadingDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }catch(Exception e){
            e.printStackTrace();
        }




    }

}
