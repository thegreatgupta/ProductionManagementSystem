 package com.jeevani.productionmanagementsystem.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SystemAppPreferences {
	
	private SharedPreferences mAppPrefs;
	private static SystemAppPreferences prefsInstance;

	public SystemAppPreferences(Context context) {
		mAppPrefs = context.getSharedPreferences("SystemAppPreferences", Context.MODE_PRIVATE);
	}
	
	
	public static SystemAppPreferences getInstance(Context context) {
		if(prefsInstance == null) {
			prefsInstance = new SystemAppPreferences(context);
		}
		return prefsInstance;
	}
//Set data

	public void setUserId(String userId) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("userId",userId.trim());
		edit.commit();
	}
	public void setFirstName(String firstName) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("firstName",firstName.trim());
		edit.commit();
	}
	public void setLastName(String lastName) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("lastName",lastName.trim());
		edit.commit();
	}
	public void setEmail(String email) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("email",email.trim());
		edit.commit();
	}
	public void setPhone(String phone) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("phone",phone.trim());
		edit.commit();
	}
	public void setDevice(String device) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("device",device.trim());
		edit.commit();
	}
	public void setType(String type) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("type",type.trim());
		edit.commit();
	}

	//Get data

	public String getUserId() {
		return mAppPrefs.getString("userId", "");
	}
	public String getFirstName() {
		return mAppPrefs.getString("firstName", "");
	}
	public String getLastName() {
		return mAppPrefs.getString("lastName", "");
	}
	public String getEmail() {
		return mAppPrefs.getString("email", "");
	}
	public String getPhone() {
		return mAppPrefs.getString("phone", "");
	}
	public String getDevice() {
		return mAppPrefs.getString("device", "");
	}
	public String getType() {
		return mAppPrefs.getString("type", "");
	}

}
