/*
 *  Copyright (C) 2016  Tobias Preuss
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

import com.google.android.gms.maps.model.LatLngBounds;

import org.parceler.Parcel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Parcel(Parcel.Serialization.BEAN)
public class BoundingBox {

    private GeoPoint southWest;

    private GeoPoint northEast;

    private static final DecimalFormat df =
            new DecimalFormat(".#####", new DecimalFormatSymbols(Locale.US));

    public BoundingBox() {
        // Required by Jackson and Parceler to de-serialize JSON content
    }

    public BoundingBox(GeoPoint southWest, GeoPoint northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
    }

    // Required by Jackson, Parceler and ProGuard to de-serialize JSON content
    @SuppressWarnings("unused")
    public void setSouthWest(GeoPoint southWest) {
        this.southWest = southWest;
    }

    // Required by Jackson, Parceler and ProGuard to de-serialize JSON content
    @SuppressWarnings("unused")
    public void setNorthEast(GeoPoint northEast) {
        this.northEast = northEast;
    }

    public GeoPoint getSouthWest() {
        return southWest;
    }

    public GeoPoint getNorthEast() {
        return northEast;
    }

    public boolean isValid() {
        return (southWest.isValid() && northEast.isValid());
    }

    public LatLngBounds toLatLngBounds() {
        return new LatLngBounds(southWest.toLatLng(), northEast.toLatLng());
    }

    public static BoundingBox getInvalidBoundingBox() {
        GeoPoint invalidLocation = GeoPoint.getInvalidGeoPoint();
        return new BoundingBox(invalidLocation, invalidLocation);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        BoundingBox boundingBox = (BoundingBox) other;
        if (southWest != null ? !southWest.equals(boundingBox.southWest)
                : boundingBox.southWest != null) {
            return false;
        }
        return northEast != null ? northEast.equals(boundingBox.northEast)
                : boundingBox.northEast == null;
    }

    @Override
    public int hashCode() {
        int result = southWest != null ? southWest.hashCode() : 0;
        result = 31 * result + (northEast != null ? northEast.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        //noinspection StringBufferReplaceableByString
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
