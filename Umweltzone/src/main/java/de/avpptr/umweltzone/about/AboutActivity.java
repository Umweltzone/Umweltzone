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

package de.avpptr.umweltzone.about;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.utils.ViewHelper;

public class AboutActivity extends BaseActivity {

    public AboutActivity() {
        super(R.layout.activity_about);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewHelper.setupTextViewSimple(this,
                R.id.buildVersion, "v." + getBuildVersionName());
        String buildTimeValue = getString(R.string.build_time);
        ViewHelper.setupTextViewSimple(this,
                R.id.buildTime, buildTimeValue);
        ViewHelper.setupTextViewSimple(this,
                R.id.buildVersionCode, "" + BuildConfig.BUILD_VERSION_CODE);
        String buildHashValue = getString(R.string.git_sha);
        ViewHelper.setupTextViewSimple(this,
                R.id.buildHash, buildHashValue);

        ViewHelper.setupTextViewExtended(this, R.id.app_info_environment_agency,
                R.string.appinfo_references_name_umweltbundesamt,
                R.string.appinfo_references_url_umweltbundesamt,
                TrackingPoint.AboutItemClick, "environment_agency");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_wikimedia_commons,
                R.string.appinfo_references_name_wikimedia_commons,
                R.string.appinfo_references_url_wikimedia_commons,
                TrackingPoint.AboutItemClick, "wikimedia_commons");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_android_testing_support_library,
                R.string.appinfo_references_name_android_testing_support_library,
                R.string.appinfo_references_url_android_testing_support_library,
                TrackingPoint.AboutItemClick, "android_testing_support_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_google_play_services,
                R.string.appinfo_references_name_google_play_services,
                R.string.appinfo_references_url_google_play_services,
                TrackingPoint.AboutItemClick, "google_play_services_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_google_androidx_library,
                R.string.appinfo_references_name_google_androidx_library,
                R.string.appinfo_references_url_google_androidx_library,
                TrackingPoint.AboutItemClick, "google_androidx_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_jackson,
                R.string.appinfo_references_name_jackson,
                R.string.appinfo_references_url_jackson,
                TrackingPoint.AboutItemClick, "jackson_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_google_analytics,
                R.string.appinfo_references_name_google_analytics,
                R.string.appinfo_references_url_google_analytics,
                TrackingPoint.AboutItemClick, "google_analytics");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_parceler,
                R.string.appinfo_references_name_parceler,
                R.string.appinfo_references_url_parceler,
                TrackingPoint.AboutItemClick, "parceler");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_road_signs,
                R.string.appinfo_references_name_road_signs,
                R.string.appinfo_references_url_road_signs,
                TrackingPoint.AboutItemClick, "road_signs");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_snack_engage,
                R.string.appinfo_references_name_snack_engage,
                R.string.appinfo_references_url_snack_engage,
                TrackingPoint.AboutItemClick, "snack_engage");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_trace_droid,
                R.string.appinfo_references_name_trace_droid,
                R.string.appinfo_references_url_trace_droid,
                TrackingPoint.AboutItemClick, "trace_droid_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_better_link_movement_method,
                R.string.appinfo_references_name_better_link_movement_method,
                R.string.appinfo_references_url_better_link_movement_method,
                TrackingPoint.AboutItemClick, "better_link_movement_method_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_ckchangelog,
                R.string.appinfo_references_name_ckchangelog,
                R.string.appinfo_references_url_ckchangelog,
                TrackingPoint.AboutItemClick, "ck_changelog_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_typed_preferences,
                R.string.appinfo_references_name_typed_preferences,
                R.string.appinfo_references_url_typed_preferences,
                TrackingPoint.AboutItemClick, "typed_preferences_library");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_gpl,
                R.string.appinfo_license_url_title_gpl,
                R.string.appinfo_license_url_gpl,
                TrackingPoint.AboutItemClick, "gpl_url");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_odbl,
                R.string.appinfo_license_url_title_odbl,
                R.string.appinfo_license_url_odbl,
                TrackingPoint.AboutItemClick, "odbl_url");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_creative_commons,
                R.string.appinfo_license_url_title_creative_commons,
                R.string.appinfo_license_url_creative_commons,
                TrackingPoint.AboutItemClick, "cc_url");
        ViewHelper.setupTextViewExtended(this, R.id.app_info_source_code,
                R.string.appinfo_sourcecode_url_title,
                R.string.appinfo_sourcecode_url,
                TrackingPoint.AboutItemClick, "source_code_url");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
