package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class SingleProductActivity extends AppCompatActivity {

    TextView single_product_name,single_product_description,single_product_category,single_product_price,single_product_qty;
    ImageView single_product_image;
    MaterialCardView single_product_back_button,single_product_qty_up,single_product_qty_down;
    ProgressDialog progressDialog;
    MaterialButton single_product_add_to_cart_button;
    int qty = 0;
    String cartUserId,cartUserType,cartProductId,cartQty,cartIsAvailableForCartStatus,cartIsAvailableForPublicStatus;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        //Loading
        progressDialog=new Stables().showLoading(this);

        //Back Button
        single_product_back_button = findViewById(R.id.single_product_back_button);

        //Product Description Text Views
        single_product_name = findViewById(R.id.single_product_name);
        single_product_description = findViewById(R.id.single_product_description);
        single_product_category = findViewById(R.id.single_product_category);
        single_product_price = findViewById(R.id.single_product_price);
        single_product_image = findViewById(R.id.single_product_image);

        //Ad to cart buttons
        single_product_add_to_cart_button = findViewById(R.id.single_product_add_to_cart_button);
        single_product_qty_up = findViewById(R.id.single_product_qty_up);
        single_product_qty_down = findViewById(R.id.single_product_qty_down);
        single_product_qty = findViewById(R.id.single_product_qty);

        //load date to text views
        loadDate();


        //cart buttons
        single_product_qty_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyUp();
            }
        });
        single_product_qty_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyDown();
            }
        });
        single_product_add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { cartProcess();
            }
        });


        single_product_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });

    }

    private void cartProcess() {
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);

        cartUserId = sharedPreferences.getString("userid","0");
        cartUserType = sharedPreferences.getString("userType","0");
        cartProductId = getIntent().getStringExtra("id");
        cartQty = single_product_qty.getText().toString().trim();


        try {

            String url=new Stables().CreateCart(cartUserId, cartUserType, cartProductId, cartQty);
            System.out.println(url);
            RequestQueue requestQueue= Volley.newRequestQueue(SingleProductActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SingleProductActivity.this, "Your Item Is Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SingleProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    private void qtyDown() {
        if (qty>0){
            qty -= 1;
            single_product_qty.setText(""+qty);
        }
    }

    private void qtyUp() {
        if (qty<100){
            qty += 1;
            single_product_qty.setText(""+qty);
        }
    }

    private void loadDate() {
        progressDialog.show();
        single_product_name.setText((getIntent().getStringExtra("title")!=null)?getIntent().getStringExtra("title"):"");
        single_product_description.setText((getIntent().getStringExtra("description")!=null)?getIntent().getStringExtra("description"):"");
        single_product_price.setText((getIntent().getStringExtra("price")!=null)?getIntent().getStringExtra("price"):"");
        Picasso.get().load(Stables.baseUrl+ getIntent().getStringExtra("image")).into(single_product_image);



        RequestQueue requestQueue= Volley.newRequestQueue(SingleProductActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getCategoryName(getIntent().getStringExtra("category")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    single_product_category.setText(jsonObject.getString("name"));
                    progressDialog.hide();
                }catch(Exception e){
                    Toast.makeText(SingleProductActivity.this, "Can't Load Category Name", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressDialog.hide();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Toast.makeText(SingleProductActivity.this, "Can't Load Category Name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    private void goToBack(View view) {
        onBackPressed();
    }
}
