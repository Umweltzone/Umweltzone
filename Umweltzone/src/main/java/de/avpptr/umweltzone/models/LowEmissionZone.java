package de.avpptr.umweltzone.models;

import android.content.Context;

import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.PreferencesHelper;

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
    public String urlUmweltPlaketteDe;

    // TODO Parser should not be called more often then needed
    public static LowEmissionZone getRecentLowEmissionZone(Context context) {
        List<LowEmissionZone> lowEmissionZones = ContentProvider.getLowEmissionZones(context);
        if (lowEmissionZones == null) {
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        String zoneName = PreferencesHelper.restoreLastKnownLocationAsString(context);
        for (LowEmissionZone lowEmissionZone : lowEmissionZones) {
            if (lowEmissionZone.name.equalsIgnoreCase(zoneName)) {
                return lowEmissionZone;
            }
        }
        return null;
    }

    @Override public String toString() {
        return "name: " + name +
                ", displayName: " + displayName +
                ", center: " + center +
                ", boundingBox: " + boundingBox +
                ", zoneNumber: " + zoneNumber +
                ", zoneNumberSince: " + zoneNumberSince +
                ", nextZoneNumberAsOf: " + nextZoneNumberAsOf +
                ", abroadLicensedVehicleZoneNumber: " + abroadLicensedVehicleZoneNumber +
                ", abroadLicensedVehicleZoneNumberUntil: " + abroadLicensedVehicleZoneNumberUntil +
                ", urlUmweltPlaketteDe: " + urlUmweltPlaketteDe;
    }

}
