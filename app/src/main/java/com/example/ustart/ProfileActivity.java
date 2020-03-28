package com.example.ustart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.PreviousActivities;
import com.example.ustart.Common.Stables;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profile_profile_image;
    private TextView profile_username,profile_user_type,profile_address,profile_email,profile_telephone,profile_joined_date;
    private MaterialCardView btnLogout,profile_back_button,profile_edit_button;
    private LinearLayout numberOfShops;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog=new Stables().showLoading(this);

        profile_back_button =  findViewById(R.id.profile_back_button);
        profile_edit_button =  findViewById(R.id.profile_edit_button);



        numberOfShops = findViewById(R.id.numberOfShops);
        profile_username =  findViewById(R.id.profile_username);
        profile_user_type =  findViewById(R.id.profile_user_type);
        profile_address =  findViewById(R.id.profile_address);
        profile_email =  findViewById(R.id.profile_email);
        profile_telephone =  findViewById(R.id.profile_telephone);
        profile_joined_date =  findViewById(R.id.profile_joined_date);
        profile_profile_image = (ImageView) findViewById(R.id.profile_profile_image);

        btnLogout = findViewById(R.id.btnLogout);

        profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });
        profile_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserProfileActivity();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });


        getProfileDetails();



    }

    public void getProfileDetails(){
        progressDialog.show();



        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);


        //Check user id availabl in db
        RequestQueue requestQueue= Volley.newRequestQueue(ProfileActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

                        if (userObj.getString("user_type")=="1"){
                            numberOfShops.setVisibility(View.VISIBLE);
                        }else if(userObj.getString("user_type")=="2"){
                            numberOfShops.setVisibility(View.INVISIBLE);
                        }

                        profile_address.setText(userObj.getString("address"));
                        profile_email.setText(userObj.getString("email"));
                        profile_telephone.setText(userObj.getString("telephone"));
                        profile_joined_date.setText(userObj.getString("joined_date"));


                        Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(profile_profile_image);

                        progressDialog.hide();

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
        SharedPreferences preferences =getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void EditUserProfileActivity() {
        PreviousActivities.profileActivity= ProfileActivity.this;
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



