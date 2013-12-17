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

public class Converter {

    // TODO Redundant definition: City names are already present in JSON file
    public static PointsProvider.Location cityNameToLocation(String cityName) {
        PointsProvider.Location location = null;
        if (cityName.equalsIgnoreCase("Berlin")) {
            location = PointsProvider.Location.Berlin;
        } else if (cityName.equalsIgnoreCase("Cologne")) {
            location = PointsProvider.Location.Cologne;
        } else if (cityName.equalsIgnoreCase("Frankfurt")) {
            location = PointsProvider.Location.Frankfurt;
        } else if (cityName.equalsIgnoreCase("Munich")) {
            location = PointsProvider.Location.Munich;
        } else if (cityName.equalsIgnoreCase("Stuttgart")) {
            location = PointsProvider.Location.Stuttgart;
        }
        return location;
    }
}
