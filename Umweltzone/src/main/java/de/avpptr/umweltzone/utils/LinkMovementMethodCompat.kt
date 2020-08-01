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

package de.avpptr.umweltzone.utils

import android.os.Build
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import me.saket.bettermovementmethod.BetterLinkMovementMethod

object LinkMovementMethodCompat {

    /**
     * Returns an instance of [BetterLinkMovementMethod] on devices running
     * Android 4.1.x Jelly Bean (API 16) or newer. [LinkMovementMethod] is
     * returned for devices running an older Android version.
     */
    @JvmStatic
    fun getInstance(): MovementMethod =
            if (shouldUseLegacyLinkMovementMethod()) {
                LinkMovementMethod.getInstance()
            } else {
                BetterLinkMovementMethod.getInstance()
            }

    /**
     * Returns true if the device is running Android 4.0.x Ice Cream Sandwich (API 15) or older;
     * false otherwise.
     */
    @JvmStatic
    fun shouldUseLegacyLinkMovementMethod() =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN

}
