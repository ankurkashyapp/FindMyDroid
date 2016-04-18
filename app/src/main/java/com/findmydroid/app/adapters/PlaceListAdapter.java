package com.findmydroid.app.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findmydroid.app.R;
import com.findmydroid.app.utilities.ItemDecorator;

/**
 * Created by Ankur Kashyap on 11-04-2016.
 */
public class PlaceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_PLACE_TITLE = 1;

    private Context context;
    private String[] places;
    private int[] placeIcons;


    public PlaceListAdapter(Context context, String[] places, int[] placeIcons) {
        this.context = context;
        this.places = places;
        this.placeIcons = placeIcons;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new HeaderTextHolder(LayoutInflater.from(context).inflate(R.layout.item_view_header_text, parent, false));
            case VIEW_TYPE_PLACE_TITLE:
                return new PlaceTitleHolder(LayoutInflater.from(context).inflate(R.layout.item_view_places_title, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            HeaderTextHolder headerTextHolder = (HeaderTextHolder)holder;
            headerTextHolder.headerText.setText("Most Visited");
        }

        else if (holder.getItemViewType() == VIEW_TYPE_PLACE_TITLE) {
            PlaceTitleHolder placeTitleHolder = (PlaceTitleHolder)holder;
            if (position == 1) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                placeTitleHolder.recyclerView.setLayoutManager(layoutManager);

                placeTitleHolder.recyclerView.addItemDecoration(new ItemDecorator(3));

                //PlacesAdapter placesAdapter = new PlacesAdapter(context, iconsMostVisited, placeMostVisited);
                //placeTitleHolder.recyclerView.setAdapter(placesAdapter);
            }

            else if (position == 3) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                placeTitleHolder.recyclerView.setLayoutManager(layoutManager);

                placeTitleHolder.recyclerView.addItemDecoration(new ItemDecorator(3));

                /*PlacesAdapter placesAdapter = new PlacesAdapter(context, iconsReligious, placeReligious);
                placeTitleHolder.recyclerView.setAdapter(placesAdapter);*/
            }
            else if (position == 5) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                placeTitleHolder.recyclerView.setLayoutManager(gridLayoutManager);
                placeTitleHolder.recyclerView.addItemDecoration(new ItemDecorator(3));

                /*PlacesAdapter placesAdapter = new PlacesAdapter(context, iconsMiscellaneous, placeMiscellaneous);
                placeTitleHolder.recyclerView.setAdapter(placesAdapter);*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class HeaderTextHolder extends RecyclerView.ViewHolder {

        TextView headerText;
        public HeaderTextHolder(View itemView) {
            super(itemView);
            headerText = (TextView)itemView.findViewById(R.id.header_text);
        }
    }

    public class PlaceTitleHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        public PlaceTitleHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.places_title);
        }
    }
}
