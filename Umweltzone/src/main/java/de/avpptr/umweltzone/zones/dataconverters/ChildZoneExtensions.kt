package de.avpptr.umweltzone.zones.dataconverters

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import de.avpptr.umweltzone.models.ChildZone
import de.avpptr.umweltzone.utils.LowEmissionZoneNumberConverter
import de.avpptr.umweltzone.zones.viewmodels.BadgeViewModel

fun ChildZone.toBadgeViewModel(context: Context) =
        BadgeViewModel("$zoneNumber", getBadgeColor(context))

@ColorInt
private fun ChildZone.getBadgeColor(context: Context): Int {
    val color = LowEmissionZoneNumberConverter.getColor(zoneNumber)
    return ContextCompat.getColor(context, color)
}
