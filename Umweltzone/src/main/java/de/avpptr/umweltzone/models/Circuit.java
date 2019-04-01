/*
 *  Copyright (C) 2019  Tobias Preuss
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

package de.avpptr.umweltzone.models;

import java.util.List;

import de.avpptr.umweltzone.utils.GeoPoint;

/**
 * A single polygon which either represents one zone or one part of a multi-polygon zone.
 * A typical use-case for a multi-polygon is when a low emission zone is divided into
 * two parts by a highway. While such a zone is considered one logical unit its parts
 * are rendered separately.
 */
public class Circuit {

    private List<GeoPoint> geoPointCoordinates;

    public List<GeoPoint> getCoordinates() {
        return geoPointCoordinates;
    }

    // Required by Jackson to de-serialize JSON content
    public void setCoordinates(List<GeoPoint> coordinates) {
        // Store drawable data type.
        // Avoid multiple conversion at each redraw step.
        // This object might be retrieved from cache.
        geoPointCoordinates = coordinates;
    }

    @Override
    public String toString() {
        return "number of coordinates = " + geoPointCoordinates.size();
    }

}
