/*
 *  Copyright (C) 2024  Tobias Preuss
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

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import androidx.core.content.IntentCompat
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.base.BaseActivity
import de.avpptr.umweltzone.models.AdministrativeZone
import org.parceler.Parcels

class DetailsActivity : BaseActivity(R.layout.activity_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    private fun initFragment() {
        val intent = intent ?: throw AssertionError("Intent cannot be null.")
        val parcelable = IntentCompat.getParcelableExtra(intent, DetailsFragment.BUNDLE_KEY_ADMINISTRATIVE_ZONE, Parcelable::class.java)
        if (parcelable == null) {
            addNoCitySelectedFragment()
        } else {
            if (!fragmentExists(DetailsFragment.FRAGMENT_TAG)) {
                addFragment(parcelable)
            }
        }
    }

    private fun addNoCitySelectedFragment() {
        addFragment(R.id.details_container,
                NoCitySelectedFragment(),
                NoCitySelectedFragment.FRAGMENT_TAG)
    }

    private fun addFragment(parcelable: Parcelable) {
        val zone = Parcels.unwrap<AdministrativeZone>(parcelable)
        val fragment = DetailsFragment.newInstance(zone)
        addFragment(R.id.details_container, fragment, DetailsFragment.FRAGMENT_TAG)
    }

}
