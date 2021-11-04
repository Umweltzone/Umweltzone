/*
 *  Copyright (C) 2021  Tobias Preuss
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

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CameraPositionExtensionsTest {

    // Testing LatLon with invalid latitude or longitude is a waste of time
    // because the LatLong normalized invalid input values at construction time.

    @Test
    fun isValidWithValidZoom() {
        assertThat(CameraPosition(LatLng(-90.0, -180.0), 0.1f, 0f, 0f).isValid()).isTrue
    }

    @Test
    fun isValidWithInvalidZoom() {
        assertThat(CameraPosition(LatLng(-90.0, -180.0), 0.0f, 0f, 0f).isValid()).isFalse
    }

}
