package com.findmydroid.app.fragments;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.findmydroid.app.services.MyAdmin;
import com.findmydroid.app.utilities.ItemDecorator;

/**
 * Created by Ankur Kashyap on 25-02-2016.
 */
public class HomeFragment extends Fragment implements FmdButton.FmdButtonListener, FeaturesListAdapter.OnFeatureClickListener {
    private static final int RESULT_ENABLE=1;
    private static final int COLUMNS_NO=2;

    public static final String FEATURE_STATUS = "featureStatus";
    public static final String LOCK = "lock";
    public static final String LOCATION = "location";
    public static final String SD_CARD = "sdCard";
    public static final String RINGING = "ringing";
    public static final String CALLS = "calls";
    public static final String WIFI = "wifi";
    public static final String DATA = "data";
    public static final String FLASHLIGHT = "flashlight";


    private RecyclerView featuresList;

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

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

        devicePolicyManager = (DevicePolicyManager)getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName=new ComponentName(getActivity(), MyAdmin.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViews();
    }

    private void initializeViews() {
        int[] featureImages = {R.drawable.ic_feature_lock, R.drawable.ic_feature_track_location, R.drawable.ic_feature_sd_card, R.drawable.ic_feature_ringing_mode, R.drawable.ic_feature_forward_calls, R.drawable.ic_feature_wifi, R.drawable.ic_feature_data, R.drawable.ic_feature_flashlight};
        String[] featureTitles = {"Lock Phone", "Track Location", "Format SD Card", "Change Ringing Mode", "Forward Calls", "Wifi ON/OFF", "Data ON/Off", "Flashlight ON/OFF"};

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

        if(view == getActivity().findViewById(R.id.device_admin)) {
            if (devicePolicyManager.isAdminActive(componentName))
                devicePolicyManager.removeActiveAdmin(componentName);
            else {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "After enabling this, your phone can be remotely locked and wiped. We recommend you to keep it enabled always.");
                startActivityForResult(intent, RESULT_ENABLE);
            }
        }
        else {
            String title = getString(R.string.title_uninstall_defence);
            HelpDialog helpDialog = HelpDialog.newInstance(title, null);
            helpDialog.show(getActivity().getSupportFragmentManager(), "UNINSTALL_DEFENCE");
        }
    }

    @Override
    public void itemImageButtonClicked(View view) {

        if(view == getActivity().findViewById(R.id.device_admin)) {
            String title = getString(R.string.title_device_admin);
            String description = getString(R.string.decription_device_admin);
            HelpDialog helpDialog = HelpDialog.newInstance(title, description);
            helpDialog.show(getActivity().getSupportFragmentManager(), "DEVICE_ADMIN");
        }
        else {
            String title = getString(R.string.title_uninstall_defence);
            String description = getString(R.string.description_uninstall_defence);
            HelpDialog helpDialog = HelpDialog.newInstance(title, description);
            helpDialog.show(getActivity().getSupportFragmentManager(), "UNINSTALL_DEFENCE");
        }
    }

    @Override
    public void onFeatureClick(View view) {
        int clickedPosition = featuresList.getChildLayoutPosition(view);

        switch (clickedPosition) {
            case 1:showLockDialog(clickedPosition);break;
            case 2:showLocationDialog(clickedPosition);break;
            case 3:showSdCardDialog(clickedPosition);break;
            case 4:showRingingDialog(clickedPosition);break;
            case 5:showCallsDialog(clickedPosition);break;
            case 6:showWifiDialog(clickedPosition);break;
            case 7:showDataDialog(clickedPosition);break;
            case 8:showFlashlightDialog(clickedPosition);break;
        }

    }

    private void showLockDialog(int position) {
        String command = "Fmd Lock <password>";
        String title = getString(R.string.lock);
        String description = getString(R.string.lock_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), LOCK);
    }

    private void showLocationDialog(int position) {
        String command = "Fmd Track";
        String title = getString(R.string.location);
        String description = getString(R.string.location_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), LOCATION);
    }

    private void showSdCardDialog(int position) {
        String command = "Fmd Format";
        String title = getString(R.string.sd_card);
        String description = getString(R.string.sd_card_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), SD_CARD);
    }

    private void showRingingDialog(int position) {

        String command = "Fmd Mode Vibrate\n" + "Fmd Mode Silent\n" + "Fmd Mode Normal";
        String title = getString(R.string.ringing);
        String description = getString(R.string.ringing_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), RINGING);
    }

    private void showCallsDialog(int position) {
        String command = "Fmd Forward";
        String title = getString(R.string.calls);
        String description = getString(R.string.calls_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), CALLS);
    }

    private void showWifiDialog(int position) {
        String command = "Fmd Wifi On"+"\nFmd Wifi Off";
        String title = getString(R.string.wifi);
        String description = getString(R.string.wifi_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), WIFI);
    }

    private void showDataDialog(int position) {
        String command = "Fmd Data On"+"\nFmd Data Off";
        String title = getString(R.string.data);
        String description = getString(R.string.data_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), DATA);
    }

    private void showFlashlightDialog(int position) {
        String command = "Fmd Flashlight <count>";
        String title = getString(R.string.flashlight);
        String description = getString(R.string.flashlight_command_description);
        boolean status = getFeatureStatus(position);

        FeatureHelpDialog featureHelpDialog = FeatureHelpDialog.newInstance(position, title, status, command, description);
        featureHelpDialog.show(getActivity().getSupportFragmentManager(), FLASHLIGHT);
    }

    private boolean getFeatureStatus(int position) {
        SharedPreferences preferences = getActivity().getSharedPreferences(FEATURE_STATUS, Context.MODE_PRIVATE);
        switch (position) {
            case 1:return preferences.getBoolean(LOCK, true);
            case 2:return preferences.getBoolean(LOCATION, true);
            case 3:return preferences.getBoolean(SD_CARD, true);
            case 4:return preferences.getBoolean(RINGING, true);
            case 5:return preferences.getBoolean(CALLS, true);
            case 6:return preferences.getBoolean(WIFI, true);
            case 7:return preferences.getBoolean(DATA, true);
            case 8:return preferences.getBoolean(FLASHLIGHT, true);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_ENABLE:
                if(resultCode== Activity.RESULT_OK)
                    Toast.makeText(getActivity(), "Device Admin Enabled", Toast.LENGTH_LONG).show();
                break;
            default:break;
        }
    }
}
