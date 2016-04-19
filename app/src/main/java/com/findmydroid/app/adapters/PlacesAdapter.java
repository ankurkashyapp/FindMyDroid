package com.findmydroid.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 11-04-2016.
 */
public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int[] placesIcons;
    private String[] placesTitles;

    private PlaceClickedListener placeClickedListener;

    public PlacesAdapter(Context context, int[] placesIcons, String[] placesTitles, PlaceClickedListener placeClickedListener) {
        this.context = context;
        this.placesIcons = placesIcons;
        this.placesTitles = placesTitles;
        this.placeClickedListener = placeClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent.getId() == R.id.places_most_visited || parent.getId() == R.id.places_religious)
            return new PlacesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_place, parent, false), parent);
        else
            return new PlacesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_miscellaneous_places, parent, false), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlacesViewHolder placesViewHolder = (PlacesViewHolder)holder;
        placesViewHolder.placeImage.setBackgroundResource(placesIcons[position]);
        placesViewHolder.placeTitle.setText(placesTitles[position]);
        setFeatureBackground(placesViewHolder.placeBackground, position);
    }

    private void setFeatureBackground(RelativeLayout featureBackground, int position) {
        switch (position) {
            case 0:featureBackground.setBackgroundResource(R.drawable.ic_back_ringing);break;
            case 1:featureBackground.setBackgroundResource(R.drawable.ic_back_lock);break;
            case 2:featureBackground.setBackgroundResource(R.drawable.ic_back_calls);break;
            case 3:featureBackground.setBackgroundResource(R.drawable.ic_back_sd_card);break;
            case 4:featureBackground.setBackgroundResource(R.drawable.ic_back_ringing);break;
            case 5:featureBackground.setBackgroundResource(R.drawable.ic_back_calls);break;
            case 6:featureBackground.setBackgroundResource(R.drawable.ic_back_wifi);break;
            case 7:featureBackground.setBackgroundResource(R.drawable.ic_back_data);break;
            case 8:featureBackground.setBackgroundResource(R.drawable.ic_back_flashlight);break;
            case 9:featureBackground.setBackgroundResource(R.drawable.ic_back_calls);break;
        }
    }

    @Override
    public int getItemCount() {
        return placesTitles.length;
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout placeBackground;
        private ImageView placeImage;
        private TextView placeTitle;
        public PlacesViewHolder(View itemView, final ViewGroup parent) {
            super(itemView);
            initializeViews(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeClickedListener.onPlaceClicked(v, parent);
                }
            });
        }

        private void initializeViews(View itemView) {
            placeBackground = (RelativeLayout)itemView.findViewById(R.id.place_background);
            placeImage = (ImageView)itemView.findViewById(R.id.place_icon);
            placeTitle = (TextView)itemView.findViewById(R.id.place_title);
        }
    }

    public interface PlaceClickedListener {
        void onPlaceClicked(View view, ViewGroup parent);
    }
}
