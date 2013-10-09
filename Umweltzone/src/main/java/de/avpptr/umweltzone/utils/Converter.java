package de.avpptr.umweltzone.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;

import de.avpptr.umweltzone.R;

public class Converter {

    // Tip for how to obtain an array of arrays from an android resource:
    // http://stackoverflow.com/a/5931094/464831
    private static int[][] getGeographicLocations(Resources resources, int resourceId) {
        TypedArray ta = resources.obtainTypedArray(resourceId);
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

    public static BoundingBox cityNameToBoundingBox(Resources resources, String cityName) {
        String[] cityNames = resources.getStringArray(R.array.city_names_values);
        int[][] zoneBounds = getGeographicLocations(resources, R.array.low_emmision_zone_bboxes);
        if (zoneBounds.length != cityNames.length) {
            throw new IllegalArgumentException("City names and zone bboxes sizes differ.");
        }

        for (int i = 0; i < cityNames.length; i++) {
            if (cityName.equalsIgnoreCase(cityNames[i])) {
                if (zoneBounds[i].length != 4) {
                    throw new IllegalArgumentException("A bounding box should have 4 values.");
                }
                GeoPoint southwest = new GeoPoint(
                        zoneBounds[i][0],
                        zoneBounds[i][1]
                );
                GeoPoint northeast = new GeoPoint(
                        zoneBounds[i][2],
                        zoneBounds[i][3]
                );
                return new BoundingBox(southwest, northeast);
            }
        }
        return null;
    }

    public static PointsProvider.Location cityNameToLocation(String cityName) {
        PointsProvider.Location location = null;
        if (cityName.equalsIgnoreCase("Berlin")) {
            location = PointsProvider.Location.Berlin;
        } else if (cityName.equalsIgnoreCase("Frankfurt")) {
            location = PointsProvider.Location.Frankfurt;
        } else if (cityName.equalsIgnoreCase("Munich")) {
            location = PointsProvider.Location.Munich;
        } else if (cityName.equalsIgnoreCase("Stuttgart")) {
            location = PointsProvider.Location.Stuttgart;
        } else if (cityName.equalsIgnoreCase("Cologne")) {
            location = PointsProvider.Location.Cologne;
        }
        return location;
    }
}
