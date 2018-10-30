package de.avpptr.umweltzone.zones.dataconverters

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.models.LowEmissionZone
import de.avpptr.umweltzone.zones.ChildZonesCount
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

fun LowEmissionZone.toZoneViewModel(context: Context): ZoneViewModel = when (childZones.size) {
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
    else -> error("Unknown child zones size: ${childZones.size}")
}

@ColorInt
private fun LowEmissionZone.getTextColor(context: Context): Int {
    val color = zoneIsDrawableToColorId(containsGeometryInformation())
    return ContextCompat.getColor(context, color)
}

private fun zoneIsDrawableToColorId(zoneIsDrawable: Boolean) =
        if (zoneIsDrawable)
            R.color.city_zone_name_drawable
        else
            R.color.city_zone_name_not_drawable
