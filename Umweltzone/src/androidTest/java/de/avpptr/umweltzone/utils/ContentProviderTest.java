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
