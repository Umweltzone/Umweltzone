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

package de.avpptr.umweltzone.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Lifecycle aware property delegate which automatically cleans (sets to `null`) object references
 * of the backing property: [volatileValue].
 *
 * Must be used in combination with the [androidx.fragment.app.Fragment.Fragment(@LayoutRes int contentLayoutId)] constructor.
 *
 * Adapted from:
 * - https://medium.com/scalereal/let-your-delegates-auto-nullify-references-%EF%B8%8F-3ad6d8875497
 * - https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 * - https://gist.github.com/Zhuinden/ea3189198938bd16c03db628e084a4fa
 */
class AutoCleanableProperty<T : Any>(

        fragment: Fragment,
        private val initializer: ((View) -> T)?

) : ReadWriteProperty<Fragment, T> {

    private var volatileValue: T? = null

    init {
        val lifecycleObserver = object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { viewLifecycleOwner ->
                viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        volatileValue = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
            }
        }
        fragment.lifecycle.addObserver(lifecycleObserver)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = volatileValue
        if (value != null) {
            return value
        }
        if (thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(INITIALIZED)) {
            return initializer?.invoke(thisRef.requireView()).also { volatileValue = it }
                    ?: error("The value has not yet been set or no default initializer provided.")
        } else {
            error("Fragment might have been destroyed or not initialized yet.")
        }
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        volatileValue = value
    }

}

inline fun <reified T : Any> Fragment.autoCleanable(

        noinline initializer: ((View) -> T)? = null

): AutoCleanableProperty<T> {

    return AutoCleanableProperty(this, initializer)

}
