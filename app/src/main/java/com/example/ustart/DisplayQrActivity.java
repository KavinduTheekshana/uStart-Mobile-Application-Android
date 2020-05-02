package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DisplayQrActivity extends AppCompatActivity {

    EditText qrvalue;
    Button generateBtn;
    ImageView qrImage;
    ProgressDialog progressDialog;
    public String lat,lng,userID,timeString,dateString,printQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr);

        qrvalue = findViewById(R.id.qrInput);
        generateBtn = findViewById(R.id.generateBtn);
        qrImage = findViewById(R.id.qrPlaceHolder);
        progressDialog=new Stables().showLoading(this);


        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                long date = System.currentTimeMillis();
                SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy");
                timeString = sdftime.format(date);
                dateString = sdfdate.format(date);
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                userID = sharedPreferences.getString("userid","0");



                //Check user id available in db
                RequestQueue requestQueue= Volley.newRequestQueue(DisplayQrActivity.this);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getLatAndLng(userID), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            lat = jsonObject.getString("lat");
                            lng = jsonObject.getString("lng");

                            printQR = dateString + "-" + timeString + "-" + userID + "-" + lat + "-" + lng;

                            QRGEncoder qrgEncoder = new QRGEncoder(printQR,null, QRGContents.Type.TEXT,500);
                            try {
                                Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                                qrImage.setImageBitmap(qrBits);
                            } catch (WriterException e) {
                                e.printStackTrace();
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
                        Intent homeIntent = new Intent(DisplayQrActivity.this,LoginActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                });
                progressDialog.hide();
                requestQueue.add(stringRequest);

















               


            }
        });
    }
}
