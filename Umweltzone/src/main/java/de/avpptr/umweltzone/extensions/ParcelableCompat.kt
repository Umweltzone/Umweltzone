/*
 *  Copyright (C) 2023  Tobias Preuss
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

@file:JvmName("ParcelableCompat")

package de.avpptr.umweltzone.extensions

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.os.Parcelable

/**
 * Retrieves extended data of type [T] from the intent. See [Intent.getParcelableExtra].
 * To be removed once the androidx.core library offers such a compat wrapper.
 */
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

/**
 * Returns the value associated with the given [key], or `null` if no mapping of the desired type
 * exists for the given [key] or a `null` value is explicitly associated with the [key].
 * See [Bundle.getParcelable]
 * To be removed once the androidx.core library offers such a compat wrapper.
 */
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Bundle.getParcelableCompat(key: String): Parcelable? = parcelable(key)
