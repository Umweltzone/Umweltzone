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
import android.support.v4.util.LruCache;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public class PointsProvider {

    private static LruCache<String, List<LatLng>> zoneNamePointsCache;

    public static List<LatLng> getCircuitPoints(final Context context, final String zoneName) {
        if (zoneName == null) {
            throw new NullPointerException("Zone name is null.");
        }
        if (zoneNamePointsCache == null) {
            // Initialize cache
            zoneNamePointsCache = new LruCache<String, List<LatLng>>(6);
            return getAndCachePointsForZoneName(context, zoneName);
        } else {
            // Try loading from cache
            List<LatLng> points = zoneNamePointsCache.get(zoneName);
            if (points == null) {
                points = getAndCachePointsForZoneName(context, zoneName);
            }
            return points;
        }
    }

    private static List<LatLng> getAndCachePointsForZoneName(final Context context, final String zoneName) {
        final String zoneFileName = "zone_" + zoneName;
        List<GeoPoint> geoPoints = ContentProvider.getCircuitPoints(context, zoneFileName);
        if (geoPoints.size() == 0) {
            Umweltzone.getTracker().trackError(TrackingPoint.NoCircuitPointsAvailableError);
            throw new IllegalStateException("There are no circuit points available");
        }
        List<LatLng> latLngPoints = getLatLngPoints(geoPoints);
        zoneNamePointsCache.put(zoneName, latLngPoints);
        return latLngPoints;
    }

    private static List<LatLng> getLatLngPoints(final List<GeoPoint> geoPoints) {
        final List<LatLng> latLngPoints = new ArrayList<LatLng>(geoPoints.size());
        for (final GeoPoint point : geoPoints) {
            latLngPoints.add(point.toLatLng());
        }
        return latLngPoints;
    }

}
