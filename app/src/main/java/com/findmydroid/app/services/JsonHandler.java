package com.findmydroid.app.services;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ankur Kashyap on 27/11/2015.
 */
public class JsonHandler {
    private String API_KEY="AIzaSyCAYKKquuyq7lnNjPkgPas4KHPfNFLshOo";
    private Context context;

    JsonHandler(Context context) {
        this.context=context;
    }
    public String makeUrl(double latitude, double longitude, String placeType) {
        StringBuilder url=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        url.append("&location="+Double.toString(latitude)+","+Double.toString(longitude));
        url.append("&radius="+3000);
        url.append("&types=" + placeType);
        url.append("&key=" + API_KEY);
        Log.e("Place Api URL", url.toString());
        return url.toString();
    }

    public String makeUrl(String nextPageToken) {
        StringBuilder url=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        url.append("pagetoken="+nextPageToken);
        url.append("&key=" + API_KEY);
        return url.toString();
    }

    public String getJsonContent(String apiUrl) {
        StringBuilder jsonContent=new StringBuilder();
        HttpURLConnection httpUrlConnection=null;
        BufferedReader bufferedReader=null;
        try {
            URL url = new URL(apiUrl);
            httpUrlConnection = (HttpURLConnection)url.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line + "\n");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                httpUrlConnection.disconnect();
                bufferedReader.close();
            }catch (Exception ex) {
            }

        }

        return jsonContent.toString();
    }
}
