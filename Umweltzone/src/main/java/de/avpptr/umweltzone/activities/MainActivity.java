package de.avpptr.umweltzone.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.utils.MapDrawer;

public class MainActivity extends FragmentActivity {

    private GoogleMap mMap;
    private MapDrawer mMapDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            int connectionResult = GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(getApplicationContext());
            if (connectionResult != ConnectionResult.SUCCESS) {
                showGooglePlayServicesErrorDialog(connectionResult);
            } else {
                mMap = ((SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map)).getMap();
                if (mMap != null) {
                    onMapIsSetUp();
                }
            }
        }
    }

    private void showGooglePlayServicesErrorDialog(int errorCode) {
        final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0);
        if (dialog == null) {
            Log.e(getClass().getName(), "GooglePlayServicesErrorDialog is null.");
        } else {
            dialog.show();
        }
    }

    protected void onMapIsSetUp() {
        mMapDrawer = new MapDrawer(mMap);
        // TODO Draw polygon on map
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
