/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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

import android.app.Activity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.contract.Resources;
import de.avpptr.umweltzone.base.BaseFragment;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.StringHelper;
import de.avpptr.umweltzone.utils.ViewHelper;

public class CityInfoFragment extends BaseFragment {

    private LowEmissionZone mLowEmissionZone;

    @Override public int getLayoutResource() {
        mLowEmissionZone = LowEmissionZone.getRecentLowEmissionZone(getActivity());
        if (mLowEmissionZone == null) {
            return R.layout.fragment_city_info_empty;
        } else {
            return R.layout.fragment_city_info;
        }
    }

    @Override public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        mLowEmissionZone = LowEmissionZone.getRecentLowEmissionZone(getActivity());
        if (mLowEmissionZone != null) {
            setUpCityInfo(activity, mLowEmissionZone);
        } else {
            setUpCityInfoEmpty(activity);
        }
    }

    private void setUpCityInfo(final Activity activity, LowEmissionZone lowEmissionZone) {
        // Title
        TextView titleTextView = (TextView) activity.findViewById(R.id.city_info_title);
        titleTextView.setText(lowEmissionZone.displayName);

        // Zone status image
        TextView zoneStatus = (TextView) activity.findViewById(R.id.city_info_zone_status);
        int zoneStatusId = zoneNumberToStatusDrawableResourceId(lowEmissionZone.zoneNumber);
        if (zoneStatusId == Resources.INVALID_RESOURCE_ID) {
            zoneStatus.setVisibility(View.GONE);
        } else {
            zoneStatus.setVisibility(View.VISIBLE);
            zoneStatus.setBackgroundResource(zoneStatusId);
        }

        // Zone description
        TextView zoneDescriptionTextView =
                (TextView) activity.findViewById(R.id.city_info_zone_description);
        String zoneDescription = lowEmissionZone.description;
        ViewHelper.setTextOrHideView(zoneDescriptionTextView, zoneDescription);

        // Zone number since
        TextView zoneNumberSinceTextView =
                (TextView) activity.findViewById(R.id.city_info_zone_number_since);
        String zoneNumberSinceText =
                StringHelper.getZoneNumberSinceText(activity, lowEmissionZone);
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
        ViewHelper.setTextOrHideView(abroadLicensedVehicleZoneNumberTextView, abroadLicensedVehicleZoneNumberText);


        // Show on map button
        Button showOnMapButton = (Button) activity.findViewById(R.id.city_info_show_on_map);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mTracking.track(TrackingPoint.CityInfoShowOnMapClick, mLowEmissionZone.name);
                startActivity(IntentHelper.getChangeCityIntent(activity, mLowEmissionZone.name));
            }
        });

        // Further information button
        Button furtherInfoButton = (Button) activity.findViewById(R.id.city_info_further_information);
        furtherInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mTracking.track(TrackingPoint.CityInfoFurtherInfoClick, mLowEmissionZone.name);
                startActivity(IntentHelper.getUriIntent(mLowEmissionZone.urlUmweltPlaketteDe));
            }
        });

        // Badge online
        TextView badgeOnlineTitle = (TextView) activity.findViewById(R.id.city_info_badge_online_title);
        Button badgeOnline = (Button) activity.findViewById(R.id.city_info_badge_online);
        final String urlBadgeOnline = mLowEmissionZone.urlBadgeOnline;
        if (TextUtils.isEmpty(urlBadgeOnline)) {
            badgeOnlineTitle.setVisibility(View.GONE);
            badgeOnline.setVisibility(View.GONE);
        } else {
            badgeOnlineTitle.setVisibility(View.VISIBLE);
            badgeOnline.setVisibility(View.VISIBLE);
            badgeOnline.setText(
                    StringHelper.spannedLinkForString(urlBadgeOnline),
                    TextView.BufferType.SPANNABLE);
            badgeOnline.setMovementMethod(LinkMovementMethod.getInstance());
        }
        badgeOnline.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mTracking.track(TrackingPoint.CityInfoBadgeOnlineClick);
            }
        });
    }

    private void setUpCityInfoEmpty(final Activity activity) {
        // Select zone button
        Button showOnMapButton = (Button) activity.findViewById(R.id.city_info_empty_select_zone);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mTracking.track(TrackingPoint.CityInfoEmptySelectZoneClick);
                startActivity(IntentHelper.getCitiesIntent(activity));
            }
        });
    }

    private int zoneNumberToStatusDrawableResourceId(int zoneNumber) {
        switch (zoneNumber) {
            case LowEmissionZoneNumbers.RED:
                return R.drawable.umweltzone_status_2;
            case LowEmissionZoneNumbers.YELLOW:
                return R.drawable.umweltzone_status_3;
            case LowEmissionZoneNumbers.GREEN:
                return R.drawable.umweltzone_status_4;
        }
        return Resources.INVALID_RESOURCE_ID;
    }

}
