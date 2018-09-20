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

package de.avpptr.umweltzone.zones

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.avpptr.umweltzone.zones.viewholders.OneZoneViewHolder
import de.avpptr.umweltzone.zones.viewholders.ZoneViewHolder
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

class ZonesAdapter(

        private val zones: List<ZoneViewModel>,
        private val onItemClick: (view: View) -> Unit,
        private val onItemViewInflationError: (viewType: Int) -> Unit

) : RecyclerView.Adapter<ZoneViewHolder<ZoneViewModel>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoneViewHolder<ZoneViewModel> {
        val inflater = LayoutInflater.from(parent.context)
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            ChildZonesCount.ONE.value -> {
                val itemView = inflater.inflate(OneZoneViewHolder.layout, parent, false)
                OneZoneViewHolder(itemView, onItemClick)
            }
            else -> {
                onItemViewInflationError.invoke(viewType)
                error("Unknown view type: $viewType")
            }
        } as ZoneViewHolder<ZoneViewModel>
    }

    override fun onBindViewHolder(viewHolder: ZoneViewHolder<ZoneViewModel>, position: Int) {
        val viewModel = zones[position]
        viewHolder.bind(viewModel)
    }

    override fun getItemCount(): Int =
            zones.size

    override fun getItemViewType(position: Int): Int =
            zones[position].childZonesCount.value

}
