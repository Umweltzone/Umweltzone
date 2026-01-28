@file:JvmName("CameraUpdateHelper")

/*
 *  Copyright (C) 2024  Tobias Preuss
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

package de.avpptr.umweltzone.utils

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds

private const val PADDING = 50

fun getCameraUpdate(activity: Activity, latLngBounds: LatLngBounds): CameraUpdate {
    val (width, height) = getDisplaySize(activity)
    var padding = PADDING
    if (width > height) {
        padding *= 2
    }
    return CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding)
}

@Suppress("DEPRECATION")
private fun getDisplaySize(activity: Activity): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = activity.windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        Pair(bounds.width(), bounds.height())
    } else {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}
