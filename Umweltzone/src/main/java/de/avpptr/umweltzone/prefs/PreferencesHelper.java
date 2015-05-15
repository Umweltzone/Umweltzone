/*
 *  Copyright (C) 2015  Tobias Preuss
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

import de.avpptr.umweltzone.contract.Preferences;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;
import info.metadude.android.typedpreferences.BooleanPreference;
import info.metadude.android.typedpreferences.FloatPreference;
import info.metadude.android.typedpreferences.StringPreference;

public class PreferencesHelper {

    private final SharedPreferences mSharedPreferences;

    protected final StringPreference mCityNamePreference;

    protected final static GeoPoint mInvalidLocation =
            new GeoPoint(GeoPoint.INVALID_LATITUDE, GeoPoint.INVALID_LONGITUDE);

    protected final GeoPointPreference mLastKnownLocationCenterPreference;

    protected final static BoundingBox mInvalidBoundingBox =
            new BoundingBox(mInvalidLocation, mInvalidLocation);

    protected final BoundingBoxPreference mLastKnownLocationBoundingBoxPreference;


    public PreferencesHelper(final SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mCityNamePreference = new StringPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME);
        mLastKnownLocationCenterPreference = new GeoPointPreference(
                mSharedPreferences, Preferences.KEY_LAST_KNOWN_LOCATION_CENTER, mInvalidLocation);
        mLastKnownLocationBoundingBoxPreference = new BoundingBoxPreference(
                mSharedPreferences,
                Preferences.KEY_LAST_KNOWN_LOCATION_BOUNDING_BOX,
                mInvalidBoundingBox);
    }

    // Last known location / city name

    public void storeLastKnownLocation(final String cityName) {
        mCityNamePreference.set(cityName);
    }

    public String restoreLastKnownLocationAsString() {
        return mCityNamePreference.get();
    }

    public boolean storesLastKnownLocation() {
        return mCityNamePreference.isSet();
    }

    // Last known location / center

    public void storeLastKnownLocation(final GeoPoint center) {
        mLastKnownLocationCenterPreference.set(center);
    }

    public GeoPoint restoreLastKnownLocationAsGeoPoint() {
        return mLastKnownLocationCenterPreference.get();
    }

    // Last known location / bounding box

    public void storeLastKnownLocation(final BoundingBox boundingBox) {
        mLastKnownLocationBoundingBoxPreference.set(boundingBox);
    }

    public BoundingBox restoreLastKnownLocationAsBoundingBox() {
        return mLastKnownLocationBoundingBoxPreference.get();
    }

    // Zoom level

    public void storeZoomLevel(float zoomLevel) {
        FloatPreference zoomLevelPreference = new FloatPreference(
                mSharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        zoomLevelPreference.set(zoomLevel);
    }

    public float restoreZoomLevel() {
        FloatPreference zoomLevelPreference = new FloatPreference(
                mSharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        return zoomLevelPreference.get();
    }

    // Zone is drawable

    public void storeZoneIsDrawable(boolean flag) {
        BooleanPreference zoneIsDrawablePreference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_ZONE_IS_DRAWABLE);
        zoneIsDrawablePreference.set(flag);
    }

    public boolean restoreZoneIsDrawable() {
        BooleanPreference zoneIsDrawablePreference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_ZONE_IS_DRAWABLE);
        return zoneIsDrawablePreference.get();
    }

    public boolean storesZoneIsDrawable() {
        BooleanPreference zoneIsDrawablePreference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_ZONE_IS_DRAWABLE);
        return zoneIsDrawablePreference.isSet();
    }

    // City name Frankfurt in preferences fixed

    public void storeCityNameFrankfurtInPreferencesFixed(boolean flag) {
        BooleanPreference preference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
        preference.set(flag);
    }

    public boolean restoreCityNameFrankfurtInPreferencesFixed() {
        BooleanPreference preference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
        return preference.get();
    }

}
