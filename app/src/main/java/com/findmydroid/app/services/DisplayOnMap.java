package com.findmydroid.app.services;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import com.findmydroid.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Created by Ankur Kashyap on 06/12/2015.
 */
public class DisplayOnMap extends AppCompatActivity {

    GoogleMap googleMap;
    Intent intent;
    String placeType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.display_on_map);
        //setSupportActionBar((Toolbar)findViewById(R.id.mapToolbar));

        initializeMap();
        intent=getIntent();
        displayPlacesOnMap();
    }

    private void initializeMap() {
        googleMap=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMyLocationEnabled(true);
        UiSettings mapUiSettings=googleMap.getUiSettings();
        mapUiSettings.setMyLocationButtonEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setZoomGesturesEnabled(true);
        mapUiSettings.setCompassEnabled(true);
    }

    private void displayPlacesOnMap() {
        placeType=intent.getStringExtra("PLACE_TYPE");
        SharedPreferences sharedPreferences=this.getSharedPreferences("ALL_PLACES_DETAILS_SHARED_PREFERENCES" + placeType, Context.MODE_PRIVATE);
        double latitude=0, longitude=0;

        Map<String,?> allvalues=sharedPreferences.getAll();
        Log.e("DisplayOnMap", "size"+allvalues.size());
        MarkerOptions markerOptions=new MarkerOptions();
        for(int i=0; i<allvalues.size()-2; i++) {
            latitude=Double.valueOf(sharedPreferences.getString(placeType + "_" + i + "_latitude", "0"));
            longitude=Double.valueOf(sharedPreferences.getString(placeType + "_" + i + "_longitude", "0"));
            markerOptions.position(new LatLng(latitude, longitude));
            markerOptions.title(sharedPreferences.getString(placeType + "_" + i + "_name", ""));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            googleMap.addMarker(markerOptions);
        }

        latitude=Double.valueOf(sharedPreferences.getString("myLatitude", "0"));
        longitude=Double.valueOf(sharedPreferences.getString("myLongitude", "0"));
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title("Here you are");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        googleMap.addMarker(markerOptions);

        CameraPosition cameraPosition=new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
