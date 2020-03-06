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

package de.avpptr.umweltzone.zones.viewholders

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import androidx.annotation.LayoutRes
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel
import kotlinx.android.synthetic.main.zones_list_item_three_zones.view.*

class ThreeZonesViewHolder(

        val view: View,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder<ZoneViewModel.ThreeZonesViewModel>(view) {

    private val zoneShape1View: GradientDrawable
    private val zoneShape2View: GradientDrawable
    private val zoneShape3View: GradientDrawable

    init {
        val badge1Background = view.zoneThreeZonesBadge1View.background as LayerDrawable
        zoneShape1View = badge1Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge2Background = view.zoneThreeZonesBadge2View.background as LayerDrawable
        zoneShape2View = badge2Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        val badge3Background = view.zoneThreeZonesBadge3View.background as LayerDrawable
        zoneShape3View = badge3Background.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
    }

    override fun bind(viewModel: ZoneViewModel.ThreeZonesViewModel) = with(view) {
        tag = viewModel
        setOnClickListener(onItemClick)
        zoneThreeZonesNameView.text = viewModel.name
        zoneThreeZonesNameView.setTextColor(viewModel.nameTextColor)
        zoneThreeZonesBadge1View.setBadge(zoneShape1View, viewModel.badge1ViewModel)
        zoneThreeZonesBadge2View.setBadge(zoneShape2View, viewModel.badge2ViewModel)
        zoneThreeZonesBadge3View.setBadge(zoneShape3View, viewModel.badge3ViewModel)
    }

    companion object {
        @LayoutRes
        const val layout = R.layout.zones_list_item_three_zones
    }

}
