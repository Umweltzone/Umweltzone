/*
 *  Copyright (C) 2016  Tobias Preuss
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
import de.avpptr.umweltzone.models.LowEmissionZone;

public class LowEmissionZoneBuilder {

    private String mName;

    private String mDisplayName;

    private List<String> mListOfCities;

    private BoundingBox mBoundingBox;

    @LowEmissionZoneNumbers.Color
    private int mZoneNumber;

    private Date mZoneNumberSince;

    private Date mNextZoneNumberAsOf;

    @LowEmissionZoneNumbers.Color
    private int mAbroadLicensedVehicleZoneNumber;

    private Date mAbroadLicensedVehicleZoneNumberUntil;

    private String mUrlUmweltPlaketteDe;

    private String mUrlBadgeOnline;

    private List<String> mContactEmails;

    private String mGeometrySource;

    private Date mGeometryUpdatedAt;

    public LowEmissionZoneBuilder setName(String name) {
        mName = name;
        return this;
    }

    public LowEmissionZoneBuilder setDisplayName(String displayName) {
        mDisplayName = displayName;
        return this;
    }

    public LowEmissionZoneBuilder setListOfCities(List<String> listOfCities) {
        mListOfCities = listOfCities;
        return this;
    }

    public LowEmissionZoneBuilder setBoundingBox(BoundingBox boundingBox) {
        mBoundingBox = boundingBox;
        return this;
    }

    public LowEmissionZoneBuilder setZoneNumber(@LowEmissionZoneNumbers.Color int zoneNumber) {
        mZoneNumber = zoneNumber;
        return this;
    }

    public LowEmissionZoneBuilder setZoneNumberSince(Date zoneNumberSince) {
        mZoneNumberSince = zoneNumberSince;
        return this;
    }

    public LowEmissionZoneBuilder setNextZoneNumberAsOf(Date nextZoneNumberAsOf) {
        mNextZoneNumberAsOf = nextZoneNumberAsOf;
        return this;
    }

    public LowEmissionZoneBuilder setAbroadLicensedVehicleZoneNumber(
            @LowEmissionZoneNumbers.Color int abroadLicensedVehicleZoneNumber) {
        mAbroadLicensedVehicleZoneNumber = abroadLicensedVehicleZoneNumber;
        return this;
    }

    public LowEmissionZoneBuilder setAbroadLicensedVehicleZoneNumberUntil(
            Date abroadLicensedVehicleZoneNumberUntil) {
        mAbroadLicensedVehicleZoneNumberUntil = abroadLicensedVehicleZoneNumberUntil;
        return this;
    }

    public LowEmissionZoneBuilder setUrlUmweltPlaketteDe(String urlUmweltPlaketteDe) {
        mUrlUmweltPlaketteDe = urlUmweltPlaketteDe;
        return this;
    }

    public LowEmissionZoneBuilder setUrlBadgeOnline(String urlBadgeOnline) {
        mUrlBadgeOnline = urlBadgeOnline;
        return this;
    }

    public LowEmissionZoneBuilder setContactEmails(List<String> contactEmails) {
        mContactEmails = contactEmails;
        return this;
    }

    public LowEmissionZoneBuilder setGeometrySource(String geometrySource) {
        mGeometrySource = geometrySource;
        return this;
    }

    public LowEmissionZoneBuilder setGeometryUpdatedAt(Date geometryUpdatedAt) {
        mGeometryUpdatedAt = geometryUpdatedAt;
        return this;
    }

    public LowEmissionZone build() {
        LowEmissionZone lowEmissionZone = new LowEmissionZone();
        lowEmissionZone.name = mName;
        lowEmissionZone.displayName = mDisplayName;
        lowEmissionZone.listOfCities = mListOfCities;
        lowEmissionZone.boundingBox = mBoundingBox;
        lowEmissionZone.zoneNumber = mZoneNumber;
        lowEmissionZone.zoneNumberSince = mZoneNumberSince;
        lowEmissionZone.nextZoneNumberAsOf = mNextZoneNumberAsOf;
        lowEmissionZone.abroadLicensedVehicleZoneNumber =
                mAbroadLicensedVehicleZoneNumber;
        lowEmissionZone.abroadLicensedVehicleZoneNumberUntil =
                mAbroadLicensedVehicleZoneNumberUntil;
        lowEmissionZone.urlUmweltPlaketteDe = mUrlUmweltPlaketteDe;
        lowEmissionZone.urlBadgeOnline = mUrlBadgeOnline;
        lowEmissionZone.contactEmails = mContactEmails;
        lowEmissionZone.geometrySource = mGeometrySource;
        lowEmissionZone.geometryUpdatedAt = mGeometryUpdatedAt;
        return lowEmissionZone;
    }

}
