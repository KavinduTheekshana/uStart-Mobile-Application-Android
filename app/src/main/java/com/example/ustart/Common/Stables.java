package com.example.ustart.Common;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.example.ustart.ProfileActivity;

import org.json.JSONObject;

import java.net.URLEncoder;

public class Stables {
    public static String baseUrl="http://192.168.8.102" +
            ":8000/";
    private String loginController=baseUrl+"api/mobilelogin";
    private String currentPassword=baseUrl+"api/currentPassword";
    private String UpdatePasswordController=baseUrl+"api/updatePasswordMobile";

    public String getLoginController(String username,String password){
        return loginController+"?username="+username+"&password="+password;
    }

    public String UpdatePasswordController(String uid,String password,String confirm_password){
        return UpdatePasswordController+"?uid="+uid+"&password="+password+"&confirm_password="+confirm_password;
    }

    public String getCurrentPassword(String uid,String password){
        return currentPassword+"?uid="+uid+"&password="+password;
    }

    public String getCheckLoginController(String id){
        return baseUrl+"api/checkLogin"+"?id="+id;
    }

    public String getProfileDetails(String id){
        return baseUrl+"api/getProfileDetails"+"?id="+id;
    }

    public String intime(String id,String date,String time){
        return baseUrl+"api/intime?uid="+id+"&date="+date+"&intime="+time;
    }
    public String outTime(String id,String date,String time){
        return baseUrl+"api/outtime?uid="+id+"&date="+date+"&outtime="+time;
    }

    public String status(String id,String date){
        return baseUrl+"api/status?uid="+id+"&date="+date;
    }

    public String getLatAndLng(String id){
        return baseUrl+"api/getlatandlng"+"?id="+id;
    }

    public String CodeVerification(String email,String code){
        String url="";
        try {
            url=baseUrl+"api/checkverificationcode?"+"email="+ URLEncoder.encode(email,"utf-8")+"&code="+URLEncoder.encode(code,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String ResetPassword(String email,String password) {
        String url = "";
        try {
            url = baseUrl + "api/resetpasswordmobile?" + "email=" + URLEncoder.encode(email, "utf-8")
                    + "&password=" + URLEncoder.encode(password, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

        public String getCategoryName(String id){
        return baseUrl+"api/getCategoryName"+"?id="+id;
    }

    public String getProductList(){
        return baseUrl+"api/getProductList";
    }

    public String getCategoryList(){
        return baseUrl+"api/getCategoryList";
    }

    public String RepGetCustomers(String userid){
        return baseUrl+"api/RepGetCustomers"+"?repid="+userid;
    }

    public String EmailVerification(String email){
        String url="";
        try {
            url=baseUrl+"api/verificationcode?"+"email="+ URLEncoder.encode(email,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }


    public String getCartItemList(String userid){
        return baseUrl+"api/getCartItemList"+"?uid="+userid;
    }

    public String SingleCustomerOrderdItems(String customerid){
        return baseUrl+"api/SingleCustomerOrderdItems"+"?customerid="+customerid;
    }



    public String DeleteCartItem(int cartId){
        return baseUrl+"api/DeleteCartItem"+"?cartid="+cartId;
    }

    public String MarkOrderItemIsComplete(int productid){
        return baseUrl+"api/MarkOrderItemIsComplete"+"?productid="+productid;
    }


    public String OrderNowMobile(String uid){
        return baseUrl+"api/OrderNowMobile"+"?uid="+uid;
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

    public String ProfileUpdatePost(){
        String url="";
        try {
            url=baseUrl+"api/profileUpdate";
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String CreateCart(String cartUserId, String cartUserType, String cartProductId, String cartQty){
        String url="";
        try {
            url=baseUrl+"api/CreateCart?"+"userid="+cartUserId+"&usertype="+URLEncoder.encode(cartUserType,"utf-8")+"&productid="+URLEncoder.encode(cartProductId,"utf-8")+"&qty="+URLEncoder.encode(cartQty,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String CreateRoute(String userID, String dateString, String timeString, String lat, String lng){
        String url="";
        try {
            url=baseUrl+"api/savecurrentlocation?"+"userid="+userID+"&dateString="+URLEncoder.encode(dateString,"utf-8")+"&timeString="+URLEncoder.encode(timeString,"utf-8")+"&lat="+URLEncoder.encode(lat,"utf-8")+"&lng="+URLEncoder.encode(lng,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String SaveLatLng(String userID, String lat, String lng){
        String url="";
        try {
            url=baseUrl+"api/savelatlng?"+"userid="+userID+"&lat="+URLEncoder.encode(lat,"utf-8")+"&lng="+URLEncoder.encode(lng,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }


    public String CurrentLocation(String userID){
        String url="";
        try {
            url=baseUrl+"api/currentlocation?"+"uid="+userID;
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
