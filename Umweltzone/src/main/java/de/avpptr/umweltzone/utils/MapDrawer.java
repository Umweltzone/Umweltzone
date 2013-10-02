package de.avpptr.umweltzone.utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapDrawer {

    protected GoogleMap mMap;

    public MapDrawer(GoogleMap map) {
        mMap = map;
    }

    public void drawPolygon(Iterable<LatLng> points, int fillColor, int strokeColor, int strokeWidth) {
        mMap.addPolygon(new PolygonOptions()
                .addAll(points)
                .strokeWidth(strokeWidth)
                .fillColor(fillColor)
                .strokeColor(strokeColor));
    }

}
