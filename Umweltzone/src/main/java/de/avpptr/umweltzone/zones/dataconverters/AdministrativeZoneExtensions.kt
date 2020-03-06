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

package de.avpptr.umweltzone.zones.dataconverters

import android.content.Context
import androidx.annotation.ColorInt
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.extensions.getColorCompat
import de.avpptr.umweltzone.models.AdministrativeZone
import de.avpptr.umweltzone.zones.ChildZonesCount
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

fun AdministrativeZone.toZoneViewModel(context: Context): ZoneViewModel = when (childZones.size) {
    ChildZonesCount.ONE.value -> {
        ZoneViewModel.OneZoneViewModel(
                name = displayName,
                nameTextColor = getTextColor(context),
                badgeViewModel = childZones[0].toBadgeViewModel(context)
        )
    }
    ChildZonesCount.TWO.value -> {
        ZoneViewModel.TwoZonesViewModel(
                name = displayName,
                nameTextColor = getTextColor(context),
                badge1ViewModel = childZones[0].toBadgeViewModel(context),
                badge2ViewModel = childZones[1].toBadgeViewModel(context)
        )
    }
    ChildZonesCount.THREE.value -> {
        ZoneViewModel.ThreeZonesViewModel(
                name = displayName,
                nameTextColor = getTextColor(context),
                badge1ViewModel = childZones[0].toBadgeViewModel(context),
                badge2ViewModel = childZones[1].toBadgeViewModel(context),
                badge3ViewModel = childZones[2].toBadgeViewModel(context)
        )
    }
    else -> error("Unknown child zones size: ${childZones.size}")
}

@ColorInt
private fun AdministrativeZone.getTextColor(context: Context): Int {
    val color = zoneIsDrawableToColorId(containsGeometryInformation())
    return context.getColorCompat(color)
}

private fun zoneIsDrawableToColorId(zoneIsDrawable: Boolean) =
        if (zoneIsDrawable)
            R.color.city_zone_name_drawable
        else
            R.color.city_zone_name_not_drawable
