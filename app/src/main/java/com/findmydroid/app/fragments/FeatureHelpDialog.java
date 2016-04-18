package com.findmydroid.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 18-03-2016.
 */
public class FeatureHelpDialog extends DialogFragment {

    private static final String FEATURE_POSITION = "position";
    private static final String FEATURE_TITLE = "title";
    private static final String FEATURE_STATUS = "status";
    private static final String FEATURE_COMMAND = "command";
    private static final String FEATURE_COMMAND_DESCRIPTION = "description";

    private TextView title, command, description;
    private Switch status;
    private Button hide;

    public static FeatureHelpDialog newInstance(int featurePosition, String featureTitle, boolean featureStatus, String featureCommand, String featureDescription) {
        Bundle args = new Bundle();
        args.putInt(FEATURE_POSITION, featurePosition);
        args.putString(FEATURE_TITLE, featureTitle);
        args.putBoolean(FEATURE_STATUS, featureStatus);
        args.putString(FEATURE_COMMAND, featureCommand);
        args.putString(FEATURE_COMMAND_DESCRIPTION, featureDescription);

        FeatureHelpDialog helpDialog = new FeatureHelpDialog();
        helpDialog.setArguments(args);
        return helpDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature_help_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        title = (TextView)view.findViewById(R.id.feature_help_title);
        command = (TextView)view.findViewById(R.id.feature_command_details);
        description = (TextView)view.findViewById(R.id.feature_help_details);
        status = (Switch)view.findViewById(R.id.feature_activation);
        hide = (Button)view.findViewById(R.id.help_hide);

        title.setText(getArguments().getString(FEATURE_TITLE));
        command.setText(getArguments().getString(FEATURE_COMMAND));
        description.setText(getArguments().getString(FEATURE_COMMAND_DESCRIPTION));
        status.setChecked(getArguments().getBoolean(FEATURE_STATUS));

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveFeatureStatus(getArguments().getInt(FEATURE_POSITION), isChecked);
            }
        });


        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void saveFeatureStatus(int position, boolean status) {
        SharedPreferences preferences = getActivity().getSharedPreferences(HomeFragment.FEATURE_STATUS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        switch (position) {
            case 1: editor.putBoolean(HomeFragment.LOCK, status);break;
            case 2: editor.putBoolean(HomeFragment.LOCATION, status);break;
            case 3: editor.putBoolean(HomeFragment.SD_CARD, status);break;
            case 4: editor.putBoolean(HomeFragment.RINGING, status);break;
            case 5: editor.putBoolean(HomeFragment.CALLS, status);break;
            case 6: editor.putBoolean(HomeFragment.WIFI, status);break;
            case 7: editor.putBoolean(HomeFragment.DATA, status);break;
            case 8: editor.putBoolean(HomeFragment.FLASHLIGHT, status);break;
        }
        editor.commit();
    }
}
