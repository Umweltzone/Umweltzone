/*
 *  Copyright (C) 2018  Tobias Preuss
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public final class GeoPoint {

    public static final double MAX_LATITUDE = 90.0;

    public static final double MIN_LATITUDE = -90.0;

    public static final double MAX_LONGITUDE = 180.0;

    public static final double MIN_LONGITUDE = -180.0;

    public static final double INVALID_LATITUDE = MIN_LATITUDE - 1;

    public static final double INVALID_LONGITUDE = MIN_LONGITUDE - 1;

    private static final DecimalFormat df =
            new DecimalFormat("@@@@@", new DecimalFormatSymbols(Locale.US));

    // Parceler: Avoid reflection
    @SuppressWarnings("WeakerAccess")
    protected double mLatitude = INVALID_LATITUDE;

    // Parceler: Avoid reflection
    @SuppressWarnings("WeakerAccess")
    protected double mLongitude = INVALID_LONGITUDE;

    public GeoPoint() {
        // Required by Jackson to de-serialize JSON content
    }

    public GeoPoint(int latitudeE6, int longitudeE6) {
        mLatitude = latitudeE6 / 1e6;
        mLongitude = longitudeE6 / 1e6;
    }

    public GeoPoint(LatLng latLng) {
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
    }

    public GeoPoint(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    // Required by Jackson to de-serialize JSON content
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    // Required by Jackson to de-serialize JSON content
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public boolean isValid() {
        return (mLatitude > MIN_LATITUDE && mLatitude <= MAX_LATITUDE
                && mLongitude > MIN_LONGITUDE && mLongitude <= MAX_LONGITUDE);
    }

    public LatLng toLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

    public static List<LatLng> getLatLngPoints(final List<GeoPoint> geoPoints) {
        int geoPointsCount = geoPoints.size();
        final List<LatLng> latLngPoints = new ArrayList<>(geoPointsCount);
        for (int i = 0; i < geoPointsCount; i++) {
            GeoPoint point = geoPoints.get(i);
            latLngPoints.add(point.toLatLng());
        }
        return latLngPoints;
    }

    public static GeoPoint getInvalidGeoPoint() {
        return new GeoPoint(INVALID_LATITUDE, INVALID_LONGITUDE);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        GeoPoint geoPoint = (GeoPoint) other;
        return Double.compare(geoPoint.mLatitude, mLatitude) == 0 &&
                Double.compare(geoPoint.mLongitude, mLongitude) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLatitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        if (isValid()) {
            return "latitude = " + df.format(mLatitude) + ", longitude = " + df.format(mLongitude);
        } else {
            return "INVALID GeoPoint";
        }
    }

}
