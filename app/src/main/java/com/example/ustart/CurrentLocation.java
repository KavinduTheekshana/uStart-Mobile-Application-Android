package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class CurrentLocation extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    Button getCurrentLocationButton;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    LatLng locationCoordinates;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        locationCoordinates = new LatLng(6.9271,79.8612);


        getCurrentLocationButton = findViewById(R.id.getCurrentLocationButton);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.currentLocationMap);
        mapFragment.getMapAsync(this);



        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CurrentLocation.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(CurrentLocation.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CurrentLocation.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(CurrentLocation.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    } else {
                        ActivityCompat.requestPermissions(CurrentLocation.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                99);
                    }
                } else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CurrentLocation.this);
                }

            }
        });
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

        System.out.println(location.getLatitude()+":"+location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(locationCoordinates).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoordinates));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

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
        mMap.addMarker(new MarkerOptions().position(locationCoordinates).title("Sri Lanka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoordinates));
    }
}
