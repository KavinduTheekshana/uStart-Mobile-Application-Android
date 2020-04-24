package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class CurrentPasswordActivity extends AppCompatActivity {
    private TextInputEditText current_password;
    private TextView btnBackToEditProfile;
    private Button btnSubmitCurrentPassword;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_password);

        progressDialog=new Stables().showLoading(this);

        current_password =findViewById(R.id.current_password);
        btnBackToEditProfile =findViewById(R.id.btnBackToEditProfile);
        btnSubmitCurrentPassword =findViewById(R.id.btnSubmitCurrentPassword);

        btnSubmitCurrentPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCurrentPassword();
            }
        });

        btnBackToEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToEditProfile();
            }
        });





    }

    private void BackToEditProfile() {
        Intent intent = new Intent(CurrentPasswordActivity.this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkCurrentPassword() {

        if (!current_password.getText().toString().isEmpty()){
            progressDialog.show();
            sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getCurrentPassword(sharedPreferences.getString("userid","0"),current_password.getText().toString().trim()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading

                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){
                            Toast.makeText(CurrentPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CurrentPasswordActivity.this, UpdatePasswordActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(jsonObject.getString("code").equals("0")){
                            Toast.makeText(CurrentPasswordActivity.this, "Your Current Password is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CurrentPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    //hide loading
                    Toast.makeText(CurrentPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
//            TextViewCompat.setTextAppearance(username,R.style.);
        }
    }


}
