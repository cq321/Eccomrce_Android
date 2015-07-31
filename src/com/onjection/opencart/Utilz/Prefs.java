package com.onjection.opencart.Utilz;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
	static Context mContext;

	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"OpenCart", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("OpenCart",
				Context.MODE_PRIVATE);
		String position = prefs.getString(key, "");
		return position;
	}

}
