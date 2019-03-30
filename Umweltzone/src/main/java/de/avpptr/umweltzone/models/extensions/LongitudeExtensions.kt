@file:JvmName("LongitudeExtensions")

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

const val MIN_LONGITUDE = -180.0
const val MAX_LONGITUDE = 180.0

/**
 * Longitude, in degrees. This value is in the range [-180, 180).
 * See: https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLng
 */
val Double.isValidLongitude
    get() = MIN_LONGITUDE <= this && this < MAX_LONGITUDE
