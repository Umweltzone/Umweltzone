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

package de.avpptr.umweltzone.details

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.LinearLayout
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.utils.IntentHelper
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class NoCitySelectedFragmentTest {

    private lateinit var context: Context

    @Suppress("BooleanLiteralArgument")
    @get:Rule
    val activityRule: ActivityTestRule<*> = ActivityTestRule(DetailsActivity::class.java, true, false)

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun renderDetails() {
        launchActivity()

        onView(allOf<View>(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withText(R.string.no_city_selected_title)))
                .perform(scrollTo())
                .check(matches(isDisplayed()))

        onView(allOf<View>(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withText(R.string.no_city_selected_status_desc)))
                .perform(scrollTo())
                .check(matches(isDisplayed()))

        onView(withId(R.id.no_city_selected_select_zone))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.no_city_selected_select_zone)))
    }

    private fun launchActivity() {
        activityRule.launchActivity(IntentHelper.getIntent(context, DetailsActivity::class.java))
    }

}
