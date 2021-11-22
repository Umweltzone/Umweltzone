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

package de.avpptr.umweltzone.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class LowEmissionZoneNumbersTests {

    @Test
    public void getNextWithRed() {
        assertThat(LowEmissionZoneNumbers.getNext(LowEmissionZoneNumbers.RED))
                .isEqualTo(LowEmissionZoneNumbers.YELLOW);
    }

    @Test
    public void getNextWithYellow() {
        assertThat(LowEmissionZoneNumbers.getNext(LowEmissionZoneNumbers.YELLOW))
                .isEqualTo(LowEmissionZoneNumbers.GREEN);
    }

    @Test
    public void getNextWithGreen() {
        try {
            LowEmissionZoneNumbers.getNext(LowEmissionZoneNumbers.GREEN);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot return next zone number after: 4");
        }
    }

    @Test
    public void getNextWithLightBlue() {
        try {
            LowEmissionZoneNumbers.getNext(LowEmissionZoneNumbers.LIGHT_BLUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot return next zone number after: 5");
        }
    }

    @Test
    public void getNextWithDarkBlue() {
        try {
            LowEmissionZoneNumbers.getNext(LowEmissionZoneNumbers.DARK_BLUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot return next zone number after: 6");
        }
    }

}
