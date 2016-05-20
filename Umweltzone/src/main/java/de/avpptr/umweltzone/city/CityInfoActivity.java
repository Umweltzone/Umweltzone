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

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class CityInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);
        initFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.city_info, menu);
        return true;
    }

    private void initFragment() {
        Intent intent = getIntent();
        if (intent == null) {
            throw new AssertionError("Intent cannot be null.");
        }
        Parcelable parcelable = intent.getParcelableExtra(
                CityInfoFragment.BUNDLE_KEY_LOW_EMISSION_ZONE);
        if (parcelable == null) {
            addFragment(R.id.city_info_container,
                    new NoCitySelectedFragment(),
                    NoCitySelectedFragment.FRAGMENT_TAG);
        } else {
            LowEmissionZone lowEmissionZone = Parcels.unwrap(parcelable);
            CityInfoFragment cityInfoFragment = CityInfoFragment.newInstance(lowEmissionZone);
            addFragment(R.id.city_info_container,
                    cityInfoFragment,
                    CityInfoFragment.FRAGMENT_TAG);
        }
    }

}
