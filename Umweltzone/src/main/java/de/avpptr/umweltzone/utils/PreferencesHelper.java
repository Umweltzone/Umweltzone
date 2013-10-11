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

    public static void storeZoomLevel(Context context, float zoomLevel) {
        storeZoomLevel(getEditor(context), zoomLevel);
    }

    public static GeoPoint restoreLastKnownLocationAsGeoPoint(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        double lat = Double.longBitsToDouble(sharedPreferences.getLong(Preferences.KEY_CENTER_LATITUDE, 0));
        double lon = Double.longBitsToDouble(sharedPreferences.getLong(Preferences.KEY_CENTER_LONGITUDE, 0));
        return new GeoPoint(lat, lon);
    }

    public static BoundingBox restoreLastKnownLocationAsBoundingBox(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        double southWestLatitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE, 0));
        double southWestLongitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE, 0));
        double northEastLatitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LATITUDE, 0));
        double northEastLongitude = Double.longBitsToDouble(
                sharedPreferences.getLong(Preferences.KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE, 0));
        GeoPoint southWest = new GeoPoint(southWestLatitude, southWestLongitude);
        GeoPoint northEast = new GeoPoint(northEastLatitude, northEastLongitude);
        return new BoundingBox(southWest, northEast);
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
