package de.avpptr.umweltzone.utils;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.avpptr.umweltzone.R;

public class PointsProvider {

    private static Location currentLocation = null;
    private static List<LatLng> currentPoints = null;

    public enum Location {
        Berlin,
        Cologne,
        Frankfurt,
        Munich,
        Stuttgart
    }

    public static List<LatLng> getCircuitPoints(Context context, String cityName) {
        Location location = Converter.cityNameToLocation(cityName);
        if (location != currentLocation || currentPoints == null) {
            currentLocation = location;
            int resourceId = 0;
            switch (location) {
                case Berlin:
                    resourceId = R.raw.zone_berlin;
                    break;
                case Cologne:
                    resourceId = R.raw.zone_cologne;
                    break;
                case Frankfurt:
                    resourceId = R.raw.zone_frankfurt_am_main;
                    break;
                case Munich:
                    resourceId = R.raw.zone_munich;
                    break;
                case Stuttgart:
                    resourceId = R.raw.zone_stuttgart;
                    break;
            }
            if (resourceId == 0) {
                throw new IllegalStateException("Location " + location + " is not supported");
            }
            List<GeoPoint> points = ContentProvider.getCircuitPoints(context, resourceId);
            currentPoints = new ArrayList<LatLng>();
            for (int i = 0; i < points.size(); i++) {
                currentPoints.add(points.get(i).toLatLng());
            }
            if (currentPoints.size() == 0) {
                throw new IllegalStateException("There are no circuit points available");
            }
        }
        return currentPoints;
    }

}
