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

package de.avpptr.umweltzone.prefs

import android.content.SharedPreferences

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import de.avpptr.umweltzone.utils.GeoPoint
import info.metadude.android.typedpreferences.FloatPreference

class CameraPositionPreference(

        sharedPreferences: SharedPreferences,
        key: String

) {

    private val targetPreference = GeoPointPreference(sharedPreferences, "$key.TARGET")
    private val zoomPreference = FloatPreference(sharedPreferences, "$key.ZOOM")
    private val tiltPreference = FloatPreference(sharedPreferences, "$key.TILT")
    private val bearingPreference = FloatPreference(sharedPreferences, "$key.BEARING")

    fun get(): CameraPosition {
        val geoPoint = targetPreference.get()
        val target = LatLng(geoPoint.latitude, geoPoint.longitude)
        val zoom = zoomPreference.get()
        val tilt = tiltPreference.get()
        val bearing = bearingPreference.get()
        return CameraPosition(target, zoom, tilt, bearing)
    }

    fun set(cameraPosition: CameraPosition) {
        targetPreference.set(GeoPoint(cameraPosition.target))
        zoomPreference.set(cameraPosition.zoom)
        tiltPreference.set(cameraPosition.tilt)
        bearingPreference.set(cameraPosition.bearing)
    }

    fun delete() {
        targetPreference.delete()
        zoomPreference.delete()
        tiltPreference.delete()
        bearingPreference.delete()
    }

}
