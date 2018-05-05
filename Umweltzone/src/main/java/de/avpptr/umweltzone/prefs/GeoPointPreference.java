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

import de.avpptr.umweltzone.utils.GeoPoint;
import info.metadude.android.typedpreferences.DoublePreference;

public class GeoPointPreference {

    protected final DoublePreference mLatitudePreference;

    protected final DoublePreference mLongitudePreference;

    public GeoPointPreference(
            final SharedPreferences sharedPreferences,
            final String key,
            final GeoPoint defaultLocation) {
        String keyLatitude = key + ".LATITUDE";
        String keyLongitude = key + ".LONGITUDE";
        mLatitudePreference = new DoublePreference(
                sharedPreferences, keyLatitude, defaultLocation.getLatitude());
        mLongitudePreference = new DoublePreference(
                sharedPreferences, keyLongitude, defaultLocation.getLongitude());
    }

    public GeoPointPreference(
            final SharedPreferences sharedPreferences,
            final String key) {
        this(sharedPreferences, key, GeoPoint.getInvalidGeoPoint());
    }

    public GeoPoint get() {
        double latitude = mLatitudePreference.get();
        double longitude = mLongitudePreference.get();
        return new GeoPoint(latitude, longitude);
    }

    public boolean isSet() {
        return mLatitudePreference.isSet() && mLongitudePreference.isSet();
    }

    public void set(final GeoPoint location) {
        mLatitudePreference.set(location.getLatitude());
        mLongitudePreference.set(location.getLongitude());
    }

    public void delete() {
        mLatitudePreference.delete();
        mLongitudePreference.delete();
    }

}
