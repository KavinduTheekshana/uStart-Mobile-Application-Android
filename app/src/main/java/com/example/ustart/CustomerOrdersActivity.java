package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.example.ustart.adapters.CartItemAdapter;
import com.example.ustart.adapters.OrderItemAdapter;
import com.example.ustart.models.CartItem;
import com.example.ustart.models.OrderItem;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerOrdersActivity extends AppCompatActivity {
    private MaterialCardView customer_orders_back_button;
    RecyclerView recyclerViewOrders;
    public ArrayList<OrderItem> orderItems;
    OrderItemAdapter orderItemAdapter;
    ProgressDialog progressDialog;
    ImageView customer_orders_profile_image;
    TextView order_item_no_orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        //progress Dialog
        progressDialog=new Stables().showLoading(this);

        //No Order Text
        order_item_no_orders = findViewById(R.id.order_item_no_orders);

        //back Button
        customer_orders_back_button = findViewById(R.id.customer_orders_back_button);
        customer_orders_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToback(null);
            }
        });

        //recycle view
        orderItems=new ArrayList<>();
        recyclerViewOrders=findViewById(R.id.customer_orders_recycle_view);
        loadOrderItems();

        //profile Image
        customer_orders_profile_image=findViewById(R.id.customer_orders_profile_image);
//        Picasso.get().load(Stables.baseUrl+ getIntent().getStringExtra("userimage")).into(customer_orders_profile_image);
    }

    private void loadOrderItems() {
        orderItemAdapter=new OrderItemAdapter(this,orderItems,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOrders.setLayoutManager(mLayoutManager);
        recyclerViewOrders.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrders.setAdapter(orderItemAdapter);
        loadOrderItemsToList();
    }

    private void loadOrderItemsToList() {
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().SingleCustomerOrderdItems(getIntent().getStringExtra("customerid")), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        OrderItem oi = new OrderItem();
                        oi.setId(jsonObject.getString("product_id"));
                        oi.setProductName(jsonObject.getString("name"));
                        oi.setPrice(jsonObject.getString("product_price"));
                        oi.setQty(jsonObject.getString("qty"));
                        oi.setImage(jsonObject.getString("product_image"));
                        System.out.println(jsonObject.getString("userProfileImage"));
                        Picasso.get().load(Stables.baseUrl+ jsonObject.getString("userProfileImage")).into(customer_orders_profile_image);

                        orderItems.add(oi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }



                orderItemAdapter.notifyDataSetChanged();

                if(orderItems.size()!=0){
                    order_item_no_orders.setVisibility(View.GONE);
                }else{
                    order_item_no_orders.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        progressDialog.dismiss();
    }

    private void goToback(Object o) {
        onBackPressed();
    }
}
