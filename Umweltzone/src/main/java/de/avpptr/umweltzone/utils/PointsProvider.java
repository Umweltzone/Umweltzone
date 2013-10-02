package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PointsProvider {

    public enum Location {
        Berlin
    }

    public static List<LatLng> getPoints(Location location) {
        switch (location) {
            case Berlin:
                return getPointsForBerlin();
            default:
                throw new IllegalStateException("Location " + location + " is not supported");
        }
    }

    public static List<LatLng> getPointsForBerlin() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }
}
