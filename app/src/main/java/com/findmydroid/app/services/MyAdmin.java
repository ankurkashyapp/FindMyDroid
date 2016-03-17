package com.findmydroid.app.services;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MyAdmin extends DeviceAdminReceiver {
	
	private DevicePolicyManager deviceManger;
	private ComponentName componentName;

	static SharedPreferences getSamplePreferences(Context context) {  
        return context.getSharedPreferences(  
          DeviceAdminReceiver.class.getName(), 0);  
    }  
  
    void showToast(Context context, CharSequence msg) {  
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();  
    }  
  
    @Override  
    public void onEnabled(Context context, Intent intent) {  
        showToast(context, "FindMyDroid Device Admin: enabled");
    }  
  
    @Override  
    public CharSequence onDisableRequested(Context context, Intent intent) {

    	deviceManger = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(context, MyAdmin.class);

		//deviceManger.resetPassword(dbh.getpwd("defencepin"), DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
		//deviceManger.lockNow();
        return "Do you really want to deactivate this?";  
    }  
  
    @Override  
    public void onDisabled(Context context, Intent intent) {  
        showToast(context, "FindMyDroid Device Admin: disabled");  
    }  
  
    @Override  
    public void onPasswordChanged(Context context, Intent intent) {    
    }  
  
    @Override  
    public void onPasswordFailed(Context context, Intent intent) {    
    }  
  
    @Override  
    public void onPasswordSucceeded(Context context, Intent intent) {    
    }   
  

}
