package com.findmydroid.app.adapters;

import android.content.Context;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findmydroid.app.R;
import com.findmydroid.app.services.PlaceContent;

import java.util.ArrayList;

/**
 * Created by Ankur Kashyap on 03/12/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private Location currentLocation;
    private ArrayList<PlaceContent> allPlacesContent;

    private String placeType;
    public ListViewAdapter(Context context, Location currentLocation, ArrayList<PlaceContent> allPlacesContent, String placeType) {
        this.context=context;
        this.currentLocation=currentLocation;
        this.allPlacesContent=allPlacesContent;
        this.placeType = placeType;
    }

    @Override
    public int getCount() {
        return allPlacesContent.size();
    }

    @Override
    public Object getItem(int position) {
        return allPlacesContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return allPlacesContent.indexOf(allPlacesContent.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null) {
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.custom_list_row, null);
            viewHolder.listRow=(RelativeLayout)convertView.findViewById(R.id.list_row);
            viewHolder.placeName=(TextView)convertView.findViewById(R.id.place_name);
            viewHolder.placeDistance=(TextView)convertView.findViewById(R.id.place_distance);
            viewHolder.rightArrow=(ImageView)convertView.findViewById(R.id.rightArrow);
            viewHolder.placeIcon = (ImageView)convertView.findViewById(R.id.place_icon);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Log.e("ListViewAdapter", "Size:"+allPlacesContent.size());

        Location placeLocation=new Location("");
        placeLocation.setLatitude(allPlacesContent.get(position).getLatitude());
        placeLocation.setLongitude(allPlacesContent.get(position).getLongitude());

        float distanceToPlace=(currentLocation.distanceTo(placeLocation))/1000;
        viewHolder.placeName.setText(allPlacesContent.get(position).getName());
        viewHolder.placeDistance.setText(String.format("%.2f", distanceToPlace)+"KM");
        setPlaceIcon(viewHolder);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        viewHolder.listRow.setLayoutParams(layoutParams);
        return convertView;
    }

    private void setPlaceIcon(ViewHolder viewHolder) {
        switch (placeType) {
            case "atm": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_atm));break;
            case "hospital": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_hospital));break;
            case "gym": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_gym));break;
            case "bank": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bank));break;
            case "movie_theater": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_movie_theatre));break;
            case "police": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_police));break;
            case "hindu_temple": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_temple));break;
            case "church": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_church));break;
            case "mosque": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_mosque));break;
            case "bar": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bar));break;
            case "bus_station": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bus_station));break;
            case "train_station": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_railway_station));break;
            case "library": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_library));break;
            case "park": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_park));break;
            case "post_office": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_post_office));break;
            case "restaurant": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_restaurant));break;
            case "school": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_school));break;
            case "university": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_university));break;
            case "shopping_mall": viewHolder.placeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_shopping_mall));break;
        }
    }

    private class ViewHolder {
        RelativeLayout listRow;
        TextView placeName, placeDistance;
        ImageView rightArrow;
        ImageView placeIcon;
    }
}
