/*
 *  Copyright (C) 2016  Tobias Preuss, Peter Vasil
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

package de.avpptr.umweltzone.city;

import org.parceler.Parcels;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseFragment;
import de.avpptr.umweltzone.contract.Resources;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.LowEmissionZoneNumberConverter;
import de.avpptr.umweltzone.utils.StringHelper;
import de.avpptr.umweltzone.utils.ViewHelper;

public class CityInfoFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".CITY_INFO_FRAGMENT_TAG";

    public static final String BUNDLE_KEY_LOW_EMISSION_ZONE =
            BuildConfig.APPLICATION_ID + ".LOW_EMISSION_ZONE_BUNDLE_KEY";

    private LowEmissionZone mLowEmissionZone;

    public static CityInfoFragment newInstance(@NonNull LowEmissionZone lowEmissionZone) {
        CityInfoFragment cityInfoFragment = new CityInfoFragment();
        Bundle extras = new Bundle();
        Parcelable lowEmissionZoneParcelable = Parcels.wrap(lowEmissionZone);
        extras.putParcelable(BUNDLE_KEY_LOW_EMISSION_ZONE, lowEmissionZoneParcelable);
        cityInfoFragment.setArguments(extras);
        return cityInfoFragment;
    }

    public CityInfoFragment() {
        // Mandatory empty constructor for the fragment manager to
        // instantiate the fragment (e.g. upon screen orientation changes).
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            Parcelable parcelable = extras.getParcelable(BUNDLE_KEY_LOW_EMISSION_ZONE);
            mLowEmissionZone = Parcels.unwrap(parcelable);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_city_info;
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null && mLowEmissionZone != null) {
            setUpCityInfo(activity, mLowEmissionZone);
        }
    }

    private void setUpCityInfo(@NonNull final Activity activity,
            @NonNull final LowEmissionZone lowEmissionZone) {

        // Title
        TextView titleTextView = (TextView) activity.findViewById(R.id.city_info_title);
        titleTextView.setText(lowEmissionZone.displayName);

        // Zone status image
        TextView zoneStatus = (TextView) activity.findViewById(R.id.city_info_zone_status);
        int zoneStatusId = LowEmissionZoneNumberConverter
                .getStatusDrawable(lowEmissionZone.zoneNumber);
        if (zoneStatusId == Resources.INVALID_RESOURCE_ID) {
            zoneStatus.setVisibility(View.GONE);
        } else {
            zoneStatus.setVisibility(View.VISIBLE);
            zoneStatus.setBackgroundResource(zoneStatusId);
        }

        // List of cities
        TextView listOfCitiesTextView =
                (TextView) activity.findViewById(R.id.city_info_list_of_cities);
        String listOfCitiesText = StringHelper.getListOfCitiesText(activity, lowEmissionZone);
        ViewHelper.setTextOrHideView(listOfCitiesTextView, listOfCitiesText);

        // Zone number since or as of
        TextView zoneNumberSinceTextView =
                (TextView) activity.findViewById(R.id.city_info_zone_number_since);
        String zoneNumberSinceText =
                StringHelper.getZoneNumberSinceAsOfText(activity, lowEmissionZone);
        zoneNumberSinceTextView.setText(zoneNumberSinceText);

        // Next zone number as of
        TextView nextZoneNumberAsOfTextView =
                (TextView) activity.findViewById(R.id.city_info_next_zone_number_as_of);
        String nextZoneNumberAsOfText =
                StringHelper.getNextZoneNumberAsOfText(activity, lowEmissionZone);
        ViewHelper.setTextOrHideView(nextZoneNumberAsOfTextView, nextZoneNumberAsOfText);

        // Abroad licenced vehicle zone number info
        TextView abroadLicensedVehicleZoneNumberTextView =
                (TextView) activity.findViewById(R.id.city_info_abroad_licensed_vehicle_zone_info);
        String abroadLicensedVehicleZoneNumberText =
                StringHelper.getAbroadLicensedVehicleZoneNumberText(activity, lowEmissionZone);
        ViewHelper.setTextOrHideView(
                abroadLicensedVehicleZoneNumberTextView,
                abroadLicensedVehicleZoneNumberText);

        // Show on map button
        Button showOnMapButton = (Button) activity.findViewById(R.id.city_info_show_on_map);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTracking.track(TrackingPoint.CityInfoShowOnMapClick, lowEmissionZone.name);
                Umweltzone.centerZoneRequested = true;
                startActivity(IntentHelper.getNewMapIntent(activity));
            }
        });

        // Further information button
        ViewHelper.setupTextViewExtended(activity,
                R.id.city_info_further_information,
                R.string.city_info_further_information,
                lowEmissionZone.urlUmweltPlaketteDe,
                TrackingPoint.CityInfoFurtherInfoClick,
                lowEmissionZone.name);

        // Badge online
        TextView badgeOnlineTextView = (TextView) activity.findViewById(
                R.id.city_info_badge_online);
        final String urlBadgeOnline = lowEmissionZone.urlBadgeOnline;
        if (TextUtils.isEmpty(urlBadgeOnline)) {
            badgeOnlineTextView.setVisibility(View.GONE);
        } else {
            badgeOnlineTextView.setVisibility(View.VISIBLE);
            ViewHelper.setupTextViewExtended(activity,
                    R.id.city_info_badge_online,
                    R.string.city_info_badge_online_title,
                    urlBadgeOnline,
                    TrackingPoint.CityInfoBadgeOnlineClick,
                    lowEmissionZone.name);
        }

        // Geometry updated at
        TextView geometryUpdatedAtTextView = (TextView) activity
                .findViewById(R.id.city_info_geometry_updated_at);
        String geometryUpdatedAtText = StringHelper
                .getGeometryUpdatedAtText(activity, lowEmissionZone);
        ViewHelper.setTextOrHideView(geometryUpdatedAtTextView, geometryUpdatedAtText);

        // Geometry source
        TextView geometrySourceTextView = (TextView) activity
                .findViewById(R.id.city_info_geometry_source);
        String geometrySourceText = StringHelper
                .getGeometrySourceText(activity, lowEmissionZone);
        ViewHelper.setTextOrHideView(geometrySourceTextView, geometrySourceText);
    }

}
