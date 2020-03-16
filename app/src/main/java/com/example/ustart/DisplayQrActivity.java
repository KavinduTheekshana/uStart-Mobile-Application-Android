package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DisplayQrActivity extends AppCompatActivity {

    EditText qrvalue;
    Button generateBtn;
    ImageView qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr);

        qrvalue = findViewById(R.id.qrInput);
        generateBtn = findViewById(R.id.generateBtn);
        qrImage = findViewById(R.id.qrPlaceHolder);


        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String dateString = sdf.format(date);
                String userID = "1";

                String printQR = dateString + " - " + userID;


               
                    QRGEncoder qrgEncoder = new QRGEncoder(printQR,null, QRGContents.Type.TEXT,500);
                    try {
                        Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(qrBits);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

            }
        });
    }
}
