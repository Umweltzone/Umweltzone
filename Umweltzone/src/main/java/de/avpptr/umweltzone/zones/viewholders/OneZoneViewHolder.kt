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

package de.avpptr.umweltzone.zones.viewholders

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.annotation.LayoutRes
import android.view.View
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel
import kotlinx.android.synthetic.main.zones_list_item_one_zone.view.*

class OneZoneViewHolder(

        val view: View,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder(view) {

    private val zoneShapeView: GradientDrawable

    init {
        val badgeBackground = view.zoneOneZoneBadgeView.background as LayerDrawable
        zoneShapeView = badgeBackground.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
    }

    fun bind(viewModel: ZoneViewModel.OneZoneViewModel) = with(view) {
        tag = viewModel
        setOnClickListener(onItemClick)
        zoneOneZoneNameView.text = viewModel.name
        zoneOneZoneNameView.setTextColor(viewModel.nameTextColor)
        zoneOneZoneBadgeView.setBadge(zoneShapeView, viewModel.badgeViewModel)
    }

    companion object {
        @LayoutRes
        const val layout = R.layout.zones_list_item_one_zone
    }

}
