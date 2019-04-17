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

package de.avpptr.umweltzone.details;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import de.avpptr.umweltzone.AndroidTestUtils;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.AdministrativeZoneBuilder;
import de.avpptr.umweltzone.utils.DateFormatter;
import de.avpptr.umweltzone.utils.DateHelper;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.LowEmissionZoneBuilder;
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
public class DetailsFragmentTest {

    private Context context;

    @Rule
    public final ActivityTestRule activityRule =
            new ActivityTestRule<>(DetailsActivity.class, true, false);

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void renderDetailsWithStandardZone() {
        String displayName = "Berlin";
        String geometrySource = "Geoportal Berlin / Umweltzone Berlin";
        Date zoneNumberSinceDate = DateHelper.getDate(2014, 5, 1);
        Date abroadLicensedVehicleZoneNumberUntilDate = DateHelper.getDate(2014, 12, 31);
        Date geometryUpdatedAtDate = DateHelper.getDate(2016, 1, 1);

        String datePattern = context.getString(R.string.city_info_zone_number_since_date_format);
        String geometryUpdatedAtDatePattern = context
                .getString(R.string.city_info_geometry_updated_at_date_format);

        LowEmissionZone firstChildZone = new LowEmissionZoneBuilder()
                .setDisplayName(displayName)
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setAbroadLicensedVehicleZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setAbroadLicensedVehicleZoneNumberUntil(abroadLicensedVehicleZoneNumberUntilDate)
                .setGeometrySource(geometrySource)
                .setGeometryUpdatedAt(geometryUpdatedAtDate)
                .build();

        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setName("berlin")
                .setDisplayName(displayName)
                .setUrlUmweltPlaketteDe("http://umwelt-plakette.de/umweltzone%20berlin.php")
                .setUrlBadgeOnline(
                        "https://www.berlin.de/labo/kfz/dienstleistungen/feinstaubplakette.shop.php")
                .setChildZones(singletonList(firstChildZone))
                .build();

        launchActivity(zone);

        onView(withId(R.id.detailsLezSectionTitleView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_section_title_lez)));

        onView(withId(R.id.detailsLezRoadSignView))
                .perform(scrollTo())
                .check(matches(withText(R.string.city_info_zone_status)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.detailsLezListOfCitiesView))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not(isDisplayed())));

        @StringRes int zoneNumberSinceAsOf = StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberSinceText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_green));
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)));

        onView(withId(R.id.detailsLezNextZoneNumberAsOfView))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not(isDisplayed())));

        String abroadLicensedVehicleZoneInfoText = context.getString(
                R.string.city_info_abroad_licensed_vehicle_zone_info,
                DateFormatter.getFormattedDate(
                        abroadLicensedVehicleZoneNumberUntilDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_green));
        onView(withId(R.id.detailsLezAbroadLicensedVehicleZoneInfoView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(abroadLicensedVehicleZoneInfoText)));

        onView(withId(R.id.detailsOtherFurtherInformationView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_further_information)))
                .check(matches(hasLinks()));

        onView(withId(R.id.detailsOtherBadgeOnlineView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_badge_online_title)))
                .check(matches(hasLinks()));

        String geometrySourceText = context.getString(
                R.string.city_info_geometry_source_text,
                geometrySource);
        onView(withId(R.id.detailsLezGeometrySourceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometrySourceText)));

        String geometryUpdatedAtText = context.getString(
                R.string.city_info_geometry_updated_at_text,
                DateFormatter.getFormattedDate(
                        geometryUpdatedAtDate, geometryUpdatedAtDatePattern));
        onView(withId(R.id.detailsLezGeometryUpdatedAtView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometryUpdatedAtText)));
    }

    @Test
    public void renderDetailsWithNextZone() {
        Date zoneNumberSinceDate = DateHelper.getDate(2011, 8, 1);
        Date zoneNumberAsOfDate = DateHelper.getDate(2013, 6, 1);
        LowEmissionZone firstChildZone = new LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.RED)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setNextZoneNumberAsOf(zoneNumberAsOfDate)
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(singletonList(firstChildZone))
                .setUrlBadgeOnline("http://mandatory.url")
                .build();

        launchActivity(zone);

        String datePattern = context.getString(R.string.city_info_zone_number_since_date_format);
        @StringRes int zoneNumberSinceAsOf =
                StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberSinceText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_red));
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)));

        String nextZoneNumberAsOfText = context.getString(
                R.string.city_info_next_zone_number_as_of,
                DateFormatter.getFormattedDate(zoneNumberAsOfDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_yellow));
        onView(withId(R.id.detailsLezNextZoneNumberAsOfView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(nextZoneNumberAsOfText)));
    }

    @Test
    public void renderDetailsWithFutureZone() {
        Date zoneNumberSinceDate = DateHelper.getDate(2034, 11, 1);
        LowEmissionZone firstChildZone = new LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.YELLOW)
                .setZoneNumberSince(zoneNumberSinceDate)
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(singletonList(firstChildZone))
                .build();

        launchActivity(zone);

        String datePattern = context.getString(R.string.city_info_zone_number_since_date_format);
        @StringRes int zoneNumberSinceAsOf =
                StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate);
        String zoneNumberAsOfText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_yellow));
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberAsOfText)));
    }

    @Test
    public void renderDetailsWithNoRestriction() {
        LowEmissionZone lowEmissionZone = new LowEmissionZoneBuilder().build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setName("bremen")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(singletonList(lowEmissionZone))
                .build();

        launchActivity(zone);

        String zoneNumberNoneText = context.getString(
                R.string.city_info_zone_number_none);
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberNoneText)));
    }

    @Test
    public void renderDetailsWithListOfCities() {
        Date zoneNumberSinceDate = DateHelper.getDate(2018, 10, 1);
        LowEmissionZone lowEmissionZone = new LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setListOfCities(new ArrayList<String>(3) {{
                    add("Bochum");
                    add("Bottrop");
                    add("Castrop-Rauxel");
                }})
                .build();
        AdministrativeZone zone = new AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(singletonList(lowEmissionZone))
                .build();

        launchActivity(zone);

        String listOfCitiesText = context.getString(
                R.string.city_info_list_of_cities,
                "Bochum, Bottrop", "Castrop-Rauxel");
        onView(withId(R.id.detailsLezListOfCitiesView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(listOfCitiesText)));
    }

    private void launchActivity(AdministrativeZone zone) {
        activityRule.launchActivity(IntentHelper.getDetailsIntent(context, zone));
        // Rotate to verify fragment is re-used
        AndroidTestUtils.rotateScreen(activityRule.getActivity());
    }

}
