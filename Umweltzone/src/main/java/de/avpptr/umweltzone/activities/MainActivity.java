package de.avpptr.umweltzone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.PagerAdapter;
import de.avpptr.umweltzone.contract.CityChangeListener;

public class MainActivity extends BaseActivity implements CityChangeListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener());
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        final ActionBar.TabListener tabListener = new TabListener();

        for (int tabIndex = 0; tabIndex < pagerAdapter.getCount(); ++tabIndex) {
            ActionBar.Tab tab = mActionBar.newTab()
                    .setText("Tab " + (tabIndex + 1))
                    .setTabListener(tabListener);
            mActionBar.addTab(tab);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cities:
                Intent intent = new Intent(this, CitiesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_faq:
                // TODO Implement FAQ options menu
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void cityChanged() {
        mViewPager.setCurrentItem(0);
    }

    class TabListener implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }

    class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }
    }

}
