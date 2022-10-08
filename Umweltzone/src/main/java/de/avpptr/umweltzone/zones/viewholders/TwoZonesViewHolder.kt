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
import de.avpptr.umweltzone.databinding.ZonesListItemTwoZonesBinding
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

class TwoZonesViewHolder(

        private val binding: ZonesListItemTwoZonesBinding,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder<ZoneViewModel.TwoZonesViewModel>(binding.root) {

    override fun bind(viewModel: ZoneViewModel.TwoZonesViewModel) = with(binding) {
        val badge1Background = zoneTwoZonesBadge1View.background as LayerDrawable
        val zoneShape1View = badge1Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge2Background = zoneTwoZonesBadge2View.background as LayerDrawable
        val zoneShape2View = badge2Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        root.tag = viewModel
        root.setOnClickListener(onItemClick)
        zoneTwoZonesNameView.text = viewModel.name
        zoneTwoZonesNameView.setTextColor(viewModel.nameTextColor)
        zoneTwoZonesBadge1View.setBadge(zoneShape1View, viewModel.badge1ViewModel)
        zoneTwoZonesBadge2View.setBadge(zoneShape2View, viewModel.badge2ViewModel)
    }

}
