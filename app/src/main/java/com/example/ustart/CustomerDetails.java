package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ustart.Common.Stables;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

public class CustomerDetails extends AppCompatActivity {
    ProgressDialog progressDialog;
    MaterialCardView customer_details_back_button;
    TextView customer_details_name,customer_details_shopname,customer_details_address,
            customer_details_email,customer_details_telephone,customer_details_joined_date,
            customer_details_city;
    ImageView customer_details_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        //Loading
        progressDialog=new Stables().showLoading(this);

        //Back Button
        customer_details_back_button = findViewById(R.id.customer_details_back_button);
        customer_details_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });


        //customer Details text Views
        customer_details_name = findViewById(R.id.customer_details_name);
        customer_details_shopname = findViewById(R.id.customer_details_shopname);
        customer_details_address = findViewById(R.id.customer_details_address);
        customer_details_email = findViewById(R.id.customer_details_email);
        customer_details_telephone = findViewById(R.id.customer_details_telephone);
        customer_details_joined_date = findViewById(R.id.customer_details_joined_date);
        customer_details_city = findViewById(R.id.customer_details_city);
        customer_details_image = findViewById(R.id.customer_details_image);

        //load date to text views
        loadDate();

    }

    private void goToBack(Object o) {
        onBackPressed();
    }

    private void loadDate() {
        progressDialog.show();
        customer_details_name.setText((getIntent().getStringExtra("name")!=null)?getIntent().getStringExtra("name"):"");
        customer_details_shopname.setText((getIntent().getStringExtra("shopname")!=null)?getIntent().getStringExtra("shopname"):"");
        customer_details_address.setText((getIntent().getStringExtra("address")!=null)?getIntent().getStringExtra("address"):"");
        customer_details_email.setText((getIntent().getStringExtra("email")!=null)?getIntent().getStringExtra("email"):"");
        customer_details_telephone.setText((getIntent().getStringExtra("telephone")!=null)?getIntent().getStringExtra("telephone"):"");
        customer_details_joined_date.setText((getIntent().getStringExtra("joined_date")!=null)?getIntent().getStringExtra("joined_date"):"");
        customer_details_city.setText((getIntent().getStringExtra("city")!=null)?getIntent().getStringExtra("city"):"");

        Picasso.get().load(Stables.baseUrl+ getIntent().getStringExtra("image")).into(customer_details_image);

        progressDialog.dismiss();
    }
}
