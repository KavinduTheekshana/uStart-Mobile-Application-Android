package com.example.ustart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private ImageView BackButton,EditButton;
    private TextView profile_username,profile_user_type,profile_address,profile_email,profile_telephone,profile_joined_date;
    private CalendarView btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BackButton = (ImageView) findViewById(R.id.btnback);
        EditButton = (ImageView) findViewById(R.id.btnEdit);

        profile_username =  findViewById(R.id.profile_username);
        profile_user_type =  findViewById(R.id.profile_user_type);
        profile_address =  findViewById(R.id.profile_address);
        profile_email =  findViewById(R.id.profile_email);
        profile_telephone =  findViewById(R.id.profile_telephone);
        profile_joined_date =  findViewById(R.id.profile_joined_date);

        btnLogOut = findViewById(R.id.btnLogOut);




        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserProfileActivity();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });






            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);


                    //Check user id availabl in db
                    RequestQueue requestQueue= Volley.newRequestQueue(ProfileActivity.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hide loading
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getString("code").equals("1")){
                                    JSONObject userObj=jsonObject.getJSONObject("user");
                                    profile_username.setText(userObj.getString("name"));
                                    if (userObj.getString("user_type")=="1"){
                                        profile_user_type.setText("(Sels Rep)");
                                    }else if(userObj.getString("user_type")=="2"){
                                        profile_user_type.setText("(Customer)");
                                    }
                                    profile_address.setText(userObj.getString("address"));
                                    profile_email.setText(userObj.getString("email"));
                                    profile_telephone.setText(userObj.getString("telephone"));
                                    profile_joined_date.setText(userObj.getString("joined_date"));

                                }else{
                                    Intent homeIntent = new Intent(ProfileActivity.this,LoginActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hide loading
                            Intent homeIntent = new Intent(ProfileActivity.this,LoginActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    });

                    requestQueue.add(stringRequest);


    }

    private void LogOut() {
//        SharedPreferences.Editor editor = user.edit();
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void EditUserProfileActivity() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }


    public void goToBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





}



