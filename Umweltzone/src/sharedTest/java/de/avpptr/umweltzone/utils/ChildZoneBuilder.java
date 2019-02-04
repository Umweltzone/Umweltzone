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

import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.ChildZone;

public class ChildZoneBuilder {

    private String mName;

    private String mDisplayName;

    @LowEmissionZoneNumbers.Color
    private int mZoneNumber;

    private Date mZoneNumberSince;

    private Date mNextZoneNumberAsOf;

    @LowEmissionZoneNumbers.Color
    private int mAbroadLicensedVehicleZoneNumber;

    private Date mAbroadLicensedVehicleZoneNumberUntil;

    private List<String> mListOfCities;

    private String mGeometrySource;

    private Date mGeometryUpdatedAt;

    public List<ChildZone> mChildZones;

    public ChildZoneBuilder setName(String name) {
        mName = name;
        return this;
    }

    public ChildZoneBuilder setDisplayName(String displayName) {
        mDisplayName = displayName;
        return this;
    }

    public ChildZoneBuilder setZoneNumber(@LowEmissionZoneNumbers.Color int zoneNumber) {
        mZoneNumber = zoneNumber;
        return this;
    }

    public ChildZoneBuilder setZoneNumberSince(Date zoneNumberSince) {
        mZoneNumberSince = zoneNumberSince;
        return this;
    }

    public ChildZoneBuilder setNextZoneNumberAsOf(Date nextZoneNumberAsOf) {
        mNextZoneNumberAsOf = nextZoneNumberAsOf;
        return this;
    }

    public ChildZoneBuilder setAbroadLicensedVehicleZoneNumber(
            @LowEmissionZoneNumbers.Color int abroadLicensedVehicleZoneNumber) {
        mAbroadLicensedVehicleZoneNumber = abroadLicensedVehicleZoneNumber;
        return this;
    }

    public ChildZoneBuilder setAbroadLicensedVehicleZoneNumberUntil(
            Date abroadLicensedVehicleZoneNumberUntil) {
        mAbroadLicensedVehicleZoneNumberUntil = abroadLicensedVehicleZoneNumberUntil;
        return this;
    }

    public ChildZoneBuilder setListOfCities(List<String> listOfCities) {
        mListOfCities = listOfCities;
        return this;
    }

    public ChildZoneBuilder setGeometrySource(String geometrySource) {
        mGeometrySource = geometrySource;
        return this;
    }

    public ChildZoneBuilder setGeometryUpdatedAt(Date geometryUpdatedAt) {
        mGeometryUpdatedAt = geometryUpdatedAt;
        return this;
    }

    public ChildZoneBuilder setChildZones(List<ChildZone> childZones) {
        mChildZones = childZones;
        return this;
    }

    public ChildZone build() {
        ChildZone zone = new ChildZone();
        zone.name = mName;
        zone.displayName = mDisplayName;
        zone.zoneNumber = mZoneNumber;
        zone.zoneNumberSince = mZoneNumberSince;
        zone.nextZoneNumberAsOf = mNextZoneNumberAsOf;
        zone.abroadLicensedVehicleZoneNumber = mAbroadLicensedVehicleZoneNumber;
        zone.abroadLicensedVehicleZoneNumberUntil = mAbroadLicensedVehicleZoneNumberUntil;
        zone.listOfCities = mListOfCities;
        zone.geometrySource = mGeometrySource;
        zone.geometryUpdatedAt = mGeometryUpdatedAt;
        return zone;
    }

}
