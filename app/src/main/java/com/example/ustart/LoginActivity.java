package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.btnLogin);
        ForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);


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
        Intent intent = new Intent(this, MainDashboardActivity.class);
        startActivity(intent);
    }
}
