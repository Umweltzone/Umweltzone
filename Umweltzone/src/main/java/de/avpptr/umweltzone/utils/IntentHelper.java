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

package de.avpptr.umweltzone.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import de.avpptr.umweltzone.activities.AboutActivity;
import de.avpptr.umweltzone.activities.CitiesActivity;
import de.avpptr.umweltzone.activities.CityInfoActivity;
import de.avpptr.umweltzone.activities.FaqActivity;
import de.avpptr.umweltzone.activities.MainActivity;
import de.avpptr.umweltzone.contract.BundleKeys;

public class IntentHelper {

    public static Intent getChangeCityIntent(Activity activity, String cityNameValue) {
        final Intent intent = getIntent(activity, MainActivity.class);
        intent.putExtra(BundleKeys.CITY_CHANGE, cityNameValue);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getHomeIntent(Activity activity) {
        final Intent intent = getIntent(activity, MainActivity.class);
        intent.putExtra(BundleKeys.HOME, BundleKeys.HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static Intent getCityInfoIntent(Activity activity) {
        return getIntent(activity, CityInfoActivity.class);
    }

    public static Intent getAboutIntent(Activity activity) {
        return getIntent(activity, AboutActivity.class);
    }

    public static Intent getCitiesIntent(Activity activity) {
        return getIntent(activity, CitiesActivity.class);
    }

    public static Intent getFaqsIntent(Activity activity) {
        return getIntent(activity, FaqActivity.class);
    }

    public static Intent getUriIntent(String uri) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    private static Intent getIntent(Activity activity, Class<?> clazz) {
        return new Intent(activity, clazz);
    }

}
