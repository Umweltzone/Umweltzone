package de.avpptr.umweltzone.zones.viewmodels

import android.support.annotation.ColorInt
import de.avpptr.umweltzone.zones.ChildZonesCount

sealed class ZoneViewModel(

        open val name: String,

        @ColorInt
        open val nameTextColor: Int,

        val childZonesCount: ChildZonesCount

) {

    data class OneZoneViewModel(

            override val name: String,

            @ColorInt
            override val nameTextColor: Int,

            val badgeViewModel: BadgeViewModel

    ) : ZoneViewModel(name, nameTextColor, ChildZonesCount.ONE)

}
