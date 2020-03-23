package com.example.ustart.Common;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.ustart.ProfileActivity;

import org.json.JSONObject;

import java.net.URLEncoder;

public class Stables {
    public static String baseUrl="http://192.168.8.101:8000/";
    private String loginController=baseUrl+"api/mobilelogin";

    public String getLoginController(String username,String password){
        return loginController+"?username="+username+"&password="+password;
    }

    public String getCheckLoginController(String id){
        return baseUrl+"api/checkLogin"+"?id="+id;
    }

    public String getProfileDetails(String id){
        return baseUrl+"api/getProfileDetails"+"?id="+id;
    }

    public String ProfileUpdate(String uid, String name, String tel, String address){
        String url="";
        try {
            url=baseUrl+"api/profileUpdate?"+"uid="+uid+"&name="+URLEncoder.encode(name,"utf-8")+"&tel="+URLEncoder.encode(tel,"utf-8")+"&address="+URLEncoder.encode(address,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }

        return url;
    }

    public ProgressDialog showLoading(Context context){
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait until the process complete");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
