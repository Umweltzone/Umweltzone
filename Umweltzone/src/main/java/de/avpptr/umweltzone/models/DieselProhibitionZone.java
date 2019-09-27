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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;

@Parcel
public class DieselProhibitionZone implements ChildZone {

    @NonNull
    public String fileName;

    @NonNull
    public String displayName;

    @LowEmissionZoneNumbers.Color
    public int zoneNumber;

    @Nullable
    public Date zoneNumberForResidentsSince;

    @Nullable
    public Date zoneNumberForNonResidentsSince;

    @NonNull
    public List<String> prohibitedVehicles;

    public boolean isCongruentWithLowEmissionZone;

    @Nullable
    public String geometrySource;

    @Nullable
    public Date geometryUpdatedAt;

    @NonNull
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getZoneNumber() {
        return zoneNumber;
    }

    @Override
    public boolean containsGeometryInformation() {
        return (geometrySource != null && geometryUpdatedAt != null) || isCongruentWithLowEmissionZone;
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
        DieselProhibitionZone zone = (DieselProhibitionZone) other;
        if (zoneNumber != zone.zoneNumber) {
            return false;
        }
        if (isCongruentWithLowEmissionZone != zone.isCongruentWithLowEmissionZone) {
            return false;
        }
        if (!fileName.equals(zone.fileName)) {
            return false;
        }
        if (!displayName.equals(zone.displayName)) {
            return false;
        }
        if (zoneNumberForResidentsSince != null ? !zoneNumberForResidentsSince.equals(zone.zoneNumberForResidentsSince) : zone.zoneNumberForResidentsSince != null) {
            return false;
        }
        if (zoneNumberForNonResidentsSince != null ? !zoneNumberForNonResidentsSince.equals(zone.zoneNumberForNonResidentsSince) : zone.zoneNumberForNonResidentsSince != null) {
            return false;
        }
        if (!prohibitedVehicles.equals(zone.prohibitedVehicles)) {
            return false;
        }
        if (geometrySource != null ? !geometrySource.equals(zone.geometrySource) : zone.geometrySource != null) {
            return false;
        }
        return geometryUpdatedAt != null ? geometryUpdatedAt.equals(zone.geometryUpdatedAt) : zone.geometryUpdatedAt != null;
    }

    @Override
    public int hashCode() {
        int result = fileName.hashCode();
        result = 31 * result + displayName.hashCode();
        result = 31 * result + zoneNumber;
        result = 31 * result + (zoneNumberForResidentsSince != null ? zoneNumberForResidentsSince.hashCode() : 0);
        result = 31 * result + (zoneNumberForNonResidentsSince != null ? zoneNumberForNonResidentsSince.hashCode() : 0);
        result = 31 * result + prohibitedVehicles.hashCode();
        result = 31 * result + (isCongruentWithLowEmissionZone ? 1 : 0);
        result = 31 * result + (geometrySource != null ? geometrySource.hashCode() : 0);
        result = 31 * result + (geometryUpdatedAt != null ? geometryUpdatedAt.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "DieselProhibitionZone{" +
                "fileName='" + fileName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", zoneNumber=" + zoneNumber +
                ", zoneNumberForResidentsSince=" + zoneNumberForResidentsSince +
                ", zoneNumberForNonResidentsSince=" + zoneNumberForNonResidentsSince +
                ", prohibitedVehicles=" + prohibitedVehicles +
                ", isCongruentWithLowEmissionZone=" + isCongruentWithLowEmissionZone +
                ", geometrySource='" + geometrySource + '\'' +
                ", geometryUpdatedAt=" + geometryUpdatedAt +
                '}';
    }

}
