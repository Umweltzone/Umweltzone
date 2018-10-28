/*
 *  Copyright (C) 2018  Lars Sadau, Tobias Preuss
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
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.ChildZone;
import de.avpptr.umweltzone.models.Circuit;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.models.LowEmissionZone;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ContentProviderTest {

    private static final String[] ZONES_WITH_COORDINATES = {"aachen", "augsburg", "balingen",
            "berlin", "bonn", "bremen", "darmstadt", "dinslaken", "duesseldorf",
            "erfurt", "eschweiler", "frankfurt_main", "freiburg_breisgau", "hagen",
            "halle", "hannover", "heidelberg", "heidenheim", "heilbronn", "herrenberg",
            "ilsfeld", "karlsruhe", "cologne", "krefeld", "langenfeld", "leipzig",
            "leonberg", "limburg", "ludwigsburg", "magdeburg", "mainz", "mannheim", "marburg",
            "moenchengladbach", "muehlacker", "munich", "muenster", "neuss", "neuulm",
            "offenbach", "osnabrueck", "overath", "pfinztal", "pforzheim", "remscheid",
            "reutlingen", "ruhrregion", "schramberg", "schwaebisch_gmuend", "siegen",
            "stuttgart", "tuebingen", "ulm", "urbach", "wendlingen", "wuppertal"};

    private static final String[] ZONES_WITHOUT_COORDINATES = {"regensburg", "wiesbaden"};

    private Context mContext;

    @Before
    public void setUp() {
        mContext = getInstrumentation().getTargetContext();
    }

    @Test
    public void testGetCircuits_failsWhenCoordinatesAreMissing() {
        String expectedErrorMessage;
        for (String zoneName : ZONES_WITHOUT_COORDINATES) {
            expectedErrorMessage = "Resource for file path 'raw/zone_" + zoneName + "' not found.";
            try {
                getCircuits(zoneName);
                fail();
            } catch (Exception e) {
                assertThat(e.getMessage()).isEqualTo(expectedErrorMessage);
            }
        }
    }

    @Test
    public void testGetCircuits_worksAtAll() {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            assertThat(getCircuits(zoneName))
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    @Test
    public void testGetCircuits_usesCaches() {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            List<Circuit> circuits = getCircuits(zoneName);
            assertThat(circuits).isSameAs(getCircuits(zoneName));
        }
    }

    @Test
    public void testGetResourceId_worksAtAll() {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            assertThat(getZoneJsonResourceId(zoneName)).isNotNull();
        }
    }

    @Test
    public void testGetResourceId_usesCaches() {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            @RawRes Integer zoneJsonResourceId = getZoneJsonResourceId(zoneName);
            assertThat(zoneJsonResourceId).isSameAs(getZoneJsonResourceId(zoneName));
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
    public void testGetLowEmissionZones_worksAtAll() {
        List<LowEmissionZone> lowEmissionZones = ContentProvider.getLowEmissionZones(mContext);
        assertThat(lowEmissionZones)
                .isNotNull()
                .isNotEmpty();
        for (LowEmissionZone lowEmissionZone : lowEmissionZones) {
            assertThat(lowEmissionZone).isNotNull();
            testLowEmissionZone(lowEmissionZone);
        }
    }

    private void testFaq(@NonNull Faq faq) {
        assertThat(faq.position).isNotNull().isGreaterThan(0);
        assertThat(faq.question).isNotNull().isNotEmpty();
        assertThat(faq.answer).isNotNull().isNotEmpty();
        assertThat(faq.label).isNotNull().isNotEmpty();
        assertThat(faq.sourceUrl).isNotNull().isNotEmpty();
    }

    private void testLowEmissionZone(@NonNull LowEmissionZone lowEmissionZone) {
        assertThat(lowEmissionZone.name).isNotNull();
        assertThat(lowEmissionZone.displayName).isNotNull();
        assertThat(lowEmissionZone.listOfCities).isNotNull();

        BoundingBox boundingBox = lowEmissionZone.boundingBox;
        assertThat(boundingBox).isNotNull();
        assertThat(boundingBox.isValid()).isTrue();

        lowEmissionZone.childZones.forEach(this::testChildZone);

        assertThat(lowEmissionZone.urlUmweltPlaketteDe)
                .isNotNull()
                .isNotEmpty();

        assertThat(lowEmissionZone.urlBadgeOnline).isNotNull();

        List<String> contactEmails = lowEmissionZone.contactEmails;
        if (contactEmails == null) {
            assertThat(lowEmissionZone.containsGeometryInformation()).isTrue();
        } else {
            assertThat(contactEmails).isNotEmpty();
            assertThat(lowEmissionZone.containsGeometryInformation()).isFalse();
        }
    }

    private void testChildZone(@NonNull ChildZone childZone) {
        assertThat(childZone.zoneNumber)
                .isNotNull()
                .isBetween(LowEmissionZoneNumbers.RED, LowEmissionZoneNumbers.GREEN);
        assertThat(childZone.zoneNumberSince).isNotNull();
        assertThat(childZone.abroadLicensedVehicleZoneNumber).isNotNull();
    }

    @NonNull
    private List<Circuit> getCircuits(@NonNull String zoneName) {
        return ContentProvider.getCircuits(mContext, zoneName);
    }

    @RawRes
    private Integer getZoneJsonResourceId(@NonNull String zoneName) {
        String fileName = "zone_" + zoneName;
        return ContentProvider.getResourceId(mContext, fileName, "raw");
    }

}
