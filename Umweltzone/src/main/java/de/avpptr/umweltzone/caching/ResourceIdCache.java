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
import android.content.res.Resources;
import android.support.v4.util.LruCache;

public class ResourceIdCache extends GenericCache {

    public ResourceIdCache(int maxSize) {
        mMaxSize = maxSize;
    }

    @Override
    protected void initialize() {
        mCache = new LruCache<String, Integer>(mMaxSize);
    }

    // Returns an integer, the resource id associated with the string resource
    // Expects two arguments: the file and the folder name
    @Override
    protected Object readObject(String... objectData) {
        if (objectData.length != 2) {
            throw new IllegalArgumentException(
                    "Expecting two arguments: file and folder name.");
        }
        final String fileName = objectData[0];
        final String folderName = objectData[1];
        return mCache.get(getFilePath(fileName, folderName));
    }

    // Returns an integer, the resource id associated with the string resource
    // Expects three arguments: the context, the file and the folder name
    @Override
    protected Object writeObject(Context context, String... objectData) {
        if (objectData.length != 2) {
            throw new IllegalArgumentException(
                    "Expecting three arguments: context, file and folder name.");
        }
        final String fileName = objectData[0];
        final String folderName = objectData[1];
        final Resources resources = context.getResources();
        final String filePath = getFilePath(fileName, folderName);
        // Look-up identifier using reflection (expensive)
        int rawResourceId = resources.getIdentifier(fileName, folderName, context.getPackageName());
        // Store in cache if resource id is not 0
        if (rawResourceId != de.avpptr.umweltzone.contract.Resources.INVALID_RESOURCE_ID) {
            @SuppressWarnings("unchecked")
            LruCache<String, Integer> cache = (LruCache<String, Integer>) mCache;
            cache.put(filePath, rawResourceId);
        }
        return rawResourceId;
    }

    private static String getFilePath(final String fileName, final String folderName) {
        return folderName + "/" + fileName;
    }
}
