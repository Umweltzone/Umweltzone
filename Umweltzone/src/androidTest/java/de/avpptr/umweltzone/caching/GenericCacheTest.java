package de.avpptr.umweltzone.caching;

import android.support.v4.util.LruCache;
import android.test.InstrumentationTestCase;

public class GenericCacheTest extends InstrumentationTestCase {


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    public void testInitialize() throws Exception {
        ResourceIdCache resourceIdCache = new ResourceIdCache(3);
        resourceIdCache.initialize();
        LruCache<String, ?> resourceIdInnerCache = ResourceIdCache.mCache;
        assertSame(resourceIdInnerCache, ResourceIdCache.mCache);

        CircuitsCache circuitsCache = new CircuitsCache(2);
        circuitsCache.initialize();
        LruCache<String, ?> circuitsInnerCache = GenericCache.mCache;
        assertSame(circuitsInnerCache, CircuitsCache.mCache);

        assertSame(resourceIdInnerCache, ResourceIdCache.mCache);
    }


}