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

package de.avpptr.umweltzone.zones

import android.os.Bundle
import android.view.View
import de.avpptr.umweltzone.BuildConfig
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.Umweltzone
import de.avpptr.umweltzone.base.BaseFragment
import de.avpptr.umweltzone.models.AdministrativeZone
import de.avpptr.umweltzone.utils.ContentProvider
import de.avpptr.umweltzone.utils.IntentHelper
import de.avpptr.umweltzone.zones.dataconverters.toZoneViewModels
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel
import kotlinx.android.synthetic.main.fragment_zones.*

class ZonesFragment : BaseFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_zones

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = requireContext()
        administrativeZones = ContentProvider.getAdministrativeZones(context)
        val zoneViewModels = administrativeZones.toZoneViewModels(context)
        administrativeZonesView.adapter = ZonesAdapter(zoneViewModels, ::onItemClick)
    }

    private fun onItemClick(view: View) {
        val zoneViewModel = view.tag as ZoneViewModel
        val zone = administrativeZones.single { it.displayName == zoneViewModel.name }
        storeSelectedLocation(zone)
        val intent = IntentHelper.getNewMapIntent(requireContext())
        Umweltzone.centerZoneRequested = true
        startActivity(intent)
    }

    private fun storeSelectedLocation(zone: AdministrativeZone) =
            preferencesHelper.storeAdministrativeZone(zone)

    private val preferencesHelper by lazy { (requireContext().applicationContext as Umweltzone).preferencesHelper }

    companion object {

        const val FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".ZONES_FRAGMENT_TAG"

        // Used for caching
        private var administrativeZones: List<AdministrativeZone> = emptyList()

    }

}
