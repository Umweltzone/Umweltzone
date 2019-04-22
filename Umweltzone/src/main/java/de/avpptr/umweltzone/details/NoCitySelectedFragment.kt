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

import android.app.Activity
import android.widget.Button
import de.avpptr.umweltzone.BuildConfig
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.base.BaseFragment
import de.avpptr.umweltzone.utils.IntentHelper

class NoCitySelectedFragment : BaseFragment() {

    override fun getLayoutResource() = R.layout.fragment_no_city_selected

    override fun onResume() {
        super.onResume()
        setUpViews(activity)
    }

    private fun setUpViews(activity: Activity) {
        val showOnMapButton = activity.findViewById<Button>(R.id.no_city_selected_select_zone)
        showOnMapButton.setOnClickListener {
            startActivity(IntentHelper.getCitiesIntent(activity))
        }
    }

    companion object {
        const val FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".NO_CITY_SELECTED_FRAGMENT_TAG"
    }

}
