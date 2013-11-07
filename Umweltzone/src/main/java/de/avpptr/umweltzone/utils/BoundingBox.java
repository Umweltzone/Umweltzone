/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BoundingBox {

    private GeoPoint southWest;
    private GeoPoint northEast;
    private static final DecimalFormat df = new DecimalFormat(".#####", new DecimalFormatSymbols(Locale.US));

    public BoundingBox() {
        // Required by Jackson to de-serialize JSON content
    }

    public BoundingBox(GeoPoint southWest, GeoPoint northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
    }

    // Required by Jackson and ProGuard to de-serialize JSON content
    public void setSouthWest(GeoPoint southWest) {
        this.southWest = southWest;
    }

    // Required by Jackson and ProGuard to de-serialize JSON content
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
