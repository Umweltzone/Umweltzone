package de.avpptr.umweltzone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.activities.MainActivity;
import de.avpptr.umweltzone.contract.BundleKeys;

public class CitiesFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] cityNames = getResources().getStringArray(R.array.city_names_display_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, cityNames);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long rowId) {
        String message = listView.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

        String[] cityNameValues = getResources().getStringArray(R.array.city_names_values);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(BundleKeys.CITY_CHANGE, cityNameValues[position]);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
