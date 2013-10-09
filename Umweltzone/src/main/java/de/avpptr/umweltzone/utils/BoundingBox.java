package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BoundingBox {

    private GeoPoint southWest;
    private GeoPoint northEast;
    private static final DecimalFormat df = new DecimalFormat(".#####", new DecimalFormatSymbols(Locale.US));

    public BoundingBox(GeoPoint southWest, GeoPoint northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
    }

    public LatLngBounds toLatLngBounds() {
        return new LatLngBounds(southWest.toLatLng(), northEast.toLatLng());
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
