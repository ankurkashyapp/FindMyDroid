package com.findmydroid.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 17-03-2016.
 */
public class HelpDialog extends DialogFragment {

    private static final String HELP_TITLE="title";
    private static final String HELP_DESCRIPTION="description";

    private TextView title, description;
    private Switch uninstallDefenceActivation;
    private Button hide;

    public static HelpDialog newInstance(String helpTitle, String helpDescription) {
        Bundle args = new Bundle();
        args.putString(HELP_TITLE, helpTitle);
        args.putString(HELP_DESCRIPTION, helpDescription);
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.setArguments(args);
        return helpDialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_dialog, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        title = (TextView)view.findViewById(R.id.help_title);
        description = (TextView)view.findViewById(R.id.help_description);
        uninstallDefenceActivation = (Switch)view.findViewById(R.id.uninstall_defence_activation);
        hide = (Button)view.findViewById(R.id.help_hide);

        title.setText(getArguments().getString(HELP_TITLE));
        description.setText(getArguments().getString(HELP_DESCRIPTION));

        if (getArguments().getString(HELP_DESCRIPTION)==null) {
            description.setVisibility(View.GONE);
            uninstallDefenceActivation.setVisibility(View.VISIBLE);

            uninstallDefenceActivation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Toast.makeText(getActivity(), "Uninstall Defence Activated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Uninstall Defence Deactivated", Toast.LENGTH_SHORT).show();
                }
            });
        }

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
