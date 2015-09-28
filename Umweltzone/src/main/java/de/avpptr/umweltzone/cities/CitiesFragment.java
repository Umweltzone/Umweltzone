/*
 *  Copyright (C) 2015  Tobias Preuss, Peter Vasil
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

package de.avpptr.umweltzone.cities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.IntentHelper;

public class CitiesFragment extends ListFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".CITIES_FRAGMENT_TAG";

    protected final Tracking mTracking;

    // Used for caching
    private static List<LowEmissionZone> mLowEmissionZones;

    public CitiesFragment() {
        mTracking = Umweltzone.getTracker();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (mLowEmissionZones == null) {
            mLowEmissionZones = ContentProvider.getLowEmissionZones(activity);
        }
        if (mLowEmissionZones.isEmpty()) {
            mTracking.trackError(TrackingPoint.ParsingZonesFromJSONFailedError, null);
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        CityListAdapter adapter = new CityListAdapter(activity, R.layout.cities_row,
                mLowEmissionZones.toArray(new LowEmissionZone[mLowEmissionZones.size()]));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long rowId) {
        LowEmissionZone lowEmissionZone = mLowEmissionZones.get(position);
        mTracking.track(TrackingPoint.CityListItemClick, lowEmissionZone.name);
        storeSelectedLocation(lowEmissionZone);
        Intent intent = IntentHelper.getChangeCityIntent(getActivity(), lowEmissionZone.name);
        startActivity(intent);
    }

    private void storeSelectedLocation(LowEmissionZone lowEmissionZone) {
        final Umweltzone application = (Umweltzone) getActivity().getApplicationContext();
        final PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        preferencesHelper.storeLastKnownLocationAsBoundingBox(lowEmissionZone.boundingBox);
        preferencesHelper.storeLastKnownLocationAsString(lowEmissionZone.name);
        preferencesHelper.storeZoneIsDrawable(lowEmissionZone.containsGeometryInformation());
    }

}
