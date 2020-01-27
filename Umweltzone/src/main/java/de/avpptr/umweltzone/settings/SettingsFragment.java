/*
 *  Copyright (C) 2020  Tobias Preuss
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

package de.avpptr.umweltzone.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.prefs.PreferencesHelper;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".SETTINGS_FRAGMENT_TAG";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        Umweltzone application = (Umweltzone) requireActivity().getApplicationContext();
        PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        findPreference(getString(R.string.settings_key_google_analytics))
                .setOnPreferenceChangeListener((preference, isEnabled) -> {
                    preferencesHelper.storeGoogleAnalyticsIsEnabled((boolean) isEnabled);
                    application.initTracking();
                    return true;
                });
    }

}
