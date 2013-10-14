package de.avpptr.umweltzone.models;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;

public class LowEmissionZone {
    public String name;
    public String displayName;
    public GeoPoint center;
    public BoundingBox boundingBox;
    public int zoneNumber;

    @Override public String toString() {
        return "name: " + name +
                ", displayName: " + displayName +
                ", center: " + center +
                ", boundingBox: " + boundingBox +
                ", zoneNumber: " + zoneNumber;
    }

}
