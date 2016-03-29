/*
 *  Copyright (C) 2016  Tobias Preuss
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
import android.view.View;
import android.widget.Button;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.base.BaseFragment;
import de.avpptr.umweltzone.utils.IntentHelper;

public class NoCitySelectedFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".NO_CITY_SELECTED_FRAGMENT_TAG";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_no_city_selected;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpViews(getActivity());
    }

    private void setUpViews(final Activity activity) {
        // Select zone button
        Button showOnMapButton = (Button) activity.findViewById(R.id.no_city_selected_select_zone);
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentHelper.getCitiesIntent(activity));
            }
        });
    }

}
