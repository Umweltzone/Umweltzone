package de.avpptr.umweltzone.utils;

import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import java.util.List;

import de.avpptr.umweltzone.models.Circuit;

public class ContentProviderTest extends InstrumentationTestCase {

    public void testGetCircuits_worksAtAll() throws Exception {
        assertNotNull(getCircuitsOfBerlin());
    }

    public void testGetCircuits_usesCaches() throws Exception {
        List<Circuit> circuitsOfBerlin = getCircuitsOfBerlin();
        assertSame(circuitsOfBerlin,getCircuitsOfBerlin());
    }

    private List<Circuit> getCircuitsOfBerlin() {
        return ContentProvider.getCircuits(getInstrumentation().getTargetContext(), "berlin");
    }
}