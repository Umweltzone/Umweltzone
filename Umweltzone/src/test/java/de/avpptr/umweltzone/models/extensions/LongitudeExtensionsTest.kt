package de.avpptr.umweltzone.models.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class LongitudeExtensionsTest(

        private val longitude: Double,
        private val isValid: Boolean

) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}.isValidLongitude => {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(-180.1, false),
                    arrayOf(-180.0, true),
                    arrayOf(0, true),
                    arrayOf(180.0, false),
                    arrayOf(180.1, false)
            )
        }
    }

    @Test
    fun isValid() {
        assertThat(longitude.isValidLongitude).isEqualTo(isValid)
    }

}
