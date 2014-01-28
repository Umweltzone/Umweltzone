package de.avpptr.umweltzone.caching;

import android.content.Context;
import android.support.v4.util.LruCache;

public abstract class GenericCache {

    protected int mMaxSize;
    protected static LruCache<String, ?> mCache;

    // Returns the object or null.
    public Object readObject(final Context context, final String... objectData) {
        if (mCache == null) {
            initialize();
            return writeObject(context, objectData);
        } else {
            // Try loading from cache
            Object object = readObject(objectData);
            if (object == null) {
                object = writeObject(context, objectData);
            }
            return object;
        }
    }

    // Initialize the data type specific cache.
    protected abstract void initialize();
    // Returns the object from cache or null.
    protected abstract Object readObject(final String... objectData);
    // Stores the data to the cache and returns the object or null.
    protected abstract Object writeObject(final Context context, final String... objectData);

}
