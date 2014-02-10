package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

public class FloatPreference {
    private final SharedPreferences mPreferences;
    private final String mKey;
    private final float mDefaultValue;

    public FloatPreference(SharedPreferences preferences, String key) {
        this(preferences, key, 0);
    }

    public FloatPreference(SharedPreferences preferences, String key, float defaultValue) {
        mPreferences = preferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public float get() {
        return mPreferences.getFloat(mKey, mDefaultValue);
    }

    public boolean isSet() {
        return mPreferences.contains(mKey);
    }

    public void set(float value) {
        mPreferences.edit().putFloat(mKey, value).commit();
    }

    public void delete() {
        mPreferences.edit().remove(mKey).commit();
    }

}
