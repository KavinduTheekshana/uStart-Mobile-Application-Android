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

public class VerificationActivity extends AppCompatActivity {
    TextView btnBack_verification_code;
    MaterialButton Submit_verification_code;
    TextInputEditText verification_code;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //progress
        progressDialog=new Stables().showLoading(this);

        btnBack_verification_code = findViewById(R.id.btnBack_verification_code);
        Submit_verification_code = findViewById(R.id.Submit_verification_code);
        verification_code = findViewById(R.id.verification_code);

        btnBack_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToBack();
            }
        });

        Submit_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificationCodeCheck();
            }
        });
    }

    private void VerificationCodeCheck() {
        progressDialog.show();

        try {
            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            final String url = new Stables().CodeVerification(sharedPreferences.getString("email","0"),verification_code.getText().toString().trim());
            RequestQueue requestQueue= Volley.newRequestQueue(VerificationActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(VerificationActivity.this,
                                            ResetPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(VerificationActivity.this, "Great! Now You Can Change Password", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(VerificationActivity.this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VerificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }

    private void GoToBack() {
        onBackPressed();
    }
}
