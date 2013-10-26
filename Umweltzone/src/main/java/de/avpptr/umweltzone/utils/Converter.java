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

import android.content.res.Resources;
import android.content.res.TypedArray;

public class Converter {

    // Tip for how to obtain an array of arrays from an android resource:
    // http://stackoverflow.com/a/5931094/464831
    private static int[][] getGeographicLocations(Resources resources, int resourceId) {
        TypedArray ta = resources.obtainTypedArray(resourceId);
        int n = ta.length();
        int[][] geographicLocations = new int[n][];

        for (int i = 0; i < n; i++) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                geographicLocations[i] = resources.getIntArray(id);
            } else {
                // something wrong with the XML
                throw new IllegalAccessError("Something is wrong with the XML.");
            }
        }
        ta.recycle(); // Important!

        return geographicLocations;
    }

    // TODO Redundant definition: City names are already present in JSON file
    public static PointsProvider.Location cityNameToLocation(String cityName) {
        PointsProvider.Location location = null;
        if (cityName.equalsIgnoreCase("Berlin")) {
            location = PointsProvider.Location.Berlin;
        } else if (cityName.equalsIgnoreCase("Frankfurt")) {
            location = PointsProvider.Location.Frankfurt;
        } else if (cityName.equalsIgnoreCase("Munich")) {
            location = PointsProvider.Location.Munich;
        } else if (cityName.equalsIgnoreCase("Stuttgart")) {
            location = PointsProvider.Location.Stuttgart;
        } else if (cityName.equalsIgnoreCase("Cologne")) {
            location = PointsProvider.Location.Cologne;
        }
        return location;
    }
}
