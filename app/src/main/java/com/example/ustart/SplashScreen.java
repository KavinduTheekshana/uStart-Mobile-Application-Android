package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.TYPE_INPUT_METHOD_DIALOG);
            setContentView(R.layout.activity_splash_screen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);

                    if(sharedPreferences!=null){
                        if(sharedPreferences.getString("userid","0").equals("0")){
                            //Login
                            Intent homeIntent = new Intent(SplashScreen.this,LoginActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }else{
                            //Check user id availabl in db
                                RequestQueue requestQueue= Volley.newRequestQueue(SplashScreen.this);
                                StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getCheckLoginController(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //hide loading
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            if(jsonObject.getString("code").equals("1")){
                                                Intent homeIntent = new Intent(SplashScreen.this,MainDashboardActivity.class);
                                                startActivity(homeIntent);
                                                finish();

                                            }else if(jsonObject.getString("code").equals("2")){
                                                Intent homeIntent = new Intent(SplashScreen.this,CustomerDashboardActivity.class);
                                                startActivity(homeIntent);
                                                finish();
                                            }
                                            else{
                                                Intent homeIntent = new Intent(SplashScreen.this,LoginActivity.class);
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
                                        Intent homeIntent = new Intent(SplashScreen.this,LoginActivity.class);
                                        startActivity(homeIntent);
                                        finish();
                                    }
                                });

                                requestQueue.add(stringRequest);
                            }

                    }else{

                        //Welcome
                        Intent homeIntent = new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }

                }
            },SPLASH_TIME_OUT);
        }
    }

