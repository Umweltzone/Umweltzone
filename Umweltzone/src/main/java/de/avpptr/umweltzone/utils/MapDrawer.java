package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapDrawer {

    protected GoogleMap mMap;

    public MapDrawer(GoogleMap map) {
        mMap = map;
    }

    public void drawPolygon(Iterable<LatLng> points, int color) {
        mMap.addPolygon(new PolygonOptions()
                .addAll(points)
                .strokeColor(color));
    }

}
