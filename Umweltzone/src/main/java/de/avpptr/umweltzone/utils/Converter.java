package de.avpptr.umweltzone.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.android.gms.maps.model.LatLng;

import de.avpptr.umweltzone.R;

public class Converter {

    // Tip for how to obtain an array of arrays from an android resource:
    // http://stackoverflow.com/a/5931094/464831
    private static int[][] getGeographicLocations(Resources resources) {
        TypedArray ta = resources.obtainTypedArray(R.array.geographic_locations);
        int n = ta.length();
        int[][] geographicLocations = new int[n][];

        for (int i = 0; i < n; i++) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                geographicLocations[i] = resources.getIntArray(id);
            } else {
                // something wrong with the XML
                throw new IllegalAccessError("Something is wrong with the XML.");
            }
        }
        ta.recycle(); // Important!

        return geographicLocations;
    }

    public static LatLng cityNameToLatLng(Resources resources, String cityName) {
        String[] cityNames = resources.getStringArray(R.array.city_names_values);
        int[][] cityCentres = getGeographicLocations(resources);
        if (cityCentres.length != cityNames.length) {
            throw new IllegalArgumentException("City names and geographic location sizes differ.");
        }

        for (int i = 0; i < cityNames.length; i++) {
            if (cityName.equalsIgnoreCase(cityNames[i])) {
                if (cityCentres[i].length != 2) {
                    throw new IllegalArgumentException("A geographic location should have 2 values.");
                }
                GeoPoint cityCentre = new GeoPoint(
                        cityCentres[i][0],
                        cityCentres[i][1]
                );
                return cityCentre.latLng;
            }
        }
        return null;
    }
}
