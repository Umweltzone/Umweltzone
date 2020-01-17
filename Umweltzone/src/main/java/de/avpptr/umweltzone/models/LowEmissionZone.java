/*
 *  Copyright (C) 2020  Tobias Preuss
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

import androidx.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;

@Parcel
public class LowEmissionZone implements ChildZone {

    public String fileName;

    public String displayName;

    @LowEmissionZoneNumbers.Color
    public int zoneNumber;

    public Date zoneNumberSince;

    public Date nextZoneNumberAsOf;

    @LowEmissionZoneNumbers.Color
    public int abroadLicensedVehicleZoneNumber;

    public Date abroadLicensedVehicleZoneNumberUntil;

    public List<String> listOfCities;

    public String geometrySource;

    public Date geometryUpdatedAt;

    @Override
    public boolean containsGeometryInformation() {
        return geometrySource != null && geometryUpdatedAt != null;
    }

    @NonNull
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getZoneNumber() {
        return zoneNumber;
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LowEmissionZone zone = (LowEmissionZone) other;
        if (zoneNumber != zone.zoneNumber) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumber != zone.abroadLicensedVehicleZoneNumber) {
            return false;
        }
        if (fileName != null ? !fileName.equals(zone.fileName) : zone.fileName != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(zone.displayName) : zone.displayName != null) {
            return false;
        }
        if (zoneNumberSince != null ? !zoneNumberSince.equals(zone.zoneNumberSince) : zone.zoneNumberSince != null) {
            return false;
        }
        if (nextZoneNumberAsOf != null ? !nextZoneNumberAsOf.equals(zone.nextZoneNumberAsOf) : zone.nextZoneNumberAsOf != null) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumberUntil != null ? !abroadLicensedVehicleZoneNumberUntil.equals(zone.abroadLicensedVehicleZoneNumberUntil) : zone.abroadLicensedVehicleZoneNumberUntil != null) {
            return false;
        }
        if (listOfCities != null ? !listOfCities.equals(zone.listOfCities) : zone.listOfCities != null) {
            return false;
        }
        if (geometrySource != null ? !geometrySource.equals(zone.geometrySource) : zone.geometrySource != null) {
            return false;
        }
        return geometryUpdatedAt != null ? geometryUpdatedAt.equals(zone.geometryUpdatedAt) : zone.geometryUpdatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + zoneNumber;
        result = 31 * result + (zoneNumberSince != null ? zoneNumberSince.hashCode() : 0);
        result = 31 * result + (nextZoneNumberAsOf != null ? nextZoneNumberAsOf.hashCode() : 0);
        result = 31 * result + abroadLicensedVehicleZoneNumber;
        result = 31 * result + (abroadLicensedVehicleZoneNumberUntil != null ? abroadLicensedVehicleZoneNumberUntil.hashCode() : 0);
        result = 31 * result + (listOfCities != null ? listOfCities.hashCode() : 0);
        result = 31 * result + (geometrySource != null ? geometrySource.hashCode() : 0);
        result = 31 * result + (geometryUpdatedAt != null ? geometryUpdatedAt.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "LowEmissionZone{" +
                "fileName='" + fileName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", zoneNumber=" + zoneNumber +
                ", zoneNumberSince=" + zoneNumberSince +
                ", nextZoneNumberAsOf=" + nextZoneNumberAsOf +
                ", abroadLicensedVehicleZoneNumber=" + abroadLicensedVehicleZoneNumber +
                ", abroadLicensedVehicleZoneNumberUntil=" + abroadLicensedVehicleZoneNumberUntil +
                ", listOfCities=" + listOfCities +
                ", geometrySource='" + geometrySource + '\'' +
                ", geometryUpdatedAt=" + geometryUpdatedAt +
                '}';
    }

}
