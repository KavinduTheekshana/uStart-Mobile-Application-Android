package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
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

    private CardView ScanQrCode,UserProfile,ProductList,Customers,onlineround;
    private ImageView main_dashboard_profile_image;
    private SwipeButton swipeButton;
    TextView tdate,onlinetext;
    private int status = 0;
    private int statusAlready = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);



        ScanQrCode = (CardView) findViewById(R.id.btnScanQrCode);
        UserProfile = (CardView) findViewById(R.id.btnUserProfile);
        ProductList = (CardView) findViewById(R.id.btnProductList);
        Customers = (CardView) findViewById(R.id.btnCustomers);

        swipeButton = findViewById(R.id.swipe_btn);
        onlineround = findViewById(R.id.onlineround);
        onlinetext = findViewById(R.id.onlinetext);
        tdate = (TextView) findViewById(R.id.time);

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








        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                if (statusAlready == 0){
                    if(status==0){
                        if(active){
                            sendIntimeData();
                            status = 1;
                        }
                    }else {
                        if(active){
                            sendOutTimeData();
                            status = 0;
                            statusAlready = 1;
                        }
                    }
                }else {
                    Toast.makeText(MainDashboardActivity.this, "You Can't Mark Attendance Again in Today", Toast.LENGTH_SHORT).show();
                }
                


            }
        });



        profilePic();
        onlineStatus();




        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

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


    private void onlineStatus() {
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy");
        final String dateString = sdfdate.format(date);
        RequestQueue requestQueue= Volley.newRequestQueue(MainDashboardActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().status(sharedPreferences.getString("userid","0"),dateString), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        statusAlready = 1;
                    }




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
    }

    private void profilePic() {
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
    }

    private void sendIntimeData() {
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy");
        final String timeString = sdftime.format(date);
        final String dateString = sdfdate.format(date);
        RequestQueue requestQueue= Volley.newRequestQueue(MainDashboardActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().intime(sharedPreferences.getString("userid","0"),dateString,timeString), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {


                    if (!response.isEmpty()){
                        Toast.makeText(MainDashboardActivity.this, "Online", Toast.LENGTH_SHORT).show();
                        textOnline();
                    }else {
                        Toast.makeText(MainDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
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
    }

    private void sendOutTimeData() {
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy");
        final String timeString = sdftime.format(date);
        final String dateString = sdfdate.format(date);
        RequestQueue requestQueue= Volley.newRequestQueue(MainDashboardActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().outTime(sharedPreferences.getString("userid","0"),dateString,timeString), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {


                    if (!response.isEmpty()){
                        Toast.makeText(MainDashboardActivity.this, "Offline", Toast.LENGTH_SHORT).show();
                        textOffline();
                    }else {
                        Toast.makeText(MainDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
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
    }


    private void textOnline() {
        onlineround.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_green));
        onlinetext.setTextColor(ContextCompat.getColor(this, R.color.card_green));
        onlinetext.setText("Online");
        swipeButton.setText("SWAP TO OFFLINE");
    }

    private void textOffline() {
        onlineround.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_orange));
        onlinetext.setTextColor(ContextCompat.getColor(this, R.color.card_orange));
        onlinetext.setText("Offline");
        swipeButton.setText("SWAP TO ONLINE");
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
