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

package de.avpptr.umweltzone.models;

import android.content.Context;
import android.support.annotation.Nullable;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.ContentProvider;

@Parcel
public class AdministrativeZone {

    public String name;

    public String displayName;

    public BoundingBox boundingBox;

    public String urlUmweltPlaketteDe;

    public String urlBadgeOnline;

    public List<String> contactEmails;

    @ParcelPropertyConverter(ChildZonesParcelConverter.class)
    public List<ChildZone> childZones;

    // Used for caching
    private static List<AdministrativeZone> mAdministrativeZones;

    @Nullable
    public static AdministrativeZone getRecentAdministrativeZone(Context context) {
        Umweltzone application = (Umweltzone) context.getApplicationContext();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        String zoneName = preferencesHelper.restoreLastKnownLocationAsString();
        return getAdministrativeZone(context, zoneName);
    }

    @Nullable
    public static AdministrativeZone getDefaultAdministrativeZone(Context context) {
        String defaultZone = context.getString(R.string.config_default_zone_name);
        return getAdministrativeZone(context, defaultZone);
    }

    // TODO Parser should not be called more often then needed
    @Nullable
    private static AdministrativeZone getAdministrativeZone(Context context, String zoneName) {
        if (mAdministrativeZones == null) {
            mAdministrativeZones = ContentProvider.getAdministrativeZones(context);
        }
        if (mAdministrativeZones.isEmpty()) {
            Umweltzone.getTracker().trackError(TrackingPoint.ParsingZonesFromJSONFailedError, null);
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        for (int i = 0, size = mAdministrativeZones.size(); i < size; i++) {
            AdministrativeZone administrativeZone = mAdministrativeZones.get(i);
            if (administrativeZone.name.equalsIgnoreCase(zoneName)) {
                return administrativeZone;
            }
        }
        return null;
    }

    public boolean containsGeometryInformation() {
        for (ChildZone childZone : childZones) {
            if (childZone.containsGeometryInformation()) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings({"SimplifiableIfStatement", "EqualsReplaceableByObjectsCall"})
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        AdministrativeZone zone = (AdministrativeZone) other;
        if (name != null ? !name.equals(zone.name) : zone.name != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(zone.displayName)
                : zone.displayName != null) {
            return false;
        }
        if (boundingBox != null ? !boundingBox.equals(zone.boundingBox)
                : zone.boundingBox != null) {
            return false;
        }
        if (urlUmweltPlaketteDe != null ? !urlUmweltPlaketteDe.equals(zone.urlUmweltPlaketteDe)
                : zone.urlUmweltPlaketteDe != null) {
            return false;
        }
        if (urlBadgeOnline != null ? !urlBadgeOnline.equals(zone.urlBadgeOnline)
                : zone.urlBadgeOnline != null) {
            return false;
        }
        if (contactEmails != null ? !contactEmails.equals(zone.contactEmails)
                : zone.contactEmails != null) {
            return false;
        }
        return childZones != null ? childZones.equals(zone.childZones)
                : zone.childZones == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (boundingBox != null ? boundingBox.hashCode() : 0);
        result = 31 * result + (urlUmweltPlaketteDe != null ? urlUmweltPlaketteDe.hashCode() : 0);
        result = 31 * result + (urlBadgeOnline != null ? urlBadgeOnline.hashCode() : 0);
        result = 31 * result + (contactEmails != null ? contactEmails.hashCode() : 0);
        result = 31 * result + (childZones != null ? childZones.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdministrativeZone{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", boundingBox=" + boundingBox +
                ", urlUmweltPlaketteDe='" + urlUmweltPlaketteDe + '\'' +
                ", urlBadgeOnline='" + urlBadgeOnline + '\'' +
                ", contactEmails=" + contactEmails +
                ", childZones=" + childZones +
                '}';
    }

}
