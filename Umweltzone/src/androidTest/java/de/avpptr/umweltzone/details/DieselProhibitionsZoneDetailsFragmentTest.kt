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
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import de.avpptr.umweltzone.AndroidTestUtils
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.details.dataconverters.allowedEmissionStandardInDpzText
import de.avpptr.umweltzone.details.dataconverters.prohibitedVehiclesText
import de.avpptr.umweltzone.details.dataconverters.zoneNumberForNonResidentsSinceText
import de.avpptr.umweltzone.details.dataconverters.zoneNumberForResidentsSinceText
import de.avpptr.umweltzone.extensions.displaysText
import de.avpptr.umweltzone.extensions.isNotDisplayed
import de.avpptr.umweltzone.models.AdministrativeZone
import de.avpptr.umweltzone.models.DieselProhibitionZone
import de.avpptr.umweltzone.models.VehicleType
import de.avpptr.umweltzone.utils.DateHelper
import de.avpptr.umweltzone.utils.IntentHelper
import de.avpptr.umweltzone.utils.StringHelper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class DieselProhibitionsZoneDetailsFragmentTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun renderDetailsWithAllViews() {
        val defaultDieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = "Max Brauer Allee, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = true
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.HGV.value, VehicleType.CAR.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        val defaultAdministrativeZone = AdministrativeZone().apply {
            name = "hamburg"
            childZones = listOf(defaultDieselProhibitionZone)
            urlUmweltPlaketteDe = "http://mandatory.url"
            urlBadgeOnline = "http://mandatory.url"
        }

        launchActivity(defaultAdministrativeZone)

        assertSectionTitleTextIsDisplayed()
        assertRoadSignIsDisplayed(context.getString(R.string.diesel_prohibition_cars_free_until_euro_5_v_open_for_residents))
        assertDisplayNameTextIsDisplayed(defaultDieselProhibitionZone.displayName)
        assertAllowedEmissionStandardInDpzTextIsDisplayed(defaultDieselProhibitionZone)
        assertIsCongruentWithLowEmissionZoneTextIsDisplayed()
        assertZoneNumberForResidentsSinceTextIsDisplayed(defaultDieselProhibitionZone)
        assertZoneNumberForNonResidentsSinceTextIsDisplayed(defaultDieselProhibitionZone)
        assertProhibitedVehiclesTextIsDisplayed(defaultDieselProhibitionZone)
        assertGeometrySourceTextIsDisplayed(defaultDieselProhibitionZone.geometrySource)
        assertGeometryUpdatedAtTextIsDisplayed(defaultDieselProhibitionZone.geometryUpdatedAt)
    }

    @Test
    fun renderDetailsWithZoneNumberSixWithHamburgStresemannstrasse() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_stresemannstrasse"
            displayName = "Stresemannstraße, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = true
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.HGV.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertRoadSignIsDisplayed(context.getString(R.string.diesel_prohibition_hgvs_free_until_euro_v_open_for_residents_hamburg))
    }

    @Test
    fun renderDetailsWithHeavyGoodsVehiclesOnly() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = "Max Brauer Allee, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = false
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.HGV.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertProhibitedVehiclesTextIsDisplayed(dieselProhibitionZone)
    }

    @Test
    fun renderDetailsWithCarsOnly() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = "Max Brauer Allee, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = false
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.CAR.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertProhibitedVehiclesTextIsDisplayed(dieselProhibitionZone)
    }

    @Test
    fun renderDetailsWithoutIsCongruentWithLowEmissionZone() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = "Max Brauer Allee, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = false
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.HGV.value, VehicleType.CAR.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertIsCongruentWithLowEmissionZoneTextIsNotDisplayed()
    }

    @Test
    fun renderDetailsWithoutZoneNumberForNonResidentsSince() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = "Max Brauer Allee, Hamburg"
            zoneNumber = 6
            isCongruentWithLowEmissionZone = true
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = null
            prohibitedVehicles = listOf(VehicleType.HGV.value, VehicleType.CAR.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertZoneNumberForNonResidentsSinceTextIsNotDisplayed()
    }

    @Test
    fun renderDetailsWithoutDisplayName() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "dpz_hamburg_max_brauer_allee"
            displayName = ""
            zoneNumber = 6
            isCongruentWithLowEmissionZone = true
            zoneNumberForResidentsSince = dateOf(2019, 1, 1)
            zoneNumberForNonResidentsSince = dateOf(2018, 5, 31)
            prohibitedVehicles = listOf(VehicleType.HGV.value, VehicleType.CAR.value)
            geometrySource = "Geoportal Hamburg"
            geometryUpdatedAt = dateOf(2019, 2, 1)
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone))
        assertDisplayNameTextIsNotDisplayed()
    }

    @Test
    fun renderDetailsWithMinimumViews() {
        val dieselProhibitionZone = DieselProhibitionZone().apply {
            fileName = "lez_stuttgart"
            displayName = ""
            zoneNumber = 5
            isCongruentWithLowEmissionZone = false
            zoneNumberForResidentsSince = null
            zoneNumberForNonResidentsSince = null
            prohibitedVehicles = emptyList()
            geometrySource = null
            geometryUpdatedAt = null
        }

        launchActivity(getAdministrativeZone(dieselProhibitionZone, "stuttgart"))
        assertSectionTitleTextIsDisplayed()
        assertDisplayNameTextIsNotDisplayed()
        assertRoadSignIsDisplayed(context.getString(R.string.diesel_prohibition_free_as_of_euro_5_v_except_delivery_vehicles_stuttgart))
        assertAllowedEmissionStandardInDpzTextIsDisplayed(dieselProhibitionZone)
        assertIsCongruentWithLowEmissionZoneTextIsNotDisplayed()
        assertZoneNumberForResidentsSinceTextIsNotDisplayed()
        assertZoneNumberForNonResidentsSinceTextIsNotDisplayed()
        assertProhibitedVehiclesTextIsNotDisplayed()
        assertGeometrySourceTextIsNotDisplayed()
        assertGeometryUpdatedAtTextIsNotDisplayed()
    }

    private fun assertSectionTitleTextIsDisplayed() {
        R.id.detailsDpzSectionTitleView.displaysText(R.string.city_info_section_title_dpz)
    }

    private fun assertRoadSignIsDisplayed(roadSignDescription: String) {
        onView(withId(R.id.genericRoadSignView))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withContentDescription(roadSignDescription)))
    }

    private fun assertDisplayNameTextIsDisplayed(displayName: String) {
        R.id.detailsDpzDisplayNameView.displaysText(displayName)
    }

    private fun assertDisplayNameTextIsNotDisplayed() {
        R.id.detailsDpzDisplayNameView.isNotDisplayed()
    }

    private fun assertAllowedEmissionStandardInDpzTextIsDisplayed(dieselProhibitionZone: DieselProhibitionZone) {
        val allowedEmissionStandardInDpzText = dieselProhibitionZone.allowedEmissionStandardInDpzText(context)
        R.id.detailsDpzAllowedEmissionStandardInDpzView.displaysText(allowedEmissionStandardInDpzText)
    }

    private fun assertIsCongruentWithLowEmissionZoneTextIsDisplayed() {
        val isCongruentWithLowEmissionZoneText = R.string.city_info_is_congruent_with_lez
        R.id.detailsDpzIsCongruentWithLowEmissionZoneView.displaysText(isCongruentWithLowEmissionZoneText)
    }

    private fun assertIsCongruentWithLowEmissionZoneTextIsNotDisplayed() {
        R.id.detailsDpzIsCongruentWithLowEmissionZoneView.isNotDisplayed()
    }

    private fun assertZoneNumberForResidentsSinceTextIsDisplayed(dieselProhibitionZone: DieselProhibitionZone) {
        val zoneNumberForResidentsSinceText = dieselProhibitionZone.zoneNumberForResidentsSinceText(context)
        R.id.detailsDpzZoneNumberForResidentsSinceView.displaysText(zoneNumberForResidentsSinceText)
    }

    private fun assertZoneNumberForResidentsSinceTextIsNotDisplayed() {
        R.id.detailsDpzZoneNumberForResidentsSinceView.isNotDisplayed()
    }

    private fun assertZoneNumberForNonResidentsSinceTextIsDisplayed(dieselProhibitionZone: DieselProhibitionZone) {
        val zoneNumberForNonResidentsSinceText = dieselProhibitionZone.zoneNumberForNonResidentsSinceText(context)
        R.id.detailsDpzZoneNumberForNonResidentsSinceView.displaysText(zoneNumberForNonResidentsSinceText)
    }

    private fun assertZoneNumberForNonResidentsSinceTextIsNotDisplayed() {
        R.id.detailsDpzZoneNumberForNonResidentsSinceView.isNotDisplayed()
    }

    private fun assertProhibitedVehiclesTextIsDisplayed(dieselProhibitionZone: DieselProhibitionZone) {
        val prohibitedVehiclesText = dieselProhibitionZone.prohibitedVehiclesText(context)
        R.id.detailsDpzProhibitedVehiclesView.displaysText(prohibitedVehiclesText)
    }

    private fun assertProhibitedVehiclesTextIsNotDisplayed() {
        R.id.detailsDpzProhibitedVehiclesView.isNotDisplayed()
    }

    private fun assertGeometrySourceTextIsDisplayed(geometrySource: String?) {
        val geometrySourceText = context.getString(R.string.city_info_geometry_source_text, geometrySource)
        R.id.detailsDpzGeometrySourceView.displaysText(geometrySourceText)
    }

    private fun assertGeometrySourceTextIsNotDisplayed() {
        R.id.detailsDpzGeometrySourceView.isNotDisplayed()
    }

    private fun assertGeometryUpdatedAtTextIsDisplayed(geometryUpdatedAt: Date?) {
        val geometryUpdatedAtText = StringHelper.getGeometryUpdatedAtText(context, geometryUpdatedAt)
        R.id.detailsDpzGeometryUpdatedAtView.displaysText(geometryUpdatedAtText)
    }

    private fun assertGeometryUpdatedAtTextIsNotDisplayed() {
        R.id.detailsDpzGeometryUpdatedAtView.isNotDisplayed()
    }

    private fun getAdministrativeZone(dieselProhibitionZone: DieselProhibitionZone, zoneName: String = "hamburg") =
            AdministrativeZone().apply {
                name = zoneName
                childZones = listOf(dieselProhibitionZone)
                urlUmweltPlaketteDe = "http://mandatory.url"
                urlBadgeOnline = "http://mandatory.url"
            }

    private fun launchActivity(zone: AdministrativeZone) {
        val scenario = ActivityScenario.launch<DetailsActivity>(IntentHelper.getDetailsIntent(context, zone))
        scenario.onActivity {
            // Rotate to verify fragment is re-used
            AndroidTestUtils.rotateScreen(it)
        }
    }

    private fun dateOf(year: Int, month: Int, day: Int): Date = DateHelper.getDate(year, month, day)

}