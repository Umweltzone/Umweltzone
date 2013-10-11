package de.avpptr.umweltzone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.utils.IntentHelper;

public abstract class BaseActivity extends ActionBarActivity {

    protected ActionBar mActionBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        // TODO Review issue #58007
        // http://code.google.com/p/android/issues/detail?id=58007
        mActionBar.setDisplayHomeAsUpEnabled(true);
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
            case R.id.action_settings:
                // TODO Implement settings
                return true;
            case R.id.action_about:
                Intent intent = IntentHelper.getAboutIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
