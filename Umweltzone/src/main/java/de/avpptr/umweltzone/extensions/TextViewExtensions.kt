@file:JvmName("TextViewExtensions")

/*
 *  Copyright (C) 2019  Tobias Preuss
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

import android.support.annotation.NonNull
import android.widget.TextView
import de.avpptr.umweltzone.contract.Resources

/**
 * Returns the text this TextView is displaying.
 * Sets the text to be displayed or hides this view if the text is empty.
 */
var @receiver:NonNull TextView.textOrHide: CharSequence
    @NonNull
    get() = this.text
    set(@NonNull value) {
        if (value.isEmpty()) {
            isVisible = false
        } else {
            isVisible = true
            this.text = value
        }
    }
/**
 * Set the background resource to be displayed or hides the view if the resource is invalid.
 * The getter is not implemented.
 */
var @receiver:NonNull TextView.setBackgroundResourceOrHide: Int
    @NonNull
    get() = throw NotImplementedError()
    set(@NonNull value) {
        if (value == Resources.INVALID_RESOURCE_ID) {
            isVisible = false
        } else {
            isVisible = true
            this.setBackgroundResource(value)
        }
    }
