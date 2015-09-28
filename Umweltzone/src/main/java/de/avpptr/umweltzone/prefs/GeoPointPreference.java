package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

import de.avpptr.umweltzone.utils.GeoPoint;
import info.metadude.android.typedpreferences.DoublePreference;

public class GeoPointPreference {

    protected final DoublePreference mLatitudePreference;

    protected final DoublePreference mLongitudePreference;

    public GeoPointPreference(
            final SharedPreferences sharedPreferences,
            final String key,
            final GeoPoint defaultLocation) {
        String keyLatitude = key + ".LATITUDE";
        String keyLongitude = key + ".LONGITUDE";
        mLatitudePreference = new DoublePreference(
                sharedPreferences, keyLatitude, defaultLocation.getLatitude());
        mLongitudePreference = new DoublePreference(
                sharedPreferences, keyLongitude, defaultLocation.getLongitude());
    }

    public GeoPointPreference(
            final SharedPreferences sharedPreferences,
            final String key) {
        this(sharedPreferences, key, GeoPoint.getInvalidGeoPoint());
    }

    public GeoPoint get() {
        double latitude = mLatitudePreference.get();
        double longitude = mLongitudePreference.get();
        return new GeoPoint(latitude, longitude);
    }

    public boolean isSet() {
        return mLatitudePreference.isSet() && mLongitudePreference.isSet();
    }

    public void set(final GeoPoint location) {
        mLatitudePreference.set(location.getLatitude());
        mLongitudePreference.set(location.getLongitude());
    }

    public void delete() {
        mLatitudePreference.delete();
        mLongitudePreference.delete();
    }

}
