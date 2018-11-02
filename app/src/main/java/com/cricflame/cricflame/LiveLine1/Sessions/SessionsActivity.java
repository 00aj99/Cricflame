package com.cricflame.cricflame.LiveLine1.Sessions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

import java.util.ArrayList;

import static com.cricflame.cricflame.LiveLine1.LiveLine.mRootref;

public class SessionsActivity extends AppCompatActivity {
ArrayList<Session> session_list = new ArrayList<Session>();
    private ImageView back;
    private RecyclerView recyclerView;

    private DatabaseReference childref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mRootref= FirebaseUtils.getSecondaryDatabase(SessionsActivity.this).getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getAllSessionData();

    }

    private void getAllSessionData() {
        childref = mRootref.child("Sessions");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SessionsActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        final SessionAdapter adapter = new SessionAdapter(SessionsActivity.this,session_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                session_list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    session_list.add(snapshot.getValue(Session.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {
        Context context;
        ArrayList<Session> stats_list = new ArrayList<Session>();


        public SessionAdapter(Context context, ArrayList<Session> leavelist) {
            this.context = context;
            this.stats_list = leavelist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_single_details, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.text1.setText(stats_list.get(position).over);
            holder.text2.setText(stats_list.get(position).open);
            holder.text3.setText(stats_list.get(position).high);
            holder.text4.setText(stats_list.get(position).low);
            holder.text5.setText(stats_list.get(position).pass);
        }

        @Override
        public int getItemCount() {
            return stats_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView text1,text2,text3,text4,text5;
            public MyViewHolder(View view) {
                super(view);
                text1 = (TextView) view.findViewById(R.id.text1);
                text2 = (TextView) view.findViewById(R.id.text2);
                text3 = (TextView) view.findViewById(R.id.text3);
                text4 = (TextView) view.findViewById(R.id.text4);
                text5 = (TextView) view.findViewById(R.id.text5);


            }
        }


        public Object getItem(int location) {
            return stats_list.get(location);
        }


    }
}
