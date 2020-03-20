package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private TextView ForgotPassword;
    private TextInputEditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.btnLogin);
        ForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewLoginActivity();
            }
        });
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewForgotPasswordActivity();
            }
        });

    }

    private void ViewForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void ViewLoginActivity() {
        createLogin();

    }

    public void createLogin() {
        if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            //loading
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getLoginController(username.getText().toString(),password.getText().toString()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading
                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){
                            Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            JSONObject userObj=jsonObject.getJSONObject("user");
                            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("userid",userObj.getString("id"));
                            editor.putString("email",userObj.getString("email"));
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this, MainDashboardActivity.class);
                            startActivity(intent);

                        }else if(jsonObject.getString("code").equals("2")){
                            Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            JSONObject userObj=jsonObject.getJSONObject("user");
                            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("userid",userObj.getString("id"));
                            editor.putString("email",userObj.getString("email"));
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
                            startActivity(intent);
                        }else if(jsonObject.getString("code").equals("3")){
                            Toast.makeText(LoginActivity.this, "Your Blocked", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    //hide loading
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
//            TextViewCompat.setTextAppearance(username,R.style.);
        }
    }
}
