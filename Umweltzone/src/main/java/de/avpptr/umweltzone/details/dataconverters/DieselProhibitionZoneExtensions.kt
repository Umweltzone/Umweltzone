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

package de.avpptr.umweltzone.details.dataconverters

import android.content.Context
import androidx.annotation.VisibleForTesting
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.details.viewmodels.DpzDetailsViewModel
import de.avpptr.umweltzone.models.DieselProhibitionZone
import de.avpptr.umweltzone.models.VehicleType
import de.avpptr.umweltzone.models.extensions.roadSignType
import de.avpptr.umweltzone.utils.StringHelper

internal fun DieselProhibitionZone.toDetailsViewModel(context: Context) = DpzDetailsViewModel(
        displayName = displayName,
        roadSignType = roadSignType,
        allowedEmissionStandardInDpz = allowedEmissionStandardInDpzText(context),
        isCongruentWithLowEmissionZone = isCongruentWithLowEmissionZoneText(context),
        zoneNumberForResidentsSince = zoneNumberForResidentsSinceText(context),
        zoneNumberForNonResidentsSince = zoneNumberForNonResidentsSinceText(context),
        prohibitedVehicles = prohibitedVehiclesText(context),
        geometrySource = StringHelper.getGeometrySourceText(context, geometrySource),
        geometryUpdatedAt = StringHelper.getGeometryUpdatedAtText(context, geometryUpdatedAt)
)

@VisibleForTesting
fun DieselProhibitionZone.allowedEmissionStandardInDpzText(context: Context): String =
        context.getString(R.string.city_info_allowed_exhaust_emission_standard_in_diesel_prohibition_zone, zoneNumber)

@VisibleForTesting
fun DieselProhibitionZone.zoneNumberForResidentsSinceText(context: Context): String =
        if (zoneNumberForResidentsSince == null) {
            ""
        } else {
            val date = StringHelper.getFormattedDate(context, R.string.city_info_zone_number_since_date_format, zoneNumberForResidentsSince!!)
            context.getString(R.string.city_info_zone_number_for_residents_since, date)
        }

@VisibleForTesting
fun DieselProhibitionZone.zoneNumberForNonResidentsSinceText(context: Context): String =
        if (zoneNumberForNonResidentsSince == null) {
            ""
        } else {
            val date = StringHelper.getFormattedDate(context, R.string.city_info_zone_number_since_date_format, zoneNumberForNonResidentsSince!!)
            context.getString(R.string.city_info_zone_number_for_non_residents_since, date)
        }

@VisibleForTesting
fun DieselProhibitionZone.prohibitedVehiclesText(context: Context): String =
        when (prohibitedVehicleTypes.size) {
            0 -> ""
            1 -> {
                val vehiclesText = when (prohibitedVehicleTypes[0]) {
                    VehicleType.CAR -> context.getString(R.string.city_info_provision_applies_to_vehicles_fragment_cars)
                    VehicleType.HGV -> context.getString(R.string.city_info_provision_applies_to_vehicles_fragment_hgvs)
                }
                context.getString(R.string.city_info_provision_applies_to_vehicles, vehiclesText)
            }
            2 -> {
                val vehiclesText: String
                if (prohibitedVehicleTypes.contains(VehicleType.CAR) && prohibitedVehicleTypes.contains(VehicleType.HGV)) {
                    vehiclesText = context.getString(R.string.city_info_provision_applies_to_vehicles_fragment_cars_and_hgvs)
                } else {
                    throw IllegalStateException("Unknown vehicles: $prohibitedVehicleTypes.")
                }
                context.getString(R.string.city_info_provision_applies_to_vehicles, vehiclesText)
            }
            else -> throw IllegalStateException("Unknown number (${prohibitedVehicleTypes.size}) of vehicles: $prohibitedVehicleTypes.")
        }

private val DieselProhibitionZone.prohibitedVehicleTypes: List<VehicleType>
    get() {
        val vehicles = mutableListOf<VehicleType>()
        prohibitedVehicles.forEach {
            when (it) {
                VehicleType.CAR.value -> vehicles.add(VehicleType.CAR)
                VehicleType.HGV.value -> vehicles.add(VehicleType.HGV)
                else -> throw IllegalStateException("Unknown vehicle: $it.")
            }
        }
        return vehicles.toList()
    }

@VisibleForTesting
fun DieselProhibitionZone.isCongruentWithLowEmissionZoneText(context: Context): String =
        if (isCongruentWithLowEmissionZone) {
            context.getString(R.string.city_info_is_congruent_with_lez)
        } else {
            ""
        }
