package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

public class BooleanPreference {
    private final SharedPreferences mPreferences;
    private final String mKey;
    private final boolean mDefaultValue;

    public BooleanPreference(SharedPreferences preferences, String key) {
        this(preferences, key, false);
    }

    public BooleanPreference(SharedPreferences preferences, String key, boolean defaultValue) {
        mPreferences = preferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public boolean get() {
        return mPreferences.getBoolean(mKey, mDefaultValue);
    }

    public boolean isSet() {
        return mPreferences.contains(mKey);
    }

    public void set(boolean value) {
        mPreferences.edit().putBoolean(mKey, value).commit();
    }

    public void delete() {
        mPreferences.edit().remove(mKey).commit();
    }

}
