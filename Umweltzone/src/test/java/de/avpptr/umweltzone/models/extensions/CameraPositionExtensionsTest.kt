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
        assertThat(CameraPosition(LatLng(-90.0, -180.0), 0.1f, 0f, 0f).isValid()).isTrue()
    }

    @Test
    fun isValidWithInvalidZoom() {
        assertThat(CameraPosition(LatLng(-90.0, -180.0), 0.0f, 0f, 0f).isValid()).isFalse()
    }

}
