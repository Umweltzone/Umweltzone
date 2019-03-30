package de.avpptr.umweltzone.models.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class LatitudeExtensionsTest(

        private val latitude: Double,
        private val isValid: Boolean

) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}.isValidLatitude => {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(-90.1, false),
                    arrayOf(-90.0, true),
                    arrayOf(0, true),
                    arrayOf(90.0, true),
                    arrayOf(90.1, false)
            )
        }
    }

    @Test
    fun isValid() {
        assertThat(latitude.isValidLatitude).isEqualTo(isValid)
    }

}
