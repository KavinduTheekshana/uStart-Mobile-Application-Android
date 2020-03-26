package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.example.ustart.adapters.ProductItemAdapter;
import com.example.ustart.models.ProductItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;

public class ProductsForUsers extends AppCompatActivity {
    private ImageView BackButton;

    RecyclerView recyclerView;
    ProductItemAdapter productItemAdapter;
    ArrayList<ProductItem> productItem;
    ProgressDialog progressDialog;
    JSONObject productObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_for_users);

        progressDialog=new Stables().showLoading(this);

        BackButton = (ImageView) findViewById(R.id.btnback);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });


        productItem=new ArrayList<>();
        recyclerView=findViewById(R.id.product_for_all_users_all_products);

        loadItems();



    }




    private void loadItems() {
        productItemAdapter=new ProductItemAdapter(productItem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productItemAdapter);
        loadItemsToList();
    }

    private void loadItemsToList() {
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getProductList(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                            ProductItem pi = new ProductItem();
                            pi.setTitle(jsonObject.getString("name"));
                            pi.setDescription(jsonObject.getString("product_price"));
                            pi.setPrice("LKR "+jsonObject.getString("product_price")+".00");

                            productItem.add(pi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                productItemAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
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
    }





















//        RequestQueue requestQueue= Volley.newRequestQueue(ProductsForUsers.this);
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProductList(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {





//                try {
//                    JSONObject jsonObject=new JSONObject(response);
//                    productObj=jsonObject.getJSONObject("product");
//                    System.out.println("sfsdfsdfsd"+productObj.length());
//
//                    for (int i = 0; i < response.length(); i++) {
//                        System.out.println(response.length());
//
//
//                            ProductItem pi = new ProductItem();
//                            pi.setTitle(jsonObject.getString("name"));
//                            pi.setTitle(jsonObject.getString("product_price"));
//                            productItem.add(pi);
//
//
//                        productItemAdapter.notifyDataSetChanged();
//                    }
//
//
//
//
//
//                    progressDialog.hide();
//
//                }catch(Exception e){
//                    System.out.println(e.getMessage());
//                    progressDialog.hide();
//                }
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
//                Toast.makeText(ProductsForUsers.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(stringRequest);















//        productItem.add(new ProductItem("title","desc"));
//        productItem.add(new ProductItem("title","desc"));
//        productItem.add(new ProductItem("title","desc"));
//
//        productItemAdapter.notifyDataSetChanged();
//    }



    public void goToBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
