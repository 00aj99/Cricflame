package com.cricflame.cricflame.Records;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsRecordActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<RecordModel> items = new ArrayList<RecordModel>();
    private TextView btnTest;
    private TextView btnT20;
    private TextView btnOdi;
    int flag=1;
    private JSONArray jsonArray;
    private static String screen;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnTest = (TextView) findViewById(R.id.btnTest);
        btnT20 = (TextView) findViewById(R.id.btnT20);
        btnOdi = (TextView) findViewById(R.id.btnOdi);
        screen = getIntent().getStringExtra("type");


        btnTest.setTextColor(getResources().getColor(R.color.green));
        btnTest.setBackgroundColor(Color.WHITE);
        btnT20.setTextColor(Color.WHITE);
        btnT20.setBackgroundColor(getResources().getColor(R.color.green));
        btnOdi.setTextColor(Color.WHITE);
        btnOdi.setBackgroundColor(getResources().getColor(R.color.green));

        if(screen.equals("batting")){
            getBattngRecord();
        }else if(screen.equals("bowling")){
            getBowlingRecord();
        }

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest.setTextColor(getResources().getColor(R.color.green));
                btnTest.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                flag =1;
                if(screen.equals("batting")){
                    getBattngRecord();
                    selectTabBat();
                }else if(screen.equals("bowling")){
                    getBowlingRecord();
                    selectTabBow();
                }


            }
        });
        btnOdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOdi.setTextColor(getResources().getColor(R.color.green));
                btnOdi.setBackgroundColor(Color.WHITE);
                btnT20.setTextColor(Color.WHITE);
                btnT20.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                flag=2;
                if(screen.equals("batting")){
                    getBattngRecord();
                    selectTabBat();
                }else if(screen.equals("bowling")){
                    getBowlingRecord();
                    selectTabBow();
                }

            }
        });
        btnT20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnT20.setTextColor(getResources().getColor(R.color.green));
                btnT20.setBackgroundColor(Color.WHITE);
                btnOdi.setTextColor(Color.WHITE);
                btnOdi.setBackgroundColor(getResources().getColor(R.color.green));
                btnTest.setTextColor(Color.WHITE);
                btnTest.setBackgroundColor(getResources().getColor(R.color.green));
                flag=3;
                if(screen.equals("batting")){
                    getBattngRecord();
                    selectTabBat();
                }else if(screen.equals("bowling")){
                    getBowlingRecord();
                    selectTabBow();
                }

            }
        });








    }

    private void getBattngRecord(){

        String Url = "http://matalanghana.com/cricklineapi/fetch_betting_record.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("betting_record");
                            selectTabBat();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsRecordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
        @Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", getIntent().getStringExtra("name"));
            return params;
        }
        };
        Volley.newRequestQueue(DetailsRecordActivity.this).add(stringRequest);
    }
    private void getBowlingRecord(){
        //String Url = "matalanghana.com/cricklineapi/fetch_betting_list.php";
        String Url = "http://matalanghana.com/cricklineapi/fetch_bowling_record.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("onResponse: ",response+"" );
                            JSONObject jsonObject = new JSONObject(response);
                             jsonArray = jsonObject.getJSONArray("betting_record");
                            selectTabBow();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsRecordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", getIntent().getStringExtra("name"));
                return params;
            }
        };
        Volley.newRequestQueue(DetailsRecordActivity.this).add(stringRequest);
    }

    public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

        private ArrayList<RecordModel> items;
        private Context mContext;

        public RecordAdapter(Context context, ArrayList<RecordModel> list) {
            this.mContext = context;
            this.items = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.record_single_details, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.text1.setText(items.get(position).getString1());
            holder.text2.setText(items.get(position).getString2());
            holder.text3.setText(items.get(position).getString3());
            holder.text4.setText(items.get(position).getString4());
            holder.text5.setText(items.get(position).getString5());



        }

        @Override
        public int getItemCount() {
            return items.size();
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
    }

    private void selectTabBat(){
        Log.e( "selectTab: ",jsonArray+"" );
        if(flag == 1){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("test"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
       if(flag ==2){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("odi"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(flag ==3){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("t20"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DetailsRecordActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        RecordAdapter adapter = new RecordAdapter(DetailsRecordActivity.this,items);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void selectTabBow(){
        Log.e( "selectTab: ",jsonArray+"" );
        if(flag == 1){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("Test"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if(flag ==2){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("ODI"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(flag ==3){
            items.clear();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(i);
                    if((object.getString("stats").equals("T20"))){
                        RecordModel data = new RecordModel();
                        data.setString1(object.getString("name"));
                        data.setString2(object.getString("m"));
                        data.setString3(object.getString("o"));
                        data.setString4(object.getString("w"));
                        data.setString5(object.getString("avg"));
                        items.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DetailsRecordActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        RecordAdapter adapter = new RecordAdapter(DetailsRecordActivity.this,items);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
