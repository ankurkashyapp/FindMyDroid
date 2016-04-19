package com.findmydroid.app.fragments.mostvisited;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.findmydroid.app.R;
import com.findmydroid.app.activities.DisplayOnMap;
import com.findmydroid.app.activities.DisplayPlaceDetailsWithMap;
import com.findmydroid.app.services.GettingPlaceDetails;
import com.findmydroid.app.services.MyLocationListener;
import com.findmydroid.app.utilities.ConnectionDetector;

/**
 * Created by Ankur Kashyap on 04/12/2015.
 */
public class Gym extends Fragment implements AdapterView.OnItemClickListener {
    ListView atmList;
    LocationManager locationManager;
    MyLocationListener myLocationListener;
    ConnectionDetector connectionDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_atm, container, false);

        atmList=(ListView)view.findViewById(R.id.list_view_atm);

        connectionDetector=new ConnectionDetector(getActivity());
        if(!connectionDetector.isConnectedToInternet()) {
            RelativeLayout relativeLayout=(RelativeLayout)view.findViewById(R.id.network_error);
            relativeLayout.setVisibility(View.VISIBLE);
        }


        locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        String provider = locationManager.getBestProvider(criteria, true);
        myLocationListener=new MyLocationListener(getActivity(), view, atmList, "gym");
        try {
            if(connectionDetector.isConnectedToInternet()) {
                Location gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location networkLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (gpsLastLocation == null && networkLastLocation == null)
                    locationManager.requestLocationUpdates(provider, 10000, 50, myLocationListener);
                else {
                    if (gpsLastLocation != null)
                        new GettingPlaceDetails(getActivity(), view, atmList, gpsLastLocation, "gym").execute();
                    else
                        new GettingPlaceDetails(getActivity(), view, atmList, networkLastLocation, "gym").execute();
                    locationManager.requestLocationUpdates(provider, 10000, 50, myLocationListener);
                }
            }
        }catch(SecurityException ex) {
        }
        atmList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            Intent intent=new Intent(getContext(), DisplayOnMap.class);
            intent.putExtra("PLACE_TYPE", "gym");
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(getContext(), DisplayPlaceDetailsWithMap.class);
            intent.putExtra("POSITION", position-1);
            intent.putExtra("PLACE_TYPE", "gym");
            startActivity(intent);
        }
    }
}
