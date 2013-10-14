package de.avpptr.umweltzone.models;

import java.util.Date;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;

public class LowEmissionZone {
    public String name;
    public String displayName;
    public GeoPoint center;
    public BoundingBox boundingBox;
    public int zoneNumber;
    public Date zoneNumberSince;
    public Date nextZoneNumberAsOf;
    public int abroadLicensedVehicleZoneNumber;
    public Date abroadLicensedVehicleZoneNumberUntil;

    @Override public String toString() {
        return "name: " + name +
                ", displayName: " + displayName +
                ", center: " + center +
                ", boundingBox: " + boundingBox +
                ", zoneNumber: " + zoneNumber +
                ", zoneNumberSince: " + zoneNumberSince +
                ", nextZoneNumberAsOf: " + nextZoneNumberAsOf +
                ", abroadLicensedVehicleZoneNumber: " + abroadLicensedVehicleZoneNumber +
                ", abroadLicensedVehicleZoneNumberUntil: " + abroadLicensedVehicleZoneNumberUntil;
    }

}
