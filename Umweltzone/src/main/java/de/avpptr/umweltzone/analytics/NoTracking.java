/*
 *  Copyright (C) 2015  Tobias Preuss, Peter Vasil
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

package de.avpptr.umweltzone.analytics;

import android.util.Log;

public class NoTracking implements Tracking {

    @Override
    public void track(TrackingPoint eventName, Object parameter) {
        Log.d(getClass().getName(), getLogMessage(eventName, parameter));
    }

    @Override
    public void trackError(TrackingPoint eventName, Object parameter) {
        Log.e(getClass().getName(), getLogMessage(eventName, parameter));
    }

    private String getLogMessage(TrackingPoint eventName, Object parameter) {
        return (parameter == null) ? eventName.toString() : eventName + ", " + parameter;
    }

}
