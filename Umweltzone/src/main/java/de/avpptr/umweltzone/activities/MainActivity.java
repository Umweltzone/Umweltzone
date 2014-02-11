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

package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;

import org.ligi.tracedroid.sending.TraceDroidEmailSender;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.prefs.PreferencesHelper;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        TraceDroidEmailSender.sendStackTraces(getString(R.string.config_tracedroid_email_address), this);
        migrateCityNameFrankfurtInPreferences();
        showChangeLogDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        if (preferencesHelper.storesLastKnownLocation()) {
            final String cityName = preferencesHelper.restoreLastKnownLocationAsString();
            if (cityName.equals("frankfurt")) {
                preferencesHelper.storeLastKnownLocation("frankfurt_main");
            }
        }
        preferencesHelper.storeCityNameFrankfurtInPreferencesFixed(true);
    }

}
