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
import android.widget.LinearLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.utils.IntentHelper
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class NoCitySelectedFragmentTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
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
        ActivityScenario.launch<DetailsActivity>(IntentHelper.getIntent(context, DetailsActivity::class.java))
    }

}
