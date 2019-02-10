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

package de.avpptr.umweltzone.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.contract.Resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class LowEmissionZoneNumberConverterTests {

    private final static
    @LowEmissionZoneNumbers.Color
    int INVALID_COLOR = 1;

    @Test
    public void testGetColor() {
        assertThat(LowEmissionZoneNumberConverter.getColor(INVALID_COLOR))
                .isEqualTo(R.color.city_zone_none);
        assertThat(LowEmissionZoneNumberConverter.getColor(LowEmissionZoneNumbers.RED))
                .isEqualTo(R.color.city_zone_2);
        assertThat(LowEmissionZoneNumberConverter.getColor(LowEmissionZoneNumbers.YELLOW))
                .isEqualTo(R.color.city_zone_3);
        assertThat(LowEmissionZoneNumberConverter.getColor(LowEmissionZoneNumbers.GREEN))
                .isEqualTo(R.color.city_zone_4);
    }

    @Test
    public void testGetColorString() {
        assertThat(LowEmissionZoneNumberConverter.getColorString(INVALID_COLOR))
                .isEqualTo(Resources.INVALID_RESOURCE_ID);
        assertThat(LowEmissionZoneNumberConverter.getColorString(LowEmissionZoneNumbers.RED))
                .isEqualTo(R.string.city_info_zone_number_since_text_fragment_red);
        assertThat(LowEmissionZoneNumberConverter.getColorString(LowEmissionZoneNumbers.YELLOW))
                .isEqualTo(R.string.city_info_zone_number_since_text_fragment_yellow);
        assertThat(LowEmissionZoneNumberConverter.getColorString(LowEmissionZoneNumbers.GREEN))
                .isEqualTo(R.string.city_info_zone_number_since_text_fragment_green);
    }

    @Test
    public void testGetShapeFillColor() {
        try {
            LowEmissionZoneNumberConverter.getShapeFillColor(INVALID_COLOR);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Unknown zone number: " + INVALID_COLOR + ".");
        }
        assertThat(LowEmissionZoneNumberConverter.getShapeFillColor(LowEmissionZoneNumbers.RED))
                .isEqualTo(R.color.shape_fill_color_zone_2);
        assertThat(LowEmissionZoneNumberConverter.getShapeFillColor(LowEmissionZoneNumbers.YELLOW))
                .isEqualTo(R.color.shape_fill_color_zone_3);
        assertThat(LowEmissionZoneNumberConverter.getShapeFillColor(LowEmissionZoneNumbers.GREEN))
                .isEqualTo(R.color.shape_fill_color_zone_4);
    }

}
