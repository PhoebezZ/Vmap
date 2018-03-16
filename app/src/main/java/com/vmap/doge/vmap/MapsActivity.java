package com.vmap.doge.vmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

//CMPS121 project, UCSC vending machine map (Vmap)
//Authors:
//Jiaming Zhao, 1416678, jzhao26@ucsc.edu
//Li Jiang, 1411197, ljiang2@ucsc.edu
//Yilin Xu, 1441665, yxu48@ucsc.edu


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button listBut;
    private Button helpBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listBut = (Button) findViewById(R.id.listBut);//button go to vending machine list
        helpBut = (Button) findViewById(R.id.Help_button_ID);

        listBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoList = new Intent(MapsActivity.this, ListActivity.class);
                startActivity(gotoList);
                finish();
            }
        });

        helpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohelper = new Intent(MapsActivity.this, HelpActivity.class);
                startActivity(gotohelper);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set map initial position to current locaiton
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"Please turn on Location Permission for Vmap", Toast.LENGTH_LONG).show();
            return;
        }
        //solved delay issue. However, user still have to use googlemap once if they just restart
        //the android phone.
        List<String> locationtmp = locationManager.getProviders(true);
        Location init_location = null;
        for(String tmpuse : locationtmp){
            Location location_tmp = locationManager.getLastKnownLocation(tmpuse);
            if(location_tmp == null){
                continue;
            }
            if(init_location == null || location_tmp.getAccuracy() < init_location.getAccuracy()){
                init_location = location_tmp;
            }
        }
        if(init_location != null){
            LatLng init_loc = new LatLng(init_location.getLatitude(),init_location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init_loc,16.0f));
            mMap.addMarker(new MarkerOptions().position(init_loc));
        }else{
            LatLng init_loc = new LatLng(36.99734, -122.05815);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init_loc,16.0f));
        }


        // List of vending machines
        String[][] markers = {
                {"Opers", "36.99463085", "-122.0544858"},
                {"S&E Library Bottom Floor", "36.99904607927167", "-122.0607041940093"},
                {"Social Science 1", "37.00295247", "-122.05825199"},
                {"social science 2", "37.00151003", "-122.05873196"},
                {"Steven Recreation", "36.99713734", "-122.05238711"},
                {"Cowell Turner Hall Laundry","36.996665", "-122.053914"},
                {"Engineering 1 first floor","37.00056510504045", "-122.06302866339685"},
                {"Mchenry Library Media Center","36.99569219", "-122.05933617"},
                {"Natural Science 2 2nd Floor"," 36.99869262630071","-122.06087216734886"},
                {"Thimann Lab 1st Floor","36.99825552","-122.06169363"},
                {"Office of registrar","36.996136","-122.057143"}
                };

        // Add markers on google map api
        for (int i = 0; i < markers.length; i++) {
            Marker myMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(markers[i][1]), Double.parseDouble(markers[i][2])))
                .title(markers[i][0]));
            myMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }


    }
}
