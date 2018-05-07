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

package de.avpptr.umweltzone.prefs;

import android.content.SharedPreferences;

import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;

class BoundingBoxPreference {

    private final GeoPointPreference mSouthWestPreference;

    private final GeoPointPreference mNorthEastPreference;

    private BoundingBoxPreference(
            final SharedPreferences sharedPreferences,
            final String key,
            final BoundingBox defaultBoundingBox) {
        String keySouthWest = key + ".SOUTHWEST";
        String keyNorthEast = key + ".NORTHEAST";
        mSouthWestPreference = new GeoPointPreference(
                sharedPreferences, keySouthWest, defaultBoundingBox.getSouthWest());
        mNorthEastPreference = new GeoPointPreference(
                sharedPreferences, keyNorthEast, defaultBoundingBox.getNorthEast());
    }

    BoundingBoxPreference(
            final SharedPreferences sharedPreferences,
            final String key) {
        this(sharedPreferences, key, BoundingBox.getInvalidBoundingBox());
    }

    public BoundingBox get() {
        GeoPoint southWest = mSouthWestPreference.get();
        GeoPoint northEast = mNorthEastPreference.get();
        return new BoundingBox(southWest, northEast);
    }

    public boolean isSet() {
        return mSouthWestPreference.isSet() && mNorthEastPreference.isSet();
    }

    public void set(final BoundingBox location) {
        mSouthWestPreference.set(location.getSouthWest());
        mNorthEastPreference.set(location.getNorthEast());
    }

    public void delete() {
        mSouthWestPreference.delete();
        mNorthEastPreference.delete();
    }

}
