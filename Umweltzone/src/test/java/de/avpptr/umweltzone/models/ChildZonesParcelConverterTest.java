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

package de.avpptr.umweltzone.models;

import android.os.Parcelable;

import org.junit.Test;
import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;
import org.parceler.Parcels;

import java.util.List;

import de.avpptr.umweltzone.utils.LowEmissionZoneBuilder;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ChildZonesParcelConverterTest {

    @Test
    public void testChildZonesParcelConverter() {
        LowEmissionZone lowEmissionZone = new LowEmissionZoneBuilder()
                .setDisplayName("LowEmissionZone1")
                .build();
        ParentZone parentZone = new ParentZone("ParentZone1", singletonList(lowEmissionZone));
        Parcelable parcelable = Parcels.wrap(parentZone);
        assertThat((ParentZone) Parcels.unwrap(parcelable)).isEqualTo(parentZone);
    }

    @SuppressWarnings("WeakerAccess")
    @Parcel
    static class ParentZone {

        public String name;

        @ParcelPropertyConverter(ChildZonesParcelConverter.class)
        public List<ChildZone> childZones;

        @SuppressWarnings("unused")
        public ParentZone() {
            // For Parceler
        }

        public ParentZone(String name, List<ChildZone> childZones) {
            this.name = name;
            this.childZones = childZones;
        }

    }
}
