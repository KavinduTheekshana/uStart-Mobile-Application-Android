package com.example.ustart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.PreviousActivities;
import com.example.ustart.Common.Stables;
//import com.jgabrielfreitas.core.BlurImageView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView edit_profile_profile_image,blurImageView;
    private TextInputEditText edit_profile_user_name,edit_profile_telephone,edit_profile_address,
            edit_profile_province,edit_profile_joined_date,edit_profile_district,edit_profile_city,
            edit_profile_shop_name;
    private Button btn_edit_profile_update;
    private MaterialCardView profile_details_change_pro_pic;
    SharedPreferences sharedPreferences;
    JSONObject userObj;
    ProgressDialog progressDialog;
    MaterialCardView edit_profile_back_button,edit_profile_password;

    //imagepart
    private static final int RESULT_CODE_REQUEST =101;
    private Uri imageuri;
    private Boolean isImageAdded=false;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog=new Stables().showLoading(this);

        edit_profile_back_button = findViewById(R.id.edit_profile_back_button);
        edit_profile_password =  findViewById(R.id.edit_profile_password);
        blurImageView = findViewById(R.id.BlurImageView);

        btn_edit_profile_update = findViewById(R.id.btn_edit_profile_update);

        edit_profile_user_name = findViewById(R.id.edit_profile_user_name);
        edit_profile_telephone = findViewById(R.id.edit_profile_telephone);
        edit_profile_address = findViewById(R.id.edit_profile_address);
        edit_profile_province = findViewById(R.id.edit_profile_province);
        edit_profile_joined_date = findViewById(R.id.edit_profile_joined_date);
        edit_profile_district = findViewById(R.id.edit_profile_district);
        edit_profile_city = findViewById(R.id.edit_profile_city);
        edit_profile_shop_name = findViewById(R.id.edit_profile_shop_name);
        profile_details_change_pro_pic = findViewById(R.id.profile_details_change_pro_pic);
        edit_profile_profile_image = (ImageView) findViewById(R.id.edit_profile_profile_image);

        edit_profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });
        btn_edit_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        edit_profile_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


        getProfileDetails();


        profile_details_change_pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });


    }

    private void updatePassword() {
        Intent homeIntent = new Intent(EditProfileActivity.this,CurrentPasswordActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void getProfileDetails(){
        progressDialog.show();
        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(EditProfileActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getProfileDetails(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    userObj=jsonObject.getJSONObject("user");

                    edit_profile_user_name.setText(userObj.getString("name"));
                    edit_profile_telephone.setText(userObj.getString("telephone"));
                    edit_profile_address.setText(userObj.getString("address"));
                    edit_profile_province.setText(userObj.getString("province"));
                    edit_profile_joined_date.setText(userObj.getString("joined_date"));
                    edit_profile_district.setText(userObj.getString("district"));
                    edit_profile_city.setText(userObj.getString("city"));

                    if (Integer.parseInt(userObj.getString("user_type"))==1){
                        edit_profile_shop_name.setVisibility(View.GONE);
                    }else if(Integer.parseInt(userObj.getString("user_type"))==2){
                        edit_profile_shop_name.setText(userObj.getString("shop_name"));
                    }

                    Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(edit_profile_profile_image);
                    Picasso.get()
                            .load(Stables.baseUrl+ userObj.getString("profile_pic"))
                            .transform(new BlurTransformation(EditProfileActivity.this, 50, 1))
                            .transform(new BrightnessFilterTransformation(EditProfileActivity.this,-0.2f))
                            .into(blurImageView);

                    progressDialog.hide();

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

    private void updateProfile(){

        try {

        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();

        jsonBody.put("uid", sharedPreferences.getString("userid","0"));
        jsonBody.put("name", edit_profile_user_name.getText().toString().trim());
        jsonBody.put("tel", edit_profile_telephone.getText().toString().trim());
        jsonBody.put("address", edit_profile_address.getText().toString().trim());


        if(bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            System.out.println(encodedImage);
            jsonBody.put("image", encodedImage);
        }


        JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().ProfileUpdatePost(), jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    getProfileDetails();
                    Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();


                }catch(Exception ex){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonOblectReq);
    }catch(Exception e){}















//        try {
//
//            String url=new Stables().ProfileUpdate(sharedPreferences.getString("userid","0"), edit_profile_user_name.getText().toString().trim(), edit_profile_telephone.getText().toString().trim(), edit_profile_address.getText().toString().trim());
//            System.out.println(url);
//            RequestQueue requestQueue= Volley.newRequestQueue(EditProfileActivity.this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            getProfileDetails();
//                            Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            requestQueue.add(stringRequest);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }


    public void goToBack(View view){
        if(PreviousActivities.profileActivity!=null){
            PreviousActivities.profileActivity.getProfileDetails();
        }
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_CODE_REQUEST && data!=null)
        {
            imageuri=data.getData();
            isImageAdded=true;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageuri);
                Picasso.get()
                        .load(imageuri)
                        .transform(new BlurTransformation(EditProfileActivity.this, 50, 1))
                        .transform(new BrightnessFilterTransformation(EditProfileActivity.this,-0.2f))
                        .into(blurImageView);
                edit_profile_profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

