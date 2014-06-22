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

import java.util.List;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.models.Circuit;
import de.avpptr.umweltzone.utils.ContentProvider;

public class CircuitsCache extends GenericCache {

    public CircuitsCache(int maxSize) {
        mMaxSize = maxSize;
    }

    @Override
    protected void initialize() {
        mCache = new LruCache<String, List<Circuit>>(mMaxSize);
    }

    // Returns a list of circuits from the cache.
    // Expects one arguments: the zone name
    @Override
    protected Object readObject(String... objectData) {
        final String zoneName = objectData[0];
        return mCache.get(zoneName);
    }

    // Returns a list of circuits representing a zone
    // which might consist of one or multiple zones - or null.
    // Expects two arguments: the context and the zone name
    @Override
    protected Object writeObject(final Context context, final String... objectData) {
        if (objectData.length != 1) {
            throw new IllegalArgumentException(
                    "Expecting two arguments: context and zone name.");
        }
        final String zoneName = objectData[0];
        final String zoneFileName = "zone_" + zoneName;
        final List<Circuit> circuits = ContentProvider.getCircuits(context, zoneFileName);
        if (circuits == null || circuits.size() == 0) {
            Umweltzone.getTracker().trackError(TrackingPoint.NoCircuitPointsAvailableError);
            throw new IllegalStateException("There are no circuit points available");
        }
        @SuppressWarnings("unchecked")
        LruCache<String, List<Circuit>> cache = (LruCache<String, List<Circuit>>) mCache;
        cache.put(zoneName, circuits);
        return circuits;
    }

}
