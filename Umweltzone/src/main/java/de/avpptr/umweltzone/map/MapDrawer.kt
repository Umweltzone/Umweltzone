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

package de.avpptr.umweltzone.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import de.avpptr.umweltzone.map.viewmodels.CircuitViewModel
import java.util.*

class MapDrawer(private val map: GoogleMap) {

    private var zonePolygons = mutableListOf<Polygon>()

    fun drawPolygons(circuitViewModels: List<CircuitViewModel>) {
        if (zonePolygons.isNotEmpty()) {
            zonePolygons.clear()
        }

        zonePolygons = ArrayList(circuitViewModels.size)
        circuitViewModels.forEach {
            zonePolygons.add(drawPolygon(it))
        }
    }

    private fun drawPolygon(viewModel: CircuitViewModel) =
            map.addPolygon(PolygonOptions()
                    .addAll(viewModel.coordinates)
                    .strokeWidth(viewModel.strokeWidth.toFloat())
                    .fillColor(viewModel.fillColor)
                    .strokeColor(viewModel.strokeColor)
            )

}
