package com.findmydroid.app.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydroid.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Created by Ankur Kashyap on 08/12/2015.
 */
public class DisplayPlaceDetailsWithMap extends AppCompatActivity {

    GoogleMap googleMap;
    MapFragment mapFragment;
    Intent intent;
    TextView name, address, latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_place_details_with_map);
        setSupportActionBar((Toolbar) findViewById(R.id.mapToolbar));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findView();
        initializeMap();
        setMapHeight();
        displayDetails();
        displayPlaceOnMap();
    }

    private void findView() {
        name=(TextView)findViewById(R.id.place_name);
        address=(TextView)findViewById(R.id.place_address);
        latitude=(TextView)findViewById(R.id.place_latitude);
        longitude=(TextView)findViewById(R.id.place_longitude);
    }

    private void displayDetails() {
        intent=getIntent();
        int position= intent.getIntExtra("POSITION", 0);
        String placeType = intent.getStringExtra("PLACE_TYPE");
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("ALL_PLACES_DETAILS_SHARED_PREFERENCES"+placeType, Context.MODE_PRIVATE);
        String placeName=sharedPreferences.getString(placeType + "_" + position + "_name", "No Element found");
        String placeAddress=sharedPreferences.getString(placeType + "_" + position + "_vicinity", "No Element found");
        String placeLatitude=sharedPreferences.getString(placeType + "_" + position + "_latitude", "No Element found");
        String placeLongitude=sharedPreferences.getString(placeType + "_" + position + "_longitude", "No Element found");

        name.setText(placeName);
        address.setText(placeAddress);
        latitude.setText(placeLatitude);
        longitude.setText(placeLongitude);

        name.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        latitude.setVisibility(View.VISIBLE);
        longitude.setVisibility(View.VISIBLE);
    }

    private void initializeMap() {
        mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

        googleMap=mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        UiSettings mapUiSettings=googleMap.getUiSettings();
        mapUiSettings.setMyLocationButtonEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setZoomGesturesEnabled(true);
        mapUiSettings.setCompassEnabled(true);

    }

    private void displayPlaceOnMap() {
        String placeType=intent.getStringExtra("PLACE_TYPE");
        int position=intent.getIntExtra("POSITION", 0);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("ALL_PLACES_DETAILS_SHARED_PREFERENCES" + placeType, Context.MODE_PRIVATE);
        double latitude=0, longitude=0;

        MarkerOptions markerOptions=new MarkerOptions();
        latitude=Double.valueOf(sharedPreferences.getString("myLatitude", "0"));
        longitude=Double.valueOf(sharedPreferences.getString("myLongitude", "0"));
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title("Here you are");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        googleMap.addMarker(markerOptions);

        latitude=Double.valueOf(sharedPreferences.getString(placeType + "_" + position + "_latitude", "0"));
        longitude=Double.valueOf(sharedPreferences.getString(placeType + "_" + position + "_longitude", "0"));
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title(sharedPreferences.getString(placeType + "_" + position + "_name", ""));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        googleMap.addMarker(markerOptions);

        CameraPosition cameraPosition=new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setMapHeight() {
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height=metrics.heightPixels;
        ViewGroup.LayoutParams layoutParams=mapFragment.getView().getLayoutParams();
        layoutParams.height=height*2/3;
        mapFragment.getView().setLayoutParams(layoutParams);
    }
}
