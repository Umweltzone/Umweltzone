/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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
