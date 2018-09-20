/*
 *  Copyright (C) 2018  Tobias Preuss
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

import com.google.android.gms.maps.model.LatLngBounds
import de.avpptr.umweltzone.models.LowEmissionZone
import de.avpptr.umweltzone.prefs.PreferencesHelper
import de.avpptr.umweltzone.utils.GeoPoint

internal class MapReadyDelegate(

        private val preferencesHelper: PreferencesHelper,
        private val getCenterZoneRequested: () -> Boolean,
        private val setCenterZoneRequested: (centerZoneRequested: Boolean) -> Unit,
        private val getDefaultLowEmissionZone: () -> LowEmissionZone?,
        private val listener: Listener

) {

    fun evaluate() {
        if (preferencesHelper.storesZoneIsDrawable()) {
            if (preferencesHelper.restoreZoneIsDrawable()) {
                listener.onDrawPolygonOverlay()
            } else {
                listener.onShowZoneNotDrawableDialog()
            }
        }
        if (getCenterZoneRequested.invoke()) {
            // City has been selected from the list
            val lastKnownPosition = preferencesHelper.restoreLastKnownLocationAsBoundingBox()
            if (lastKnownPosition.isValid) {
                listener.onZoomToBounds(lastKnownPosition.toLatLngBounds())
            }
            setCenterZoneRequested.invoke(false)
        } else {
            val lastKnownPosition = preferencesHelper.restoreLastKnownLocationAsGeoPoint()
            if (lastKnownPosition.isValid) {
                val zoomLevel = preferencesHelper.restoreZoomLevel()
                listener.onZoomToLocation(lastKnownPosition, zoomLevel)
            } else {
                // Select default city at first application start
                getDefaultLowEmissionZone.invoke()?.let {
                    storeLastLowEmissionZone(it)
                    if (preferencesHelper.storesZoneIsDrawable() && preferencesHelper.restoreZoneIsDrawable()) {
                        listener.onDrawPolygonOverlay()
                    } else {
                        listener.onShowZoneNotDrawableDialog()
                    }
                    listener.onZoomToBounds(it.boundingBox.toLatLngBounds())
                    listener.onStoreLastMapState()
                }
            }
        }
    }

    private fun storeLastLowEmissionZone(defaultLowEmissionZone: LowEmissionZone) {
        preferencesHelper.storeLastKnownLocationAsString(defaultLowEmissionZone.name)
        preferencesHelper.storeLastKnownLocationAsBoundingBox(defaultLowEmissionZone.boundingBox)
        preferencesHelper.storeZoneIsDrawable(defaultLowEmissionZone.containsGeometryInformation())
    }

    internal interface Listener {

        fun onDrawPolygonOverlay()

        fun onShowZoneNotDrawableDialog()

        fun onStoreLastMapState()

        fun onZoomToBounds(latLngBounds: LatLngBounds)

        fun onZoomToLocation(location: GeoPoint, zoomLevel: Float)

    }

}
