/*
 *  Copyright (C) 2019  Tobias Preuss
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

import android.app.Activity
import de.avpptr.umweltzone.details.viewmodels.LezDetailsViewModel
import de.avpptr.umweltzone.models.LowEmissionZone
import de.avpptr.umweltzone.models.extensions.roadSignType
import de.avpptr.umweltzone.utils.StringHelper

fun LowEmissionZone.toDetailsViewModel(activity: Activity) = LezDetailsViewModel(
        roadSignType = roadSignType,
        listOfCitiesText = StringHelper.getListOfCitiesText(activity, this),
        zoneNumberSinceText = StringHelper.getZoneNumberSinceAsOfText(activity, this),
        nextZoneNumberAsOfText = StringHelper.getNextZoneNumberAsOfText(activity, this),
        abroadLicensedVehicleZoneNumberText = StringHelper.getAbroadLicensedVehicleZoneNumberText(activity, this),
        geometryUpdatedAtText = StringHelper.getGeometryUpdatedAtText(activity, geometryUpdatedAt),
        geometrySourceText = StringHelper.getGeometrySourceText(activity, geometrySource)
)
