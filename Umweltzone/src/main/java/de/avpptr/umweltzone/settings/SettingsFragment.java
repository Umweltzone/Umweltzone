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

package de.avpptr.umweltzone.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.prefs.PreferencesHelper;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".SETTINGS_FRAGMENT_TAG";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        Umweltzone application = (Umweltzone) getActivity().getApplicationContext();
        PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        Tracking tracker = Umweltzone.getTracker();
        findPreference(getString(R.string.settings_key_google_analytics))
                .setOnPreferenceChangeListener((preference, isEnabled) -> {
                    boolean isEnabledValue = (boolean) isEnabled;
                    String suffix = isEnabledValue ? "enabled" : "disabled";
                    tracker.track(TrackingPoint.SettingsItemClick, "google_analytics_" + suffix);
                    preferencesHelper.storeGoogleAnalyticsIsEnabled(isEnabledValue);
                    application.initTracking();
                    return true;
                });
        findPreference(getString(R.string.settings_key_data_privacy_statement_de))
                .setOnPreferenceClickListener(preference -> {
                    tracker.track(TrackingPoint.SettingsItemClick, "data_privacy_statement_de");
                    return false;
                });
    }

}
