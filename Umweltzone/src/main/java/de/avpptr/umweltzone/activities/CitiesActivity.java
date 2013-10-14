package de.avpptr.umweltzone.activities;

import android.os.Bundle;

import de.avpptr.umweltzone.fragments.CitiesFragment;

public class CitiesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(new CitiesFragment());
    }

}
