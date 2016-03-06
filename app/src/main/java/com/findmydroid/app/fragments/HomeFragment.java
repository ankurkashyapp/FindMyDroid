package com.findmydroid.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.findmydroid.app.R;
import com.findmydroid.app.custom_views.FmdButton;

/**
 * Created by Ankur Kashyap on 25-02-2016.
 */
public class HomeFragment extends Fragment implements FmdButton.FmdButtonListener {
    public FmdButton deviceAdmin, uninstallDefence;
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
        deviceAdmin = (FmdButton)getActivity().findViewById(R.id.device_admin);
        uninstallDefence = (FmdButton)getActivity().findViewById(R.id.uninstall_defence);
        deviceAdmin.setClickListener(this);
        uninstallDefence.setClickListener(this);
    }

    @Override
    public void itemButtonClicked(View view) {
        if(view == deviceAdmin)
            Toast.makeText(getActivity(), "Button 1 Clicked", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Button 2 Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemImageButtonClicked(View view) {

    }
}
