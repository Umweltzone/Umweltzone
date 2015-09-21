/*
 *  Copyright (C) 2015  Tobias Preuss
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

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.contract.Resources;

public abstract class LowEmissionZoneNumberConverter {

    @DrawableRes
    public static int getStatusDrawable(@LowEmissionZoneNumbers.Color int zoneNumber) {
        switch (zoneNumber) {
            case LowEmissionZoneNumbers.RED:
                return R.drawable.umweltzone_status_2;
            case LowEmissionZoneNumbers.YELLOW:
                return R.drawable.umweltzone_status_3;
            case LowEmissionZoneNumbers.GREEN:
                return R.drawable.umweltzone_status_4;
        }
        return Resources.INVALID_RESOURCE_ID;
    }

    @ColorRes
    public static int getColor(@LowEmissionZoneNumbers.Color int zoneNumber) {
        switch (zoneNumber) {
            case LowEmissionZoneNumbers.RED:
                return R.color.city_zone_2;
            case LowEmissionZoneNumbers.YELLOW:
                return R.color.city_zone_3;
            case LowEmissionZoneNumbers.GREEN:
                return R.color.city_zone_4;
        }
        return R.color.city_zone_none;
    }

    @StringRes
    public static int getColorString(@LowEmissionZoneNumbers.Color int zoneNumber) {
        switch (zoneNumber) {
            case LowEmissionZoneNumbers.RED:
                return R.string.city_info_zone_number_since_text_fragment_red;
            case LowEmissionZoneNumbers.YELLOW:
                return R.string.city_info_zone_number_since_text_fragment_yellow;
            case LowEmissionZoneNumbers.GREEN:
                return R.string.city_info_zone_number_since_text_fragment_green;
        }
        return Resources.INVALID_RESOURCE_ID;
    }

}
