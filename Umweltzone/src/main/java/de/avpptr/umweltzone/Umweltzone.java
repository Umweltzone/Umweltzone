/*
 *  Copyright (C) 2024  Tobias Preuss
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

import static com.google.android.gms.maps.MapsInitializer.Renderer.LATEST;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;

import org.ligi.tracedroid.TraceDroid;
import org.ligi.tracedroid.logging.Log;

import de.avpptr.umweltzone.prefs.PreferencesHelper;

public class Umweltzone extends Application implements OnMapsSdkInitializedCallback {

    private static final String LOG_TAG = "Umweltzone";

    private PreferencesHelper mPreferencesHelper;

    public static boolean centerZoneRequested = false;

    public void onCreate() {
        super.onCreate();
        TraceDroid.init(this);
        MapsInitializer.initialize(this, LATEST, this);
    }

    public PreferencesHelper getPreferencesHelper() {
        if (mPreferencesHelper == null) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            mPreferencesHelper = new PreferencesHelper(sharedPreferences);
        }
        return mPreferencesHelper;
    }

    @Override
    public void onMapsSdkInitialized(@NonNull MapsInitializer.Renderer renderer) {
        switch (renderer) {
            case LATEST:
                Log.d(LOG_TAG, "The latest version of the renderer is used.");
                break;
            case LEGACY:
                Log.d(LOG_TAG, "The legacy version of the renderer is used.");
                break;
        }
    }

}
