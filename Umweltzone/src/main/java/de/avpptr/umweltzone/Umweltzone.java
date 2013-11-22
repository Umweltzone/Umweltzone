/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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

import org.ligi.tracedroid.TraceDroid;

import de.avpptr.umweltzone.analytics.GoogleAnalyticsTracking;
import de.avpptr.umweltzone.analytics.Tracking;

public class Umweltzone extends Application {

    private static GoogleAnalyticsTracking mGa;

    public void onCreate() {
        super.onCreate();

        mGa = new GoogleAnalyticsTracking(getApplicationContext());

        TraceDroid.init(this);
    }

    public static Tracking getTracker() {
        return mGa;
    }
}
