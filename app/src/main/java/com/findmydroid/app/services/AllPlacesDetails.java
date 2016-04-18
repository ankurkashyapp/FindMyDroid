package com.findmydroid.app.services;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ankur Kashyap on 27/11/2015.
 */
public class AllPlacesDetails {
    private Context context;
    private double latitude, longitude;
    private String placeType;
    private int urlIteration=0;

    public AllPlacesDetails(Context context, double latitude, double longitude, String placeType) {
        this.context=context;
        this.latitude=latitude;
        this.longitude=longitude;
        this.placeType=placeType;
    }

    public ArrayList<PlaceContent> getAllPlacesDetails() {
        JsonHandler jsonHandler=new JsonHandler(context);
        String url=jsonHandler.makeUrl(latitude, longitude, placeType);

        final ArrayList<PlaceContent> allPlacesDetails=new ArrayList<PlaceContent>();
        try {

            String jsonContent=jsonHandler.getJsonContent(url);
            JSONObject jsonObject=new JSONObject(jsonContent);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                allPlacesDetails.add(PlaceContent.getPlaceDetails(results.getJSONObject(i)));
            }

            return allPlacesDetails;
        }catch(Exception ex) {
            Log.e("APD", "inside catch"+ex);
        }
        return  null;
    }
}
