package com.findmydroid.app.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 25-02-2016.
 */
public class FmdButton extends LinearLayout implements View.OnClickListener {
    private Button action;
    private ImageButton help;
    private View view;
    private FmdButtonListener fmdButtonListener;

    public void setClickListener(FmdButtonListener fmdButtonListener) {
        this.fmdButtonListener = fmdButtonListener;
    }

    public FmdButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.from(context).inflate(R.layout.fmd_button, this, true);
        action = (Button)getChildAt(0);
        help = (ImageButton)getChildAt(1);

        action.setOnClickListener(this);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == action)
            fmdButtonListener.itemButtonClicked(view);
        else if (v == help)
            fmdButtonListener.itemImageButtonClicked(view);
    }

    public interface FmdButtonListener {
        void itemButtonClicked(View view);
        void itemImageButtonClicked(View view);
    }
}
