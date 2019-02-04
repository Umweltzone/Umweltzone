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

import de.avpptr.umweltzone.models.ChildZone;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class LowEmissionZoneBuilder {

    private String mName;

    private String mDisplayName;

    private BoundingBox mBoundingBox;

    private String mUrlUmweltPlaketteDe;

    private String mUrlBadgeOnline;

    private List<String> mContactEmails;

    public List<ChildZone> mChildZones;

    public LowEmissionZoneBuilder setName(String name) {
        mName = name;
        return this;
    }

    public LowEmissionZoneBuilder setDisplayName(String displayName) {
        mDisplayName = displayName;
        return this;
    }

    public LowEmissionZoneBuilder setBoundingBox(BoundingBox boundingBox) {
        mBoundingBox = boundingBox;
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

    public LowEmissionZoneBuilder setChildZones(List<ChildZone> childZones) {
        mChildZones = childZones;
        return this;
    }

    public LowEmissionZone build() {
        LowEmissionZone lowEmissionZone = new LowEmissionZone();
        lowEmissionZone.name = mName;
        lowEmissionZone.displayName = mDisplayName;
        lowEmissionZone.boundingBox = mBoundingBox;
        lowEmissionZone.urlUmweltPlaketteDe = mUrlUmweltPlaketteDe;
        lowEmissionZone.urlBadgeOnline = mUrlBadgeOnline;
        lowEmissionZone.contactEmails = mContactEmails;
        lowEmissionZone.childZones = mChildZones;
        return lowEmissionZone;
    }

}
