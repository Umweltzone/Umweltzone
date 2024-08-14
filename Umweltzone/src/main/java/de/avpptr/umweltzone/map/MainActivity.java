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

package de.avpptr.umweltzone.map;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.ligi.snackengage.SnackEngage;
import org.ligi.snackengage.conditions.AfterNumberOfOpportunities;
import org.ligi.snackengage.conditions.NeverAgainWhenClickedOnce;
import org.ligi.snackengage.conditions.connectivity.IsConnectedViaWiFiOrUnknown;
import org.ligi.snackengage.snacks.BaseSnack;
import org.ligi.snackengage.snacks.DefaultRateSnack;
import org.ligi.snackengage.snacks.GooglePlayOpenBetaTestSnack;

import java.util.Arrays;
import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.extensions.ContextExtensions;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.tracedroid.TraceDroidEmailSender;
import de.avpptr.umweltzone.utils.ContentProvider;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(new MapFragment(), MapFragment.FRAGMENT_TAG);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        showTraceDroidDialog();
        migrateNewZonesAddedInVersion250();
        migrateCityNameFrankfurtInPreferences();
        migrateZonesRemoval();
        showChangeLogDialog();
        initUserEngagement();
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            setShowWhenLocked(true);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (NullPointerException e) {
            // Avoid crash when Google Play Services are not present
            // http://stackoverflow.com/a/20905954/356895
            e.printStackTrace();
        }
    }

    private void showTraceDroidDialog() {
        if (!isFinishing()) {
            TraceDroidEmailSender.sendStackTraces(this);
        }
    }

    private void initUserEngagement() {
        int actionColor = ContextExtensions.getColorCompat(this, R.color.snack_engage_action_text);
        int backgroundColor = ContextExtensions.getColorCompat(this, R.color.snack_engage_action_background);
        BaseSnack rateSnack = new DefaultRateSnack()
                .overrideTitleText(getString(R.string.snack_engage_rate_title))
                .overrideActionText(getString(R.string.snack_engage_rate_action));
        rateSnack.setActionColor(actionColor);
        rateSnack.setBackgroundColor(backgroundColor);
        BaseSnack betaTestSnack = new GooglePlayOpenBetaTestSnack()
                .overrideTitleText(getString(R.string.snack_engage_beta_testing_title))
                .overrideActionText(getString(R.string.snack_engage_beta_testing_action))
                .withConditions(
                        new NeverAgainWhenClickedOnce(),
                        new AfterNumberOfOpportunities(13),
                        new IsConnectedViaWiFiOrUnknown());
        betaTestSnack.setActionColor(actionColor);
        betaTestSnack.setBackgroundColor(backgroundColor);
        SnackEngage.from(this)
                .withSnack(rateSnack)
                .withSnack(betaTestSnack)
                .build()
                .engageWhenAppropriate();
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

    private void migrateZonesRemoval() {
        List<String> zoneNames = Arrays.asList(
                // "bochum" has been removed since it is contained in "ruhrgebiet".
                "bochum",
                // "balingen" has been removed because it was discontinued as of 01.11.2020.
                "balingen",
                // "erfurt" has been removed because it was discontinued as of 07.05.2021
                "erfurt",
                // "hannover" has been removed because it was discontinued as of 22.02.2024
                "hannover",
                // "heidelberg" has been removed because it was discontinued as of 01.03.2023
                "heidelberg",
                // "karlsruhe" has been removed because it was discontinued as of 01.03.2023
                "karlsruhe",
                // "pfinztal" has been removed because it was discontinued as of 01.03.2023
                "pfinztal",
                // "reutlingen" has been removed because it was discontinued as of 04.06.2024
                "reutlingen",
                // "schramberg" has been removed because it was discontinued as of 01.03.2023
                "schramberg",
                // "tuebingen" has been removed because it was discontinued as of 04.06.2024
                "tuebingen"
        );
        migrateZonesRemoval(zoneNames);
    }

    /**
     * Removes the given {@code removedZoneNames} as the last selected zone name and
     * resets the map location to the default setting.
     */
    private void migrateZonesRemoval(@NonNull List<String> removedZoneNames) {
        Umweltzone application = (Umweltzone) getApplication();
        PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        if (preferencesHelper.storesLastKnownLocationAsString()) {
            String lastZoneName = preferencesHelper.restoreLastKnownLocationAsString();
            for (String removedZoneName : removedZoneNames) {
                if (lastZoneName.equals(removedZoneName)) {
                    // Reset to default low emission zone.
                    preferencesHelper.deleteLastKnownLocation();
                }
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
