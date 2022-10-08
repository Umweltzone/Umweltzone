/*
 *  Copyright (C) 2022  Tobias Preuss
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

package de.avpptr.umweltzone.zones.viewholders

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.databinding.ZonesListItemThreeZonesBinding
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

class ThreeZonesViewHolder(

        private val binding: ZonesListItemThreeZonesBinding,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder<ZoneViewModel.ThreeZonesViewModel>(binding.root) {

    override fun bind(viewModel: ZoneViewModel.ThreeZonesViewModel) = with(binding) {
        val badge1Background = zoneThreeZonesBadge1View.background as LayerDrawable
        val zoneShape1View = badge1Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge2Background = zoneThreeZonesBadge2View.background as LayerDrawable
        val zoneShape2View = badge2Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge3Background = zoneThreeZonesBadge3View.background as LayerDrawable
        val zoneShape3View = badge3Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        root.tag = viewModel
        root.setOnClickListener(onItemClick)
        zoneThreeZonesNameView.text = viewModel.name
        zoneThreeZonesNameView.setTextColor(viewModel.nameTextColor)
        zoneThreeZonesBadge1View.setBadge(zoneShape1View, viewModel.badge1ViewModel)
        zoneThreeZonesBadge2View.setBadge(zoneShape2View, viewModel.badge2ViewModel)
        zoneThreeZonesBadge3View.setBadge(zoneShape3View, viewModel.badge3ViewModel)
    }

}
