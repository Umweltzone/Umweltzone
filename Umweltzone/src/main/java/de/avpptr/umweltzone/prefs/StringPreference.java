package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

public class StringPreference {
    private final SharedPreferences mPreferences;
    private final String mKey;
    private final String mDefaultValue;

    public StringPreference(SharedPreferences preferences, String key) {
        this(preferences, key, "");
    }

    public StringPreference(SharedPreferences preferences, String key, String defaultValue) {
        mPreferences = preferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public String get() {
        return mPreferences.getString(mKey, mDefaultValue);
    }

    public boolean isSet() {
        return mPreferences.contains(mKey);
    }

    public void set(String value) {
        mPreferences.edit().putString(mKey, value).commit();
    }

    public void delete() {
        mPreferences.edit().remove(mKey).commit();
    }

}
