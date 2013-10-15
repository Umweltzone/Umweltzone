package de.avpptr.umweltzone.fragments;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.StringHelper;

public class CityInfoFragment extends BaseFragment {

    LowEmissionZone mLowEmissionZone;

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
        if (nextZoneNumberAsOfText == null) {
            nextZoneNumberAsOfTextView.setVisibility(View.GONE);
        } else {
            nextZoneNumberAsOfTextView.setVisibility(View.VISIBLE);
            nextZoneNumberAsOfTextView.setText(nextZoneNumberAsOfText);
        }

        // Abroad licenced vehicle zone number info
        TextView abroadLicensedVehicleZoneNumberTextView =
                (TextView) activity.findViewById(R.id.city_info_abroad_licensed_vehicle_zone_info);
        String abroadLicensedVehicleZoneNumberText =
                StringHelper.getAbroadLicensedVehicleZoneNumberText(activity, lowEmissionZone);
        if (abroadLicensedVehicleZoneNumberText == null) {
            abroadLicensedVehicleZoneNumberTextView.setVisibility(View.GONE);
        } else {
            abroadLicensedVehicleZoneNumberTextView.setVisibility(View.VISIBLE);
            abroadLicensedVehicleZoneNumberTextView.setText(abroadLicensedVehicleZoneNumberText);
        }

        // Show on map button
        Button showOnMapButton = (Button) activity.findViewById(R.id.city_info_show_on_map);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(IntentHelper.getChangeCityIntent(activity, mLowEmissionZone.name));
            }
        });

        // Further information button
        Button furtherInfoButton = (Button) activity.findViewById(R.id.city_info_further_information);
        furtherInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(IntentHelper.getUriIntent(mLowEmissionZone.urlUmweltPlaketteDe));
            }
        });
    }

    private void setUpCityInfoEmpty(final Activity activity) {
        // Select zone button
        Button showOnMapButton = (Button) activity.findViewById(R.id.city_info_empty_select_zone);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(IntentHelper.getCitiesIntent(activity));
            }
        });
    }

}
