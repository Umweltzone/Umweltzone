package de.avpptr.umweltzone.caching;

import android.content.Context;
import android.support.v4.util.LruCache;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.GeoPoint;

public class CircuitPointsCache extends GenericCache {

    public CircuitPointsCache(int maxSize) {
        mMaxSize = maxSize;
    }

    @Override
    protected void initialize() {
        mCache = new LruCache<String, List<LatLng>>(mMaxSize);
    }

    // Returns a list of points from the cache.
    // Expects one arguments: the zone name
    @Override
    protected Object readObject(String... objectData) {
        final String zoneName = objectData[0];
        return mCache.get(zoneName);
    }

    // Returns a list of points representing the zone or null.
    // Expects two arguments: the context and the zone name
    @Override
    protected Object writeObject(final Context context, final String... objectData) {
        if (objectData.length != 1) {
            throw new IllegalArgumentException(
                    "Expecting two arguments: context and zone name.");
        }
        final String zoneName = objectData[0];
        final String zoneFileName = "zone_" + zoneName;
        final List<GeoPoint> geoPoints = ContentProvider.getCircuitPoints(context, zoneFileName);
        if (geoPoints == null || geoPoints.size() == 0) {
            Umweltzone.getTracker().trackError(TrackingPoint.NoCircuitPointsAvailableError);
            throw new IllegalStateException("There are no circuit points available");
        }
        final List<LatLng> latLngPoints = getLatLngPoints(geoPoints);
        @SuppressWarnings("unchecked")
        LruCache<String, List<LatLng>> cache = (LruCache<String, List<LatLng>>) mCache;
        cache.put(zoneName, latLngPoints);
        return latLngPoints;
    }

    // Returns a list of points converted into LatLng data type
    private static List<LatLng> getLatLngPoints(final List<GeoPoint> geoPoints) {
        final List<LatLng> latLngPoints = new ArrayList<LatLng>(geoPoints.size());
        for (final GeoPoint point : geoPoints) {
            latLngPoints.add(point.toLatLng());
        }
        return latLngPoints;
    }

}
