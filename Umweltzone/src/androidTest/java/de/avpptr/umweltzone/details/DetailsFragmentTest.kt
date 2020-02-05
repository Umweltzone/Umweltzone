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

package de.avpptr.umweltzone.details

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasLinks
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import de.avpptr.umweltzone.AndroidTestUtils
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers
import de.avpptr.umweltzone.models.AdministrativeZone
import de.avpptr.umweltzone.models.ChildZone
import de.avpptr.umweltzone.utils.*
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import java.util.*

@LargeTest
class DetailsFragmentTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun renderDetailsWithStandardZone() {
        val displayName = "Berlin"
        val geometrySource = "Geoportal Berlin / Umweltzone Berlin"
        val zoneNumberSinceDate = DateHelper.getDate(2014, 5, 1)
        val abroadLicensedVehicleZoneNumberUntilDate = DateHelper.getDate(2014, 12, 31)
        val geometryUpdatedAtDate = DateHelper.getDate(2016, 1, 1)

        val datePattern = context.getString(R.string.city_info_zone_number_since_date_format)
        val geometryUpdatedAtDatePattern = context
                .getString(R.string.city_info_geometry_updated_at_date_format)

        val firstChildZone = LowEmissionZoneBuilder()
                .setDisplayName(displayName)
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setAbroadLicensedVehicleZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setAbroadLicensedVehicleZoneNumberUntil(abroadLicensedVehicleZoneNumberUntilDate)
                .setGeometrySource(geometrySource)
                .setGeometryUpdatedAt(geometryUpdatedAtDate)
                .build()

        val zone = AdministrativeZoneBuilder()
                .setName("berlin")
                .setDisplayName(displayName)
                .setUrlUmweltPlaketteDe("http://umwelt-plakette.de/umweltzone%20berlin.php")
                .setUrlBadgeOnline(
                        "https://www.berlin.de/labo/kfz/dienstleistungen/feinstaubplakette.shop.php")
                .setChildZones(listOf<ChildZone>(firstChildZone))
                .build()

        launchActivity(zone)

