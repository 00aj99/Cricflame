package com.cricflame.cricflame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Adapter.DBAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PollActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView back;
    TextView yes,no,yes_vote,no_vote,question,option1,option2;
    LinearLayout quiz_layout;
   public static String id,qid,tableId;
    DBAdapter dbAdapter;
    PieChart pieChart;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_poll);

        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        quiz_layout= (LinearLayout) findViewById(com.cricflame.cricflame.R.id.quiz_layout);
        pieChart = (PieChart) findViewById(com.cricflame.cricflame.R.id.chart);
        question= (TextView) findViewById(com.cricflame.cricflame.R.id.question);
        dbAdapter=new DBAdapter(PollActivity.this);
        yes= (TextView) findViewById(com.cricflame.cricflame.R.id.yes);
        option1= (TextView) findViewById(com.cricflame.cricflame.R.id.option1);
        option2= (TextView) findViewById(com.cricflame.cricflame.R.id.option2);
        yes_vote= (TextView) findViewById(com.cricflame.cricflame.R.id.yes_percnt);
        no= (TextView) findViewById(com.cricflame.cricflame.R.id.no);
        no_vote= (TextView) findViewById(com.cricflame.cricflame.R.id.no_percnt);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getPoll();

    }
        public void getPoll(){
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL + "poll.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject poll=response.getJSONObject("data");
                        question.setText(poll.getString("question"));
                        id=poll.getString("id");
                        yes_vote.setText(poll.getString("yesper") +" %");
                        yes.setText(poll.getString("answer1"));
                        option1.setText(poll.getString("answer1")+" :");
                        no.setText(poll.getString("answer2"));
                        option2.setText(poll.getString("answer2")+" :");
                        no_vote.setText(poll.getString("noper")+" %");
                        float vote1= Float.parseFloat(poll.getString("yesper"));
                        float vote2= Float.parseFloat(poll.getString("noper"));

                        entries.add((Entry) new Entry(vote1, 0));
                        entries.add((Entry) new Entry(vote2, 1));
                        labels.add("Yes");
                        labels.add("No");

                        PieDataSet dataSet = new PieDataSet(entries, "Poll Result");
                        // entries.add(new Entry(totalbooking, bookingavailable));
                        PieData data = new PieData(labels, dataSet);
                        // In Percentage
                        data.setValueFormatter(new PercentFormatter());
                        // Default value
                        //data.setValueFormatter(new DefaultValueFormatter(0));
                        pieChart.setData(data);
                        pieChart.setDescription("");
                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setTransparentCircleRadius(58f);
                        pieChart.setNoDataTextDescription("");
                        pieChart.setHoleRadius(58f);
                        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                        pieChart.setDrawSliceText(false);
                        pieChart.invalidate();
                        data.setValueTextSize(13f);
                        data.setValueTextColor(Color.DKGRAY);
                        pieChart.animateXY(1400, 1400);


                        if (id.equals(qid)){
                            quiz_layout.setVisibility(View.GONE);
                        }
                        else{
                            qid="";
                            // dbAdapter.clearPoll();
                            quiz_layout.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            jsonObjectRequest.setShouldCache(false);
            Volley.newRequestQueue(PollActivity.this).add(jsonObjectRequest);



        }
//    public String getRecords(){
//
//        DBAdapter dbAdapter=new DBAdapter(this);
//        String query="SELECT * FROM "+ TABLE_POLL;
//        // String query="SELECT * FROM "+TABLE_NAME ;
//        String result="";
//        SQLiteDatabase db=dbAdapter.getReadableDatabase();
//        Cursor cursor=db.rawQuery(query,null);
//
//        cursor.moveToFirst();
//        while(cursor.isAfterLast()==false){
//
//            tableId=cursor.getString(1);
//            cursor.moveToNext();
//        }
//
//        db.close();
//        return result;
//    }

    public void pollView(String tid,String ans){
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Global.URL + "pollview.php?id="+tid+"&answer="+ans, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        Toast.makeText(getApplicationContext(),""+response.getString("message"),Toast.LENGTH_SHORT).show();
                        JSONObject poll=response.getJSONObject("data");
                        qid=id;
                      //  dbAdapter.insertPoll(id);
                        yes_vote.setText(poll.getString("yesper"));
                        no_vote.setText(poll.getString("noper"));
                        quiz_layout.setVisibility(View.GONE);
                        startActivity(getIntent());
                      //  getPoll();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            jsonObjectRequest.setShouldCache(false);
            Volley.newRequestQueue(PollActivity.this).add(jsonObjectRequest);


        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(PollActivity.this,MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
           case com.cricflame.cricflame.R.id.yes:
               pollView(id,"Yes");

           case com.cricflame.cricflame.R.id.no:
               pollView(id,"No");

        }

    }
}
