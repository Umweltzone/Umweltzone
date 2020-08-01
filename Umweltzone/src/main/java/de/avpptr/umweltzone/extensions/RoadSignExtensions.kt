@file:JvmName("RoadSignExtensions")

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

package de.avpptr.umweltzone.extensions

import androidx.annotation.NonNull
import androidx.core.view.isVisible
import info.metadude.kotlin.library.roadsigns.RoadSign

/**
 * Returns the type this RoadSign is displaying.
 * Sets the type to be displayed or hides this view if the type is [RoadSign.Type.None].
 */
var @receiver:NonNull RoadSign.typeOrHide: RoadSign.Type
    @NonNull
    get() = this.type
    set(@NonNull value) {
        if (RoadSign.Type.None == value) {
            isVisible = false
        } else {
            isVisible = true
            this.type = value
        }
    }
