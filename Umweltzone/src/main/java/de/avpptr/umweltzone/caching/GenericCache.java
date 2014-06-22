/*
 *  Copyright (C) 2014  Tobias Preuss
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
