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

    protected final GeoPointPreference mLastKnownLocationCenterPreference;

    protected final BoundingBoxPreference mLastKnownLocationBoundingBoxPreference;

    protected final FloatPreference mZoomLevelPreference;

    protected final BooleanPreference mZoneIsDrawablePreference;

    protected final BooleanPreference mCityNameFrankfurtInPreferencesFixedPreference;

    protected final BooleanPreference mDidParseZoneDataAfterUpdate250Preference;


    public PreferencesHelper(final SharedPreferences sharedPreferences) {
        mCityNamePreference = new StringPreference(
                sharedPreferences, Preferences.KEY_CITY_NAME);
        mLastKnownLocationCenterPreference = new GeoPointPreference(
                sharedPreferences, Preferences.KEY_LAST_KNOWN_LOCATION_CENTER);
        mLastKnownLocationBoundingBoxPreference = new BoundingBoxPreference(
                sharedPreferences, Preferences.KEY_LAST_KNOWN_LOCATION_BOUNDING_BOX);
        mZoomLevelPreference = new FloatPreference(
                sharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        mZoneIsDrawablePreference = new BooleanPreference(
                sharedPreferences, Preferences.KEY_ZONE_IS_DRAWABLE);
        mCityNameFrankfurtInPreferencesFixedPreference = new BooleanPreference(
                sharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
        mDidParseZoneDataAfterUpdate250Preference = new BooleanPreference(
                sharedPreferences, Preferences.KEY_DID_PARSE_ZONE_DATA_AFTER_UPDATE_250);
    }

    // Last known location / city name

    public void storeLastKnownLocationAsString(final String cityName) {
        mCityNamePreference.set(cityName);
    }

    public String restoreLastKnownLocationAsString() {
        return mCityNamePreference.get();
    }

    public boolean storesLastKnownLocationAsString() {
        return mCityNamePreference.isSet();
    }

    public void deleteLastKnownLocationAsString() {
        mCityNamePreference.delete();
    }

    // Last known location / center

    public void storeLastKnownLocationAsGeoPoint(final GeoPoint center) {
        mLastKnownLocationCenterPreference.set(center);
    }

    public GeoPoint restoreLastKnownLocationAsGeoPoint() {
        return mLastKnownLocationCenterPreference.get();
    }

    public void deleteLastKnownLocationAsGeoPoint() {
        mLastKnownLocationCenterPreference.delete();
    }

    // Last known location / bounding box

    public void storeLastKnownLocationAsBoundingBox(final BoundingBox boundingBox) {
        mLastKnownLocationBoundingBoxPreference.set(boundingBox);
    }

    public BoundingBox restoreLastKnownLocationAsBoundingBox() {
        return mLastKnownLocationBoundingBoxPreference.get();
    }

    public void deleteLastKnownLocationAsBoundingBox() {
        mLastKnownLocationBoundingBoxPreference.delete();
    }

    // Zoom level

    public void storeZoomLevel(float zoomLevel) {
        mZoomLevelPreference.set(zoomLevel);
    }

    public float restoreZoomLevel() {
        return mZoomLevelPreference.get();
    }

    public void deleteZoomLevel() {
        mZoomLevelPreference.delete();
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

    public void deleteZoneIsDrawable() {
        mZoneIsDrawablePreference.delete();
    }

    // City name Frankfurt in preferences fixed

    public void storeCityNameFrankfurtInPreferencesFixed(boolean flag) {
        mCityNameFrankfurtInPreferencesFixedPreference.set(flag);
    }

    public boolean restoreCityNameFrankfurtInPreferencesFixed() {
        return mCityNameFrankfurtInPreferencesFixedPreference.get();
    }

    // Enforce parsing zone data at first start after update to v.2.5.0

    public void storeDidParseZoneDataAfterUpdate250(boolean flag) {
        mDidParseZoneDataAfterUpdate250Preference.set(flag);
    }

    public boolean restoreDidParseZoneDataAfterUpdate250() {
        return mDidParseZoneDataAfterUpdate250Preference.get();
    }

    // Smart helper methods

    public void deleteLastKnownLocation() {
        deleteLastKnownLocationAsString();
        deleteLastKnownLocationAsBoundingBox();
        deleteLastKnownLocationAsGeoPoint();
        deleteZoomLevel();
        deleteZoneIsDrawable();
    }

}
