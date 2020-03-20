package com.example.ustart.Common;

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
}
