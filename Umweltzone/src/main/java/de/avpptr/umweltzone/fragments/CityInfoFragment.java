package de.avpptr.umweltzone.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.PreferencesHelper;
import de.avpptr.umweltzone.utils.StringHelper;

public class CityInfoFragment extends BaseFragment {

    LowEmissionZone mLowEmissionZone;

    @Override public int getLayoutResource() {
        mLowEmissionZone = getLowEmissionZone(getActivity());
        if (mLowEmissionZone == null) {
            return R.layout.fragment_city_info_empty;
        } else {
            return R.layout.fragment_city_info;
        }
    }

    @Override public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        mLowEmissionZone = getLowEmissionZone(activity);
        if (mLowEmissionZone != null) {
            setUpCityInfo(activity, mLowEmissionZone);
        }
    }

    private void setUpCityInfo(Activity activity, LowEmissionZone lowEmissionZone) {
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
        }
    }

    private LowEmissionZone getLowEmissionZone(Context context) {
        List<LowEmissionZone> lowEmissionZones = ContentProvider.getLowEmissionZones(context);
        if (lowEmissionZones == null) {
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        String zoneName = PreferencesHelper.restoreLastKnownLocationAsString(context);
        for (LowEmissionZone lowEmissionZone : lowEmissionZones) {
            if (lowEmissionZone.name.equalsIgnoreCase(zoneName)) {
                return lowEmissionZone;
            }
        }
        return null;
    }

}
