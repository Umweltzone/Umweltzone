/*
 *  Copyright (C) 2019  Tobias Preuss
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

package de.avpptr.umweltzone.utils;

import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.models.DieselProhibitionZone;

public class DieselProhibitionZoneBuilder {

    private String fileName;
    private String displayName;
    private int zoneNumber;
    private Date zoneNumberForResidentsSince;
    private Date zoneNumberForNonResidentsSince;
    private List<String> prohibitedVehicles;
    private boolean isCongruentWithLowEmissionZone;
    private String geometrySource;
    private Date geometryUpdatedAt;

    public DieselProhibitionZoneBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public DieselProhibitionZoneBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public DieselProhibitionZoneBuilder setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
        return this;
    }

    public DieselProhibitionZoneBuilder setZoneNumberForResidentsSince(Date zoneNumberForResidentsSince) {
        this.zoneNumberForResidentsSince = zoneNumberForResidentsSince;
        return this;
    }

    public DieselProhibitionZoneBuilder setZoneNumberForNonResidentsSince(Date zoneNumberForNonResidentsSince) {
        this.zoneNumberForNonResidentsSince = zoneNumberForNonResidentsSince;
        return this;
    }

    public DieselProhibitionZoneBuilder setProhibitedVehicles(List<String> prohibitedVehicles) {
        this.prohibitedVehicles = prohibitedVehicles;
        return this;
    }

    public DieselProhibitionZoneBuilder setIsCongruentWithLowEmissionZone(boolean isCongruentWithLowEmissionZone) {
        this.isCongruentWithLowEmissionZone = isCongruentWithLowEmissionZone;
        return this;
    }

    public DieselProhibitionZoneBuilder setGeometrySource(String geometrySource) {
        this.geometrySource = geometrySource;
        return this;
    }

    public DieselProhibitionZoneBuilder setGeometryUpdatedAt(Date geometryUpdatedAt) {
        this.geometryUpdatedAt = geometryUpdatedAt;
        return this;
    }

    public DieselProhibitionZone build() {
        DieselProhibitionZone zone = new DieselProhibitionZone();
        zone.fileName = fileName;
        zone.displayName = displayName;
        zone.zoneNumber = zoneNumber;
        zone.zoneNumberForNonResidentsSince = zoneNumberForNonResidentsSince;
        zone.zoneNumberForResidentsSince = zoneNumberForResidentsSince;
        zone.prohibitedVehicles = prohibitedVehicles;
        zone.isCongruentWithLowEmissionZone = isCongruentWithLowEmissionZone;
        zone.geometrySource = geometrySource;
        zone.geometryUpdatedAt = geometryUpdatedAt;
        return zone;
    }

}
