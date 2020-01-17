/*
 *  Copyright (C) 2020  Tobias Preuss
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

@file:JvmName("LowEmissionZoneNumberConverter")

package de.avpptr.umweltzone.utils

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers
import de.avpptr.umweltzone.contract.Resources

val @receiver:LowEmissionZoneNumbers.Color Int.color
    @ColorRes get() = when (this) {
        LowEmissionZoneNumbers.RED -> R.color.city_zone_2
        LowEmissionZoneNumbers.YELLOW -> R.color.city_zone_3
        LowEmissionZoneNumbers.GREEN -> R.color.city_zone_4
        LowEmissionZoneNumbers.LIGHT_BLUE -> R.color.city_zone_5
        LowEmissionZoneNumbers.DARK_BLUE -> R.color.city_zone_6
        else -> R.color.city_zone_none
    }

val @receiver:LowEmissionZoneNumbers.Color Int.colorString
    @StringRes get() = when (this) {
        LowEmissionZoneNumbers.RED -> R.string.city_info_zone_number_since_text_fragment_red
        LowEmissionZoneNumbers.YELLOW -> R.string.city_info_zone_number_since_text_fragment_yellow
        LowEmissionZoneNumbers.GREEN -> R.string.city_info_zone_number_since_text_fragment_green
        else -> Resources.INVALID_RESOURCE_ID
    }

val @receiver:LowEmissionZoneNumbers.Color Int.shapeFillColor
    @ColorRes get() = when (this) {
        LowEmissionZoneNumbers.RED -> R.color.shape_fill_color_zone_2
        LowEmissionZoneNumbers.YELLOW -> R.color.shape_fill_color_zone_3
        LowEmissionZoneNumbers.GREEN -> R.color.shape_fill_color_zone_4
        LowEmissionZoneNumbers.LIGHT_BLUE -> R.color.shape_fill_color_zone_5
        LowEmissionZoneNumbers.DARK_BLUE -> R.color.shape_fill_color_zone_6
        else -> throw IllegalStateException("Unknown zone number: $this.")
    }
