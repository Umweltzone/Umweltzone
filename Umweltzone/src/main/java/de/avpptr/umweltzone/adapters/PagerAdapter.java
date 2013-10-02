package de.avpptr.umweltzone.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.avpptr.umweltzone.fragments.MapFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int itemIndex) {
        switch (itemIndex) {
            case 0:
                return new MapFragment();
            default:
                throw new IllegalStateException("Unsupported item index");
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

}
