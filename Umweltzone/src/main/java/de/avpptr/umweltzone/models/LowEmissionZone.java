package de.avpptr.umweltzone.models;

import com.google.android.gms.maps.model.LatLng;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.Converter;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.PointsProvider;

public class LowEmissionZone {
    public String name;
    public String displayName;
    public GeoPoint center;
    public BoundingBox boundingBox;
    public int zoneNumber;

    public static Iterable<LatLng> getCircuitPoints(String cityName) {
        PointsProvider.Location location = Converter.cityNameToLocation(cityName);
        return PointsProvider.getPoints(location);
    }

    @Override public String toString() {
        return "name: " + name +
                ", displayName: " + displayName +
                ", center: " + center +
                ", boundingBox: " + boundingBox +
                ", zoneNumber: " + zoneNumber;
    }

}
