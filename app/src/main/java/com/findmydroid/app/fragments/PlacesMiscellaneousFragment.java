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
import com.findmydroid.app.fragments.miscellaneous.Bar;
import com.findmydroid.app.fragments.miscellaneous.BusStation;
import com.findmydroid.app.fragments.miscellaneous.Library;
import com.findmydroid.app.fragments.miscellaneous.Park;
import com.findmydroid.app.fragments.miscellaneous.PostOffice;
import com.findmydroid.app.fragments.miscellaneous.Restaurant;
import com.findmydroid.app.fragments.miscellaneous.School;
import com.findmydroid.app.fragments.miscellaneous.ShoppingMall;
import com.findmydroid.app.fragments.miscellaneous.TrainStation;
import com.findmydroid.app.fragments.miscellaneous.University;

/**
 * Created by Ankur Kashyap on 11/12/2015.
 */
public class PlacesMiscellaneousFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public static PlacesMiscellaneousFragment newInstance(int placePosition) {
        PlacesMiscellaneousFragment placesMiscellaneousFragment = new PlacesMiscellaneousFragment();
        Bundle data = new Bundle();
        data.putInt(ShowPlacesActivity.PLACE_POSITION, placePosition);
        placesMiscellaneousFragment.setArguments(data);
        return placesMiscellaneousFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_places, null);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        setUpViewPager();
        return view;
    }

    private void setUpViewPager() {
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new Bar(), "Bar");
        pagerAdapter.addFragment(new BusStation(), "Bus Station");
        pagerAdapter.addFragment(new TrainStation(), "Railway Station");
        pagerAdapter.addFragment(new Library(), "Library");
        pagerAdapter.addFragment(new Park(), "Park");
        pagerAdapter.addFragment(new PostOffice(), "Post office");
        pagerAdapter.addFragment(new Restaurant(), "Restaurant");
        pagerAdapter.addFragment(new School(), "School");
        pagerAdapter.addFragment(new University(), "University");
        pagerAdapter.addFragment(new ShoppingMall(), "Shopping Mall");


        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(getArguments().getInt(ShowPlacesActivity.PLACE_POSITION, 0));
    }
}
