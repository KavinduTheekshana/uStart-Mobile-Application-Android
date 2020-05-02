package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.ustart.Common.Stables;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ScannerActivity extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Context mContext;
    ProgressDialog progressDialog;
    String lat,lng,userID,timeString,dateString,printQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mContext = this;
        scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannView);
        resultData = findViewById(R.id.resultsOfQr);
        progressDialog=new Stables().showLoading(this);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(result.getText().contains("-")){
                            dateString=result.getText().split("-")[0];
                            timeString=result.getText().split("-")[1];
                            userID=result.getText().split("-")[2];
                            lat=result.getText().split("-")[3];
                            lng=result.getText().split("-")[4];




                            progressDialog.show();
                            //Check user id available in db
                            RequestQueue requestQueue= Volley.newRequestQueue(ScannerActivity.this);
                            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().CreateRoute(userID,dateString,timeString,lat,lng), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    try {
                                        JSONObject jsonObject=new JSONObject(response);

                                        if(jsonObject.getString("code").equals("1")){
                                            Toast.makeText(ScannerActivity.this, "Done", Toast.LENGTH_SHORT).show();



                                        }else{
                                            Toast.makeText(ScannerActivity.this, jsonObject.getString("Wrong Code"), Toast.LENGTH_SHORT).show();
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }





                                }
                            }, new Response.ErrorListener(){

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //hide loading
                                    Intent homeIntent = new Intent(ScannerActivity.this,LoginActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                            });
                            progressDialog.hide();
                            requestQueue.add(stringRequest);







                            mContext.startActivity(new Intent(mContext, CustomerOrdersActivity.class)
                                    .putExtra("customerid",userID)
                            );

                        }else{
                            Toast.makeText(ScannerActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                        }
                        
                    }
                });

            }
        });


        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }


    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(ScannerActivity.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


}
