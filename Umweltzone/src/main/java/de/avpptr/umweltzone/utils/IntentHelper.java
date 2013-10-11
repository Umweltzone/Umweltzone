package de.avpptr.umweltzone.utils;

import android.app.Activity;
import android.content.Intent;

import de.avpptr.umweltzone.activities.AboutActivity;
import de.avpptr.umweltzone.activities.CitiesActivity;
import de.avpptr.umweltzone.activities.CityInfoActivity;
import de.avpptr.umweltzone.activities.FaqActivity;
import de.avpptr.umweltzone.activities.MainActivity;
import de.avpptr.umweltzone.contract.BundleKeys;

public class IntentHelper {

    public static Intent getChangeCityIntent(Activity activity, String cityNameValue) {
        Intent intent = getIntent(activity, MainActivity.class);
        intent.putExtra(BundleKeys.CITY_CHANGE, cityNameValue);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getHomeIntent(Activity activity) {
        Intent intent = getIntent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

    private static Intent getIntent(Activity activity, Class<?> clazz) {
        return new Intent(activity, clazz);
    }

}
