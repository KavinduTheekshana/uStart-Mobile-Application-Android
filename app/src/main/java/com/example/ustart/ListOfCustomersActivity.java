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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.example.ustart.adapters.CategoryItemAdapter;
import com.example.ustart.adapters.CustomerAdapter;
import com.example.ustart.models.CategoryItem;
import com.example.ustart.models.Customers;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfCustomersActivity extends AppCompatActivity {
    private MaterialCardView list_of_customers_back_button;
    RecyclerView list_of_customers_recycle_view;
    CustomerAdapter customerAdapter;
    ArrayList<Customers> customers;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_customers);

        //progress
        progressDialog=new Stables().showLoading(this);

        list_of_customers_back_button= findViewById(R.id.list_of_customers_back_button);
        list_of_customers_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack();
            }
        });

        customers=new ArrayList<>();
        list_of_customers_recycle_view=findViewById(R.id.list_of_customers_recycle_view);
        loadCustomers();
    }

    private void loadCustomers() {
        customerAdapter=new CustomerAdapter(this,this,customers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list_of_customers_recycle_view.setLayoutManager(mLayoutManager);
        list_of_customers_recycle_view.setItemAnimator(new DefaultItemAnimator());
        list_of_customers_recycle_view.setAdapter(customerAdapter);
        loadCustomersToList();
    }

    private void loadCustomersToList() {
        progressDialog.show();

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().RepGetCustomers(sharedPreferences.getString("userid","0")), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Customers cus = new Customers();
                        cus.setId(jsonObject.getString("id"));
                        cus.setName(jsonObject.getString("name"));
                        cus.setAddress(jsonObject.getString("address"));
                        cus.setShopname(jsonObject.getString("shopname"));
                        cus.setEmail(jsonObject.getString("email"));
                        cus.setTelephone(jsonObject.getString("telephone"));
                        cus.setProvince(jsonObject.getString("province"));
                        cus.setDistrict(jsonObject.getString("district"));
                        cus.setCity(jsonObject.getString("city"));
                        cus.setJoined_date(jsonObject.getString("joined_date"));
                        cus.setProfile_pic(jsonObject.getString("profile_pic"));
                        cus.setGooogleaddress(jsonObject.getString("gooogleaddress"));
                        cus.setLat(jsonObject.getString("lat"));
                        cus.setLng(jsonObject.getString("lng"));
                        customers.add(cus);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                customerAdapter.notifyDataSetChanged();
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

    private void goToBack() {
        onBackPressed();
    }
}
