/*
 *  Copyright (C) 2014  Lars Sadau, Tobias Preuss
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

import android.test.InstrumentationTestCase;

import java.util.List;

import de.avpptr.umweltzone.models.Circuit;

public class ContentProviderTest extends InstrumentationTestCase {

    public void testGetCircuits_worksAtAll() throws Exception {
        assertNotNull(getCircuitsOfBerlin());
    }

    public void testGetCircuits_usesCaches() throws Exception {
        List<Circuit> circuitsOfBerlin = getCircuitsOfBerlin();
        assertSame(circuitsOfBerlin, getCircuitsOfBerlin());
    }

    private List<Circuit> getCircuitsOfBerlin() {
        return ContentProvider.getCircuits(
                getInstrumentation().getTargetContext(), "berlin");
    }

    public void testGetResourceId_worksAtAll() throws Exception {
        assertNotNull(getResourceIdOfBerlin_JSON());
    }

    public void testGetResourceId_usesCaches() throws Exception {
        Integer resourceIdOfBerlin_JSON = getResourceIdOfBerlin_JSON();
        assertSame(resourceIdOfBerlin_JSON, getResourceIdOfBerlin_JSON());
    }

    private Integer getResourceIdOfBerlin_JSON() {
        return ContentProvider.getResourceId(
                getInstrumentation().getTargetContext(), "zone_berlin", "raw");
    }

}
