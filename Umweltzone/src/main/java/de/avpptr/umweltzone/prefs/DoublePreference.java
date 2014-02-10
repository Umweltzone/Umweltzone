package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

public class DoublePreference {
    private final SharedPreferences mPreferences;
    private final String mKey;
    private final double mDefaultValue;

    public DoublePreference(SharedPreferences preferences, String key) {
        this(preferences, key, 0);
    }

    public DoublePreference(SharedPreferences preferences, String key, double defaultValue) {
        mPreferences = preferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public double get() {
        long defaultValue = Double.doubleToLongBits(mDefaultValue);
        long aLong = mPreferences.getLong(mKey, defaultValue);
        return Double.longBitsToDouble(aLong);
    }

    public boolean isSet() {
        return mPreferences.contains(mKey);
    }

    public void set(double value) {
        long aLong = Double.doubleToLongBits(value);
        mPreferences.edit().putLong(mKey, aLong).commit();
    }

    public void delete() {
        mPreferences.edit().remove(mKey).commit();
    }

}
