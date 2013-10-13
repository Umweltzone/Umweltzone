package de.avpptr.umweltzone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.CityListAdapter;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.PreferencesHelper;

public class CitiesFragment extends ListFragment {

    List<LowEmissionZone> mLowEmissionZones;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mLowEmissionZones = ContentProvider.getLowEmissionZones(activity);
        if (mLowEmissionZones == null) {
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        CityListAdapter adapter = new CityListAdapter(activity, R.layout.cities_row,
                mLowEmissionZones.toArray(new LowEmissionZone[0]));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long rowId) {
        LowEmissionZone lowEmissionZone = mLowEmissionZones.get(position);
        storeSelectedLocation(lowEmissionZone);
        Intent intent = IntentHelper.getChangeCityIntent(getActivity(), lowEmissionZone.name);
        startActivity(intent);
    }

    private void storeSelectedLocation(LowEmissionZone lowEmissionZone) {
        PreferencesHelper.storeLastKnownLocation(getActivity(), lowEmissionZone.boundingBox);
        PreferencesHelper.storeLastKnownLocation(getActivity(), lowEmissionZone.name);
    }

}
