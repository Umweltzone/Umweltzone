/*=========================================================================
 Copyright (c) Tobias Preuss, 2012
=========================================================================*/

package de.avpptr.umweltzone.utils;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;


public class BoundingBoxHelper {

    public static BoundingBox getBoundingBox(GoogleMap map) {
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        GeoPoint southWest = new GeoPoint(bounds.southwest);
        GeoPoint northEast = new GeoPoint(bounds.northeast);
        BoundingBox boundingBox = new BoundingBox(southWest, northEast);
        Log.d(BoundingBoxHelper.class.getName(), "Bounding box = " + boundingBox);
        return boundingBox;
    }

}
