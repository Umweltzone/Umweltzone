@file:JvmName("ChildZonesExtensions")

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

package de.avpptr.umweltzone.map.dataconverters

import android.content.Context
import android.support.annotation.ColorInt
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.extensions.getColorCompat
import de.avpptr.umweltzone.map.viewmodels.CircuitViewModel
import de.avpptr.umweltzone.models.ChildZone
import de.avpptr.umweltzone.models.Circuit
import de.avpptr.umweltzone.models.DieselProhibitionZone
import de.avpptr.umweltzone.utils.ContentProvider
import de.avpptr.umweltzone.utils.GeoPoint
import de.avpptr.umweltzone.utils.shapeFillColor

/**
 * Returns a list of child zones which are sorted in order to render
 * DieselProhibitionZone elements on top of LowEmissionZone elements
 * to enhance visual perception.
 */
val List<ChildZone>.visuallySorted: List<ChildZone>
    get() = sortedBy { it is DieselProhibitionZone }

fun List<ChildZone>.toCircuitViewModels(context: Context): List<CircuitViewModel> {
    val strokeColor = context.getColorCompat(R.color.shape_stroke_color)
    val strokeWidth = context.resources.getInteger(R.integer.shape_stroke_width)
    return flatMap { it.toCircuitViewModels(context, strokeColor, strokeWidth) }
}

private fun ChildZone.toCircuitViewModels(context: Context, @ColorInt strokeColor: Int, strokeWith: Int): List<CircuitViewModel> {
    val fillColor = context.getColorCompat(zoneNumber.shapeFillColor)
    val circuits = ContentProvider.getCircuits(context, fileName)
    return circuits.toCircuitViewModels(fillColor, strokeColor, strokeWith)
}

private fun List<Circuit>.toCircuitViewModels(@ColorInt fillColor: Int, @ColorInt strokeColor: Int, strokeWith: Int) =
        map { it.toCircuitViewModel(fillColor, strokeColor, strokeWith) }

private fun Circuit.toCircuitViewModel(@ColorInt fillColor: Int, @ColorInt strokeColor: Int, strokeWith: Int) =
        CircuitViewModel(coordinates.toLatLngList(), fillColor, strokeColor, strokeWith)

private fun List<GeoPoint>.toLatLngList() = map { it.toLatLng() }
