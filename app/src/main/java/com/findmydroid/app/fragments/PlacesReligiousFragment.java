package com.findmydroid.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findmydroid.app.R;
import com.findmydroid.app.activities.ShowPlacesActivity;
import com.findmydroid.app.adapters.ViewPagerAdapter;
import com.findmydroid.app.fragments.religious.Church;
import com.findmydroid.app.fragments.religious.Mosque;
import com.findmydroid.app.fragments.religious.Temple;

/**
 * Created by Ankur Kashyap on 11/12/2015.
 */
public class PlacesReligiousFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public static PlacesReligiousFragment newInstance(int placePosition) {
        PlacesReligiousFragment placesReligiousFragment = new PlacesReligiousFragment();
        Bundle data = new Bundle();
        data.putInt(ShowPlacesActivity.PLACE_POSITION, placePosition);
        placesReligiousFragment.setArguments(data);
        return placesReligiousFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_places, null);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        setUpViewPager();
        return view;
    }

    private void setUpViewPager() {
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new Temple(), "Temple");
        pagerAdapter.addFragment(new Mosque(), "Mosque");
        pagerAdapter.addFragment(new Church(), "Church");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(getArguments().getInt(ShowPlacesActivity.PLACE_POSITION, 0));
    }
}
