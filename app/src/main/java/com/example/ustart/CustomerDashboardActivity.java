package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class CustomerDashboardActivity extends AppCompatActivity {
    private CardView DisplayQR,UserProfile;
    private ImageView customer_dashboard_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        DisplayQR = (CardView) findViewById(R.id.btnDisplayQrCode);
        UserProfile = (CardView) findViewById(R.id.btnUserProfile);

        customer_dashboard_profile_image = (ImageView) findViewById(R.id.customer_dashboard_profile_image);

        DisplayQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DisplayQrCodeActivity();
            }
        });
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileActivity();
            }
        });



        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(CustomerDashboardActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject userObj=jsonObject.getJSONObject("user");
                    Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(customer_dashboard_profile_image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Intent homeIntent = new Intent(CustomerDashboardActivity.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        requestQueue.add(stringRequest);



        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.time);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                }catch (InterruptedException e){

                }
            }

        };
        t.start();

    }

    private void UserProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    private void DisplayQrCodeActivity() {
        Intent intent = new Intent(this, DisplayQrActivity.class);
        startActivity(intent);
    }



}
