package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.fragments.CityInfoFragment;

public class CityInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(new CityInfoFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.city_info, menu);
        return true;
    }

}
