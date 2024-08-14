/*
 *  Copyright (C) 2024  Lars Sadau, Tobias Preuss
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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.ChildZone;
import de.avpptr.umweltzone.models.Circuit;
import de.avpptr.umweltzone.models.DieselProhibitionZone;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.models.LowEmissionZone;
import kotlin.NotImplementedError;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ContentProviderTest {

    private static final String[] ZONE_FILE_NAMES_WITH_COORDINATES = {
            "lez_aachen", "lez_augsburg", "lez_berlin", "lez_bonn", "lez_bremen",
            "lez_darmstadt", "lez_dinslaken", "lez_duesseldorf", "lez_eschweiler",
            "lez_frankfurt_main", "lez_freiburg_breisgau", "lez_hagen", "lez_halle",
            "lez_heidenheim", "lez_heilbronn", "lez_herrenberg", "lez_ilsfeld",
            "lez_cologne", "lez_krefeld", "lez_langenfeld", "lez_leipzig",
            "lez_leonberg", "lez_limburg", "lez_ludwigsburg", "lez_magdeburg", "lez_mainz",
            "lez_mannheim", "lez_marburg", "lez_moenchengladbach", "lez_muehlacker", "lez_munich",
            "lez_muenster", "lez_neuss", "lez_neuulm", "lez_offenbach", "lez_osnabrueck",
            "lez_overath", "lez_pforzheim", "lez_remscheid", "lez_reutlingen",
            "lez_ruhrregion", "lez_schwaebisch_gmuend", "lez_siegen",
            "lez_stuttgart", "lez_tuebingen", "lez_ulm", "lez_urbach", "lez_wendlingen",
            "lez_wuppertal",
            "dpz_hamburg_max_brauer_allee", "dpz_hamburg_stresemannstrasse",
            "dpz_berlin", "dpz_darmstadt_huegelstrasse", "dpz_darmstadt_heinrichstrasse"
    };

    private static final String[] ZONE_FILE_NAMES_WITHOUT_COORDINATES = {
            "lez_regensburg", "lez_wiesbaden"
    };

    private Context mContext;

    @Before
    public void setUp() {
        mContext = getInstrumentation().getTargetContext();
    }

    @Test
    public void testGetCircuits_failsWhenCoordinatesAreMissing() {
        String expectedErrorMessage;
        for (String zoneFileName : ZONE_FILE_NAMES_WITHOUT_COORDINATES) {
            expectedErrorMessage = "Resource for file path 'raw/" + zoneFileName + "' not found.";
            try {
                getCircuits(zoneFileName);
                fail();
            } catch (IllegalStateException e) {
                assertThat(e.getMessage()).isEqualTo(expectedErrorMessage);
            }
        }
    }

    @Test
    public void testGetCircuits_worksAtAll() {
        for (String zoneFileName : ZONE_FILE_NAMES_WITH_COORDINATES) {
            assertThat(getCircuits(zoneFileName))
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    @Test
    public void testGetCircuits_usesCaches() {
        for (String zoneFileName : ZONE_FILE_NAMES_WITH_COORDINATES) {
            List<Circuit> circuits = getCircuits(zoneFileName);
            assertThat(circuits).isSameAs(getCircuits(zoneFileName));
        }
    }

    @Test
    public void testGetResourceId_worksAtAll() {
        for (String zoneFileName : ZONE_FILE_NAMES_WITH_COORDINATES) {
            assertThat(getZoneJsonResourceId(zoneFileName)).isNotNull();
        }
    }

    @Test
    public void testGetResourceId_usesCaches() {
        for (String zoneFileName : ZONE_FILE_NAMES_WITH_COORDINATES) {
            @RawRes Integer zoneJsonResourceId = getZoneJsonResourceId(zoneFileName);
            assertThat(zoneJsonResourceId).isSameAs(getZoneJsonResourceId(zoneFileName));
        }
    }

    @Test
    public void testGetFaqs_worksAtAll() {
        List<Faq> faqs = ContentProvider.getFaqs(mContext);
        assertThat(faqs)
                .isNotNull()
                .isNotEmpty();
        for (Faq faq : faqs) {
            assertThat(faq).isNotNull();
            testFaq(faq);
        }
    }

    @Test
    public void testGetAdministrativeZones_worksAtAll() {
        List<AdministrativeZone> administrativeZones = ContentProvider.getAdministrativeZones(mContext);
        assertThat(administrativeZones)
                .isNotNull()
                .isNotEmpty();
        for (AdministrativeZone administrativeZone : administrativeZones) {
            assertThat(administrativeZone).isNotNull();
            testAdministrativeZone(administrativeZone);
        }
    }

    private void testFaq(@NonNull Faq faq) {
        assertThat(faq.position).isNotNull().isGreaterThan(0);
        assertThat(faq.question).isNotNull().isNotEmpty();
        assertThat(faq.answer).isNotNull().isNotEmpty();
        assertThat(faq.label).isNotNull().isNotEmpty();
    }

    private void testAdministrativeZone(@NonNull AdministrativeZone administrativeZone) {
        assertThat(administrativeZone.name).isNotNull();
        assertThat(administrativeZone.displayName).isNotNull();

        BoundingBox boundingBox = administrativeZone.boundingBox;
        assertThat(boundingBox).isNotNull();
        assertThat(boundingBox.isValid()).isTrue();

        for (ChildZone childZone : administrativeZone.childZones) {
            if (childZone instanceof LowEmissionZone) {
                testLowEmissionZone((LowEmissionZone) childZone);
            } else if (childZone instanceof DieselProhibitionZone) {
                testDieselProhibitionZone((DieselProhibitionZone) childZone);
            } else {
                fail();
                throw new NotImplementedError();
            }
        }

        assertThat(administrativeZone.urlUmweltPlaketteDe)
                .isNotNull()
                .isNotEmpty();

        assertThat(administrativeZone.urlBadgeOnline).isNotNull();

        List<String> contactEmails = administrativeZone.contactEmails;
        if (contactEmails == null) {
            assertThat(administrativeZone.containsGeometryInformation()).isTrue();
        } else {
            assertThat(contactEmails).isNotEmpty();
            assertThat(administrativeZone.containsGeometryInformation()).isFalse();
        }
    }

    private void testLowEmissionZone(@NonNull LowEmissionZone lowEmissionZone) {
        assertThat(lowEmissionZone.zoneNumber)
                .isNotNull()
                .isBetween(LowEmissionZoneNumbers.RED, LowEmissionZoneNumbers.GREEN)
                .isLessThan(LowEmissionZoneNumbers.LIGHT_BLUE);
        assertThat(lowEmissionZone.zoneNumberSince).isNotNull();
        assertThat(lowEmissionZone.abroadLicensedVehicleZoneNumber).isNotNull();
        assertThat(lowEmissionZone.listOfCities).isNotNull();
    }

    private void testDieselProhibitionZone(@NonNull DieselProhibitionZone dieselProhibitionZone) {
        assertThat(dieselProhibitionZone.fileName).isNotEmpty();
        assertThat(dieselProhibitionZone.displayName).isNotNull();
        assertThat(dieselProhibitionZone.zoneNumber)
                .isNotNull()
                .isBetween(LowEmissionZoneNumbers.LIGHT_BLUE, LowEmissionZoneNumbers.DARK_BLUE)
                .isGreaterThan(LowEmissionZoneNumbers.GREEN);
        assertThat(dieselProhibitionZone.prohibitedVehicles).isNotNull();
    }

    @NonNull
    private List<Circuit> getCircuits(@NonNull String zoneFileName) {
        return ContentProvider.getCircuits(mContext, zoneFileName);
    }

    @RawRes
    private Integer getZoneJsonResourceId(@NonNull String zoneFileName) {
        return ContentProvider.getResourceId(mContext, zoneFileName, "raw");
    }

}
