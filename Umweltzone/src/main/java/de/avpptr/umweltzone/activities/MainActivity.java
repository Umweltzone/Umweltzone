package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.PagerAdapter;

public class MainActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener());
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        @Override public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }
    }

}
