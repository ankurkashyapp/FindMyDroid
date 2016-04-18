package com.findmydroid.app.utilities;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by Ankur Kashyap on 13/12/2015.
 */
public class ConnectionDetector {

    Context context;
    public ConnectionDetector(Context context) {
        this.context=context;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isConnectedToGps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(connectivityManager!=null) {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            for(int i=0; i<info.length; i++) {
                if(info[i].getState()==NetworkInfo.State.CONNECTED)
                    return true&&isConnectedToGps;
            }
        }
        return false;
    }
}
