package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextView BackButton;
    MaterialButton reset_password_submit_button;
    ProgressDialog progressDialog;
    TextInputEditText reset_password_new_password,reset_password_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        //progress
        progressDialog=new Stables().showLoading(this);


        BackButton = findViewById(R.id.btnBack);
        reset_password_submit_button = findViewById(R.id.reset_password_submit_button);
        reset_password_new_password = findViewById(R.id.reset_password_new_password);
        reset_password_confirm_password = findViewById(R.id.reset_password_confirm_password);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });

        reset_password_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password_re = reset_password_new_password.getText().toString().trim();
                String password_new = reset_password_confirm_password.getText().toString().trim();

                if (ValidateUserData.update_password_validate(password_re,password_new)){

                    if (ValidateUserData.check_password_validate(password_re,password_new)){

                        ResetPassword();

                    }else {
                        Toast.makeText(ResetPasswordActivity.this, "Password Not Same", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ResetPasswordActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ResetPassword() {

        progressDialog.show();

        try {
            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            final String url = new Stables().ResetPassword(sharedPreferences.getString("email","0"),reset_password_new_password.getText().toString().trim());
            RequestQueue requestQueue= Volley.newRequestQueue(ResetPasswordActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(url);
                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(ResetPasswordActivity.this,
                                            LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(ResetPasswordActivity.this, "Password Change Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ResetPasswordActivity.this, "Password Changing Process is Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResetPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
        progressDialog.dismiss();

    }

    public void goToBack(View view){
        startActivity(new Intent(ResetPasswordActivity.this,
                ForgotPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }


}
