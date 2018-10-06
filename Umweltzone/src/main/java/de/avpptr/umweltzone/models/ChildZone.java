/*
 *  Copyright (C) 2018  Tobias Preuss
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

import org.parceler.Parcel;

import java.util.Date;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;

@Parcel
public class ChildZone {

    public String name;

    public String displayName;

    @LowEmissionZoneNumbers.Color
    public int zoneNumber;

    public Date zoneNumberSince;

    public Date nextZoneNumberAsOf;

    @LowEmissionZoneNumbers.Color
    public int abroadLicensedVehicleZoneNumber;

    public Date abroadLicensedVehicleZoneNumberUntil;

    public String geometrySource;

    public Date geometryUpdatedAt;

    public boolean containsGeometryInformation() {
        return geometrySource != null && geometryUpdatedAt != null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ChildZone childZone = (ChildZone) other;
        if (zoneNumber != childZone.zoneNumber) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumber != childZone.abroadLicensedVehicleZoneNumber) {
            return false;
        }
        if (name != null ? !name.equals(childZone.name) : childZone.name != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(childZone.displayName) : childZone.displayName != null) {
            return false;
        }
        if (zoneNumberSince != null ? !zoneNumberSince.equals(childZone.zoneNumberSince) : childZone.zoneNumberSince != null) {
            return false;
        }
        if (nextZoneNumberAsOf != null ? !nextZoneNumberAsOf.equals(childZone.nextZoneNumberAsOf) : childZone.nextZoneNumberAsOf != null) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumberUntil != null ? !abroadLicensedVehicleZoneNumberUntil.equals(childZone.abroadLicensedVehicleZoneNumberUntil) : childZone.abroadLicensedVehicleZoneNumberUntil != null) {
            return false;
        }
        if (geometrySource != null ? !geometrySource.equals(childZone.geometrySource) : childZone.geometrySource != null) {
            return false;
        }
        return geometryUpdatedAt != null ? geometryUpdatedAt.equals(childZone.geometryUpdatedAt) : childZone.geometryUpdatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + zoneNumber;
        result = 31 * result + (zoneNumberSince != null ? zoneNumberSince.hashCode() : 0);
        result = 31 * result + (nextZoneNumberAsOf != null ? nextZoneNumberAsOf.hashCode() : 0);
        result = 31 * result + abroadLicensedVehicleZoneNumber;
        result = 31 * result + (abroadLicensedVehicleZoneNumberUntil != null ? abroadLicensedVehicleZoneNumberUntil.hashCode() : 0);
        result = 31 * result + (geometrySource != null ? geometrySource.hashCode() : 0);
        result = 31 * result + (geometryUpdatedAt != null ? geometryUpdatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChildZone{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", zoneNumber=" + zoneNumber +
                ", zoneNumberSince=" + zoneNumberSince +
                ", nextZoneNumberAsOf=" + nextZoneNumberAsOf +
                ", abroadLicensedVehicleZoneNumber=" + abroadLicensedVehicleZoneNumber +
                ", abroadLicensedVehicleZoneNumberUntil=" + abroadLicensedVehicleZoneNumberUntil +
                ", geometrySource='" + geometrySource + '\'' +
                ", geometryUpdatedAt=" + geometryUpdatedAt +
                '}';
    }

}
