package com.findmydroid.app.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.findmydroid.app.R;

import java.util.ArrayList;

/**
 * Created by Ankur Kashyap on 03/12/2015.
 */
public class GettingPlaceDetails extends AsyncTask<Void, Void, ArrayList<PlaceContent>> {

    Context context;
    ListView listView;
    View view;
    Location location;
    String placeType;

    AllPlacesDetails allPlacesDetails;
    ProgressDialog progressDialog;

    public ArrayList<PlaceContent> allPlacesDetailsArray;

    public GettingPlaceDetails(Context context, View view, ListView listView, Location location,String placeType) {
        super();
        this.context=context;
        this.listView=listView;
        this.view=view;
        this.location=location;
        this.placeType=placeType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Getting Place details. Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<PlaceContent> allPlacesDetailsArray) {
        super.onPostExecute(allPlacesDetailsArray);
        progressDialog.dismiss();

        saveDataIntoSharedPreferences();
        SharedPreferences sharedPreferences=context.getSharedPreferences("ALL_PLACES_DETAILS_SHARED_PREFERENCES"+placeType, Context.MODE_PRIVATE);
        Log.e("GettingPlaceDetails", "Name: " + sharedPreferences.getString(placeType + "_0_name", "No element found"));

        ListViewAdapter listViewAdapter=new ListViewAdapter(context, location, allPlacesDetailsArray);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.list_header, null);
        if(listView.getHeaderViewsCount()==0)
            listView.addHeaderView(listHeader);

        listView.setAdapter(listViewAdapter);

        TextView noPlacesToShow=(TextView)view.findViewById(R.id.no_places_to_show);
        if(listViewAdapter.getCount()==0) {
            listView.removeHeaderView(listHeader);
            noPlacesToShow.setVisibility(View.VISIBLE);
        }

        Log.e("GettingPlaceDetails", "ListAdapter count"+listViewAdapter.getCount());
    }

    @Override
    protected ArrayList<PlaceContent> doInBackground(Void... params) {
        allPlacesDetailsArray=new ArrayList<PlaceContent>();
        allPlacesDetails=new AllPlacesDetails(context, location.getLatitude(), location.getLongitude(), placeType);
        allPlacesDetailsArray=allPlacesDetails.getAllPlacesDetails();
        return allPlacesDetailsArray;
    }

    public ArrayList<PlaceContent> returnAllPlacesDetailsArray() {
        return allPlacesDetailsArray;
    }

    private void saveDataIntoSharedPreferences() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("ALL_PLACES_DETAILS_SHARED_PREFERENCES"+placeType, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor=sharedPreferences.edit();
        int i;

        for(i=0;i<allPlacesDetailsArray.size();i++) {
            editor.putString(placeType + "_" + i + "_id", allPlacesDetailsArray.get(i).getId());
            editor.putString(placeType + "_" + i + "_icon", allPlacesDetailsArray.get(i).getIcon());
            editor.putString(placeType + "_" + i + "_name", allPlacesDetailsArray.get(i).getName());
            editor.putString(placeType + "_" + i + "_vicinity", allPlacesDetailsArray.get(i).getVicinity());
            editor.putString(placeType + "_" + i + "_latitude", allPlacesDetailsArray.get(i).getLatitude().toString());
            editor.putString(placeType + "_" + i + "_longitude", allPlacesDetailsArray.get(i).getLongitude().toString());
        }
        editor.putString("myLatitude", String.valueOf(location.getLatitude()));
        editor.putString("myLongitude", String.valueOf(location.getLongitude()));
        editor.commit();
    }
}
