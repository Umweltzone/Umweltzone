/*
 *  Copyright (C) 2018  Tobias Preuss
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

    String KEY_LAST_KNOWN_LOCATION_CENTER =
            BuildConfig.APPLICATION_ID + ".LAST_KNOWN_LOCATION_CENTER";

    String KEY_LAST_KNOWN_LOCATION_BOUNDING_BOX =
            BuildConfig.APPLICATION_ID + ".LAST_KNOWN_LOCATION_BOUNDING_BOX";

    String KEY_CITY_NAME =
            BuildConfig.APPLICATION_ID + ".CITY_NAME";

    String KEY_ZOOM_LEVEL =
            BuildConfig.APPLICATION_ID + ".ZOOM_LEVEL";

    String KEY_CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED =
            BuildConfig.APPLICATION_ID + ".CITY_NAME_FRANKFURT_IN_PREFERENCES_FIXED";

    String KEY_ZONE_IS_DRAWABLE =
            BuildConfig.APPLICATION_ID + ".ZONE_IS_DRAWABLE";

    String KEY_DID_PARSE_ZONE_DATA_AFTER_UPDATE_250 =
            BuildConfig.APPLICATION_ID + ".KEY_DID_PARSE_ZONE_DATA_AFTER_UPDATE_250";

    String KEY_GOOGLE_ANALYTICS_IS_ENABLED =
            BuildConfig.APPLICATION_ID + ".KEY_GOOGLE_ANALYTICS_IS_ENABLED";
}
