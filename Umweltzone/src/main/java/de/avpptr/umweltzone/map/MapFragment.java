/*
 *  Copyright (C) 2015  Tobias Preuss
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

package de.avpptr.umweltzone.map;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

import org.ligi.tracedroid.logging.Log;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.contract.BundleKeys;
import de.avpptr.umweltzone.models.Circuit;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.ConnectionResultHelper;
import de.avpptr.umweltzone.utils.ContentProvider;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.MapDrawer;

public class MapFragment extends SupportMapFragment {

    private GoogleMap mMap;

    private MapDrawer mMapDrawer;

    private final GoogleMap.OnCameraChangeListener mOnCameraChangeListener;

    private boolean fragmentCreated;

    protected final Tracking mTracking;

    private PreferencesHelper mPreferencesHelper;

    public MapFragment() {
        this.mOnCameraChangeListener = new OnCameraChangeListener();
        mTracking = Umweltzone.getTracker();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Umweltzone application = (Umweltzone) getActivity().getApplicationContext();
        mPreferencesHelper = application.getPreferencesHelper();
        fragmentCreated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void zoomToBounds(LatLngBounds latLngBounds) {
        if (mMap == null) {
            mTracking.trackError(TrackingPoint.MapIsNullError, null);
            throw new IllegalStateException("Map is null");
        } else {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            int padding = 50;
            if (width > height) {
                padding *= 2;
            }
            CameraUpdate zoneBounds = CameraUpdateFactory.newLatLngBounds(
                    latLngBounds, width, height, padding);
            mMap.moveCamera(zoneBounds);
        }
    }

    private void zoomToLocation(GeoPoint location, float zoomLevel) {
        if (location.isValid() && zoomLevel > 0) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    location.toLatLng(), zoomLevel);
            mMap.moveCamera(cameraUpdate);
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            FragmentActivity activity = getActivity();
            Context context = activity.getApplicationContext();
            int connectionResult = GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(context);
            if (connectionResult != ConnectionResult.SUCCESS) {
                final String connectionResultString =
                        ConnectionResultHelper.connectionResultToString(connectionResult);
                mTracking.trackError(
                        TrackingPoint.GooglePlayServicesNotAvailableError,
                        connectionResultString);
                showGooglePlayServicesErrorDialog(activity, connectionResult);
            } else {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                SupportMapFragment mapFragment =
                        (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
                mMap = mapFragment.getMap();
                if (mMap != null) {
                    onMapIsSetUp(activity);
                }
            }
        }
    }

    private void showGooglePlayServicesErrorDialog(Activity activity, int errorCode) {
        final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, activity, 0);
        if (dialog == null) {
            Log.e("GooglePlayServicesErrorDialog is null.");
        } else {
            dialog.show();
        }
    }

    protected void onMapIsSetUp(Activity activity) {
        mMapDrawer = new MapDrawer(mMap);
        mMap.setOnCameraChangeListener(mOnCameraChangeListener);
        mMap.setMyLocationEnabled(true);
        if (mPreferencesHelper.storesZoneIsDrawable()) {
            if (mPreferencesHelper.restoreZoneIsDrawable()) {
                drawPolygonOverlay();
            } else {
                showZoneNotDrawableDialog();
            }
        }

        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null || TextUtils.isEmpty(extras.getString(BundleKeys.CITY_CHANGE))) {
            // Select default city at first application start
            GeoPoint lastKnownPosition = mPreferencesHelper.restoreLastKnownLocationAsGeoPoint();
            if (!lastKnownPosition.isValid()) {
                LowEmissionZone defaultLowEmissionZone = LowEmissionZone
                        .getDefaultLowEmissionZone(activity);
                if (defaultLowEmissionZone != null) {
                    storeLastLowEmissionZone(defaultLowEmissionZone);
                    if (mPreferencesHelper.storesZoneIsDrawable() &&
                            mPreferencesHelper.restoreZoneIsDrawable()) {
                        drawPolygonOverlay();
                    } else {
                        showZoneNotDrawableDialog();
                    }
                    zoomToBounds(defaultLowEmissionZone.boundingBox.toLatLngBounds());
                    storeLastMapState();
                }
            } else if (fragmentCreated) {
                // MapFragment gets created after app start
                fragmentCreated = false;
                float zoomLevel = mPreferencesHelper.restoreZoomLevel();
                zoomToLocation(lastKnownPosition, zoomLevel);
            }
        } else {
            String cityName = extras.getString(BundleKeys.CITY_CHANGE);
            if (cityName != null) {
                // City has been selected from the list
                BoundingBox lastKnownPosition = mPreferencesHelper
                        .restoreLastKnownLocationAsBoundingBox();
                if (lastKnownPosition.isValid()) {
                    zoomToBounds(lastKnownPosition.toLatLngBounds());
                }
            } else {
                // Home button has been selected
                GeoPoint lastKnownPosition = mPreferencesHelper
                        .restoreLastKnownLocationAsGeoPoint();
                float zoomLevel = mPreferencesHelper.restoreZoomLevel();
                zoomToLocation(lastKnownPosition, zoomLevel);
            }
        }
        updateSubTitle();
    }

    private void drawPolygonOverlay() {
        Activity activity = getActivity();
        String cityName = mPreferencesHelper.restoreLastKnownLocationAsString();
        if (cityName == null || cityName.length() < 1) {
            return;
        }
        @SuppressWarnings("unchecked")
        List<Circuit> circuits = ContentProvider.getCircuits(activity, cityName);
        Resources resources = getResources();
        int fillColor = resources.getColor(R.color.shape_fill_color);
        int strokeColor = resources.getColor(R.color.shape_stroke_color);
        int strokeWidth = resources.getInteger(R.integer.shape_stroke_width);
        mMapDrawer.drawPolygons(circuits, fillColor, strokeColor, strokeWidth);
    }

    private void showZoneNotDrawableDialog() {
        FragmentActivity activity = getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager
                .findFragmentByTag(ZoneNotDrawableDialog.FRAGMENT_TAG);
        if (fragment == null) {
            LowEmissionZone lowEmissionZone = LowEmissionZone.getRecentLowEmissionZone(activity);
            if (lowEmissionZone != null) {
                ZoneNotDrawableDialog dialog = new ZoneNotDrawableDialog();
                dialog.show(fragmentManager, ZoneNotDrawableDialog.FRAGMENT_TAG);
            }
        }
    }

    private void storeLastLowEmissionZone(LowEmissionZone defaultLowEmissionZone) {
        mPreferencesHelper.storeLastKnownLocationAsString(defaultLowEmissionZone.name);
        mPreferencesHelper.storeLastKnownLocationAsBoundingBox(defaultLowEmissionZone.boundingBox);
        mPreferencesHelper.storeZoneIsDrawable(
                defaultLowEmissionZone.containsGeometryInformation());
    }

    private void storeLastMapState() {
        if (mMap != null) {
            GeoPoint mapCenter = new GeoPoint(mMap.getCameraPosition().target);
            mPreferencesHelper.storeLastKnownLocationAsGeoPoint(mapCenter);
            float zoomLevel = mMap.getCameraPosition().zoom;
            mPreferencesHelper.storeZoomLevel(zoomLevel);
        }
    }

    private void updateSubTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        final LowEmissionZone lowEmissionZone = LowEmissionZone.getRecentLowEmissionZone(activity);
        if (activity != null && lowEmissionZone != null) {
            String title = lowEmissionZone.displayName;
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(title);
            }
        }
    }

    private class OnCameraChangeListener implements GoogleMap.OnCameraChangeListener {

        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            storeLastMapState();
        }
    }
}
