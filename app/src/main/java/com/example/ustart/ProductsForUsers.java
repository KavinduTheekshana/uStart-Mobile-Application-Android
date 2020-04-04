package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.example.ustart.adapters.CategoryItemAdapter;
import com.example.ustart.adapters.ProductItemAdapter;
import com.example.ustart.models.CategoryItem;
import com.example.ustart.models.ProductItem;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;

public class ProductsForUsers extends AppCompatActivity {
    private MaterialCardView product_for_all_users_back_button,product_for_all_users_cart_button;

    private TextView product_for_all_users_empty_product;

    private TextInputEditText product_for_all_users_search;
    RecyclerView recyclerViewProduct,recyclerViewCategory;
    ProductItemAdapter productItemAdapter;
    CategoryItemAdapter categoryItemAdapter;
    public ArrayList<ProductItem> productItem;
    ArrayList<CategoryItem> categoryItems;
    ProgressDialog progressDialog;
    JSONObject productObj;

    public String filter;

    String searchPrefix;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_for_users);

        product_for_all_users_search = findViewById(R.id.product_for_all_users_search);

        //progress
        progressDialog=new Stables().showLoading(this);

        product_for_all_users_back_button = (MaterialCardView) findViewById(R.id.product_for_all_users_back_button);
        product_for_all_users_cart_button = (MaterialCardView) findViewById(R.id.product_for_all_users_cart_button);
        product_for_all_users_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToBack(null);
            }
        });
        product_for_all_users_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToCart(null);
            }
        });

        product_for_all_users_empty_product=findViewById(R.id.product_for_all_users_empty_product);

        searchPrefix="";

        filter="0";
        productItem=new ArrayList<>();
        categoryItems=new ArrayList<>();

        recyclerViewProduct=findViewById(R.id.product_for_all_users_all_products);
        recyclerViewCategory=findViewById(R.id.product_for_all_users_all_category);

        loadItems();
        loadCategory();

        addSearchListener();

    }

    private void addSearchListener() {
        product_for_all_users_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchPrefix=product_for_all_users_search.getText().toString();
                loadItemsToList(filter);

//                if(product_for_all_users_search.getText().toString().length()>2){
//                    searchPrefix=product_for_all_users_search.getText().toString();
//                    loadItemsToList(filter);
//                }else{
//                    if(product_for_all_users_search.getText().toString().length()==0){
//                        searchPrefix="";
//                        loadItemsToList(filter);
//                    }
//                }


            }
        });
    }

    private void goToCart(Object o) {
        Intent intent = new Intent(ProductsForUsers.this, CartActivity.class);
        startActivity(intent);
    }


    public void loadItems() {
        productItemAdapter=new ProductItemAdapter(this,productItem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProduct.setAdapter(productItemAdapter);
        loadItemsToList(filter);


    }

    private void loadCategory() {
        categoryItemAdapter=new CategoryItemAdapter(categoryItems, ProductsForUsers.this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(mLayoutManager);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategory.setAdapter(categoryItemAdapter);
        loadCategoryToList();
    }


    private void loadItemsToList(final String filterPrefix) {



        if(searchPrefix.trim().equals("")){
            progressDialog.show();
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getProductList(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                productItem.clear();

                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        ProductItem pi = new ProductItem();


                        if(jsonObject.getString("name").trim().contains(searchPrefix.trim())){
                            if(filterPrefix=="0"){
                                pi.setTitle(jsonObject.getString("name"));
                                pi.setDescription(jsonObject.getString("description"));
                                pi.setPrice("LKR "+jsonObject.getString("product_price")+".00");
                                pi.setImage(jsonObject.getString("product_image"));
                                pi.setCategory(jsonObject.getString("category_id"));
                                pi.setId(jsonObject.getString("id"));
                                //pi.setCategory();

                                productItem.add(pi);
                            }else if(jsonObject.getString("category_id").equals(filterPrefix)){
                                pi.setTitle(jsonObject.getString("name"));
                                pi.setDescription(jsonObject.getString("description"));
                                pi.setPrice("LKR "+jsonObject.getString("product_price")+".00");
                                pi.setImage(jsonObject.getString("product_image"));
                                pi.setCategory(jsonObject.getString("category_id"));
                                pi.setId(jsonObject.getString("id"));

                                //pi.setCategory();

                                productItem.add(pi);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }

                //recyclerViewProduct.setAdapter(productItemAdapter);
                productItemAdapter.notifyDataSetChanged();

                if(productItem.size()!=0){
                    product_for_all_users_empty_product.setVisibility(View.GONE);
                }else{
                    product_for_all_users_empty_product.setVisibility(View.VISIBLE);
                }

                if(searchPrefix.trim().equals("")){
                    progressDialog.dismiss();
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
    }



    private void loadCategoryToList() {
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getCategoryList(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                CategoryItem all = new CategoryItem("0","All");
                categoryItems.add(all);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        CategoryItem ci = new CategoryItem(jsonObject.getString("id"),jsonObject.getString("name"));

                        categoryItems.add(ci);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                categoryItemAdapter.notifyDataSetChanged();
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

        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        if (Integer.parseInt(sharedPreferences.getString("userType","0"))==1){
            onBackPressed();
            Intent intent = new Intent(ProductsForUsers.this, MainDashboardActivity.class);
            startActivity(intent);
        }else {
            onBackPressed();
            Intent intent = new Intent(ProductsForUsers.this, CustomerDashboardActivity.class);
            startActivity(intent);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
