package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PointsProvider {

    public enum Location {
        Berlin,
        Cologne,
        Frankfurt,
        Munich,
        Stuttgart
    }

    public static List<LatLng> getPoints(Location location) {
        switch (location) {
            case Berlin:
                return getPointsForBerlin();
            case Cologne:
                return getPointsForCologne();
            case Frankfurt:
                return getPointsForFrankfurt();
            case Munich:
                return getPointsForMunich();
            case Stuttgart:
                return getPointsForStuttgart();
            default:
                throw new IllegalStateException("Location " + location + " is not supported");
        }
    }

    public static List<LatLng> getPointsForBerlin() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }

    public static List<LatLng> getPointsForCologne() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }

    public static List<LatLng> getPointsForFrankfurt() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }

    public static List<LatLng> getPointsForMunich() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }

    public static List<LatLng> getPointsForStuttgart() {
        List<LatLng> points = new ArrayList<LatLng>();
        // TODO Add GPS coordinates here
        return points;
    }

}
