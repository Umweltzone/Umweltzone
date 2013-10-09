package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BoundingBox {

    private GeoPoint southWest;
    private GeoPoint northEast;
    private GeoPoint northWest;
    private GeoPoint southEast;
    private static final DecimalFormat df = new DecimalFormat(".#####", new DecimalFormatSymbols(Locale.US));


    public GeoPoint getSouthWest() {
        return southWest;
    }

    public GeoPoint getNorthEast() {
        return northEast;
    }

    public GeoPoint getNorthWest() {
        return northWest;
    }

    public GeoPoint getSouthEast() {
        return southEast;
    }

    public BoundingBox(GeoPoint southWest, GeoPoint northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
        invalidate();
    }

    public void moveBounds(double deltaLatitude, double deltaLongitude) {
        southWest = new GeoPoint(southWest.getLatitude() - deltaLatitude, southWest.getLongitude() - deltaLongitude);
        northEast = new GeoPoint(northEast.getLatitude() + deltaLatitude, northEast.getLongitude() + deltaLongitude);
        invalidate();
    }

    private void invalidate() {
        northWest = new GeoPoint(northEast.getLatitude(), southWest.getLongitude());
        southEast = new GeoPoint(southWest.getLatitude(), northEast.getLongitude());
    }

    /**
     * Returns a boolean that indicated whether the location is
     * within the bounds of this bounding box.
     *
     * @param location A location.
     * @return True, if the location is within the bounds; otherwise false.
     */
    public boolean containsLocation(GeoPoint location) {
        return (location.getLatitude() >= southWest.getLatitude() &&
                location.getLatitude() <= northEast.getLatitude() &&
                location.getLongitude() >= southWest.getLongitude() &&
                location.getLongitude() <= northEast.getLongitude());
    }

    public double getLatitudeSpan() {
        return northEast.getLatitude() - southEast.getLatitude();
    }

    public double getLongitudeSpan() {
        return southEast.getLongitude() - southWest.getLongitude();
    }

    public boolean isValid() {
        return (southWest.getLatitude() != northEast.getLatitude() &&
                southWest.getLongitude() != northEast.getLongitude());
    }

    public boolean hasMinimumSize(double minLatitudeSpan, double minLongitudeSpan) {
        return (northEast.getLatitude() - southWest.getLatitude() > minLatitudeSpan &&
                northEast.getLongitude() - southWest.getLongitude() > minLongitudeSpan);
    }

    public LatLngBounds toLatLngBounds() {
        return new LatLngBounds(southWest.toLatLng(), northEast.toLatLng());
    }

    /**
     * Returns a string of the bounding box coordinates.
     * 1) Western longitude
     * 2) Southern latitude
     * 3) Eastern longitude
     * 4) Northern latitude
     *
     * @return Concatenated coordinates.
     */
    public String getLocationString() {
        StringBuilder string = new StringBuilder();
        string.append(df.format(southWest.getLongitude())).append(",");
        string.append(df.format(southWest.getLatitude())).append(",");
        string.append(df.format(northEast.getLongitude())).append(",");
        string.append(df.format(northEast.getLatitude()));
        return string.toString();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("SW: ");
        string.append(df.format(southWest.getLatitude())).append(",");
        string.append(df.format(southWest.getLongitude())).append(" | ");
        string.append("NE: ");
        string.append(df.format(northEast.getLatitude())).append(",");
        string.append(df.format(northEast.getLongitude()));
        return string.toString();
    }

}
