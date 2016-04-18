package com.findmydroid.app.services;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ankur Kashyap on 27/11/2015.
 */
public class PlaceContent {
    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double latitude;
    private Double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    static PlaceContent getPlaceDetails(JSONObject jsonObject) {
        try {
            PlaceContent placeDetails=new PlaceContent();
            JSONObject geometry=(JSONObject)jsonObject.get("geometry");
            JSONObject location=(JSONObject)geometry.get("location");

            placeDetails.setLatitude(location.getDouble("lat"));
            placeDetails.setLongitude(location.getDouble("lng"));
            placeDetails.setIcon(jsonObject.getString("icon"));
            placeDetails.setId(jsonObject.getString("id"));
            placeDetails.setName(jsonObject.getString("name"));
            placeDetails.setVicinity(jsonObject.getString("vicinity"));
            return placeDetails;
        }catch(JSONException ex) {
            Log.e("PlaceContent Exception",ex.toString());
        }
        return null;
    }
}
