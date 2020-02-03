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

@file:JvmName("ChildZoneExtensions")

package de.avpptr.umweltzone.models.extensions

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers
import de.avpptr.umweltzone.models.ChildZone
import info.metadude.kotlin.library.roadsigns.RoadSign

val ChildZone.roadSignType: RoadSign.Type
    get() = when {
        zoneNumber == LowEmissionZoneNumbers.NONE -> RoadSign.Type.None
        zoneNumber == LowEmissionZoneNumbers.RED -> RoadSign.Type.EnvironmentalBadge.RedYellowGreen
        zoneNumber == LowEmissionZoneNumbers.YELLOW -> RoadSign.Type.EnvironmentalBadge.YellowGreen
        zoneNumber == LowEmissionZoneNumbers.GREEN -> RoadSign.Type.EnvironmentalBadge.Green

        zoneNumber == LowEmissionZoneNumbers.LIGHT_BLUE && fileName == "lez_stuttgart" ->
            RoadSign.Type.DieselProhibition.FreeAsOfEuro5VExceptDeliveryVehiclesStuttgart
        zoneNumber == LowEmissionZoneNumbers.DARK_BLUE && fileName == "dpz_hamburg_max_brauer_allee" ->
            RoadSign.Type.DieselProhibition.CarsFreeUntilEuro5VOpenForResidentsHamburg
        zoneNumber == LowEmissionZoneNumbers.DARK_BLUE && fileName == "dpz_hamburg_stresemannstrasse" ->
            RoadSign.Type.DieselProhibition.HgvsFreeUntilEuroVOpenForResidentsHamburg
        zoneNumber == LowEmissionZoneNumbers.DARK_BLUE && fileName == "dpz_berlin" ->
            RoadSign.Type.DieselProhibition.CarsFreeUntilEuro5VOpenForResidentsBerlin
        zoneNumber == LowEmissionZoneNumbers.DARK_BLUE && fileName == "dpz_darmstadt" ->
            // TODO Show Darmstadt specific sign instead of Berlin sign
            RoadSign.Type.DieselProhibition.CarsFreeUntilEuro5VOpenForResidentsBerlin

        else -> throw IllegalStateException("Unknown combination of zone number: '$zoneNumber' and file name: '$fileName'.")
    }
