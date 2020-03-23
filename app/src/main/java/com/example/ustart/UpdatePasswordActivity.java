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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class UpdatePasswordActivity extends AppCompatActivity {
    TextInputEditText txtPasswordUpdate,txtPasswordConfirmUpdate;
    Button btnUpdatePassword;
    TextView btnBackToEditProfile_Update;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        progressDialog=new Stables().showLoading(this);

        txtPasswordUpdate = findViewById(R.id.txtPasswordUpdate);
        txtPasswordConfirmUpdate = findViewById(R.id.txtPasswordConfirmUpdate);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        btnBackToEditProfile_Update = findViewById(R.id.btnBackToEditProfile_Update);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassword();
            }
        });

        btnBackToEditProfile_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToEditProfile();
            }
        });
    }

    private void UpdatePassword() {

        if (!txtPasswordUpdate.getText().toString().isEmpty() && !txtPasswordConfirmUpdate.getText().toString().isEmpty()){
            progressDialog.show();
            sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().UpdatePasswordController(sharedPreferences.getString("userid","0"),txtPasswordUpdate.getText().toString(),txtPasswordConfirmUpdate.getText().toString()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading
                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){
                            Toast.makeText(UpdatePasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UpdatePasswordActivity.this, EditProfileActivity.class);
                            startActivity(intent);
                            finish();

                        }else if(jsonObject.getString("code").equals("0")){
                            Toast.makeText(UpdatePasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(UpdatePasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.hide();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(UpdatePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
//            TextViewCompat.setTextAppearance(username,R.style.);
        }




    }


    private void BackToEditProfile() {
        Intent intent = new Intent(UpdatePasswordActivity.this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }
}


