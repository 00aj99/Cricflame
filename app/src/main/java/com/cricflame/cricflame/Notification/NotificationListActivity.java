package com.cricflame.cricflame.Notification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.MatchDetailActivity;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Notification.Model.NotificationData;

import com.cricflame.cricflame.Fragment.All_Matches_Fragment;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity {
    ListView simpleList;
    ArrayList<NotificationData> notificationList=new ArrayList<>();
    MyAdapter myAdapter;
    ImageView back;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_notification_list);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadingDialog = new LoadingDialog(NotificationListActivity.this);
        simpleList = (ListView) findViewById(com.cricflame.cricflame.R.id.notificationList);
        getNotificationData();
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAdapter= (MyAdapter) parent.getAdapter();
                NotificationData viewData=myAdapter.getItem(position);
                if (viewData.getType().equals("schedule")){
                    Intent intent = new Intent(getApplicationContext(),MatchDetailActivity.class);
                    All_Matches_Fragment.BMatchId=viewData.getId();
                    // Toast.makeText(context1,""+id,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
               else {

                }
            }
        });

    }

    public void getNotificationData(){
        loadingDialog.show();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Global.URL + "get-notification.php",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject video = data.getJSONObject(i);
                        NotificationData videoData = new NotificationData();
                        videoData.setId(video.getString("match_id"));
                        videoData.setHeading(video.getString("title"));
                        videoData.setDescription(video.getString("msg"));
                        videoData.setType(video.getString("type"));

                        notificationList.add(videoData);

                    }



                    myAdapter=new MyAdapter(NotificationListActivity.this, com.cricflame.cricflame.R.layout.game_layout_view,notificationList);
                    simpleList.setAdapter(myAdapter);
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();

                   } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();

            }
        });
        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(NotificationListActivity.this).add(jsonArrayRequest);


    }


    public class MyAdapter extends ArrayAdapter<NotificationData> {

        ArrayList<NotificationData> animalList = new ArrayList<>();

        public MyAdapter(Context context, int textViewResourceId, ArrayList<NotificationData> objects) {
            super(context, textViewResourceId, objects);
            animalList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(com.cricflame.cricflame.R.layout.game_layout_view, null);
            RelativeLayout Main_lay = v.findViewById(R.id.main_notifi_lay);
          /*  if(position % 2 == 0){
                Main_lay.setBackgroundColor(getContext().getResources().getColor(R.color.match_back));
            }*/
            TextView name = (TextView) v.findViewById(com.cricflame.cricflame.R.id.name);
            TextView id = (TextView) v.findViewById(com.cricflame.cricflame.R.id.id);
            TextView description = (TextView) v.findViewById(com.cricflame.cricflame.R.id.desp);
            name.setText(animalList.get(position).getHeading());
            id.setText(animalList.get(position).getId());
            description.setText(animalList.get(position).getDescription());
            return v;

        }

    }

}
