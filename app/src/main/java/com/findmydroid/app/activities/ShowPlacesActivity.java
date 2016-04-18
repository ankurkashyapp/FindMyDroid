package com.findmydroid.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.findmydroid.app.R;
import com.findmydroid.app.fragments.PlacesMostVisitedFragment;

/**
 * Created by Ankur Kashyap on 18-04-2016.
 */
public class ShowPlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.place_container, new PlacesMostVisitedFragment()).commit();
    }
}
