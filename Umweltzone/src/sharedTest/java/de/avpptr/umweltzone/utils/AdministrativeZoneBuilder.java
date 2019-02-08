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

import java.util.List;

import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class AdministrativeZoneBuilder {

    private String mName;

    private String mDisplayName;

    private BoundingBox mBoundingBox;

    private String mUrlUmweltPlaketteDe;

    private String mUrlBadgeOnline;

    private List<String> mContactEmails;

    public List<LowEmissionZone> mChildZones;

    public AdministrativeZoneBuilder setName(String name) {
        mName = name;
        return this;
    }

    public AdministrativeZoneBuilder setDisplayName(String displayName) {
        mDisplayName = displayName;
        return this;
    }

    public AdministrativeZoneBuilder setBoundingBox(BoundingBox boundingBox) {
        mBoundingBox = boundingBox;
        return this;
    }

    public AdministrativeZoneBuilder setUrlUmweltPlaketteDe(String urlUmweltPlaketteDe) {
        mUrlUmweltPlaketteDe = urlUmweltPlaketteDe;
        return this;
    }

    public AdministrativeZoneBuilder setUrlBadgeOnline(String urlBadgeOnline) {
        mUrlBadgeOnline = urlBadgeOnline;
        return this;
    }

    public AdministrativeZoneBuilder setContactEmails(List<String> contactEmails) {
        mContactEmails = contactEmails;
        return this;
    }

    public AdministrativeZoneBuilder setChildZones(List<LowEmissionZone> childZones) {
        mChildZones = childZones;
        return this;
    }

    public AdministrativeZone build() {
        AdministrativeZone administrativeZone = new AdministrativeZone();
        administrativeZone.name = mName;
        administrativeZone.displayName = mDisplayName;
        administrativeZone.boundingBox = mBoundingBox;
        administrativeZone.urlUmweltPlaketteDe = mUrlUmweltPlaketteDe;
        administrativeZone.urlBadgeOnline = mUrlBadgeOnline;
        administrativeZone.contactEmails = mContactEmails;
        administrativeZone.childZones = mChildZones;
        return administrativeZone;
    }

}
