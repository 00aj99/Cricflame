package com.cricflame.cricflame.Chat.firstlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Chat.Constants;
import com.cricflame.cricflame.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by marco on 13/07/16.
 */

public class UserFirstLoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int SELECT_PHOTO = 1;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Toolbar toolbar;
    private EditText nameEditText;
    private CircleImageView profileImageView;

    private boolean hasImageChanged = false;
    private EditText phone;
    private Spinner spCountryList;
    private String whichCountry = "";

    boolean phoneVerified = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);

        spCountryList = (Spinner) findViewById(R.id.countrySpinner);
        phone = (EditText) findViewById(R.id.phoneEditText);
        spCountryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                whichCountry = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String image = getIntent().getStringExtra(Constants.FIREBASE_USERS_IMAGE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("");
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        if (firebaseUser.getDisplayName() != null && firebaseUser.getDisplayName().length() > 0)
            nameEditText.setText(firebaseUser.getDisplayName());
        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);

        profileImageView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_firstlogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:

                if(isValidate()){

                    if (firebaseUser != null) {
                        final HashMap<String,String> values = new HashMap<>();
                        values.put(Constants.FIREBASE_USERS_EMAIL,firebaseUser.getEmail());
                        values.put(Constants.FIREBASE_USERS_NAME,nameEditText.getText().toString());
                        values.put(Constants.FIREBASE_USERS_UID,firebaseUser.getUid());

                        if (hasImageChanged) {
                            profileImageView.setDrawingCacheEnabled(true);
                            profileImageView.buildDrawingCache();
                            Bitmap bitmap = profileImageView.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            final String imageRef = profileImageView.getDrawingCache().hashCode() + System.currentTimeMillis() + ".jpg";
                            StorageReference mountainsRef = storageReference.child(imageRef);
                            UploadTask uploadTask = mountainsRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    values.put(Constants.FIREBASE_USERS_IMAGE,imageRef);
                                    databaseReference.child(Constants.FIREBASE_USERS)
                                            .child(firebaseUser.getUid()).setValue(values);
                                    setToken();

                                    finish();
                                }
                            });
                        } else {
                            databaseReference.child(Constants.FIREBASE_USERS)
                                    .child(firebaseUser.getUid()).setValue(values);
                            setToken();

                            finish();
                        }
                    }


                   /* if(whichCountry.equals("IND")){
                    //sendPhone();
                   // InputOtpDialog();
                    }else {
                        if (firebaseUser != null) {
                            final HashMap<String,String> values = new HashMap<>();
                            values.put(Constants.FIREBASE_USERS_EMAIL,firebaseUser.getEmail());
                            values.put(Constants.FIREBASE_USERS_NAME,nameEditText.getText().toString());
                            values.put(Constants.FIREBASE_USERS_UID,firebaseUser.getUid());

                            if (hasImageChanged) {
                                profileImageView.setDrawingCacheEnabled(true);
                                profileImageView.buildDrawingCache();
                                Bitmap bitmap = profileImageView.getDrawingCache();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                final String imageRef = profileImageView.getDrawingCache().hashCode() + System.currentTimeMillis() + ".jpg";
                                StorageReference mountainsRef = storageReference.child(imageRef);
                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        values.put(Constants.FIREBASE_USERS_IMAGE,imageRef);
                                        databaseReference.child(Constants.FIREBASE_USERS)
                                                .child(firebaseUser.getUid()).setValue(values);
                                        setToken();

                                        finish();
                                    }
                                });
                            } else {
                                databaseReference.child(Constants.FIREBASE_USERS)
                                        .child(firebaseUser.getUid()).setValue(values);
                                setToken();

                                finish();
                            }
                        }
                    }*/
                }
                break;
        }
        return false;
    }

    private boolean isValidate() {
        if (nameEditText.getText() == null) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (nameEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.getText() == null || phone.getText().toString().equals("")) {
            Toast.makeText(this, "Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.getText().toString().trim().length() < 10 || phone.getText().toString().trim().length() > 10) {
            Toast.makeText(this, "Enter 10 digit phone number...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (whichCountry.equals("") || whichCountry == null) {
            Toast.makeText(this, "Choose at least one country!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (whichCountry.equals("Select Country")) {
            Toast.makeText(this, "Choose at least one country!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void InputOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserFirstLoginActivity.this);
        builder.setMessage("Please enter OTP");
        builder.setCancelable(false);
        final EditText input = new EditText(UserFirstLoginActivity.this);
        input.setHint("Enter OTP here!");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        //input.setHeight(15);
        builder.setView(input);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(input.getText().toString()!=""){
                   // pDialog.show();
                    confirmOTP(input.getText().toString().trim());
                }
                else{
                    Toast.makeText(UserFirstLoginActivity.this, "Enter OTP You Received!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void sendPhone(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://cricflame.com/verify/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFirstLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //Adding the parameters otp and username
                params.put("phone_no", phone.getText().toString().trim());
                params.put("password", whichCountry);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(UserFirstLoginActivity.this);
        queue.add(stringRequest);
    }

    private void confirmOTP(final String otp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://cricflame.com/verify/confirm.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("success")){
                            if (firebaseUser != null) {
                                final HashMap<String,String> values = new HashMap<>();
                                values.put(Constants.FIREBASE_USERS_EMAIL,firebaseUser.getEmail());
                                values.put(Constants.FIREBASE_USERS_NAME,nameEditText.getText().toString());
                                values.put(Constants.FIREBASE_USERS_UID,firebaseUser.getUid());

                                if (hasImageChanged) {
                                    profileImageView.setDrawingCacheEnabled(true);
                                    profileImageView.buildDrawingCache();
                                    Bitmap bitmap = profileImageView.getDrawingCache();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();

                                    final String imageRef = profileImageView.getDrawingCache().hashCode() + System.currentTimeMillis() + ".jpg";
                                    StorageReference mountainsRef = storageReference.child(imageRef);
                                    UploadTask uploadTask = mountainsRef.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {

                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            values.put(Constants.FIREBASE_USERS_IMAGE,imageRef);
                                            databaseReference.child(Constants.FIREBASE_USERS)
                                                    .child(firebaseUser.getUid()).setValue(values);
                                            setToken();

                                            finish();
                                        }
                                    });
                                } else {
                                    databaseReference.child(Constants.FIREBASE_USERS)
                                            .child(firebaseUser.getUid()).setValue(values);
                                    setToken();

                                    finish();
                                }
                            }
                        }else if(response.equalsIgnoreCase("fail")){
                            Toast.makeText(UserFirstLoginActivity.this,"Wrong OTP Please Try Again",Toast.LENGTH_LONG).show();
                            InputOtpDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        Toast.makeText(UserFirstLoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //Adding the parameters otp and username
                params.put("phone_no", phone.getText().toString().trim());
                params.put("otp", otp);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(UserFirstLoginActivity.this);
        queue.add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profileImageView.setImageBitmap(selectedImage);
                        hasImageChanged = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profileImageView:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
        }
    }

    private void setToken() {
        DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_FCM);
        tokenRef.child(firebaseUser.getUid() + "/" + Constants.FIREBASE_FCM_TOKEN).setValue(FirebaseInstanceId.getInstance().getToken());
        tokenRef.child(firebaseUser.getUid() + "/" + Constants.FIREBASE_FCM_ENABLED).setValue(Boolean.TRUE.toString());
    }

}
