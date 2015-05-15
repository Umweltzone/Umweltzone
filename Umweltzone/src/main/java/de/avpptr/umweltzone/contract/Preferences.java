/*
 *  Copyright (C) 2015  Tobias Preuss, Peter Vasil
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

import de.avpptr.umweltzone.BuildConfig;

public interface Preferences {

    public static final String KEY_LAST_KNOWN_LOCATION_CENTER =
            BuildConfig.APPLICATION_ID + ".LAST_KNOWN_LOCATION_CENTER";


    public static final String KEY_BOUNDING_BOX_SOUTHWEST_LATITUDE =
            BuildConfig.APPLICATION_ID + ".BOUNDING_BOX_SOUTHWEST_LATITUDE";

    public static final String KEY_BOUNDING_BOX_SOUTHWEST_LONGITUDE =
            BuildConfig.APPLICATION_ID + ".BOUNDING_BOX_SOUTHWEST_LONGITUDE";

    public static final String KEY_BOUNDING_BOX_NORTHEAST_LATITUDE =
            BuildConfig.APPLICATION_ID + ".BOUNDING_BOX_NORTHEAST_LATITUDE";

    public static final String KEY_BOUNDING_BOX_NORTHEAST_LONGITUDE =
            BuildConfig.APPLICATION_ID + ".BOUNDING_BOX_NORTHEAST_LONGITUDE";

    public static final String KEY_CITY_NAME =
            BuildConfig.APPLICATION_ID + ".CITY_NAME";

    public static final String KEY_ZOOM_LEVEL =
            BuildConfig.APPLICATION_ID + ".ZOOM_LEVEL";

    public static final String KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED =
            BuildConfig.APPLICATION_ID + ".CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED";

    public static final String KEY_ZONE_IS_DRAWABLE =
            BuildConfig.APPLICATION_ID + ".ZONE_IS_DRAWABLE";

}
