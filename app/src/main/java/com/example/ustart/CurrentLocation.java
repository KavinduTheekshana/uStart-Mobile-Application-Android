package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Common.Stables;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class CurrentLocation extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    Button getCurrentLocationButton,btnsavelocation;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    TextView latlngtxt;

    LatLng locationCoordinates;

    private GoogleMap mMap;

    String initialLocationString;
    private double lat,lng;
    private int status = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        getCurrentLocationButton = findViewById(R.id.getCurrentLocationButton);
        btnsavelocation = findViewById(R.id.btnsavelocation);
        latlngtxt = findViewById(R.id.latlngtxt);



        enablebtn();

        btnsavelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate();
            }
        });


        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(CurrentLocation.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().CurrentLocation(sharedPreferences.getString("userid","0")), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray locationArray=new JSONArray(response);
                    JSONObject locationObj=(JSONObject)locationArray.get(0);

                    initialLocationString=locationObj.getString("address");
                    latlngtxt.setText("Lat :"+locationObj.getString("lat") + " | "+"Lng :"+locationObj.getString("lng"));
                    locationCoordinates = new LatLng(Double.parseDouble(locationObj.getString("lat")),Double.parseDouble(locationObj.getString("lng")));

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(locationCoordinates).title(initialLocationString));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoordinates));
                    mMap.animateCamera( CameraUpdateFactory.zoomTo( 13.0f ) );

                }catch(Exception e){
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Intent homeIntent = new Intent(CurrentLocation.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        requestQueue.add(stringRequest);










        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.currentLocationMap);
        mapFragment.getMapAsync(this);



        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(CurrentLocation.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(CurrentLocation.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(CurrentLocation.this,
//                            Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(CurrentLocation.this,
//                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    } else {
//                        ActivityCompat.requestPermissions(CurrentLocation.this,
//                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                                99);
//                    }
//                } else {
//                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CurrentLocation.this);
//                }

                setListenersForLocationService();

            }
        });
    }

    private void UpdateDate() {

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        RequestQueue requestQueue= Volley.newRequestQueue(CurrentLocation.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().SaveLatLng(sharedPreferences.getString("userid","0"),String.valueOf(lat),String.valueOf(lng)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    if(response.equals("1")){
                        Toast.makeText(CurrentLocation.this, "Done", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CurrentLocation.this, "Unsucessfull", Toast.LENGTH_SHORT).show();
                    }



                }catch(Exception e){
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Intent homeIntent = new Intent(CurrentLocation.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        requestQueue.add(stringRequest);




    }

    private void enablebtn() {
        if (status == 0){
            btnsavelocation.setEnabled(false);
        }else {
            btnsavelocation.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println(requestCode);
        switch (requestCode) {
            case 99: {
                System.out.println(grantResults.length);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission Allowed");
                    setListenersForLocationService();
                } else {
                    System.out.println("Permission Denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    void setListenersForLocationService() {
        //Loading
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CurrentLocation.this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationCoordinates = new LatLng(location.getLatitude(),location.getLongitude());

        mMap.clear();
        lat = location.getLatitude();
        lng = location.getLongitude();
        status = 1;
        enablebtn();
        latlngtxt.setText("Lat :"+lat + " | "+"Lng :"+lng);

        System.out.println(location.getLatitude()+":"+location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(locationCoordinates).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoordinates));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );

        locationManager.removeUpdates(CurrentLocation.this);
        //hide loading
        locationManager=null;


    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
