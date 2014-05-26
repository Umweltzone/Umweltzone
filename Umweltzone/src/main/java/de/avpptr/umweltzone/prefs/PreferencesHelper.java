/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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
import info.metadude.android.typedpreferences.DoublePreference;
import info.metadude.android.typedpreferences.FloatPreference;
import info.metadude.android.typedpreferences.StringPreference;

public class PreferencesHelper {

    private final SharedPreferences mSharedPreferences;

    public PreferencesHelper(final SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public GeoPoint restoreLastKnownLocationAsGeoPoint() {
        DoublePreference latitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_CENTER_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference longitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_CENTER_LONGITUDE, GeoPoint.INVALID_LONGITUDE);
        double lat = latitudePreference.get();
        double lon = longitudePreference.get();
        return new GeoPoint(lat, lon);
    }

    public BoundingBox restoreLastKnownLocationAsBoundingBox() {
        DoublePreference southWestLatitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference southWestLongitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE, GeoPoint.INVALID_LONGITUDE);
        DoublePreference northEastLatitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_NORTHEAST_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference northEastLongitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE, GeoPoint.INVALID_LONGITUDE);

        double southWestLatitude = southWestLatitudePreference.get();
        double southWestLongitude = southWestLongitudePreference.get();
        double northEastLatitude = northEastLatitudePreference.get();
        double northEastLongitude = northEastLongitudePreference.get();
        GeoPoint southWest = new GeoPoint(southWestLatitude, southWestLongitude);
        GeoPoint northEast = new GeoPoint(northEastLatitude, northEastLongitude);
        return new BoundingBox(southWest, northEast);
    }

    public String restoreLastKnownLocationAsString() {
        StringPreference cityNamePreference = new StringPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME);
        return cityNamePreference.get();
    }

    public float restoreZoomLevel() {
        FloatPreference zoomLevelPreference = new FloatPreference(
                mSharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        return zoomLevelPreference.get();
    }

    public boolean restoreCityNameFrankfurtInPreferencesFixed() {
        BooleanPreference preference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
        return preference.get();
    }

    public void storeLastKnownLocation(final GeoPoint center) {
        DoublePreference latitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_CENTER_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference longitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_CENTER_LONGITUDE, GeoPoint.INVALID_LONGITUDE);
        latitudePreference.set(center.getLatitude());
        longitudePreference.set(center.getLongitude());
    }

    public void storeLastKnownLocation(final BoundingBox boundingBox) {
        DoublePreference southWestLatitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference southWestLongitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE, GeoPoint.INVALID_LONGITUDE);
        DoublePreference northEastLatitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_NORTHEAST_LATITUDE, GeoPoint.INVALID_LATITUDE);
        DoublePreference northEastLongitudePreference = new DoublePreference(mSharedPreferences,
                Preferences.KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE, GeoPoint.INVALID_LONGITUDE);

        GeoPoint southWest = boundingBox.getSouthWest();
        southWestLatitudePreference.set(southWest.getLatitude());
        southWestLongitudePreference.set(southWest.getLongitude());
        GeoPoint northEast = boundingBox.getNorthEast();
        northEastLatitudePreference.set(northEast.getLatitude());
        northEastLongitudePreference.set(northEast.getLongitude());
    }

    public void storeLastKnownLocation(final String cityName) {
        StringPreference cityNamePreference = new StringPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME);
        cityNamePreference.set(cityName);
    }

    public boolean storesLastKnownLocation() {
        StringPreference cityNamePreference = new StringPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME);
        return cityNamePreference.isSet();
    }

    public void storeZoomLevel(float zoomLevel) {
        FloatPreference zoomLevelPreference = new FloatPreference(
                mSharedPreferences, Preferences.KEY_ZOOM_LEVEL);
        zoomLevelPreference.set(zoomLevel);
    }

    public void storeCityNameFrankfurtInPreferencesFixed(boolean flag) {
        BooleanPreference preference = new BooleanPreference(
                mSharedPreferences, Preferences.KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED);
        preference.set(flag);
    }

}
