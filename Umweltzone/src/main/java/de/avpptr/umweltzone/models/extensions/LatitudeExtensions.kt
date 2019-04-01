@file:JvmName("LatitudeExtensions")

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

package de.avpptr.umweltzone.models.extensions

const val MIN_LATITUDE = -90.0
private const val MAX_LATITUDE = 90.0

/**
 * Latitude, in degrees. This value is in the range [-90, 90].
 * See: https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLng
 */
val Double.isValidLatitude
    get() = (MIN_LATITUDE..MAX_LATITUDE).contains(this)
