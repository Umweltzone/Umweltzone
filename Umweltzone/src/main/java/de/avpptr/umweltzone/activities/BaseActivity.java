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

package de.avpptr.umweltzone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.utils.IntentHelper;

public abstract class BaseActivity extends ActionBarActivity {

    protected ActionBar mActionBar;
    protected final Tracking mTracking;

    public BaseActivity() {
        mTracking = Umweltzone.getTracker();
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = IntentHelper.getHomeIntent(this);
                startActivityIfNeeded(intent, 0);
                return true;
            }
            case R.id.action_cities:
                startActivity(IntentHelper.getCitiesIntent(this));
                return true;
            case R.id.action_city_info:
                startActivity(IntentHelper.getCityInfoIntent(this));
                return true;
            case R.id.action_faq:
                startActivity(IntentHelper.getFaqsIntent(this));
                return true;
            case R.id.action_about:
                Intent intent = IntentHelper.getAboutIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getContentViewCompat(), fragment);
        fragmentTransaction.commit();
    }

    // http://stackoverflow.com/a/17998802/356895
    // http://code.google.com/p/android/issues/detail?id=58108
    private static int getContentViewCompat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ?
                android.R.id.content : R.id.action_bar_activity_content;
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String, Activity> parameters = new HashMap<String, Activity>();
        parameters.put("activity", this);
        mTracking.track(TrackingPoint.ActivityStart, parameters);
    }

    @Override
    public void onStop() {
        super.onStop();
        Map<String, Activity> parameters = new HashMap<String, Activity>();
        parameters.put("activity", this);
        mTracking.track(TrackingPoint.ActivityStop, parameters);
    }

}
