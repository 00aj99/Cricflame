package com.cricflame.cricflame.LiveLine1.Commentries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Commentary extends AppCompatActivity{
    //done by shiddhat
    private List<CommentryDataPicker> commentary_list;


    // ArrayList<String> list=new ArrayList<>();
    // ArrayAdapter<String> adapter;
    //ListView lv;

    ImageView back;
    //DB REFERENCE/
    //
    // /
    Integer icount=1;

    TextView display_inning;
    int type;
    Button btn_show_1_inning,btn_show_2_inning,btn_show_3_inning,btn_show_4_inning;


    //DatabaseReference mRootref= FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mchildref=mRootref.child("Match").child("Commentory");
    private CommentryDataPicker cdp;
    private RecyclerView _recycleView;
    private RecyclerView.LayoutManager layoutManager;
    private CommentaryAdapter adapter;
    private String i1,i2,i3;
    private FirebaseApp secondApp;
    private FirebaseDatabase secondaryDatabase;
    private DatabaseReference mRootref;
    private DatabaseReference mchildref;
    private String Key,Inning;
    private Integer fapp=0;
    private DatabaseReference whichinning;
    private DatabaseReference mchildref2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_commentory);

        Key = getIntent().getExtras().getString("key");
        Inning = getIntent().getExtras().getString("inning");
        i1 = getIntent().getExtras().getString("i1");
        i2 = getIntent().getExtras().getString("i2");
        i3 = getIntent().getExtras().getString("i3");
        type = getIntent().getExtras().getInt("type");

        fapp+=1;

        _recycleView=(RecyclerView)findViewById(com.cricflame.cricflame.R.id.lv);



        mRootref= FirebaseUtils.getThirdDatabase(Commentary.this).getReference();
        // mchildref=mRootref.child("Match").child("Commentory");


        if(_recycleView !=null){
            _recycleView.setHasFixedSize(true);
        }
        layoutManager  = new LinearLayoutManager(this);
        _recycleView.setLayoutManager(layoutManager);

        commentary_list = new ArrayList<CommentryDataPicker>();

        adapter = new CommentaryAdapter(Commentary.this, commentary_list);
        _recycleView.setAdapter(adapter);

        //final TextView display_inning,btn_show_1_inning,btn_show_2_inning;
        display_inning = (TextView)findViewById(R.id.display_inning);
        btn_show_1_inning = (Button) findViewById(R.id.tv_inning_1);
        btn_show_2_inning = (Button) findViewById(R.id.tv_inning_2);
        btn_show_3_inning = (Button) findViewById(R.id.tv_inning_3);
        btn_show_4_inning = (Button) findViewById(R.id.tv_inning_4);


        if(type == 1){
            btn_show_1_inning.setVisibility(View.VISIBLE);
            btn_show_2_inning.setVisibility(View.VISIBLE);
            btn_show_3_inning.setVisibility(View.VISIBLE);
            btn_show_4_inning.setVisibility(View.VISIBLE);
        }

        btn_show_1_inning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentary_list.clear();
                adapter.notifyDataSetChanged();
                mchildref = mRootref.child("Commentary").child(Key).child("inning1");
                try{
                    loadPreviousAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }
        });

        btn_show_2_inning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentary_list.clear();
                adapter.notifyDataSetChanged();
                mchildref = mRootref.child("Commentary").child(Key).child("inning2");
                try{
                    loadCurrentAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }
        });

        btn_show_3_inning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentary_list.clear();
                adapter.notifyDataSetChanged();
                mchildref = mRootref.child("Commentary").child(Key).child("inning3");
                try{
                    loadCurrentAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }
        });

        btn_show_4_inning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentary_list.clear();
                adapter.notifyDataSetChanged();
                mchildref = mRootref.child("Commentary").child(Key).child("inning4");
                try{
                    loadCurrentAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }
        });

        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Inning.equalsIgnoreCase("1")){

            if(i1.equalsIgnoreCase("0")||i1.equalsIgnoreCase("")) {
                display_inning.setText("Current Inning: 1");
                mchildref = mRootref.child("Commentary").child(Key).child("inning1");
                loadCurrentAdapter();
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }else {
                display_inning.setText("Current Inning: 3");
                mchildref = mRootref.child("Commentary").child(Key).child("inning3");
                loadCurrentAdapter();
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }

            mchildref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        loadCurrentAdapter();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            if(i2.equalsIgnoreCase("0")||i2.equalsIgnoreCase("")) {
                display_inning.setText("Current Inning: 2");
                mchildref = mRootref.child("Commentary").child(Key).child("inning2");
                try{
                    loadCurrentAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }else {
                display_inning.setText("Current Inning: 4");
                mchildref = mRootref.child("Commentary").child(Key).child("inning4");
                try{
                    loadCurrentAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn_show_4_inning.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                btn_show_4_inning.setTextColor(getResources().getColor(R.color.white));
                btn_show_1_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_1_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_2_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_2_inning.setTextColor(getResources().getColor(R.color.parrot_base));
                btn_show_3_inning.setBackground(getResources().getDrawable(R.drawable.round_unselected_green));
                btn_show_3_inning.setTextColor(getResources().getColor(R.color.parrot_base));
            }
            mchildref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{
                        loadCurrentAdapter();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void loadCurrentAdapter(){
        try{
            icount=1;
            adapter.notifyItemRangeRemoved(0, commentary_list.size());
            commentary_list.clear();
            mchildref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try{
                        String s1=dataSnapshot.getValue(String.class);
                        if(!s1.equalsIgnoreCase("0")){
                            String[] separated = s1.split(":");
                            cdp = new CommentryDataPicker();
                            cdp.over = separated[0];
                            cdp.status = separated[1];
                            cdp.ssn = separated[2];
                            cdp.rate = separated[3];
                            cdp.fav_team = separated[4];
                            cdp.score = separated[5];
                            int prevSize = commentary_list.size();
                            commentary_list.add(cdp);
                            if(icount==1) {
                                try{
                                    adapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                icount++;
                            }
                            //_recycleView.scrollToPosition(commentary_list.size()-1);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String s1=dataSnapshot.getValue(String.class);
                    commentary_list.remove(s1);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            //adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void loadPreviousAdapter(){
        try {
            commentary_list.clear();

            mchildref2 = mRootref.child("Commentary").child(Key).child("inning1");

            mchildref2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try{
                        String s1 = dataSnapshot.getValue(String.class);
                        // Log.e("onChildAdded: ",s1 );

                        if (!s1.equalsIgnoreCase("0")) {
                            String[] separated = s1.split(":");
                            cdp = new CommentryDataPicker();
                            cdp.over = separated[0];
                            cdp.status = separated[1];
                            cdp.ssn = separated[2];
                            cdp.rate = separated[3];
                            cdp.fav_team = separated[4];
                            cdp.score = separated[5];
                            commentary_list.add(cdp);
                            adapter.notifyDataSetChanged();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String s1 = dataSnapshot.getValue(String.class);
                    commentary_list.remove(s1);
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

