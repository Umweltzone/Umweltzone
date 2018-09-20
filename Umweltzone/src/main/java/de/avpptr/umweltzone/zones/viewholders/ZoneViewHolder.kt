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

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import de.avpptr.umweltzone.zones.viewmodels.BadgeViewModel
import de.avpptr.umweltzone.zones.viewmodels.ZoneViewModel

abstract class ZoneViewHolder<in T: ZoneViewModel>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(viewModel: T)

    protected fun TextView.setBadge(zoneShapeView: GradientDrawable, viewModel: BadgeViewModel) {
        this.text = viewModel.text
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            // Replaces the round badge with a colored square.
            val colorDrawable = ColorDrawable(viewModel.backgroundColor)
            this.setBackgroundDrawable(colorDrawable)
        } else {
            zoneShapeView.setColor(viewModel.backgroundColor)
        }
    }

}
