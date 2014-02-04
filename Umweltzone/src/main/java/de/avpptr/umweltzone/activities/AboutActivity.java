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

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.utils.ViewHelper;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ViewHelper.setupTextViewSimple(this, R.id.buildVersion, "v." + getBuildVersionName());
        ViewHelper.setupTextViewSimple(this, R.id.buildTime, BuildConfig.BUILD_TIME);
        ViewHelper.setupTextViewSimple(this, R.id.buildVersionCode, "" + BuildConfig.BUILD_VERSION_CODE);
        ViewHelper.setupTextViewSimple(this, R.id.buildHash, BuildConfig.GIT_SHA);

        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonEnvironmentAgency,
                R.string.appinfo_references_url_umweltbundesamt, TrackingPoint.AboutItemClick,
                "environment_agency");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonWikimedia,
                R.string.appinfo_references_url_wikimedia_commons, TrackingPoint.AboutItemClick,
                "wikimedia_commons");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonGooglePlay,
                R.string.appinfo_references_url_googleplayservices, TrackingPoint.AboutItemClick,
                "google_play_services_library");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonGoogleSupportLibrary,
                R.string.appinfo_references_url_googlesupportlibrary, TrackingPoint.AboutItemClick,
                "google_support_library");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonActionBarCompat,
                R.string.appinfo_references_url_actionbarcompat, TrackingPoint.AboutItemClick,
                "action_bat_compat_library");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonJackson,
                R.string.appinfo_references_url_jackson, TrackingPoint.AboutItemClick,
                "jackson_library");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonGoogleAnalytics,
                R.string.appinfo_references_url_ga, TrackingPoint.AboutItemClick,
                "google_analytics");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonGpl,
                R.string.appinfo_license_url_gpl, TrackingPoint.AboutItemClick,
                "gpl_url");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonCreativeCommons,
                R.string.appinfo_license_url_cc, TrackingPoint.AboutItemClick,
                "cc_url");
        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonSourceCode,
                R.string.appinfo_sourcecode_url, TrackingPoint.AboutItemClick,
                "source_code_url");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
