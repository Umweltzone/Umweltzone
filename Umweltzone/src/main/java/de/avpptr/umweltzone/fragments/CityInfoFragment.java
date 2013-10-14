package de.avpptr.umweltzone.fragments;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.PreferencesHelper;

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
            TextView cityInfoTitle = (TextView) activity.findViewById(R.id.city_info_title);
            cityInfoTitle.setText(mLowEmissionZone.displayName);
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
