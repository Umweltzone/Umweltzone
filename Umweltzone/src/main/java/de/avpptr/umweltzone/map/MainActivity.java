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

package de.avpptr.umweltzone.map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.WindowManager;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.tracedroid.TraceDroidEmailSender;
import de.avpptr.umweltzone.utils.ContentProvider;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        showTraceDroidDialog();
        migrateNewZonesAddedInVersion250();
        migrateBochumRemoval();
        migrateCityNameFrankfurtInPreferences();
        showChangeLogDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (NullPointerException e) {
            // Avoid crash when Google Play Services are not present
            // http://stackoverflow.com/a/20905954/356895
            mTracking.trackError(TrackingPoint.GooglePlayServicesNotAvailableError,
                    "MainActivity.startActivityForResult: " +
                            TextUtils.join("\nat ", e.getStackTrace()));
            e.printStackTrace();
        }
    }

    private void showTraceDroidDialog() {
        if (!isFinishing()) {
            TraceDroidEmailSender.sendStackTraces(this);
        }
    }

    // Renames the stored city name in the preferences
    // according to file name in the "res/raw" folder.
    // This avoid a potential IllegalStateException in the ContentProvider
    // when "frankfurt" has been selected before the update.
    private void migrateCityNameFrankfurtInPreferences() {
        final Umweltzone application = (Umweltzone) getApplication();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        if (preferencesHelper.restoreCityNameFrankfurtInPreferencesFixed()) {
            return;
        }

        if (preferencesHelper.storesLastKnownLocationAsString()) {
            final String cityName = preferencesHelper.restoreLastKnownLocationAsString();
            if (cityName.equals("frankfurt")) {
                preferencesHelper.storeLastKnownLocationAsString("frankfurt_main");
            }
        }
        preferencesHelper.storeCityNameFrankfurtInPreferencesFixed(true);
    }

    // Remove "bochum" as the last selected city name
    // and reset the map location
    // The city name has been removed since it is contained in "ruhrgebiet".
    private void migrateBochumRemoval() {
        final Umweltzone application = (Umweltzone) getApplication();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        if (preferencesHelper.storesLastKnownLocationAsString()) {
            final String cityName = preferencesHelper.restoreLastKnownLocationAsString();
            if (cityName.equals("bochum")) {
                // Reset to default low emission zone.
                preferencesHelper.deleteLastKnownLocation();
            }
        }
    }

    // When the user did select one of the missing cities before the update to v.2.5.0
    // then the ZoneNotDrawableDialog will be shown although the zone data is available
    // meanwhile. Therefore, the content provider cache is cleared and the zone selection
    // is reset to the default low emission zone.
    private void migrateNewZonesAddedInVersion250() {
        final Umweltzone application = (Umweltzone) getApplication();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        if (preferencesHelper.restoreDidParseZoneDataAfterUpdate250()) {
            return;
        }

        if (preferencesHelper.storesLastKnownLocationAsString()) {
            final String cityName = preferencesHelper.restoreLastKnownLocationAsString();
            if (cityName.equals("magdeburg") ||
                    cityName.equals("heidelberg") ||
                    cityName.equals("wuppertal")) {
                // Enforce that zone data is parsed from file
                ContentProvider.enforceContentUpdate();
                // Reset to default low emission zone.
                preferencesHelper.deleteLastKnownLocation();
            }
        }
        preferencesHelper.storeDidParseZoneDataAfterUpdate250(true);
    }

}
