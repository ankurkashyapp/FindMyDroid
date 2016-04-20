package com.findmydroid.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 30-03-2016.
 */
public class AppPasswordFragment extends DialogFragment implements View.OnClickListener {

    private static final int MAX_PASSWORD_LENGTH = 4;
    private int currentIndicator = 0;
    private StringBuffer enteredPassword;

    //Password Indicator group
    private LinearLayout pwdIndicatorGroup;
    private View indicatorOne;
    private View indicatorTwo;
    private View indicatorThree;
    private View indicatorFour;


    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private Button exit;
    private Button delete;


    public static AppPasswordFragment newInstance() {
        return new AppPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_password, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        enteredPassword = new StringBuffer();

        pwdIndicatorGroup = (LinearLayout)view.findViewById(R.id.pwd_indicator_group);
        indicatorOne = view.findViewById(R.id.pwd_indicator_one);
        indicatorTwo = view.findViewById(R.id.pwd_indicator_two);
        indicatorThree = view.findViewById(R.id.pwd_indicator_three);
        indicatorFour = view.findViewById(R.id.pwd_indicator_four);

        one = (Button)view.findViewById(R.id.one);
        two = (Button)view.findViewById(R.id.two);
        three = (Button)view.findViewById(R.id.three);
        four = (Button)view.findViewById(R.id.four);
        five = (Button)view.findViewById(R.id.five);
        six = (Button)view.findViewById(R.id.six);
        seven = (Button)view.findViewById(R.id.seven);
        eight = (Button)view.findViewById(R.id.eight);
        nine = (Button)view.findViewById(R.id.nine);
        zero = (Button)view.findViewById(R.id.zero);
        exit = (Button)view.findViewById(R.id.exit);
        delete = (Button)view.findViewById(R.id.delete);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        exit.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.one: onePressed(); break;
            case R.id.two: twoPressed(); break;
            case R.id.three: threePressed(); break;
            case R.id.four: fourPressed(); break;
            case R.id.five: fivePressed(); break;
            case R.id.six: sixPressed(); break;
            case R.id.seven: sevenPressed(); break;
            case R.id.eight: eightPressed(); break;
            case R.id.nine: ninePressed(); break;
            case R.id.zero: zeroPressed(); break;
            case R.id.exit: break;
            case R.id.delete:
                if (currentIndicator>0)
                    deletePressed(); break;
        }

    }

    private void onePressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(1);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void twoPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(2);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void threePressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(3);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void fourPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(4);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void fivePressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(5);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void sixPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(6);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void sevenPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(7);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void eightPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(8);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void ninePressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(9);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void zeroPressed() {
        if (currentIndicator < MAX_PASSWORD_LENGTH) {
            enteredPassword.append(0);
            currentIndicator++;
            updatePasswordIndicator();
        }
    }

    private void deletePressed() {
        Log.e("AppPasswordFragment", "EnteredPassword: " + enteredPassword.toString());
        currentIndicator--;
        enteredPassword.deleteCharAt(currentIndicator);
        updatePasswordIndicator();
    }

    private void updatePasswordIndicator() {
        switch (currentIndicator) {
            case 0:
                indicatorOne.setBackgroundResource(R.drawable.circular_stroke);
                indicatorTwo.setBackgroundResource(R.drawable.circular_stroke);
                indicatorThree.setBackgroundResource(R.drawable.circular_stroke);
                indicatorFour.setBackgroundResource(R.drawable.circular_stroke); break;

            case 1:
                indicatorOne.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorTwo.setBackgroundResource(R.drawable.circular_stroke);
                indicatorThree.setBackgroundResource(R.drawable.circular_stroke);
                indicatorFour.setBackgroundResource(R.drawable.circular_stroke); break;

            case 2:
                indicatorOne.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorTwo.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorThree.setBackgroundResource(R.drawable.circular_stroke);
                indicatorFour.setBackgroundResource(R.drawable.circular_stroke); break;

            case 3:
                indicatorOne.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorTwo.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorThree.setBackgroundResource(R.drawable.circular_stroke_filled);
                indicatorFour.setBackgroundResource(R.drawable.circular_stroke); break;

            case 4:
                if (enteredPassword.toString().equals(getAppPassword()))
                    dismiss();
                else  {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    pwdIndicatorGroup.startAnimation(shake);
                    currentIndicator = 0;
                    enteredPassword.delete(0, 4);
                    Log.e("AppPasswordFragment", "EnteredPassword: " + enteredPassword.toString());
                    updatePasswordIndicator();
                }
        }
    }

    private String getAppPassword() {
        SharedPreferences preferences = getActivity().getSharedPreferences(SettingsFragment.APP_DATA, Context.MODE_PRIVATE);
        return preferences.getString(SettingsFragment.APP_PASSWORD, "1234");
    }
}
