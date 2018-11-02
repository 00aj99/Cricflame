package com.cricflame.cricflame.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.LiveLine1.PitchReport.AnalysisActivity;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TourData;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Register extends AppCompatActivity {
    String Key,Team1,Team2,Flag1,Flag2;
    EditText Name,Email,Password,Mobile;
    Button Register;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_play_now);

        loadingDialog = new LoadingDialog(Activity_Register.this,"","");

        Key = getIntent().getExtras().getString("key");
        Team1 = getIntent().getExtras().getString("team1");
        Team2 = getIntent().getExtras().getString("team2");
        Flag1 = getIntent().getExtras().getString("flag1");
        Flag2 = getIntent().getExtras().getString("flag2");


        Name = findViewById(R.id.nameEditText);
        Email = findViewById(R.id.emailEditText);
        Password = findViewById(R.id.passwordEditText);
        Mobile = findViewById(R.id.mobileEditText);
        Register = findViewById(R.id.signupButton);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Name.getText().toString().trim().length()<1){
                    Name.setError("");
                    Name.requestFocus();
                }else if(!isEmailValid(Email.getText().toString().trim())){
                    Email.setError("");
                    Email.requestFocus();
                }else if(Password.getText().toString().trim().length()<4){
                    Password.setError("");
                    Password.requestFocus();
                }else if(Mobile.getText().toString().trim().length()<10){
                    Mobile.setError("");
                    Mobile.requestFocus();
                }else{
                    RegisterUser();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(Activity_Register.this , Activity_Play_Login.class);
        i.putExtra("key",Key);
        i.putExtra("team1", Team1);
        i.putExtra("team2", Team2);
        i.putExtra("flag1", Flag1);
        i.putExtra("flag2", Flag2);
        finish();
        startActivity(i);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void RegisterUser() {
        loadingDialog.show();

        StringRequest productRequest = new StringRequest(Request.Method.POST, Global.URL + "registration.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

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
                params.put("name", Name.getText().toString());
                params.put("email",Email.getText().toString());
                params.put("password",Password.getText().toString());
                params.put("mobile",Mobile.getText().toString());
                return params;
            }
        };
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Activity_Register.this).add(productRequest);

    }

}
