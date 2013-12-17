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

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.contract.Resources;

public class PointsProvider {

    private static Location currentLocation = null;
    private static List<LatLng> currentPoints = null;

    public enum Location {
        Berlin,
        Bremen,
        Cologne,
        Duesseldorf,
        Frankfurt,
        Hannover,
        Leipzig,
        Munich,
        Stuttgart
    }

    public static List<LatLng> getCircuitPoints(Context context, String cityName) {
        Location location = Converter.cityNameToLocation(cityName);
        if (location == null) {
            throw new IllegalStateException("City name `" + cityName + "` " +
                    "has not been converted into location.");
        }
        if (location != currentLocation || currentPoints == null) {
            currentLocation = location;
            int resourceId = Resources.INVALID_RESOURCE_ID;
            switch (location) {
                case Berlin:
                    resourceId = R.raw.zone_berlin;
                    break;
                case Bremen:
                    resourceId = R.raw.zone_bremen;
                    break;
                case Cologne:
                    resourceId = R.raw.zone_cologne;
                    break;
                case Duesseldorf:
                    resourceId = R.raw.zone_duesseldorf;
                    break;
                case Frankfurt:
                    resourceId = R.raw.zone_frankfurt_am_main;
                    break;
                case Hannover:
                    resourceId = R.raw.zone_hannover;
                    break;
                case Leipzig:
                    resourceId = R.raw.zone_leipzig;
                    break;
                case Munich:
                    resourceId = R.raw.zone_munich;
                    break;
                case Stuttgart:
                    resourceId = R.raw.zone_stuttgart;
                    break;
            }
            if (resourceId == Resources.INVALID_RESOURCE_ID) {
                Umweltzone.getTracker().trackError(TrackingPoint.LocationNotSupportedError);
                throw new IllegalStateException("Location " + location + " is not supported");
            }
            List<GeoPoint> points = ContentProvider.getCircuitPoints(context, resourceId);
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
