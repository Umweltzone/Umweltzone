/*
 *  Copyright (C) 2024  Tobias Preuss
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

package de.avpptr.umweltzone.details

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import de.avpptr.umweltzone.BuildConfig
import de.avpptr.umweltzone.R
import de.avpptr.umweltzone.base.BaseFragment
import de.avpptr.umweltzone.details.dataconverters.toDetailsViewModel
import de.avpptr.umweltzone.details.dataconverters.toOtherDetailsViewModel
import de.avpptr.umweltzone.details.viewmodels.DpzDetailsViewModel
import de.avpptr.umweltzone.details.viewmodels.LezDetailsViewModel
import de.avpptr.umweltzone.details.viewmodels.OtherDetailsViewModel
import de.avpptr.umweltzone.extensions.textOrHide
import de.avpptr.umweltzone.extensions.typeOrHide
import de.avpptr.umweltzone.models.AdministrativeZone
import de.avpptr.umweltzone.models.ChildZone
import de.avpptr.umweltzone.models.DieselProhibitionZone
import de.avpptr.umweltzone.models.LowEmissionZone
import de.avpptr.umweltzone.utils.ViewHelper
import info.metadude.kotlin.library.roadsigns.RoadSign
import org.parceler.Parcels

class DetailsFragment : BaseFragment() {

    private val zoneDetailsView by lazy { view?.findViewById(R.id.zoneDetailsView) as LinearLayout }

    private var administrativeZone: AdministrativeZone? = null

    public override fun getLayoutResource() = R.layout.fragment_zone_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = arguments
        if (extras != null) {
            val parcelable = BundleCompat.getParcelable(extras, BUNDLE_KEY_ADMINISTRATIVE_ZONE, Parcelable::class.java)
            administrativeZone = Parcels.unwrap<AdministrativeZone>(parcelable)
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity != null && administrativeZone != null) {
            updateDetails(activity!!, administrativeZone!!)
            updateSubTitle(administrativeZone!!.displayName)
        }
    }

    private fun updateDetails(activity: Activity, zone: AdministrativeZone) {
        zoneDetailsView.removeAllViews()
        zone.childZones.forEach { updateChildZoneDetails(activity, it) }
        addOtherDetails(zone.toOtherDetailsViewModel())
        addVerticalSpace()
    }

    private fun updateChildZoneDetails(activity: Activity, childZone: ChildZone) {
        when (childZone) {
            is LowEmissionZone -> addLowEmissionZoneDetails(childZone.toDetailsViewModel(activity))
            is DieselProhibitionZone -> addDieselProhibitionZoneDetails(childZone.toDetailsViewModel(activity))
        }
    }

    private fun addLowEmissionZoneDetails(viewModel: LezDetailsViewModel) {
        val detailsView = layoutInflater.inflate(R.layout.details_low_emission_zone)
        updateLezDetails(detailsView, viewModel)
        zoneDetailsView.addChildView(detailsView)
    }

    private fun addDieselProhibitionZoneDetails(viewModel: DpzDetailsViewModel) {
        val detailsView = layoutInflater.inflate(R.layout.details_diesel_prohibition_zone)
        updateDpzDetails(detailsView, viewModel)
        zoneDetailsView.addChildView(detailsView)
    }

    private fun addOtherDetails(viewModel: OtherDetailsViewModel) {
        val detailsView = layoutInflater.inflate(R.layout.details_other)
        updateOtherDetails(detailsView, viewModel)
        zoneDetailsView.addChildView(detailsView)
    }

    private fun addVerticalSpace() {
        val detailsView = layoutInflater.inflate(R.layout.vertical_space)
        zoneDetailsView.addChildView(detailsView)
    }

    private fun LayoutInflater.inflate(@LayoutRes resource: Int) =
            inflate(resource, zoneDetailsView, false)

    private fun LinearLayout.addChildView(view: View) =
            addView(view, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

    private fun updateLezDetails(rootView: View, model: LezDetailsViewModel) {
        val roadSignView = rootView.findViewById(R.id.detailsLezRoadSignView) as RoadSign
        val listOfCitiesView = rootView.findViewById(R.id.detailsLezListOfCitiesView) as TextView
        val zoneNumberSinceView = rootView.findViewById(R.id.detailsLezZoneNumberSinceView) as TextView
        val nextZoneNumberAsOfView = rootView.findViewById(R.id.detailsLezNextZoneNumberAsOfView) as TextView
        val abroadLicensedVehicleZoneInfoView = rootView.findViewById(R.id.detailsLezAbroadLicensedVehicleZoneInfoView) as TextView
        val geometryUpdatedAtView = rootView.findViewById(R.id.detailsLezGeometryUpdatedAtView) as TextView
        val geometrySourceView = rootView.findViewById(R.id.detailsLezGeometrySourceView) as TextView
        return with(model) {
            roadSignView.typeOrHide = roadSignType
            listOfCitiesView.textOrHide = listOfCitiesText
            zoneNumberSinceView.textOrHide = zoneNumberSinceText
            nextZoneNumberAsOfView.textOrHide = nextZoneNumberAsOfText
            abroadLicensedVehicleZoneInfoView.textOrHide = abroadLicensedVehicleZoneNumberText
            geometryUpdatedAtView.textOrHide = geometryUpdatedAtText
            geometrySourceView.textOrHide = geometrySourceText
        }
    }

    private fun updateDpzDetails(rootView: View, model: DpzDetailsViewModel) {
        val displayNameView = rootView.findViewById(R.id.detailsDpzDisplayNameView) as TextView
        val roadSignView = rootView.findViewById(R.id.detailsDpzRoadSignView) as RoadSign
        val allowedEmissionStandardInDpzView = rootView.findViewById(R.id.detailsDpzAllowedEmissionStandardInDpzView) as TextView
        val isCongruentWithLowEmissionZoneView = rootView.findViewById(R.id.detailsDpzIsCongruentWithLowEmissionZoneView) as TextView
        val zoneNumberForResidentsSinceView = rootView.findViewById(R.id.detailsDpzZoneNumberForResidentsSinceView) as TextView
        val zoneNumberForNonResidentsSinceView = rootView.findViewById(R.id.detailsDpzZoneNumberForNonResidentsSinceView) as TextView
        val prohibitedVehiclesView = rootView.findViewById(R.id.detailsDpzProhibitedVehiclesView) as TextView
        val geometrySourceView = rootView.findViewById(R.id.detailsDpzGeometrySourceView) as TextView
        val geometryUpdatedAtView = rootView.findViewById(R.id.detailsDpzGeometryUpdatedAtView) as TextView
        return with(model) {
            displayNameView.textOrHide = displayName
            roadSignView.typeOrHide = roadSignType
            allowedEmissionStandardInDpzView.textOrHide = allowedEmissionStandardInDpz
            isCongruentWithLowEmissionZoneView.textOrHide = isCongruentWithLowEmissionZone
            zoneNumberForResidentsSinceView.textOrHide = zoneNumberForResidentsSince
            zoneNumberForNonResidentsSinceView.textOrHide = zoneNumberForNonResidentsSince
            prohibitedVehiclesView.textOrHide = prohibitedVehicles
            geometrySourceView.textOrHide = geometrySource
            geometryUpdatedAtView.textOrHide = geometryUpdatedAt
        }
    }

    private fun updateOtherDetails(rootView: View, model: OtherDetailsViewModel) {
        val furtherInformationView = rootView.findViewById(R.id.detailsOtherFurtherInformationView) as TextView
        val badgeOnlineView = rootView.findViewById(R.id.detailsOtherBadgeOnlineView) as TextView
        val activity = requireActivity()
        return with(model) {
            ViewHelper.setupTextViewExtended(activity,
                    furtherInformationView,
                    R.string.city_info_further_information,
                    furtherInformation)

            if (urlBadgeOnline.isEmpty()) {
                badgeOnlineView.isVisible = false
            } else {
                badgeOnlineView.isVisible = true
                ViewHelper.setupTextViewExtended(activity,
                        badgeOnlineView,
                        R.string.city_info_badge_online_title,
                        urlBadgeOnline)
            }
        }
    }

    companion object {

        const val FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".DETAILS_FRAGMENT_TAG"

        const val BUNDLE_KEY_ADMINISTRATIVE_ZONE = BuildConfig.APPLICATION_ID + ".ADMINISTRATIVE_ZONE_BUNDLE_KEY"

        @JvmStatic
        fun newInstance(administrativeZone: AdministrativeZone): DetailsFragment {
            val fragment = DetailsFragment()
            val parcelable = Parcels.wrap(administrativeZone)
            val extras = bundleOf(
                    BUNDLE_KEY_ADMINISTRATIVE_ZONE to parcelable
            )
            fragment.arguments = extras
            return fragment
        }
    }

}
