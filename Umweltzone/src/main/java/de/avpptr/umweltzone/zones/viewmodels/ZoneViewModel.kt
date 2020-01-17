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

package de.avpptr.umweltzone.zones.viewmodels

import androidx.annotation.ColorInt
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

    data class TwoZonesViewModel(

            override val name: String,

            @ColorInt
            override val nameTextColor: Int,

            val badge1ViewModel: BadgeViewModel,

            val badge2ViewModel: BadgeViewModel

    ) : ZoneViewModel(name, nameTextColor, ChildZonesCount.TWO)

}
