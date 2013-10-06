package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLng;

public final class GeoPoint {

    public LatLng latLng;

    public GeoPoint(int latitudeE6, int longitudeE6) {
        latLng = new LatLng(latitudeE6 / 1e6, longitudeE6 / 1e6);
    }
}
