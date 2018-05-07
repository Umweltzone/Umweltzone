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

package de.avpptr.umweltzone.city;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.utils.IntentHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class NoCitySelectedFragmentTest {

    private Context mContext;

    @Rule
    public final ActivityTestRule mActivityRule =
            new ActivityTestRule<>(CityInfoActivity.class, true, false);

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void renderCityInfo() {
        launchActivity();

        onView(allOf(
                withParent(isAssignableFrom(LinearLayout.class)),
                withText(R.string.no_city_selected_title)))
                .perform(scrollTo())
                .check(matches(isDisplayed()));

        onView(allOf(
                withParent(isAssignableFrom(LinearLayout.class)),
                withText(R.string.no_city_selected_status_desc)))
                .perform(scrollTo())
                .check(matches(isDisplayed()));

        onView(withId(R.id.no_city_selected_select_zone))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.no_city_selected_select_zone)));
    }

    private void launchActivity() {
        mActivityRule.launchActivity(IntentHelper.getCityInfoIntent(mContext, null));
    }

}
