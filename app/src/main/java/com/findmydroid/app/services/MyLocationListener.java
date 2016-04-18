package com.findmydroid.app.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Ankur Kashyap on 03/12/2015.
 */
public class MyLocationListener implements LocationListener {
    Context context;
    View view;
    ListView listView;
    String placeType;
    GettingPlaceDetails gettingPlaceDetails;

    public MyLocationListener(Context context, View view, ListView listView, String placeType) {

        this.context=context;
        this.view=view;
        this.listView=listView;
        this.placeType=placeType;
    }


    @Override
    public void onLocationChanged(Location location) {
        gettingPlaceDetails=new GettingPlaceDetails(context, view, listView, location, placeType);
        gettingPlaceDetails.execute();
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

}
