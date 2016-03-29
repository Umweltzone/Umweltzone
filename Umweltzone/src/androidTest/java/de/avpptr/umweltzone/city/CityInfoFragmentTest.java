/*
 *  Copyright (C) 2016  Tobias Preuss
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

package de.avpptr.umweltzone.city;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.ArrayList;
import java.util.Date;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.DateFormatter;
import de.avpptr.umweltzone.utils.DateHelper;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.LowEmissionZoneBuilder;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CityInfoFragmentTest {

    private Context mContext;

    @Rule
    public ActivityTestRule mActivityRule =
            new ActivityTestRule<>(CityInfoActivity.class, true, false);

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void renderCityInfoWithStandardZone() {
        String displayName = "Berlin";
        String geometrySource = "Geoportal Berlin / Umweltzone Berlin";
        Date zoneNumberSinceDate = DateHelper.getDate(2014, 5, 1);
        Date abroadLicensedVehicleZoneNumberUntilDate = DateHelper.getDate(2014, 12, 31);
        Date geometryUpdatedAtDate = DateHelper.getDate(2016, 1, 1);

        String datePattern = mContext.getString(R.string.city_info_zone_number_since_date_format);
        String geometryUpdatedAtDatePattern = mContext
                .getString(R.string.city_info_geometry_updated_at_date_format);

        LowEmissionZone zone = new LowEmissionZoneBuilder()
                .setDisplayName(displayName)
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setAbroadLicensedVehicleZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setAbroadLicensedVehicleZoneNumberUntil(abroadLicensedVehicleZoneNumberUntilDate)
                .setUrlUmweltPlaketteDe("http://umwelt-plakette.de/umweltzone%20berlin.php")
                .setUrlBadgeOnline(
                        "https://www.berlin.de/labo/kfz/dienstleistungen/feinstaubplakette.shop.php")
                .setGeometrySource(geometrySource)
                .setGeometryUpdatedAt(geometryUpdatedAtDate)
                .build();

        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));

        onView(withId(R.id.city_info_title))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(displayName)));

        onView(withId(R.id.city_info_zone_status))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_zone_status)));

        onView(withId(R.id.city_info_list_of_cities))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not(isDisplayed())));

        String zoneNumberSinceText = mContext.getString(
                R.string.city_info_zone_number_since,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_green));
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)));

        onView(withId(R.id.city_info_next_zone_number_as_of))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not(isDisplayed())));

        String abroadLicensedVehicleZoneInfoText = mContext.getString(
                R.string.city_info_abroad_licensed_vehicle_zone_info,
                DateFormatter.getFormattedDate(
                        abroadLicensedVehicleZoneNumberUntilDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_green));
        onView(withId(R.id.city_info_abroad_licensed_vehicle_zone_info))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(abroadLicensedVehicleZoneInfoText)));

        onView(withId(R.id.city_info_show_on_map))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_show_on_map)));

        onView(withId(R.id.city_info_further_information))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_further_information)))
                .check(matches(hasLinks()));

        onView(withId(R.id.city_info_badge_online))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_badge_online_title)))
                .check(matches(hasLinks()));

        String geometrySourceText = mContext.getString(
                R.string.city_info_geometry_source_text,
                geometrySource);
        onView(withId(R.id.city_info_geometry_source))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometrySourceText)));

        String geometryUpdatedAtText = mContext.getString(
                R.string.city_info_geometry_updated_at_text,
                DateFormatter.getFormattedDate(
                        geometryUpdatedAtDate, geometryUpdatedAtDatePattern));
        onView(withId(R.id.city_info_geometry_updated_at))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometryUpdatedAtText)));
    }

    @Test
    public void renderCityInfoWithNextZone() {
        Date zoneNumberSinceDate = DateHelper.getDate(2011, 8, 1);
        Date zoneNumberAsOfDate = DateHelper.getDate(2013, 6, 1);
        LowEmissionZone zone = new LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.RED)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setNextZoneNumberAsOf(zoneNumberAsOfDate)
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .build();

        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));

        String datePattern = mContext.getString(R.string.city_info_zone_number_since_date_format);

        String zoneNumberSinceText = mContext.getString(
                R.string.city_info_zone_number_since,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_red));
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)));

        String nextZoneNumberAsOfText = mContext.getString(
                R.string.city_info_next_zone_number_as_of,
                DateFormatter.getFormattedDate(zoneNumberAsOfDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_yellow));
        onView(withId(R.id.city_info_next_zone_number_as_of))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(nextZoneNumberAsOfText)));
    }

    @Test
    public void renderCityInfoWithFutureZone() {
        Date zoneNumberSinceDate = DateHelper.getDate(2034, 11, 1);
        LowEmissionZone zone = new LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.YELLOW)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .build();

        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));

        String datePattern = mContext.getString(R.string.city_info_zone_number_since_date_format);
        String zoneNumberAsOfText = mContext.getString(
                R.string.city_info_zone_number_as_of,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_yellow));
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberAsOfText)));
    }

    @Test
    public void renderCityInfoWithNoRestriction() {
        LowEmissionZone zone = new LowEmissionZoneBuilder()
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .build();

        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));

        String zoneNumberNoneText = mContext.getString(
                R.string.city_info_zone_number_none);
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberNoneText)));
    }

    @Test
    public void renderCityInfoWithListOfCities() {
        LowEmissionZone zone = new LowEmissionZoneBuilder()
                .setListOfCities(new ArrayList<String>(3) {{
                    add("Bochum");
                    add("Bottrop");
                    add("Castrop-Rauxel");
                }})
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .build();

        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));

        String listOfCitiesText = mContext.getString(
                R.string.city_info_list_of_cities,
                "Bochum, Bottrop", "Castrop-Rauxel");
        onView(withId(R.id.city_info_list_of_cities))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(listOfCitiesText)));
    }

}
