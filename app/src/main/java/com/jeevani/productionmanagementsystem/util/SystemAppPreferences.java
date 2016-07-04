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

	public void setUserName(String name) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserName",name.trim());
		edit.commit();
	}
	public String getUserName() {
		return mAppPrefs.getString("UserName", "");
	}

	public void setUserEmail(String emailString) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserEmail",emailString);
		edit.commit();
	}

	public String getUserEmail() {
		return mAppPrefs.getString("UserEmail", null);
	}

	public void setUserLocation(String location) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserLocation",location);
		edit.commit();
	}

	public void setUserLatitude(double latitude) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserLatitude", ""+latitude);
		edit.commit();
	}

	public void setUserLongitude(double longitude) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserLongitude", ""+longitude);
		edit.commit();
	}

	public void setUserCloudID(String id) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserCloudID", id);
		edit.commit();
	}

	public String getUserCloudID() {
		return mAppPrefs.getString("UserCloudID", null);
	}


	public void setUserAge(String ageString) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserAge", ageString);
		edit.commit();
	}


	public void setUserProfession(String professionString) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserProfession", professionString);
		edit.commit();
	}


	public void setUserGender(String gender) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("UserGender", gender);
		edit.commit();
	}

}
