package com.cricflame.cricflame.Quiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cricflame.cricflame.Chat.global.data_model.Message;
import com.cricflame.cricflame.DBHelper.DBHelper;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.R;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_play_now extends AppCompatActivity {
    String Key,Team1,Team2,Flag1,Flag2,QuestionId="",Answer="";
    LoadingDialog loadingDialog;
    DBHelper dbHelper;
    TextView Questions;
    Button Submit;
    int Position = 0;
    Button Option1,Option2,Option3,Option4;
    RelativeLayout ExtraButtons,Option1_lay,Option2_lay,Option3_lay,Option4_lay;
    JSONArray data;
    Toolbar toolbar;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_now);
        dbHelper = new DBHelper(Activity_play_now.this);
        loadingDialog = new LoadingDialog(Activity_play_now.this,"","");
        back = (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Key = getIntent().getExtras().getString("key");
        Team1 = getIntent().getExtras().getString("team1");
        Team2 = getIntent().getExtras().getString("team2");
        Flag1 = getIntent().getExtras().getString("flag1");
        Flag2 = getIntent().getExtras().getString("flag2");
        Questions = findViewById(R.id.question);
        Option1_lay = findViewById(R.id.option1);
        Option2_lay = findViewById(R.id.option2);
        Option3_lay = findViewById(R.id.option3);
        Option4_lay = findViewById(R.id.option4);
        Submit = findViewById(R.id.button_submit);
        Option1 = findViewById(R.id.option1_button);
        Option2 = findViewById(R.id.option2_button);
        Option3 = findViewById(R.id.option3_button);
        Option4 = findViewById(R.id.option4_button);
        ExtraButtons = findViewById(R.id.extra_button_lay);

        try{
            ImageView Flagteam_1 =  findViewById(R.id.team1_flag);
            Glide.with(this).load(Flag1).into(Flagteam_1);
            ImageView Flagteam_2 = findViewById(R.id.team2_flag);
            Glide.with(this).load(Flag2).into(Flagteam_2);

            ((TextView) findViewById(R.id.team1_name_short)).setText(Team1);
            ((TextView) findViewById(R.id.team2_name_short)).setText(Team2);
        }catch (Exception e){
            e.printStackTrace();
        }


        getQuestions();
        Submit.setBackgroundResource(R.color.grey);
        Submit.setTextColor(getResources().getColor(R.color.black_brown));
        Submit.setEnabled(false);
        Submit.setClickable(false);


        Option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option1.setBackgroundResource(R.drawable.round_button_green);
                Option2.setBackgroundResource(R.drawable.round_unselected_green);
                Option3.setBackgroundResource(R.drawable.round_unselected_green);
                Option4.setBackgroundResource(R.drawable.round_unselected_green);

                Submit.setBackgroundResource(R.drawable.toolbar_gradient_clr);
                Submit.setTextColor(getResources().getColor(R.color.white));
                Submit.setEnabled(true);
                Submit.setClickable(true);
                Answer = "option1";

            }
        });

        Option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option2.setBackgroundResource(R.drawable.round_button_green);
                Option1.setBackgroundResource(R.drawable.round_unselected_green);
                Option3.setBackgroundResource(R.drawable.round_unselected_green);
                Option4.setBackgroundResource(R.drawable.round_unselected_green);

                Submit.setBackgroundResource(R.drawable.toolbar_gradient_clr);
                Submit.setTextColor(getResources().getColor(R.color.white));
                Submit.setEnabled(true);
                Submit.setClickable(true);

                Answer = "option2";
            }
        });

        Option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option3.setBackgroundResource(R.drawable.round_button_green);
                Option2.setBackgroundResource(R.drawable.round_unselected_green);
                Option1.setBackgroundResource(R.drawable.round_unselected_green);
                Option4.setBackgroundResource(R.drawable.round_unselected_green);
                Submit.setBackgroundResource(R.drawable.toolbar_gradient_clr);
                Submit.setTextColor(getResources().getColor(R.color.white));
                Submit.setEnabled(true);
                Submit.setClickable(true);

                Answer = "option3";
            }
        });

        Option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option4.setBackgroundResource(R.drawable.round_button_green);
                Option2.setBackgroundResource(R.drawable.round_unselected_green);
                Option3.setBackgroundResource(R.drawable.round_unselected_green);
                Option1.setBackgroundResource(R.drawable.round_unselected_green);
                Submit.setBackgroundResource(R.drawable.toolbar_gradient_clr);
                Submit.setTextColor(getResources().getColor(R.color.white));
                Submit.setEnabled(true);
                Submit.setClickable(true);
                Answer = "option4";
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitAnswer();
            }
        });

    }

    public void getQuestions() {
        loadingDialog.show();

        StringRequest productRequest = new StringRequest(Request.Method.POST, Global.URL + "quiz.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    if(jObj.getString("status").equalsIgnoreCase("200")){
                        Questions.setVisibility(View.VISIBLE);
                        ExtraButtons.setVisibility(View.VISIBLE);
                        Submit.setVisibility(View.VISIBLE);
                         data =  jObj.getJSONArray("data");
                        Questions.setText(data.getJSONObject(0).getString("question"));
                        QuestionId = data.getJSONObject(0).getString("id");
                        if(data.length()==1){
                            Submit.setText("Submit & Quit");
                        }else{
                            Submit.setText("Submit & Next");
                        }
                        if(data.getJSONObject(0).getString("option1").equalsIgnoreCase("")){
                          Option1.setVisibility(View.GONE);
                          Option1_lay.setVisibility(View.GONE);
                        }else{
                            Option1_lay.setVisibility(View.VISIBLE);
                            Option1.setVisibility(View.VISIBLE);
                            Option1.setText(data.getJSONObject(0).getString("option1"));
                        }
                        if(data.getJSONObject(0).getString("option2").equalsIgnoreCase("")){
                            Option2.setVisibility(View.GONE);
                            Option2_lay.setVisibility(View.GONE);
                        }else{
                            Option2_lay.setVisibility(View.VISIBLE);
                            Option2.setVisibility(View.VISIBLE);
                            Option2.setText(data.getJSONObject(0).getString("option2"));
                        }
                        if(data.getJSONObject(0).getString("option3").equalsIgnoreCase("")){
                            Option3.setVisibility(View.GONE);
                            Option3_lay.setVisibility(View.GONE);
                        }else{
                            Option3_lay.setVisibility(View.VISIBLE);
                            Option3.setVisibility(View.VISIBLE);
                            Option3.setText(data.getJSONObject(0).getString("option3"));
                        }
                        if(data.getJSONObject(0).getString("option4").equalsIgnoreCase("")){
                            Option4.setVisibility(View.GONE);
                            Option4_lay.setVisibility(View.GONE);
                        }else{
                            Option4_lay.setVisibility(View.VISIBLE);
                            Option4.setVisibility(View.VISIBLE);
                            Option4.setText(data.getJSONObject(0).getString("option4"));
                        }

                    }else{
                        if(jObj.has("message")){
                            TastyToast.makeText(Activity_play_now.this, jObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            Questions.setVisibility(View.GONE);
                            ExtraButtons.setVisibility(View.GONE);
                            Submit.setVisibility(View.GONE);
                        }else{
                            TastyToast.makeText(Activity_play_now.this, "No Questions Found..Try Later", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            Questions.setVisibility(View.GONE);
                            ExtraButtons.setVisibility(View.GONE);
                            Submit.setVisibility(View.GONE);
                        }
                    }
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("match_id",Key);
                params.put("user_id",dbHelper.getFieldValueUser("gameUserid"));
                return params;
            }
        };
        productRequest.setShouldCache(false);
        productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Activity_play_now.this).add(productRequest);

    }


    public void SubmitAnswer() {
        loadingDialog.show();

        StringRequest productRequest = new StringRequest(Request.Method.POST, Global.URL + "quiz_answer.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    if(jObj.getString("status").equalsIgnoreCase("200")){
                        QuestionId = "";
                        Answer = "";
                        Option1.setBackgroundResource(R.drawable.round_unselected_green);
                        Option2.setBackgroundResource(R.drawable.round_unselected_green);
                        Option3.setBackgroundResource(R.drawable.round_unselected_green);
                        Option4.setBackgroundResource(R.drawable.round_unselected_green);

                        Submit.setBackgroundResource(R.color.grey);
                        Submit.setTextColor(getResources().getColor(R.color.black_brown));
                        Submit.setEnabled(false);
                        Submit.setClickable(false);
                        ++Position;
                        System.out.print(Position);
                        if(Submit.getText().toString().equalsIgnoreCase("Submit & Quit")){
                            //MessaView.setVisibility(View.VISIBLE);
                            new DroidDialog.Builder(Activity_play_now.this)
                                    .icon(R.drawable.ic_action_tick)
                                    .title("Thank you For Playing!!!! ")
                                    .content("Cricflame Faster than your Thoughts")
                                    .cancelable(true, true)
                                    .positiveButton("OK", new DroidDialog.onPositiveListener() {
                                        @Override
                                        public void onPositive(Dialog droidDialog) {
                                            try{
                                                finish();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    })

                                    .animation(AnimUtils.AnimFadeInOut)
                                    .color(ContextCompat.getColor(Activity_play_now.this, R.color.colorPrimary), ContextCompat.getColor(Activity_play_now.this, R.color.white),
                                            ContextCompat.getColor(Activity_play_now.this, R.color.dark_indigo))
                                    .show();
                        }else{
                            if(Position == data.length()-1){
                                Questions.setVisibility(View.VISIBLE);
                                ExtraButtons.setVisibility(View.VISIBLE);
                                Submit.setVisibility(View.VISIBLE);
                                Submit.setText("Submit & Quit");
                                Questions.setText(data.getJSONObject(Position).getString("question"));
                                QuestionId = data.getJSONObject(Position).getString("id");
                                if(data.getJSONObject(Position).getString("option1").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option1").equalsIgnoreCase("null")){
                                    Option1.setVisibility(View.GONE);
                                    Option1_lay.setVisibility(View.GONE);
                                }else{
                                    Option1_lay.setVisibility(View.VISIBLE);
                                    Option1.setVisibility(View.VISIBLE);
                                    Option1.setText(data.getJSONObject(Position).getString("option1"));
                                }
                                if(data.getJSONObject(Position).getString("option2").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option2").equalsIgnoreCase("null")){
                                    Option2.setVisibility(View.GONE);
                                    Option2_lay.setVisibility(View.GONE);
                                }else{
                                    Option2_lay.setVisibility(View.VISIBLE);
                                    Option2.setVisibility(View.VISIBLE);
                                    Option2.setText(data.getJSONObject(Position).getString("option2"));
                                }
                                if(data.getJSONObject(Position).getString("option3").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option3").equalsIgnoreCase("null")){
                                    Option3.setVisibility(View.GONE);
                                    Option3_lay.setVisibility(View.GONE);
                                }else{
                                    Option3_lay.setVisibility(View.VISIBLE);
                                    Option3.setVisibility(View.VISIBLE);
                                    Option3.setText(data.getJSONObject(Position).getString("option3"));
                                }
                                if(data.getJSONObject(Position).getString("option4").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option4").equalsIgnoreCase("null")){
                                    Option4.setVisibility(View.GONE);
                                    Option4_lay.setVisibility(View.GONE);
                                }else{
                                    Option4_lay.setVisibility(View.VISIBLE);
                                    Option4.setVisibility(View.VISIBLE);
                                    Option4.setText(data.getJSONObject(Position).getString("option4"));
                                }
                            }else{
                                Submit.setText("Submit & Next");
                                Questions.setVisibility(View.VISIBLE);
                                ExtraButtons.setVisibility(View.VISIBLE);
                                Submit.setVisibility(View.VISIBLE);
                                Questions.setText(data.getJSONObject(Position).getString("question"));
                                QuestionId = data.getJSONObject(Position).getString("id");
                                if(data.getJSONObject(Position).getString("option1").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option1").equalsIgnoreCase("null")){
                                    Option1.setVisibility(View.GONE);
                                    Option1_lay.setVisibility(View.GONE);
                                }else{
                                    Option1_lay.setVisibility(View.VISIBLE);
                                    Option1.setVisibility(View.VISIBLE);
                                    Option1.setText(data.getJSONObject(Position).getString("option1"));
                                }
                                if(data.getJSONObject(Position).getString("option2").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option2").equalsIgnoreCase("null")){
                                    Option2.setVisibility(View.GONE);
                                    Option2_lay.setVisibility(View.GONE);
                                }else{
                                    Option2_lay.setVisibility(View.VISIBLE);
                                    Option2.setVisibility(View.VISIBLE);
                                    Option2.setText(data.getJSONObject(Position).getString("option2"));
                                }
                                if(data.getJSONObject(Position).getString("option3").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option3").equalsIgnoreCase("null")){
                                    Option3.setVisibility(View.GONE);
                                    Option3_lay.setVisibility(View.GONE);
                                }else{
                                    Option3_lay.setVisibility(View.VISIBLE);
                                    Option3.setVisibility(View.VISIBLE);
                                    Option3.setText(data.getJSONObject(Position).getString("option3"));
                                }
                                if(data.getJSONObject(Position).getString("option4").equalsIgnoreCase("") || data.getJSONObject(Position).getString("option4").equalsIgnoreCase("null")){
                                    Option4.setVisibility(View.GONE);
                                    Option4_lay.setVisibility(View.GONE);
                                }else{
                                    Option4_lay.setVisibility(View.VISIBLE);
                                    Option4.setVisibility(View.VISIBLE);
                                    Option4.setText(data.getJSONObject(Position).getString("option4"));
                                }
                            }
                        }

                    }else{
                        if(jObj.has("msg")){
                            TastyToast.makeText(Activity_play_now.this, jObj.getString("msg"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }else{
                            TastyToast.makeText(Activity_play_now.this, "Something Went Wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }
                    }
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    if(loadingDialog.isShowing())
                        loadingDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",dbHelper.getFieldValueUser("gameUserid"));
                params.put("ques_id",QuestionId);
                params.put("answer",Answer);
                return params;
            }
        };
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Activity_play_now.this).add(productRequest);

    }


}
