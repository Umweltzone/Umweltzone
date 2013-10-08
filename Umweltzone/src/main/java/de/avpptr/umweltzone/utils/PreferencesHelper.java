package de.avpptr.umweltzone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import de.avpptr.umweltzone.contract.Preferences;

public class PreferencesHelper {

    public static void storeLastKnownLocation(Context context, GeoPoint center) {
        storeLastKnownLocation(getEditor(context), center);
    }

    public static GeoPoint restoreLastKnownLocation(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        double lat = Double.longBitsToDouble(sharedPreferences.getLong(Preferences.KEY_CENTER_LATITUDE, 0));
        double lon = Double.longBitsToDouble(sharedPreferences.getLong(Preferences.KEY_CENTER_LONGITUDE, 0));
        return new GeoPoint(lat, lon);
    }

    private static void storeLastKnownLocation(SharedPreferences.Editor editor, GeoPoint center) {
        editor.putLong(Preferences.KEY_CENTER_LATITUDE, Double.doubleToLongBits(center.getLatitude()));
        editor.putLong(Preferences.KEY_CENTER_LONGITUDE, Double.doubleToLongBits(center.getLongitude()));
        editor.commit();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Preferences.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

}
