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
import com.findmydroid.app.fragments.mostvisited.Atm;
import com.findmydroid.app.fragments.mostvisited.Bank;
import com.findmydroid.app.fragments.mostvisited.Gym;
import com.findmydroid.app.fragments.mostvisited.Hospital;
import com.findmydroid.app.fragments.mostvisited.MovieTheater;
import com.findmydroid.app.fragments.mostvisited.PoliceStation;

/**
 * Created by Ankur Kashyap on 18-04-2016.
 */
public class PlacesMostVisitedFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public static PlacesMostVisitedFragment newInstance(int placePosition) {
        PlacesMostVisitedFragment placesMostVisitedFragment = new PlacesMostVisitedFragment();
        Bundle data = new Bundle();
        data.putInt(ShowPlacesActivity.PLACE_POSITION, placePosition);
        placesMostVisitedFragment.setArguments(data);
        return placesMostVisitedFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_places, null);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(0);
        setUpViewPager();
        return view;
    }

    private void setUpViewPager() {
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new Atm(), "ATM");
        pagerAdapter.addFragment(new Hospital(), "Hospital");
        pagerAdapter.addFragment(new Gym(), "Gym");
        pagerAdapter.addFragment(new Bank(), "Bank");
        pagerAdapter.addFragment(new MovieTheater(), "Movie Theatre");
        pagerAdapter.addFragment(new PoliceStation(), "Police Station");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(getArguments().getInt(ShowPlacesActivity.PLACE_POSITION, 0));
    }
}
