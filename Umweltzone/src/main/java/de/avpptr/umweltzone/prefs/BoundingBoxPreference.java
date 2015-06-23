package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;

public class BoundingBoxPreference {

    protected final GeoPointPreference mSouthWestPreference;

    protected final GeoPointPreference mNorthEastPreference;

    public BoundingBoxPreference(
            final SharedPreferences sharedPreferences,
            final String key,
            final BoundingBox defaultBoundingBox) {
        String keySouthWest = key + ".SOUTHWEST";
        String keyNorthEast = key + ".NORTHEAST";
        mSouthWestPreference = new GeoPointPreference(
                sharedPreferences, keySouthWest, defaultBoundingBox.getSouthWest());
        mNorthEastPreference = new GeoPointPreference(
                sharedPreferences, keyNorthEast, defaultBoundingBox.getNorthEast());
    }

    public BoundingBox get() {
        GeoPoint southWest = mSouthWestPreference.get();
        GeoPoint northEast = mNorthEastPreference.get();
        return new BoundingBox(southWest, northEast);
    }

    public boolean isSet() {
        return mSouthWestPreference.isSet() && mNorthEastPreference.isSet();
    }

    public void set(final BoundingBox location) {
        mSouthWestPreference.set(location.getSouthWest());
        mNorthEastPreference.set(location.getNorthEast());
    }

    public void delete() {
        mSouthWestPreference.delete();
        mNorthEastPreference.delete();
    }

}
