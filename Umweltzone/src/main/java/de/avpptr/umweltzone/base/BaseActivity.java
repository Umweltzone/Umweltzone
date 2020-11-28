/*
 *  Copyright (C) 2020  Tobias Preuss
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

package de.avpptr.umweltzone.base;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.cketti.library.changelog.ChangeLog;

public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;

    public BaseActivity() {
        super();
    }

    @ContentView
    public BaseActivity(@LayoutRes int contentLayoutId) {
        // This constructor is annotated with @ContentView
        super(contentLayoutId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
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
                AdministrativeZone administrativeZone = AdministrativeZone.getRecentAdministrativeZone(this);
                startActivity(IntentHelper.getDetailsIntent(this, administrativeZone));
                return true;
            case R.id.action_faq:
                startActivity(IntentHelper.getFaqsIntent(this));
                return true;
            case R.id.action_feedback:
                startActivity(IntentHelper.getFeedbackIntent(this));
                return true;
            case R.id.action_share_app:
                Intent shareIntent = IntentHelper.getShareIntent(this);
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                return true;
            case R.id.action_changelog:
                showFullChangeLogDialog();
                return true;
            case R.id.action_settings:
                startActivity(IntentHelper.getSettingsIntent(this));
                return true;
            case R.id.action_about:
                Intent intent = IntentHelper.getAboutIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Must use appcompat-v7:19.0.0 and newer to work on Android 2.3. See issue 58108
        fragmentTransaction.replace(android.R.id.content, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    protected boolean fragmentExists(@NonNull String fragmentTag) {
        return getSupportFragmentManager().findFragmentByTag(fragmentTag) != null;
    }

    protected void addFragment(
            @IdRes int containerViewId,
            @NonNull Fragment fragment,
            @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    protected String getBuildVersionName() {
        final PackageManager packageManager = getPackageManager();
        try {
            assert packageManager != null;
            final PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected void showChangeLogDialog() {
        final ChangeLog changeLog = new ChangeLog(this);
        if (changeLog.isFirstRun() && !isFinishing()) {
            // TODO Remove once androidx.appcompat:appcompat:1.2.0 is available
            // Workaround for Android 5. See https://github.com/cketti/ckChangeLog/issues/57
            try {
                changeLog.getLogDialog().show();
            } catch (Resources.NotFoundException e) {
                showErrorToast();
                e.printStackTrace();
            }
        }
    }

    private void showFullChangeLogDialog() {
        final ChangeLog changeLog = new ChangeLog(this);
        // TODO Remove once androidx.appcompat:appcompat:1.2.0 is available
        // Workaround for Android 5. See https://github.com/cketti/ckChangeLog/issues/57
        try {
            changeLog.getFullLogDialog().show();
        } catch (Resources.NotFoundException e) {
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void showErrorToast() {
        String message = getString(R.string.changelog_error_resource_not_android_5);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
