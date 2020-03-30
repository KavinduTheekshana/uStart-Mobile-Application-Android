package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.example.ustart.adapters.CartItemAdapter;
import com.example.ustart.adapters.ProductItemAdapter;
import com.example.ustart.models.CartItem;
import com.example.ustart.models.CategoryItem;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private MaterialCardView cart_back_button,cart_home_button;
    RecyclerView recyclerViewProduct;
    ArrayList<CartItem> cartItems;
    CartItemAdapter cartItemAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    TextView cart_empty_cart;
    Button cart_order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        progressDialog=new Stables().showLoading(this);

        cart_empty_cart = findViewById(R.id.cart_empty_cart);
        cart_order_button = findViewById(R.id.cart_order_button);

        cart_back_button = findViewById(R.id.cart_back_button);
        cart_home_button = findViewById(R.id.cart_home_button);
        cart_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToback(null);
            }
        });
        cart_home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToHome(null);
            }
        });

        cartItems=new ArrayList<>();
        recyclerViewProduct=findViewById(R.id.cart_recycle_view);
        loadCartItems();
    }

    private void goToHome(Object o) {
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        int usertype = Integer.parseInt(sharedPreferences.getString("userType","0"));
        if(usertype==1){
            Intent intent = new Intent(CartActivity.this, MainDashboardActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(CartActivity.this, CustomerDashboardActivity.class);
            startActivity(intent);
        }


    }

    private void loadCartItems() {
        cartItemAdapter=new CartItemAdapter(this,cartItems,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProduct.setAdapter(cartItemAdapter);
        loadItemsToList();
    }

    private void loadItemsToList() {

        progressDialog.show();

        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getCartItemList(sharedPreferences.getString("userid","0")), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        CartItem ci = new CartItem();
                        ci.setId(jsonObject.getString("id"));
                        ci.setTitle(jsonObject.getString("title"));
                        ci.setPrice("LKR "+jsonObject.getString("price")+".00");
                        ci.setQty(jsonObject.getString("qty"));

                        int qty = jsonObject.getInt("qty");
                        int price = jsonObject.getInt("price");
                        String totalPrice = String.valueOf(qty*price);
                        ci.setTotal("LKR "+totalPrice+".00");

                        ci.setImage(jsonObject.getString("image"));

                        cartItems.add(ci);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                if(cartItems.size()!=0){
                    cart_empty_cart.setVisibility(View.GONE);
                    cart_order_button.setEnabled(true);
                }else{
                    cart_empty_cart.setVisibility(View.VISIBLE);
                    cart_order_button.setEnabled(false);
                }
                cartItemAdapter.notifyDataSetChanged();
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