        onView(withId(R.id.detailsLezSectionTitleView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_section_title_lez)))

        onView(withId(R.id.detailsLezRoadSignView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))

        onView(withId(R.id.genericRoadSignView))
                .perform(scrollTo())
                .check(matches(withContentDescription(R.string.environmental_badges_content_description_green)))

        onView(withId(R.id.detailsLezListOfCitiesView))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not<View>(isDisplayed())))

        @StringRes val zoneNumberSinceAsOf = StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate)
        val zoneNumberSinceText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_green))
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)))

        onView(withId(R.id.detailsLezNextZoneNumberAsOfView))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
                .check(matches(not<View>(isDisplayed())))

        val abroadLicensedVehicleZoneInfoText = context.getString(
                R.string.city_info_abroad_licensed_vehicle_zone_info,
                DateFormatter.getFormattedDate(
                        abroadLicensedVehicleZoneNumberUntilDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_green))
        onView(withId(R.id.detailsLezAbroadLicensedVehicleZoneInfoView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(abroadLicensedVehicleZoneInfoText)))

        onView(withId(R.id.detailsOtherFurtherInformationView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_further_information)))
                .check(matches(hasLinks()))

        onView(withId(R.id.detailsOtherBadgeOnlineView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.city_info_badge_online_title)))
                .check(matches(hasLinks()))

        val geometrySourceText = context.getString(
                R.string.city_info_geometry_source_text,
                geometrySource)
        onView(withId(R.id.detailsLezGeometrySourceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometrySourceText)))

        val geometryUpdatedAtText = context.getString(
                R.string.city_info_geometry_updated_at_text,
                DateFormatter.getFormattedDate(
                        geometryUpdatedAtDate, geometryUpdatedAtDatePattern))
        onView(withId(R.id.detailsLezGeometryUpdatedAtView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(geometryUpdatedAtText)))
    }

    @Test
    fun renderDetailsWithNextZone() {
        val zoneNumberSinceDate = DateHelper.getDate(2011, 8, 1)
        val zoneNumberAsOfDate = DateHelper.getDate(2013, 6, 1)
        val firstChildZone = LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.RED)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setNextZoneNumberAsOf(zoneNumberAsOfDate)
                .build()
        val zone = AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setChildZones(listOf<ChildZone>(firstChildZone))
                .setUrlBadgeOnline("http://mandatory.url")
                .build()

        launchActivity(zone)

        val datePattern = context.getString(R.string.city_info_zone_number_since_date_format)
        @StringRes val zoneNumberSinceAsOf = StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate)
        val zoneNumberSinceText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_red))
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberSinceText)))

        val nextZoneNumberAsOfText = context.getString(
                R.string.city_info_next_zone_number_as_of,
                DateFormatter.getFormattedDate(zoneNumberAsOfDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_yellow))
        onView(withId(R.id.detailsLezNextZoneNumberAsOfView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(nextZoneNumberAsOfText)))
    }

    @Test
    fun renderDetailsWithFutureZone() {
        val zoneNumberSinceDate = DateHelper.getDate(2034, 11, 1)
        val firstChildZone = LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.YELLOW)
                .setZoneNumberSince(zoneNumberSinceDate)
                .build()
        val zone = AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(listOf<ChildZone>(firstChildZone))
                .build()

        launchActivity(zone)

        val datePattern = context.getString(R.string.city_info_zone_number_since_date_format)
        @StringRes val zoneNumberSinceAsOf = StringHelper.getZoneNumberSinceAsOfResourceId(zoneNumberSinceDate)
        val zoneNumberAsOfText = context.getString(
                zoneNumberSinceAsOf,
                DateFormatter.getFormattedDate(zoneNumberSinceDate, datePattern),
                context.getString(R.string.city_info_zone_number_since_text_fragment_yellow))
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberAsOfText)))
    }

    @Test
    fun renderDetailsWithNoRestriction() {
        val lowEmissionZone = LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.NONE)
                .build()
        val zone = AdministrativeZoneBuilder()
                .setName("bremen")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(listOf<ChildZone>(lowEmissionZone))
                .build()

        launchActivity(zone)

        val zoneNumberNoneText = context.getString(
                R.string.city_info_zone_number_none)
        onView(withId(R.id.detailsLezRoadSignView))
                .check(matches(not(isDisplayed())))
        onView(withId(R.id.detailsLezZoneNumberSinceView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(zoneNumberNoneText)))
    }

    @Test
    fun renderDetailsWithListOfCities() {
        val zoneNumberSinceDate = DateHelper.getDate(2018, 10, 1)
        val lowEmissionZone = LowEmissionZoneBuilder()
                .setZoneNumber(LowEmissionZoneNumbers.GREEN)
                .setZoneNumberSince(zoneNumberSinceDate)
                .setListOfCities(object : ArrayList<String>(3) {
                    init {
                        add("Bochum")
                        add("Bottrop")
                        add("Castrop-Rauxel")
                    }
                })
                .build()
        val zone = AdministrativeZoneBuilder()
                .setName("ruhrgebiet")
                .setUrlUmweltPlaketteDe("http://mandatory.url")
                .setUrlBadgeOnline("http://mandatory.url")
                .setChildZones(listOf<ChildZone>(lowEmissionZone))
                .build()

        launchActivity(zone)

        val listOfCitiesText = context.getString(
                R.string.city_info_list_of_cities,
                "Bochum, Bottrop", "Castrop-Rauxel")
        onView(withId(R.id.detailsLezListOfCitiesView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(listOfCitiesText)))
    }

    private fun launchActivity(zone: AdministrativeZone) {
        val scenario = ActivityScenario.launch<DetailsActivity>(IntentHelper.getDetailsIntent(context, zone))
        scenario.onActivity {
            // Rotate to verify fragment is re-used
            AndroidTestUtils.rotateScreen(it)
        }
    }

}
