package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.ustart.Common.Stables;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class MainDashboardActivity extends AppCompatActivity {

    private CardView ScanQrCode,UserProfile,ProductList,Customers;
    private ImageView main_dashboard_profile_image;
    private SwipeButton swipeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);



        ScanQrCode = (CardView) findViewById(R.id.btnScanQrCode);
        UserProfile = (CardView) findViewById(R.id.btnUserProfile);
        ProductList = (CardView) findViewById(R.id.btnProductList);
        Customers = (CardView) findViewById(R.id.btnCustomers);

        swipeButton = findViewById(R.id.swipe_btn);

        main_dashboard_profile_image = (ImageView) findViewById(R.id.main_dashboard_profile_image);

        ScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanQrCodeActivity();
            }
        });
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileActivity();
            }
        });
        ProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListActivity();
            }
        });
        Customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomersActivity();
            }
        });



        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(MainDashboardActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject userObj=jsonObject.getJSONObject("user");
                    Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(main_dashboard_profile_image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Intent homeIntent = new Intent(MainDashboardActivity.this,LoginActivity.class);
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


//
//        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
//            @Override
//            public void onStateChange(boolean active) {
//                Toast.makeText(MainDashboardActivity.this, "State: " + active, Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    private void CustomersActivity() {
        Intent intent = new Intent(this, ListOfCustomersActivity.class);
        startActivity(intent);
    }

    private void ScanQrCodeActivity() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }

    private void UserProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void ProductListActivity() {
        Intent intent = new Intent(this, ProductsForUsers.class);
        startActivity(intent);
    }

}
