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

package de.avpptr.umweltzone;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.ligi.tracedroid.TraceDroid;

import de.avpptr.umweltzone.analytics.GoogleAnalyticsTracking;
import de.avpptr.umweltzone.analytics.NoTracking;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.prefs.PreferencesHelper;

public class Umweltzone extends Application {

    private static Tracking mTracking;

    private PreferencesHelper mPreferencesHelper;

    public static boolean centerZoneRequested = false;

    public void onCreate() {
        super.onCreate();
        initTracking();
        TraceDroid.init(this);
    }

    public void initTracking() {
        mTracking = getTracking(getApplicationContext());
    }

    private Tracking getTracking(Context context) {
        if (BuildConfig.DEBUG) {
            return new NoTracking();
        } else {
            boolean isEnabled = getPreferencesHelper().restoreGoogleAnalyticsIsEnabled();
            if (isEnabled) {
                return new GoogleAnalyticsTracking(context);
            } else {
                return new NoTracking();
            }
        }
    }

    public static Tracking getTracker() {
        return mTracking;
    }

    public PreferencesHelper getPreferencesHelper() {
        if (mPreferencesHelper == null) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            mPreferencesHelper = new PreferencesHelper(sharedPreferences);
        }
        return mPreferencesHelper;
    }
}
