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

package de.avpptr.umweltzone.about;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AboutActivityTest {

    @Rule
    public ActivityTestRule mActivityRule =
            new ActivityTestRule<>(AboutActivity.class);

    @Test
    public void renderBuildInformation() {
        onView(withId(R.id.buildVersion))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withId(R.id.buildVersion))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText("v." + BuildConfig.VERSION_NAME)));
        onView(withId(R.id.buildTime))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withId(R.id.buildVersionCode))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText("" + BuildConfig.VERSION_CODE)));
        onView(withId(R.id.buildHash))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void renderExternalLinks() {
        onView(withId(R.id.app_info_environment_agency))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_umweltbundesamt)));
        onView(withId(R.id.app_info_wikimedia_commons))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_wikimedia_commons)));
    }

    @Test
    public void renderLibraries() {
        onView(withId(R.id.app_info_android_testing_support_library))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_android_testing_support_library)));
        onView(withId(R.id.app_info_ckchangelog))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_ckchangelog)));
        onView(withId(R.id.app_info_google_analytics))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_google_analytics)));
        onView(withId(R.id.app_info_google_support_library))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_google_support_library)));
        onView(withId(R.id.app_info_google_play_services))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_google_play_services)));
        onView(withId(R.id.app_info_jackson))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_jackson)));
        onView(withId(R.id.app_info_parceler))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_parceler)));
        onView(withId(R.id.app_info_snack_engage))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_snack_engage)));
        onView(withId(R.id.app_info_trace_droid))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_trace_droid)));
        onView(withId(R.id.app_info_typed_preferences))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_references_name_typed_preferences)));
    }

    @Test
    public void renderLicenses() {
        onView(withId(R.id.app_info_gpl))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_license_url_title_gpl)));
        onView(withId(R.id.app_info_odbl))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_license_url_title_odbl)));
        onView(withId(R.id.app_info_creative_commons))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_license_url_title_creative_commons)));
    }

    @Test
    public void renderSourceCodeUrl() {
        onView(withId(R.id.app_info_source_code))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.appinfo_sourcecode_url_title)));
    }

}
