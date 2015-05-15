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

    protected final StringPreference mCityNamePreference;

    protected final static GeoPoint mInvalidLocation =
            new GeoPoint(GeoPoint.INVALID_LATITUDE, GeoPoint.INVALID_LONGITUDE);

    protected final GeoPointPreference mLastKnownLocationCenterPreference;

    protected final static BoundingBox mInvalidBoundingBox =
            new BoundingBox(mInvalidLocation, mInvalidLocation);

    protected final BoundingBoxPreference mLastKnownLocationBoundingBoxPreference;

    protected final FloatPreference mZoomLevelPreference;

    protected final BooleanPreference mZoneIsDrawablePreference;

    protected final BooleanPreference mCityNameFrankfurtInPreferencesFixedPreference;


    public PreferencesHelper(final SharedPreferences sharedPreferences) {
        mCityNamePreference = new StringPreference(
                sharedPreferences, Preferences.KEY_CITY_NAME);
        mLastKnownLocationCenterPreference = new GeoPointPreference(
                sharedPreferences, Preferences.KEY_LAST_KNOWN_LOCATION_CENTER, mInvalidLocation);
        mLastKnownLocationBoundingBoxPreference = new BoundingBoxPreference(
                sharedPreferences,
                Preferences.KEY_LAST_KNOWN_LOCATION_BOUNDING_BOX,
                mInvalidBoundingBox);
        mZoomLevelPreference = new FloatPreference(
                sharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        mZoneIsDrawablePreference = new BooleanPreference(
                sharedPreferences, Preferences.KEY_ZONE_IS_DRAWABLE);
        mCityNameFrankfurtInPreferencesFixedPreference = new BooleanPreference(
                sharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
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
        mZoomLevelPreference.set(zoomLevel);
    }

    public float restoreZoomLevel() {
        return mZoomLevelPreference.get();
    }

    // Zone is drawable

    public void storeZoneIsDrawable(boolean flag) {
        mZoneIsDrawablePreference.set(flag);
    }

    public boolean restoreZoneIsDrawable() {
        return mZoneIsDrawablePreference.get();
    }

    public boolean storesZoneIsDrawable() {
        return mZoneIsDrawablePreference.isSet();
    }

    // City name Frankfurt in preferences fixed

    public void storeCityNameFrankfurtInPreferencesFixed(boolean flag) {
        mCityNameFrankfurtInPreferencesFixedPreference.set(flag);
    }

    public boolean restoreCityNameFrankfurtInPreferencesFixed() {
        return mCityNameFrankfurtInPreferencesFixedPreference.get();
    }

}
