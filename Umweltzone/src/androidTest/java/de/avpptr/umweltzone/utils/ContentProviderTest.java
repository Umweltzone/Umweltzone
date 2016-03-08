/*
 *  Copyright (C) 2016  Lars Sadau, Tobias Preuss
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
import android.test.InstrumentationTestCase;

import java.util.List;

import de.avpptr.umweltzone.models.Circuit;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentProviderTest extends InstrumentationTestCase {

    private static final String[] ZONES_WITH_COORDINATES = {"aachen", "augsburg",
            "berlin", "bonn", "bremen", "darmstadt", "dinslaken", "duesseldorf", "erfurt",
            "frankfurt_main", "freiburg_breisgau", "hagen", "halle", "hannover", "heidelberg",
            "heidenheim", "heilbronn", "herrenberg", "ilsfeld", "karlsruhe", "cologne",
            "krefeld", "langenfeld", "leipzig", "leonberg", "ludwigsburg", "magdeburg",
            "mainz", "mannheim", "moenchengladbach", "muehlacker", "munich", "muenster",
            "neuss", "neuulm", "offenbach", "osnabrueck", "pfinztal", "pforzheim",
            "remscheid", "reutlingen", "ruhrregion", "schramberg", "schwaebisch_gmuend",
            "siegen", "stuttgart", "tuebingen", "ulm", "urbach", "wendlingen", "wuppertal"};

    private static final String[] ZONES_WITHOUT_COORDINATES = {"marburg", "wiesbaden"};

    private Context mContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getInstrumentation().getTargetContext();
    }

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

    public void testGetCircuits_worksAtAll() throws Exception {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            assertThat(getCircuits(zoneName))
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    public void testGetCircuits_usesCaches() throws Exception {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            List<Circuit> circuits = getCircuits(zoneName);
            assertThat(circuits).isSameAs(getCircuits(zoneName));
        }
    }

    public void testGetResourceId_worksAtAll() throws Exception {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            assertThat(getZoneJsonResourceId(zoneName)).isNotNull();
        }
    }

    public void testGetResourceId_usesCaches() throws Exception {
        for (String zoneName : ZONES_WITH_COORDINATES) {
            @RawRes Integer zoneJsonResourceId = getZoneJsonResourceId(zoneName);
            assertThat(zoneJsonResourceId).isSameAs(getZoneJsonResourceId(zoneName));
        }
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
