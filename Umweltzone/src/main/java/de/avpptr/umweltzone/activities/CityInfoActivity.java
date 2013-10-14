package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.fragments.CityInfoFragment;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.IntentHelper;

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

    public void onSelectZoneClick(View view) {
        startActivity(IntentHelper.getCitiesIntent(this));
    }

    public void onShowOnMapClick(View view) {
        startActivity(IntentHelper.getChangeCityIntent(this, "Unevaluated"));
    }

    public void onFurtherInformationClick(View view) {
        LowEmissionZone lowEmissionZone = LowEmissionZone.getRecentLowEmissionZone(this);
        startActivity(IntentHelper.getUriIntent(lowEmissionZone.urlUmweltPlaketteDe));
    }

}
