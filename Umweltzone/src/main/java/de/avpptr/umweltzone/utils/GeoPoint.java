package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLng;

public final class GeoPoint {

    protected double mLatitude;
    protected double mLongitude;

    public GeoPoint(int latitudeE6, int longitudeE6) {
        mLatitude = latitudeE6 / 1e6;
        mLongitude = longitudeE6 / 1e6;
    }

    public LatLng toLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

}
