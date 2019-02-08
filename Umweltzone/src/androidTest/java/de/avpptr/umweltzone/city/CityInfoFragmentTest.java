/*
 *  Copyright (C) 2019  Tobias Preuss
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

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import de.avpptr.umweltzone.AndroidTestUtils;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.ChildZone;
import de.avpptr.umweltzone.utils.AdministrativeZoneBuilder;
import de.avpptr.umweltzone.utils.ChildZoneBuilder;
import de.avpptr.umweltzone.utils.DateFormatter;
import de.avpptr.umweltzone.utils.DateHelper;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.StringHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CityInfoFragmentTest {

    private Context mContext;

    @Rule
    public final ActivityTestRule mActivityRule =
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

        ChildZone firstChildZone = new ChildZoneBuilder()
                .setDisplayName(displayName)
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setAbroadLicensedVehicleZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setAbroadLicensedVehicleZoneNumberUntil(abroadLicensedVehicleZoneNumberUntilDate)
                .setGeometrySource(geometrySource)
                .setGeometryUpdatedAt(geometryUpdatedAtDate)
                .build();

        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setDisplayName(displayName)
                .setUrlUmweltPlaketteDe("http://umwelt-plakette.de/umweltzone%20berlin.php")
                .setUrlBadgeOnline(
                        "https://www.berlin.de/labo/kfz/dienstleistungen/feinstaubplakette.shop.php")
                .setChildZones(singletonList(firstChildZone))
                .build();

        launchActivity(zone);

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

        @StringRes int zoneNumberSinceAsOf = StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberSinceText = mContext.getString(
                zoneNumberSinceAsOf,
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
        ChildZone firstChildZone = new ChildZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.RED)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setNextZoneNumberAsOf(zoneNumberAsOfDate)
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(singletonList(firstChildZone))
                .build();

        launchActivity(zone);

        String datePattern = mContext.getString(R.string.city_info_zone_number_since_date_format);
        @StringRes int zoneNumberSinceAsOf =
                StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberSinceText = mContext.getString(
                zoneNumberSinceAsOf,
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
        ChildZone firstChildZone = new ChildZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.YELLOW)
                .setZoneNumberSince(zoneNumberSinceDate)
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(singletonList(firstChildZone))
                .build();

        launchActivity(zone);

        String datePattern = mContext.getString(R.string.city_info_zone_number_since_date_format);
        @StringRes int zoneNumberSinceAsOf =
                StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberAsOfText = mContext.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                mContext.getString(R.string.city_info_zone_number_since_text_fragment_yellow));
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberAsOfText)));
    }

    @Test
    public void renderCityInfoWithNoRestriction() {
        ChildZone childZone = new ChildZoneBuilder().build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(singletonList(childZone))
                .build();

        launchActivity(zone);

        String zoneNumberNoneText = mContext.getString(
                R.string.city_info_zone_number_none);
        onView(withId(R.id.city_info_zone_number_since))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberNoneText)));
    }

    @Test
    public void renderCityInfoWithListOfCities() {
        ChildZone childZone = new ChildZoneBuilder()
                .setListOfCities(new ArrayList<String>(3) {{
                    add("Bochum");
                    add("Bottrop");
                    add("Castrop-Rauxel");
                }})
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(singletonList(childZone))
                .build();

        launchActivity(zone);

        String listOfCitiesText = mContext.getString(
                R.string.city_info_list_of_cities,
                "Bochum, Bottrop", "Castrop-Rauxel");
        onView(withId(R.id.city_info_list_of_cities))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(listOfCitiesText)));
    }

    private void launchActivity(AdministrativeZone zone) {
        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, zone));
        // Rotate to verify fragment is re-used
        AndroidTestUtils.rotateScreen(mActivityRule.getActivity());
    }

}
