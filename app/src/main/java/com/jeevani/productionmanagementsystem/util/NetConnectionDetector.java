package com.jeevani.productionmanagementsystem.util;

import java.net.ContentHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnectionDetector {

	private Context context;

	public static NetConnectionDetector getInstance(Context context) {
		NetConnectionDetector netConnectionDetector = new NetConnectionDetector(context);
		return netConnectionDetector;
	}
	
	NetConnectionDetector(Context context) {
		this.context = context;
	}
	

	public boolean isConntectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
			}
		}
		return false;
	}

}
