package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
//import com.jgabrielfreitas.core.BlurImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView BackButton,edit_profile_profile_image,blurImageView;
    private TextInputEditText edit_profile_user_name,edit_profile_telephone,edit_profile_address,
            edit_profile_provience,edit_profile_joined_date,edit_profile_district,edit_profile_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        BackButton = (ImageView) findViewById(R.id.edit_profile_btnback);
        edit_profile_profile_image = (ImageView) findViewById(R.id.edit_profile_profile_image);
        blurImageView = (ImageView) findViewById(R.id.BlurImageView);

        edit_profile_user_name = findViewById(R.id.edit_profile_user_name);
        edit_profile_telephone = findViewById(R.id.edit_profile_telephone);
        edit_profile_address = findViewById(R.id.edit_profile_address);
        edit_profile_provience = findViewById(R.id.edit_profile_provience);
        edit_profile_joined_date = findViewById(R.id.edit_profile_joined_date);
        edit_profile_district = findViewById(R.id.edit_profile_district);
        edit_profile_city = findViewById(R.id.edit_profile_city);


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });


        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(EditProfileActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject userObj=jsonObject.getJSONObject("user");

                    edit_profile_user_name.setText(userObj.getString("name"));
                    edit_profile_telephone.setText(userObj.getString("telephone"));
                    edit_profile_address.setText(userObj.getString("address"));
                    edit_profile_provience.setText(userObj.getString("province"));
                    edit_profile_joined_date.setText(userObj.getString("joined_date"));
                    edit_profile_district.setText(userObj.getString("district"));
                    edit_profile_city.setText(userObj.getString("city"));

                    Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(edit_profile_profile_image);
                    Picasso.get()
                            .load(Stables.baseUrl+ userObj.getString("profile_pic"))
                            .transform(new BlurTransformation(EditProfileActivity.this, 50, 1))
                            .transform(new BrightnessFilterTransformation(EditProfileActivity.this,-0.2f))
                            .into(blurImageView);


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Intent homeIntent = new Intent(EditProfileActivity.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        requestQueue.add(stringRequest);




    }


    public void goToBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}

