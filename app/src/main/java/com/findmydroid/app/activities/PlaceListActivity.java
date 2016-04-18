package com.findmydroid.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.findmydroid.app.R;
import com.findmydroid.app.adapters.PlacesAdapter;
import com.findmydroid.app.utilities.ItemDecorator;

/**
 * Created by Ankur Kashyap on 11-04-2016.
 */
public class PlaceListActivity extends AppCompatActivity implements PlacesAdapter.PlaceClickedListener {
    private RecyclerView placesMostVisited;
    private RecyclerView placesReligious;
    private RecyclerView placesMiscellaneous;

    private String[] placeMostVisited = {"ATM", "BANK", "GYM", "HOSPITAL", "MOVIE THEATRE", "POLICE STATION"};
    private String[] placeReligious = {"TEMPLES", "MOSQUES", "CHURCHES"};
    private String[] placeMiscellaneous = {"BAR", "BUS STATION", "RAILWAY STATION", "LIBRARY", "PARK", "POST OFFICE", "RESTAURANT", "SCHOOL", "UNIVERSITY", "SHOPPING MALL"};

    private int[] iconsMostVisited = {R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight};
    private int[] iconsReligious = {R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight};
    private int[] iconsMiscellaneous = {R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight, R.drawable.ic_feature_flashlight};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setupMostVisitedPlaces();
        setupReligiousPlaces();
        setupMiscellaneousPlaces();
    }

    private void initViews() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        placesMostVisited = (RecyclerView)findViewById(R.id.places_most_visited);
        placesMostVisited.setLayoutManager(layoutManager1);
        placesMostVisited.addItemDecoration(new ItemDecorator(5));

        placesReligious = (RecyclerView)findViewById(R.id.places_religious);
        placesReligious.setLayoutManager(layoutManager2);
        placesReligious.addItemDecoration(new ItemDecorator(5));

        placesMiscellaneous = (RecyclerView)findViewById(R.id.places_miscellaneous);
        placesMiscellaneous.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        placesMiscellaneous.setLayoutManager(gridLayoutManager);
        placesMiscellaneous.addItemDecoration(new ItemDecorator(3));
    }

    private void setupMostVisitedPlaces() {
        PlacesAdapter adapter = new PlacesAdapter(this, iconsMostVisited, placeMostVisited, this);
        placesMostVisited.setAdapter(adapter);
    }

    private void setupReligiousPlaces() {
        PlacesAdapter adapter = new PlacesAdapter(this, iconsReligious, placeReligious, this);
        placesReligious.setAdapter(adapter);
    }

    private void setupMiscellaneousPlaces() {
        PlacesAdapter adapter = new PlacesAdapter(this, iconsMiscellaneous, placeMiscellaneous, this);
        placesMiscellaneous.setAdapter(adapter);
    }

    @Override
    public void onPlaceClicked(View view, ViewGroup parent) {
        switch (parent.getId()) {
            case R.id.places_most_visited:mostVisitedClicked(view);break;
            case R.id.places_religious:religiousClicked(view);break;
            case R.id.places_miscellaneous:miscellaneousClicked(view);break;
        }
    }

    private void mostVisitedClicked(View view) {
        int position = placesMostVisited.getChildLayoutPosition(view);
        startActivity(new Intent(PlaceListActivity.this, ShowPlacesActivity.class));
    }

    private void religiousClicked(View view) {
        int position = placesReligious.getChildLayoutPosition(view);
    }

    private void miscellaneousClicked(View view) {
        int position = placesMiscellaneous.getChildLayoutPosition(view);
    }
}
