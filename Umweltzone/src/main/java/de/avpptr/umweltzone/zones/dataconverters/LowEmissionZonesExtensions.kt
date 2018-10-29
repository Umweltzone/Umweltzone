package de.avpptr.umweltzone.zones.dataconverters

import android.content.Context
import de.avpptr.umweltzone.models.LowEmissionZone

fun List<LowEmissionZone>.toZoneViewModels(context: Context) =
        map { it.toZoneViewModel(context) }
