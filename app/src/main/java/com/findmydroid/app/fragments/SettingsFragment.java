package com.findmydroid.app.fragments;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findmydroid.app.R;
import com.findmydroid.app.services.MyAdmin;

/**
 * Created by Ankur Kashyap on 19-03-2016.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static final String APP_DATA = "appData";
    public static final String APP_STATUS = "appStatus";
    public static final String CHANGED_SIM_NUMBER = "changedSimNumber";
    public static final String UNINSTALL_DEFENCE = "uninstallDefence";
    public static final String ANY_NUMBER = "anyNumber";
    public static final String APP_PASSWORD = "appPassword";
    public static final String NOTIFICATION_ONE = "notificationOne";
    public static final String NOTIFICATION_TWO = "notificationTwo";

    private Button deviceAdmin, anyNumber, uninstallDefence, updatePwd, updateNumber;
    private EditText pwd, confirmPwd, notificationOne, notificationTwo;

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViews();

    }

    private void initializeViews() {
        deviceAdmin = (Button)getActivity().findViewById(R.id.device_admin);
        anyNumber = (Button)getActivity().findViewById(R.id.additional_settings);
        uninstallDefence = (Button)getActivity().findViewById(R.id.uninstall_defence);
        updatePwd = (Button)getActivity().findViewById(R.id.update_password);
        updateNumber = (Button)getActivity().findViewById(R.id.update_number);

        pwd = (EditText)getActivity().findViewById(R.id.enter_password);
        confirmPwd = (EditText)getActivity().findViewById(R.id.confirm_password);
        notificationOne = (EditText)getActivity().findViewById(R.id.notif_number_one);
        notificationTwo = (EditText)getActivity().findViewById(R.id.notif_number_two);

        deviceAdmin.setOnClickListener(this);
        anyNumber.setOnClickListener(this);
        uninstallDefence.setOnClickListener(this);
        updatePwd.setOnClickListener(this);
        updateNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.device_admin: break;
            case R.id.additional_settings: break;
            case R.id.uninstall_defence: break;
            case R.id.update_password: updatePassword(); break;
            case R.id.update_number: updateNumber(); break;
        }
    }

    private void deviceAdminActivation() {

    }

    private void anyNumberSettings() {

    }

    private void uninstallDefence() {

    }

    private void updatePassword() {
        if(pwd.getText().toString().length()>0) {
            if(pwd.getText().toString().equals(confirmPwd.getText().toString())) {
                SharedPreferences preferences = getActivity().getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString(APP_PASSWORD, pwd.getText().toString());
                Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                pwd.setText("");
                confirmPwd.setText("");
            }
            else
                Toast.makeText(getActivity(), "Both password should match each other", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getActivity(), "Should not be empty!!", Toast.LENGTH_LONG).show();
    }

    private void updateNumber() {

        if(notificationOne.getText().toString()!=null || notificationTwo.getText().toString()!=null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= preferences.edit();
            editor.putString(NOTIFICATION_ONE, notificationOne.getText().toString());
            editor.putString(NOTIFICATION_TWO, notificationTwo.getText().toString());
            notificationOne.setText(preferences.getString(NOTIFICATION_ONE, ""));
            notificationTwo.setText(preferences.getString(NOTIFICATION_TWO, ""));
            Toast.makeText(getActivity(), "Notification number updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
