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

package de.avpptr.umweltzone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import de.avpptr.umweltzone.contract.Preferences;

public class PreferencesHelper {

    public static void storeLastKnownLocation(Context context, GeoPoint center) {
        storeLastKnownLocation(getEditor(context), center);
    }

    public static void storeLastKnownLocation(Context context, BoundingBox boundingBox) {
        storeLastKnownLocation(getEditor(context), boundingBox);
    }

    public static void storeLastKnownLocation(Context context, String cityName) {
        storeLastKnownLocation(getEditor(context), cityName);
    }

    public static void storeZoomLevel(Context context, float zoomLevel) {
        storeZoomLevel(getEditor(context), zoomLevel);
    }

    public static GeoPoint restoreLastKnownLocationAsGeoPoint(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        double lat = Double.longBitsToDouble(sharedPreferences.getLong(
                Preferences.KEY_CENTER_LATITUDE,
                Double.doubleToLongBits(Double.NaN)));
        double lon = Double.longBitsToDouble(sharedPreferences.getLong(
                Preferences.KEY_CENTER_LONGITUDE,
                Double.doubleToLongBits(Double.NaN)));
        return new GeoPoint(lat, lon);
    }

    public static BoundingBox restoreLastKnownLocationAsBoundingBox(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        double southWestLatitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE,
                        Double.doubleToLongBits(Double.NaN)));
        double southWestLongitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE,
                        Double.doubleToLongBits(Double.NaN)));
        double northEastLatitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LATITUDE,
                        Double.doubleToLongBits(Double.NaN)));
        double northEastLongitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE,
                        Double.doubleToLongBits(Double.NaN)));
        GeoPoint southWest = new GeoPoint(southWestLatitude, southWestLongitude);
        GeoPoint northEast = new GeoPoint(northEastLatitude, northEastLongitude);
        return new BoundingBox(southWest, northEast);
    }

    public static String restoreLastKnownLocationAsString(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(Preferences.KEY_CITY_NAME, "");
    }

    public static float restoreZoomLevel(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getFloat(Preferences.KEY_ZOOM_LEVEL, 0);
    }

    private static void storeLastKnownLocation(SharedPreferences.Editor editor, GeoPoint center) {
        editor.putLong(Preferences.KEY_CENTER_LATITUDE, Double.doubleToLongBits(center.getLatitude()));
        editor.putLong(Preferences.KEY_CENTER_LONGITUDE, Double.doubleToLongBits(center.getLongitude()));
        editor.commit();
    }

    private static void storeLastKnownLocation(SharedPreferences.Editor editor, BoundingBox boundingBox) {
        GeoPoint southWest = boundingBox.getSouthWest();
        editor.putLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE,
                Double.doubleToLongBits(southWest.getLatitude()));
        editor.putLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE,
                Double.doubleToLongBits(southWest.getLongitude()));
        GeoPoint northEast = boundingBox.getNorthEast();
        editor.putLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LATITUDE,
                Double.doubleToLongBits(northEast.getLatitude()));
        editor.putLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE,
                Double.doubleToLongBits(northEast.getLongitude()));
        editor.commit();
    }

    private static void storeLastKnownLocation(SharedPreferences.Editor editor, String cityName) {
        editor.putString(Preferences.KEY_CITY_NAME, cityName);
        editor.commit();
    }

    private static void storeZoomLevel(SharedPreferences.Editor editor, float zoomLevel) {
        editor.putFloat(Preferences.KEY_ZOOM_LEVEL, zoomLevel);
        editor.commit();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Preferences.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

}
