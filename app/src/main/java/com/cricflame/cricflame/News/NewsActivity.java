package com.cricflame.cricflame.News;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.cricflame.cricflame.Adapter.ItemClickSupport;
import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.R;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private List<NewsModel> newsArrayData = new ArrayList<NewsModel>();
    RecyclerView news_list;
    Newss_Adapter news_adapter;
    ImageView back;

    private FirebaseApp secondApp;
    private FirebaseDatabase secondaryDatabase;
    private DatabaseReference mRootref;
    private static final int TOTAL_ITEM_EACH_LOAD = 5;
    private ProgressBar mProgressBar;

    private int mTotalItemCount;
    private int currentPage,firstItem=0;
    private int mLastVisibleItemPosition;
    LoadingDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressDialog = new LoadingDialog(NewsActivity.this);
        mRootref = FirebaseUtils.getThirdDatabase(NewsActivity.this).getReference();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog.show();


        //to Get The First Child of News
        mRootref.child("News").orderByChild("id").limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            firstItem = Integer.parseInt(snapshot.child("id").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Handle possible errors.
                    }
                });





        //to Get The Last Child of News
        mRootref.child("News").orderByChild("id").limitToLast(1)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //String userName = snapshot.child("user").getValue(String.class);
                    currentPage = Integer.parseInt(snapshot.child("id").getValue().toString());
                }

                loadData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });



        news_list = (RecyclerView) findViewById(R.id.news_List);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        news_list.setLayoutManager(mLayoutManager);

        /*mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);*/

        news_list.setItemAnimator(new DefaultItemAnimator());
        news_list.setHasFixedSize(true);
        news_adapter = new Newss_Adapter(NewsActivity.this, newsArrayData);
        news_list.setAdapter(news_adapter);
        news_list.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                currentPage = currentPage-5;
                loadMoreData();

               // mTotalItemCount = mLayoutManager.getItemCount();
                //mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();

               // Toast.makeText(NewsActivity.this, mLastVisibleItemPosition+"", Toast.LENGTH_SHORT).show();


            }
        });



        ItemClickSupport.addTo(news_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        news_adapter = (Newss_Adapter) recyclerView.getAdapter();
                        NewsModel newsData = (NewsModel) newsArrayData.get(position);
                        Intent intent = new Intent(NewsActivity.this, NewsDetail.class);
                        intent.putExtra("Date", newsData.getDate());
                        intent.putExtra("Headline", newsData.getHeadline());
                        intent.putExtra("Para", newsData.getPara());
                        intent.putExtra("Image", newsData.getImage());
                        intent.putExtra("Intro", newsData.getIntro());
                        intent.putExtra("Source", newsData.getSource());
                        intent.putExtra("dataFrom","firebase");
                        startActivity(intent);
                    }
                });


    }



    private void loadData() {
            try{
                mRootref.child("News")
                        .orderByChild("id")
                        .startAt(String.valueOf(currentPage))
                        //.endAt(firstItem)
                        .limitToFirst(TOTAL_ITEM_EACH_LOAD)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (!dataSnapshot.hasChildren()) {
                                    Toast.makeText(NewsActivity.this, "No more news.", Toast.LENGTH_SHORT).show();
                                    currentPage--;
                                }
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    newsArrayData.add(snapshot.getValue(NewsModel.class));
                                    news_adapter.notifyDataSetChanged();
                                }

                                mProgressBar.setVisibility(RecyclerView.GONE);

                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                mProgressBar.setVisibility(RecyclerView.GONE);
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }


    }

    private void loadMoreData() {

        if((firstItem-4)<currentPage){
            loadData();
            mProgressBar.setVisibility(RecyclerView.VISIBLE);
        }else{
            Toast.makeText(NewsActivity.this, "No more news.", Toast.LENGTH_SHORT).show();
        }


    }



    public class Newss_Adapter extends RecyclerView.Adapter<Newss_Adapter.MyViewHolder> {

        private Activity activity;

        private LayoutInflater inflater;
        private List<NewsModel> Items;


        public Newss_Adapter(Activity activity, List<NewsModel> Items) {
            this.activity = activity;
            this.Items = Items;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_home_layout_view, parent, false);
            MyViewHolder myholder = new MyViewHolder(view);
            return myholder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (Items.get(position).getIntro() != null) {
                holder.subheading.setText(Items.get(position).getIntro());
            }
            if (Items.get(position).getDate() != null) {
                holder.Duration.setText(Items.get(position).getDate());
            }
            if (Items.get(position).getPara() != null) {
                holder.Description.setText(Items.get(position).getPara());
            }
            if (Items.get(position).getHeadline() != null) {
                holder.Title.setText(Items.get(position).getHeadline());
            }
            if (Items.get(position).getImage().equals(" ") || Items.get(position).getImage() == null) {
                Glide.with(activity).load(R.drawable.app_logo).into(holder.Image);
            } else {
                Glide.with(activity).load(Items.get(position).getImage()).into(holder.Image);
            }
        }

        @Override
        public int getItemCount() {
            return Items.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView subheading;
            TextView Title;
            ImageView Image;
            TextView Description;
            TextView Duration;

            public MyViewHolder(View itemView) {

                super(itemView);

                subheading = (TextView) itemView.findViewById(R.id.news_heading);

                Title = (TextView) itemView.findViewById(R.id.title);

                Duration = (TextView) itemView.findViewById(R.id.duration);

                Description = (TextView) itemView.findViewById(R.id.description);

                Image = (ImageView) itemView.findViewById(R.id.news_image);
            }


        }


    }
}
