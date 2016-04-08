/*
 *  Copyright (C) 2016  Tobias Preuss, Peter Vasil
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

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.ContentProvider;

@Parcel
public class LowEmissionZone {

    public String name;

    public String displayName;

    public List<String> listOfCities;

    public BoundingBox boundingBox;

    @LowEmissionZoneNumbers.Color
    public int zoneNumber;

    public Date zoneNumberSince;

    public Date nextZoneNumberAsOf;

    @LowEmissionZoneNumbers.Color
    public int abroadLicensedVehicleZoneNumber;

    public Date abroadLicensedVehicleZoneNumberUntil;

    public String urlUmweltPlaketteDe;

    public String urlBadgeOnline;

    public List<String> contactEmails;

    public String geometrySource;

    public Date geometryUpdatedAt;

    // Used for caching
    private static List<LowEmissionZone> mLowEmissionZones;

    @Nullable
    public static LowEmissionZone getRecentLowEmissionZone(Context context) {
        Umweltzone application = (Umweltzone) context.getApplicationContext();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        String zoneName = preferencesHelper.restoreLastKnownLocationAsString();
        return getLowEmissionZone(context, zoneName);
    }

    @Nullable
    public static LowEmissionZone getDefaultLowEmissionZone(Context context) {
        String defaultZone = context.getString(R.string.config_default_zone_name);
        return getLowEmissionZone(context, defaultZone);
    }

    // TODO Parser should not be called more often then needed
    @Nullable
    private static LowEmissionZone getLowEmissionZone(Context context, String zoneName) {
        if (mLowEmissionZones == null) {
            mLowEmissionZones = ContentProvider.getLowEmissionZones(context);
        }
        if (mLowEmissionZones.isEmpty()) {
            Umweltzone.getTracker().trackError(TrackingPoint.ParsingZonesFromJSONFailedError, null);
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        for (int i = 0, size = mLowEmissionZones.size(); i < size; i++) {
            LowEmissionZone lowEmissionZone = mLowEmissionZones.get(i);
            if (lowEmissionZone.name.equalsIgnoreCase(zoneName)) {
                return lowEmissionZone;
            }
        }
        return null;
    }

    public boolean containsGeometryInformation() {
        return geometrySource != null && geometryUpdatedAt != null;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LowEmissionZone lez = (LowEmissionZone) other;
        if (zoneNumber != lez.zoneNumber) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumber != lez.abroadLicensedVehicleZoneNumber) {
            return false;
        }
        if (name != null ? !name.equals(lez.name) : lez.name != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(lez.displayName)
                : lez.displayName != null) {
            return false;
        }
        if (listOfCities != null ? !listOfCities.equals(lez.listOfCities)
                : lez.listOfCities != null) {
            return false;
        }
        if (boundingBox != null ? !boundingBox.equals(lez.boundingBox)
                : lez.boundingBox != null) {
            return false;
        }
        if (zoneNumberSince != null ? !zoneNumberSince.equals(lez.zoneNumberSince)
                : lez.zoneNumberSince != null) {
            return false;
        }
        if (nextZoneNumberAsOf != null ? !nextZoneNumberAsOf.equals(lez.nextZoneNumberAsOf)
                : lez.nextZoneNumberAsOf != null) {
            return false;
        }
        if (abroadLicensedVehicleZoneNumberUntil != null ? !abroadLicensedVehicleZoneNumberUntil
                .equals(lez.abroadLicensedVehicleZoneNumberUntil)
                : lez.abroadLicensedVehicleZoneNumberUntil != null) {
            return false;
        }
        if (urlUmweltPlaketteDe != null ? !urlUmweltPlaketteDe.equals(lez.urlUmweltPlaketteDe)
                : lez.urlUmweltPlaketteDe != null) {
            return false;
        }
        if (urlBadgeOnline != null ? !urlBadgeOnline.equals(lez.urlBadgeOnline)
                : lez.urlBadgeOnline != null) {
            return false;
        }
        if (contactEmails != null ? !contactEmails.equals(lez.contactEmails)
                : lez.contactEmails != null) {
            return false;
        }
        if (geometrySource != null ? !geometrySource.equals(lez.geometrySource)
                : lez.geometrySource != null) {
            return false;
        }
        return geometryUpdatedAt != null ? geometryUpdatedAt.equals(lez.geometryUpdatedAt)
                : lez.geometryUpdatedAt == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (listOfCities != null ? listOfCities.hashCode() : 0);
        result = 31 * result + (boundingBox != null ? boundingBox.hashCode() : 0);
        result = 31 * result + zoneNumber;
        result = 31 * result + (zoneNumberSince != null ? zoneNumberSince.hashCode() : 0);
        result = 31 * result + (nextZoneNumberAsOf != null ? nextZoneNumberAsOf.hashCode() : 0);
        result = 31 * result + abroadLicensedVehicleZoneNumber;
        result = 31 * result + (abroadLicensedVehicleZoneNumberUntil != null
                ? abroadLicensedVehicleZoneNumberUntil.hashCode() : 0);
        result = 31 * result + (urlUmweltPlaketteDe != null ? urlUmweltPlaketteDe.hashCode() : 0);
        result = 31 * result + (urlBadgeOnline != null ? urlBadgeOnline.hashCode() : 0);
        result = 31 * result + (contactEmails != null ? contactEmails.hashCode() : 0);
        result = 31 * result + (geometrySource != null ? geometrySource.hashCode() : 0);
        result = 31 * result + (geometryUpdatedAt != null ? geometryUpdatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "name: " + name +
                ", displayName: " + displayName +
                ", listOfCities: " + listOfCities +
                ", boundingBox: " + boundingBox +
                ", zoneNumber: " + zoneNumber +
                ", zoneNumberSince: " + zoneNumberSince +
                ", nextZoneNumberAsOf: " + nextZoneNumberAsOf +
                ", abroadLicensedVehicleZoneNumber: " + abroadLicensedVehicleZoneNumber +
                ", abroadLicensedVehicleZoneNumberUntil: " + abroadLicensedVehicleZoneNumberUntil +
                ", urlUmweltPlaketteDe: " + urlUmweltPlaketteDe +
                ", urlBadgeOnline: " + urlBadgeOnline +
                ", contactEmails: " + contactEmails +
                ", geometrySource: " + geometrySource +
                ", geometryUpdatedAt: " + geometryUpdatedAt;
    }

}
