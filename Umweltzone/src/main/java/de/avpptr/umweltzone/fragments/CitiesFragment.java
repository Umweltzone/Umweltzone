package de.avpptr.umweltzone.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.CityListAdapter;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.Converter;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.PreferencesHelper;

public class CitiesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources resources = getResources();
        String[] cityNames = resources.getStringArray(R.array.city_names_values);
        String[] cityDisplayNames = resources.getStringArray(R.array.city_names_display_names);
        int[] cityZoneNumbers = resources.getIntArray(R.array.city_zone_value);

        LowEmissionZone[] zones = new LowEmissionZone[cityZoneNumbers.length];
        for (int i = 0; i < cityNames.length; i++) {
            final String cityName = cityNames[i];
            final String cityDisplayName = cityDisplayNames[i];
            final int cityZoneNumber = cityZoneNumbers[i];
            LowEmissionZone lowEmissionZone = new LowEmissionZone() {{
                name = cityName;
                displayName = cityDisplayName;
                zoneNumber = cityZoneNumber;
            }};
            zones[i] = lowEmissionZone;
        }
        CityListAdapter adapter =
                new CityListAdapter(getActivity(), R.layout.cities_row, zones);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long rowId) {
        String[] cityNameValues = getResources().getStringArray(R.array.city_names_values);
        String cityNameValue = cityNameValues[position];
        storeSelectedLocation(cityNameValue);
        Intent intent = IntentHelper.getChangeCityIntent(getActivity(), cityNameValue);
        startActivity(intent);
    }

    private void storeSelectedLocation(String cityNameValue) {
        BoundingBox boundingBox = Converter.cityNameToBoundingBox(getResources(), cityNameValue);
        PreferencesHelper.storeLastKnownLocation(getActivity(), boundingBox);
    }

}
