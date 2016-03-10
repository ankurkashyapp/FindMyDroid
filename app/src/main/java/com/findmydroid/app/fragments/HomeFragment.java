package com.findmydroid.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.findmydroid.app.R;
import com.findmydroid.app.adapters.FeaturesListAdapter;
import com.findmydroid.app.custom_views.FmdButton;
import com.findmydroid.app.utilities.ItemDecorator;

/**
 * Created by Ankur Kashyap on 25-02-2016.
 */
public class HomeFragment extends Fragment implements FmdButton.FmdButtonListener, FeaturesListAdapter.OnFeatureClickListener {
    private static final int COLUMNS_NO=2;
    private RecyclerView featuresList;
    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViews();
    }

    private void initializeViews() {
        int[] featureImages = {R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock, R.drawable.ic_lock};
        String[] featureTitles = {"Lock Phone", "Track Location", "Format SD Card", "Change Ringing Mode", "Forward Calls", "Wifi ON/OFF", "Data ON/Off", "Flashlight"};

        featuresList = (RecyclerView)getActivity().findViewById(R.id.features_list);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMNS_NO);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position==0)
                    return 2;
                else return 1;
            }
        });

        featuresList.setLayoutManager(layoutManager);
        featuresList.addItemDecoration(new ItemDecorator(5));


        FeaturesListAdapter featuresListAdapter = new FeaturesListAdapter(getActivity(), featureImages, featureTitles, HomeFragment.this, HomeFragment.this);
        featuresList.setAdapter(featuresListAdapter);

    }

    @Override
    public void itemButtonClicked(View view) {
        if(view == getActivity().findViewById(R.id.device_admin))
            Toast.makeText(getActivity(), "Device Admin", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Uninstall Defence", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemImageButtonClicked(View view) {
        if(view == getActivity().findViewById(R.id.device_admin))
            Toast.makeText(getActivity(), "Help for Device Admin", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Help for Uninstall Defence", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeatureClick(View view) {
    }
}
