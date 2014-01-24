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

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public class PointsProvider {

    private static String currentZoneName = null;
    private static List<LatLng> currentPoints = null;

    public static List<LatLng> getCircuitPoints(final Context context, final String zoneName) {
        if (zoneName == null) {
            throw new NullPointerException("Zone name is null.");
        }
        if (!zoneName.equals(currentZoneName) || currentPoints == null) {
            currentZoneName = zoneName;
            final String zoneFileName = "zone_" + zoneName;
            List<GeoPoint> points = ContentProvider.getCircuitPoints(context, zoneFileName);
            currentPoints = new ArrayList<LatLng>();
            for (GeoPoint point : points) {
                currentPoints.add(point.toLatLng());
            }
            if (currentPoints.size() == 0) {
                Umweltzone.getTracker().trackError(TrackingPoint.NoCircuitPointsAvailableError);
                throw new IllegalStateException("There are no circuit points available");
            }
        }
        return currentPoints;
    }

}
