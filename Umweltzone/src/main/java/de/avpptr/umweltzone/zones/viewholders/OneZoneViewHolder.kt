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
import de.avpptr.umweltzone.databinding.ZonesListItemOneZoneBinding
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

class OneZoneViewHolder(

        private val binding: ZonesListItemOneZoneBinding,
        private val onItemClick: (view: View) -> Unit

) : ZoneViewHolder<ZoneViewModel.OneZoneViewModel>(binding.root) {

    override fun bind(viewModel: ZoneViewModel.OneZoneViewModel) = with(binding) {
        val badgeBackground = zoneOneZoneBadgeView.background as LayerDrawable
        val zoneShapeView = badgeBackground.findDrawableByLayerId(R.id.zone_shape) as GradientDrawable
        root.tag = viewModel
        root.setOnClickListener(onItemClick)
        zoneOneZoneNameView.text = viewModel.name
        zoneOneZoneNameView.setTextColor(viewModel.nameTextColor)
        zoneOneZoneBadgeView.setBadge(zoneShapeView, viewModel.badgeViewModel)
    }

}
