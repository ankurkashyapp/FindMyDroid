package com.findmydroid.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.findmydroid.app.R;
import com.findmydroid.app.fragments.PlacesMiscellaneousFragment;
import com.findmydroid.app.fragments.PlacesMostVisitedFragment;
import com.findmydroid.app.fragments.PlacesReligiousFragment;

/**
 * Created by Ankur Kashyap on 18-04-2016.
 */
public class ShowPlacesActivity extends AppCompatActivity {

    private static final int PLACE_MOST_VISITED = 1;
    private static final int PLACE_RELIGIOUS = 2;
    private static final int PLACE_MISCELLANEOUS = 3;

    public static final String PLACE_TYPE = "placeType";
    public static final String PLACE_POSITION = "placePosition";

    private int placeType;
    private int placePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeType = getIntent().getIntExtra(PLACE_TYPE, 1);
        placePosition = getIntent().getIntExtra(PLACE_POSITION, 0);
        showRespectiveFragment();
    }

    private void showRespectiveFragment() {
        switch (placeType) {
            case PLACE_MOST_VISITED:showMostVisitedPlacesFragment();break;
            case PLACE_RELIGIOUS:showReligiousPlacesFragment();break;
            case PLACE_MISCELLANEOUS:showMiscellaneousPlacesFragment();break;
        }
    }

    private void showMostVisitedPlacesFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlacesMostVisitedFragment fragment = PlacesMostVisitedFragment.newInstance(placePosition);
        fragmentTransaction.replace(R.id.place_container, fragment).commit();
    }

    private void showReligiousPlacesFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlacesReligiousFragment fragment = PlacesReligiousFragment.newInstance(placePosition);
        fragmentTransaction.replace(R.id.place_container, fragment).commit();
    }

    private void showMiscellaneousPlacesFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlacesMiscellaneousFragment fragment = PlacesMiscellaneousFragment.newInstance(placePosition);
        fragmentTransaction.replace(R.id.place_container, fragment).commit();
    }
}
