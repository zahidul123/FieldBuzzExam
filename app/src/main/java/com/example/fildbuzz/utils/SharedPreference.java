package com.example.fildbuzz.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {
    private SharedPreferences preferences;

    public SharedPreference(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }
    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }
}
