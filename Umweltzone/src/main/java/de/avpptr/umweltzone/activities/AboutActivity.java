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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView buildVersionTextView = (TextView) findViewById(R.id.buildVersion);
        buildVersionTextView.setText("v." + BuildConfig.BUILD_VERSION);
        TextView buildTimeTextView = (TextView) findViewById(R.id.buildTime);
        buildTimeTextView.setText(BuildConfig.BUILD_TIME);
        TextView buildVersionCodeTextView = (TextView) findViewById(R.id.buildVersionCode);
        buildVersionCodeTextView.setText("" + BuildConfig.BUILD_VERSION_CODE);
        TextView buildHashTextView = (TextView) findViewById(R.id.buildHash);
        buildHashTextView.setText(BuildConfig.GIT_SHA);

        Button userVoiceButton = (Button) findViewById(R.id.aboutButtonUserVoice);
        userVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.UserVoiceClick);
            }
        });

        TextView contactEmailTextView = (TextView) findViewById(R.id.aboutContactEmail);
        contactEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.SupportMailClick);
            }
        });

        Button environmentAgencyButton = (Button) findViewById(R.id.aboutButtonEnvironmentAgency);
        environmentAgencyButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("environment_agency");
            }
        });

        Button wikimediaButton = (Button) findViewById(R.id.aboutButtonWikimedia);
        wikimediaButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("wikimedia_commons");
            }
        });

        Button libraryGooglePlayButton = (Button) findViewById(R.id.aboutButtonGooglePlay);
        libraryGooglePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("google_play_services_library");
            }
        });

        Button libraryGoogleSupportButton = (Button) findViewById(R.id.aboutButtonGoogleSupportLibrary);
        libraryGoogleSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("google_support_library");
            }
        });

        Button libraryActionBarCompatButton = (Button) findViewById(R.id.aboutButtonActionBarCompat);
        libraryActionBarCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("action_bat_compat_library");
            }
        });

        Button libraryJacksonButton = (Button) findViewById(R.id.aboutButtonJackson);
        libraryJacksonButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("jackson_library");
            }
        });

        Button libraryGoogleAnalyticsButton = (Button) findViewById(R.id.aboutButtonGoogleAnalytics);
        libraryGoogleAnalyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("google_analytics");
            }
        });

        Button gplButton = (Button) findViewById(R.id.aboutButtonGpl);
        gplButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("gpl_url");
            }
        });

        Button ccButton = (Button) findViewById(R.id.aboutButtonCC);
        ccButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("cc_url");
            }
        });

        Button sourceButton = (Button) findViewById(R.id.aboutButtonSourceCode);
        sourceButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                trackAboutItemClick("source_code_url");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void trackAboutItemClick(final String itemName) {
        mTracking.track(TrackingPoint.AboutItemClick, itemName);
    }

}
