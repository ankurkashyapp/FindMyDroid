package com.findmydroid.app.services;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findmydroid.app.R;

import java.util.ArrayList;

/**
 * Created by Ankur Kashyap on 03/12/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    Location currentLocation;
    ArrayList<PlaceContent> allPlacesContent;
    public ListViewAdapter(Context context, Location currentLocation, ArrayList<PlaceContent> allPlacesContent) {
        this.context=context;
        this.currentLocation=currentLocation;
        this.allPlacesContent=allPlacesContent;
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

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        viewHolder.listRow.setLayoutParams(layoutParams);
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout listRow;
        TextView placeName, placeDistance;
        ImageView rightArrow;
    }
}
