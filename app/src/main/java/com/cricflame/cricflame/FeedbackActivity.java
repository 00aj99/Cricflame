package com.cricflame.cricflame;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import com.cricflame.cricflame.Help.FirebaseUtils;
import com.cricflame.cricflame.Model.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    TextInputEditText _name,_email,_subject,_phone,_feedback;
    private Button _submitButton;
    private ImageView back;

    LoadingDialog progressDialog;


    private DatabaseReference mRootref;
    private DatabaseReference childref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {
        progressDialog=new LoadingDialog(FeedbackActivity.this);
        _name = (TextInputEditText) findViewById(R.id.nameEditText);
        _email = (TextInputEditText) findViewById(R.id.emailEditText);
        _subject = (TextInputEditText) findViewById(R.id.subjectEditText);
        _phone = (TextInputEditText) findViewById(R.id.phoneEditText);
        _feedback = (TextInputEditText) findViewById(R.id.feedbackEditText);

        _submitButton = (Button)findViewById(R.id.submitButton);
        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidate()){
                    postFeedbackToFirebase();
                }
            }
        });


    }

    private void postFeedbackToFirebase() {
        progressDialog.show();
        String name,email,phone,feedback;
        String subject;
        name = _name.getText().toString().trim();
        email = _email.getText().toString().trim();
        phone = _phone.getText().toString().trim();
        subject = _subject.getText().toString().trim();
        feedback = _feedback.getText().toString().trim();


        mRootref = FirebaseUtils.getSecondaryDatabase(FeedbackActivity.this).getReference();
        childref = mRootref.child("Feedback");
        Map<String, String> map = new HashMap<String,String>();
        map.put("name", name);
        map.put("email", email);
        map.put("phone", phone);
        map.put("subject", subject);
        map.put("feedback", feedback);
        map.put("from", "Cricflame App");
        childref.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                _name.setText("");
                _email.setText("");
                _phone.setText("");
                _subject.setText("");
                _feedback.setText("");
                progressDialog.dismiss();

            }
        });




    }


    private boolean isValidate() {
        String name,email,phone,feedback;
        name = _name.getText().toString().trim();
        email = _email.getText().toString().trim();
        phone = _phone.getText().toString();
        feedback = _feedback.getText().toString();

        if(name.equals("") || email.equals("") || phone.equals("") || feedback.equals("")){
            Toast.makeText(this, "Mandatory fields must not be blank!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
