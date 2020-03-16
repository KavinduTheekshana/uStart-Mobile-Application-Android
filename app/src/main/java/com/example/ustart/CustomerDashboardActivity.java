package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class CustomerDashboardActivity extends AppCompatActivity {
    private CardView DisplayQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        DisplayQR = (CardView) findViewById(R.id.btnDisplayQrCode);

        DisplayQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DisplayQrCodeActivity();
            }
        });

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

    }


    private void DisplayQrCodeActivity() {
        Intent intent = new Intent(this, DisplayQrActivity.class);
        startActivity(intent);
    }



}
