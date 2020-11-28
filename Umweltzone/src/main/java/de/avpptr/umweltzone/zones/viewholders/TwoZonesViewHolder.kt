/*
 *  Copyright (C) 2021  Tobias Preuss
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
import androidx.annotation.LayoutRes
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.databinding.ZonesListItemTwoZonesBinding
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

class TwoZonesViewHolder(

        val view: View,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder<ZoneViewModel.TwoZonesViewModel>(view) {

    private val binding =  ZonesListItemTwoZonesBinding.bind(view)
    private val zoneShape1View: GradientDrawable
    private val zoneShape2View: GradientDrawable

    init {
        val badge1Background = binding.zoneTwoZonesBadge1View.background as LayerDrawable
        zoneShape1View = badge1Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge2Background = binding.zoneTwoZonesBadge2View.background as LayerDrawable
        zoneShape2View = badge2Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
    }

    override fun bind(viewModel: ZoneViewModel.TwoZonesViewModel) = with(view) {
        tag = viewModel
        setOnClickListener(onItemClick)
        binding.zoneTwoZonesNameView.text = viewModel.name
        binding.zoneTwoZonesNameView.setTextColor(viewModel.nameTextColor)
        binding.zoneTwoZonesBadge1View.setBadge(zoneShape1View, viewModel.badge1ViewModel)
        binding.zoneTwoZonesBadge2View.setBadge(zoneShape2View, viewModel.badge2ViewModel)
    }

    companion object {
        @LayoutRes
        const val layout = R.layout.zones_list_item_two_zones
    }

}
