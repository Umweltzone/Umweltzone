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

package de.avpptr.umweltzone.contract;

public interface Preferences {
    public static final String SHARED_PREFERENCES = "de.avpptr.umweltzone.SHARED_PREFERENCES";
    public static final String KEY_CENTER_LATITUDE = "de.avpptr.umweltzone.CENTER_LATITUDE";
    public static final String KEY_CENTER_LONGITUDE = "de.avpptr.umweltzone.CENTER_LONGITUDE";
    public static final String KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE =
            "de.avpptr.umweltzone.BOUNDING_BOX_SOUTHWEST_LATITUDE";
    public static final String KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE =
            "de.avpptr.umweltzone.BOUNDING_BOX_SOUTHWEST_LONGITUDE";
    public static final String KEY_BOUNDING_BOX_NORTHEAST_LATITUDE =
            "de.avpptr.umweltzone.BOUNDING_BOX_NORTHEAST_LATITUDE";
    public static final String KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE =
            "de.avpptr.umweltzone.BOUNDING_BOX_NORTHEAST_LONGITUDE";
    public static final String KEY_CITY_NAME = "de.avpptr.umweltzone.CITY_NAME";
    public static final String KEY_ZOOM_LEVEL = "de.avpptr.umweltzone.ZOOM_LEVEL";
}
