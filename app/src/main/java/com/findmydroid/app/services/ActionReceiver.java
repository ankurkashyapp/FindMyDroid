package com.findmydroid.app.services;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.findmydroid.app.fragments.HomeFragment;
import com.findmydroid.app.fragments.SettingsFragment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Ankur Kashyap on 28-03-2016.
 */
public class ActionReceiver extends BroadcastReceiver {

    private Context context;
    private DevicePolicyManager deviceManger;
    private ComponentName componentName;

    private SharedPreferences preferences;

    private String messageBody = null, messageFrom = null;
    private boolean isFlashOn=false, hasFlash;

    private Camera camera = null;
    private Camera.Parameters params = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        deviceManger = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, MyAdmin.class);

        preferences = context.getSharedPreferences(SettingsFragment.APP_DATA, Context.MODE_PRIVATE);

        //If SIM has changed after boot, send alert message to notification numbers
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            sendAlertMessage();
        }

        //if any message has received, read it and perform the corresponding action
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            performActions(intent);
        }
    }

    private void sendAlertMessage() {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String currentSimNumber = telephonyManager.getLine1Number();

        SharedPreferences.Editor editor= preferences.edit();

        if(currentSimNumber.equals(preferences.getString(SettingsFragment.CHANGED_SIM_NUMBER, "")))
            Toast.makeText(context, "SIM has not changed", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(context, "SIM has changed", Toast.LENGTH_LONG).show();
            try {
                editor.putString(SettingsFragment.CHANGED_SIM_NUMBER, currentSimNumber);
                String notifNoOne, notifNoTwo;
                notifNoOne = preferences.getString(SettingsFragment.NOTIFICATION_ONE, "");
                notifNoTwo = preferences.getString(SettingsFragment.NOTIFICATION_TWO, "");

                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(notifNoOne, null, "Hey\nThis person is using your phone.Track or control it using the FINDMYDROID REMOTE FEATURES\nMore details:www.fb.com/kashapps\nRegards ~Team KashApps", null, null);
                sms.sendTextMessage(notifNoTwo , null, "Hey\nThis person is using your phone.Track or control it using the FINDMYDROID REMOTE FEATURES\nMore details:www.fb.com/kashapps\nRegards ~Team KashApps", null, null);

            }
            catch(Exception e) {
                Toast.makeText(context, "Message sending failed",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void performActions(Intent intent) {
        Toast.makeText(context, "SMS Received", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        String notifNoOne = preferences.getString(SettingsFragment.NOTIFICATION_ONE, "");
        String notifNoTwo = preferences.getString(SettingsFragment.NOTIFICATION_TWO, "");
        if (bundle != null) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            SmsMessage shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
            messageBody = shortMessage.getDisplayMessageBody();
            messageFrom = shortMessage.getOriginatingAddress();
            Toast.makeText(context, messageBody + "\nFrom:" + messageFrom, Toast.LENGTH_SHORT).show();
        }

        if (preferences.getBoolean(SettingsFragment.APP_STATUS, false)) {
            String messageToLowerCase = messageBody.toLowerCase();

            if (messageToLowerCase.startsWith("fmd lock")) {
                String lockPassword = messageBody.substring(9);
                if (preferences.getBoolean(SettingsFragment.ANY_NUMBER, true))
                    lock(lockPassword);
                else if (PhoneNumberUtils.compare(context, messageFrom, notifNoOne) || PhoneNumberUtils.compare(context, messageFrom, notifNoTwo))
                    lock(lockPassword);
            }

            if (messageToLowerCase.startsWith("fmd track")) {
                track();
            }

            if (messageToLowerCase.startsWith("fmd format")) {
                format();
            }

            if (messageToLowerCase.startsWith("fmd mode")) {
                changeRingingMode();
            }

            if (messageToLowerCase.startsWith("fmd forward")) {
                callForward();
            }

            if (messageToLowerCase.startsWith("fmd wifi")) {
                wifi();
            }

            if (messageToLowerCase.startsWith("fmd data")) {
                mobileData();
            }

            if (messageToLowerCase.startsWith("fmd flashlight")) {
                flashlight();
            }
        }
    }

    private void lock(String lockPassword) {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) && preferences.getBoolean(HomeFragment.LOCK, true)) {

            if(deviceManger.isAdminActive(componentName)) {
                deviceManger.resetPassword(lockPassword, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                Toast.makeText(context, "Phone Locked with password" + lockPassword, Toast.LENGTH_LONG).show();
                deviceManger.lockNow();
            }

        }
        else
            Toast.makeText(context, "Admin is not active", Toast.LENGTH_SHORT).show();
    }

    private void track() {

        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.LOCATION, true)) {

        }

    }

    private void format() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.SD_CARD, true)) {
            File deleteMatchingFile = new File(Environment.getExternalStorageDirectory().toString());
            try {
                File[] filenames = deleteMatchingFile.listFiles();
                if (filenames != null && filenames.length > 0) {
                    for (File tempFile : filenames) {
                        if (tempFile.isDirectory()) {
                            wipeDirectory(tempFile.toString());
                            tempFile.delete();
                        } else {
                            tempFile.delete();
                        }
                    }
                } else {
                    deleteMatchingFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeRingingMode() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.RINGING, true)) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if(messageBody.substring(9, 15).equalsIgnoreCase("normal"))
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            if(messageBody.substring(9, 16).equalsIgnoreCase("vibrate"))
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            if(messageBody.substring(9, 15).equalsIgnoreCase("silent"))
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }

    private void callForward() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.CALLS, true)) {
            Intent intentCallForward = new Intent(Intent.ACTION_CALL);
            intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Uri mmiCode = Uri.fromParts("tel", "*21*"+messageFrom+"#", "#");
            intentCallForward.setData(mmiCode);
            try {
                context.startActivity(intentCallForward);
            }catch (SecurityException ex){}

        }
    }

    private void wifi() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.WIFI, true)) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (messageBody.substring(0, 11).equalsIgnoreCase("fmd wifi on")) {
                if (!wifi.isWifiEnabled())
                    wifi.setWifiEnabled(true);
            } else if (messageBody.substring(0, 12).equalsIgnoreCase("fmd wifi off")) {
                if (wifi.isWifiEnabled())
                    wifi.setWifiEnabled(false);
            }
        }
    }

    private void mobileData() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.DATA, true)) {
            if(messageBody.substring(0, 11).equalsIgnoreCase("fmd data on"))
            {
                try {
                    turnMobileConnection(context,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (messageBody.substring(0, 12).equalsIgnoreCase("fmd data off"))
            {
                try {
                    turnMobileConnection(context,false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void flashlight() {
        if (preferences.getBoolean(SettingsFragment.APP_STATUS, true) || preferences.getBoolean(HomeFragment.FLASHLIGHT, true)) {
            if(camera==null) {
                try{
                    camera=Camera.open();
                    params=camera.getParameters();
                }catch(RuntimeException e){}

            }
            for(int i=1;i<=Integer.parseInt(messageBody.substring(15));i++) {
                if(isFlashOn) turnOffFlash();
                else turnOnFlash();
            }

            if(camera!=null) {
                camera.release();
                camera=null;
            }
        }
    }

    private void turnMobileConnection(Context context,boolean ON) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Method method=null;

        try{
            method = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        }catch(Exception e){}

        method.setAccessible(false);
        if(ON){
            try{
                method.invoke(connectivityManager, true);
            }catch (IllegalArgumentException e) {
                Toast.makeText(context, "Illegal Argument Exception", Toast.LENGTH_SHORT).show();
            } catch (IllegalAccessException e) {
                Toast.makeText(context, "Illegal Access Exception", Toast.LENGTH_SHORT).show();
            }catch (InvocationTargetException e) {
                Toast.makeText(context, "Invocation Target Exception", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try{
                method.invoke(connectivityManager, false);
            }catch (Exception e) {
                Toast.makeText(context, "Error in disabling data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void wipeDirectory(String name) {
        File directoryFile = new File(name);
        File[] filenames = directoryFile.listFiles();
        if (filenames != null && filenames.length > 0) {
            for (File tempFile : filenames) {
                if (tempFile.isDirectory()) {
                    wipeDirectory(tempFile.toString());
                    tempFile.delete();
                } else {
                    tempFile.delete();
                }
            }
        }
    }

    private void turnOnFlash() {
        if(!isFlashOn) {
            if(camera == null || params == null)
                return;
        }

        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashOn = true;
    }

    private void turnOffFlash() {
        if(isFlashOn) {
            if(camera == null || params == null)
                return;
        }

        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashOn = false;
    }
}
