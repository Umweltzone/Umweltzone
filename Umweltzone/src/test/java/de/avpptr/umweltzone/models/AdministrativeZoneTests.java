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

package de.avpptr.umweltzone.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.utils.AdministrativeZoneBuilder;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.DateHelper;
import de.avpptr.umweltzone.utils.DieselProhibitionZoneBuilder;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.LowEmissionZoneBuilder;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class AdministrativeZoneTests {

    @Test
    public void wrapUnwrapParcelable() {
        AdministrativeZone administrativeZone = Parcels.unwrap(Parcels.wrap(getAdministrativeZone()));
        assertThat(administrativeZone).isEqualTo(getAdministrativeZone());
    }

    private AdministrativeZone getAdministrativeZone() {
        GeoPoint southWest = new GeoPoint(52.464504, 13.282079);
        GeoPoint northEast = new GeoPoint(52.549808, 13.475550);
        return new AdministrativeZoneBuilder()
                .setName("berlin")
                .setDisplayName("Berlin")
                .setBoundingBox(new BoundingBox(southWest, northEast))
                .setUrlUmweltPlaketteDe("http://umwelt-plakette.de/umweltzone%20berlin.php")
                .setUrlBadgeOnline(
                        "https://www.berlin.de/labo/kfz/dienstleistungen/feinstaubplakette.shop.php")
                .setContactEmails(new ArrayList<>() {{
                    add("john.doe@example.com");
                    add("anna.mae@example.com");
                }})
                .setChildZones(getChildZones())
                .build();
    }

    private List<ChildZone> getChildZones() {
        Date date = DateHelper.getDate(2016, 4, 1);
        LowEmissionZone lowEmissionZone = new LowEmissionZoneBuilder()
                .setFileName("lez_berlin")
                .setDisplayName("Berlin 1")
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(date)
                .setNextZoneNumberAsOf(null)
                .setAbroadLicensedVehicleZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setAbroadLicensedVehicleZoneNumberUntil(date)
                .setListOfCities(new ArrayList<>(3) {{
                    add("Bochum");
                    add("Bottrop");
                    add("Castrop-Rauxel");
                }})
                .setGeometrySource("Geoportal Berlin / Umweltzone Berlin")
                .setGeometryUpdatedAt(date)
                .build();
        Date zoneNumberForNonResidentsSince = DateHelper.getDate(2018, 2, 1);
        Date zoneNumberForResidentsSince = DateHelper.getDate(2018, 4, 1);
        Date geometryUpdatedAt = DateHelper.getDate(2019, 2, 1);
        DieselProhibitionZone dieselProhibitionZone = new DieselProhibitionZoneBuilder()
                .setFileName("dpz_hamburg_max_brauer_allee")
                .setDisplayName("Max Brauer Allee, Hamburg")
                .setZoneNumber(6)
                .setZoneNumberForNonResidentsSince(zoneNumberForNonResidentsSince)
                .setZoneNumberForResidentsSince(zoneNumberForResidentsSince)
                .setProhibitedVehicles(singletonList(VehicleType.CAR.INSTANCE.getValue()))
                .setIsCongruentWithLowEmissionZone(true)
                .setGeometrySource("Hamburger Behörde")
                .setGeometryUpdatedAt(geometryUpdatedAt)
                .build();
        return asList(lowEmissionZone, dieselProhibitionZone);
    }

}
