package com.cricflame.cricflame.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.DBHelper.DBHelper;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.R;
import com.cricflame.cricflame.Util.SessionManager_Play_Now;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Play_Login extends AppCompatActivity {
    Button Register_Now,Login;
    String Key,Team1,Team2,Flag1,Flag2;
    EditText Email,Password;
    LoadingDialog loadingDialog;
    DBHelper dbHelper;
    SessionManager_Play_Now sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_now_login);

        loadingDialog = new LoadingDialog(Activity_Play_Login.this,"","");
        dbHelper = new DBHelper(Activity_Play_Login.this);
        sessionManager = new SessionManager_Play_Now(Activity_Play_Login.this);
        Key = getIntent().getExtras().getString("key");
        Team1 = getIntent().getExtras().getString("team1");
        Team2 = getIntent().getExtras().getString("team2");
        Flag1 = getIntent().getExtras().getString("flag1");
        Flag2 = getIntent().getExtras().getString("flag2");

        Register_Now = findViewById(R.id.registerButton);
        Login = findViewById(R.id.loginButton);
        Email = findViewById(R.id.emailEditText);
        Password = findViewById(R.id.passwordEditText);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmailValid(Email.getText().toString().trim())){
                    Email.setError("");
                    Email.requestFocus();
                }else if(Password.getText().toString().trim().length()<4){
                    Password.setError("");
                    Password.requestFocus();
                }else{
                    LoginUser();
                }

            }
        });


        Register_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_Play_Login.this , Activity_Register.class);
                i.putExtra("key",Key);
                i.putExtra("team1", Team1);
                i.putExtra("team2", Team2);
                i.putExtra("flag1", Flag1);
                i.putExtra("flag2", Flag2);
                finish();
                startActivity(i);
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void LoginUser() {
        loadingDialog.show();

        StringRequest productRequest = new StringRequest(Request.Method.POST, Global.URL + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    if(jObj.getString("status").equalsIgnoreCase("200")){
                        JSONArray data =  jObj.getJSONArray("data");
                        dbHelper.insertFielduser("gameUserid",data.getJSONObject(0).getString("id"));
                        sessionManager.setLogin(true);
                        Intent i = new Intent(Activity_Play_Login.this , Activity_play_now.class);
                        i.putExtra("key",Key);
                        i.putExtra("team1", Team1);
                        i.putExtra("team2", Team2);
                        i.putExtra("flag1", Flag1);
                        i.putExtra("flag2", Flag2);
                        finish();
                        startActivity(i);
                    }else{
                        if(jObj.has("msg")){
                            TastyToast.makeText(Activity_Play_Login.this, jObj.getString("msg"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }else{
                            TastyToast.makeText(Activity_Play_Login.this, "Something Went Wrong", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                params.put("email",Email.getText().toString());
                params.put("password",Password.getText().toString());
                return params;
            }
        };
        productRequest.setShouldCache(false);productRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Activity_Play_Login.this).add(productRequest);

    }
}
