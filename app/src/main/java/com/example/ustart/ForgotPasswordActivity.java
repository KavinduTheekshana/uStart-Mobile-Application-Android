package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ustart.Common.Stables;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btnForgotPassword;
    private TextView BackButton;
    private TextInputEditText forgot_password_email_address;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        //progress
        progressDialog=new Stables().showLoading(this);

        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        BackButton = (TextView) findViewById(R.id.btnBack);
        forgot_password_email_address = findViewById(R.id.forgot_password_email_address);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewResetPasswordActivity();
            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });
    }

    private void ViewResetPasswordActivity() {
        progressDialog.show();

        final String url = new Stables().EmailVerification(forgot_password_email_address.getText().toString().trim());

        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                try {
                    URL urlObj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
                    con.setRequestMethod("GET");

                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    System.out.println(sb.toString());

                    JSONObject jsonObject=new JSONObject(sb.toString());

                    if(jsonObject.getString("code").equals("1")){

                        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email",forgot_password_email_address.getText().toString().trim());
                        editor.commit();

                        doProcessInUiThread("Please Check Your Email Address");

                        progressDialog.dismiss();


                        startActivity(new Intent(ForgotPasswordActivity.this,
                                VerificationActivity.class));
                    }
                    else{
                        doProcessInUiThread("Cant Found Email");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


//        Intent intent = new Intent(this, ResetPasswordActivity.class);
//        startActivity(intent);
    }

    private void doProcessInUiThread(final String message) {
        ForgotPasswordActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
