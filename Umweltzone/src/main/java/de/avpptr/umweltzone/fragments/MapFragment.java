/*
 *  Copyright (C) 2013  Tobias Preuss, Peter Vasil
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

package de.avpptr.umweltzone.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.BundleKeys;
import de.avpptr.umweltzone.models.LowEmissionZone;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.MapDrawer;
import de.avpptr.umweltzone.utils.PointsProvider;
import de.avpptr.umweltzone.utils.PreferencesHelper;

public class MapFragment extends SupportMapFragment {

    private GoogleMap mMap;
    private MapDrawer mMapDrawer;
    private final GoogleMap.OnCameraChangeListener mOnCameraChangeListener;
    private boolean fragmentCreated;

    public MapFragment() {
        this.mOnCameraChangeListener = new OnCameraChangeListener();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentCreated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void zoomToBounds(LatLngBounds latLngBounds) {
        if (mMap == null) {
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
            CameraUpdate zoneBounds = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding);
            mMap.moveCamera(zoneBounds);
        }
    }

    private void zoomToLocation(GeoPoint location, float zoomLevel) {
        if (location.isValid() && zoomLevel > 0) {
            CameraUpdate cameraUpdate =
                    CameraUpdateFactory.newLatLngZoom(location.toLatLng(), zoomLevel);
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
                showGooglePlayServicesErrorDialog(activity, connectionResult);
            } else {
                mMap = ((SupportMapFragment) activity.getSupportFragmentManager()
                        .findFragmentById(R.id.map)).getMap();
                if (mMap != null) {
                    onMapIsSetUp(activity);
                }
            }
        }
    }

    private void showGooglePlayServicesErrorDialog(Activity activity, int errorCode) {
        final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, activity, 0);
        if (dialog == null) {
            Log.e(activity.getClass().getName(), "GooglePlayServicesErrorDialog is null.");
        } else {
            dialog.show();
        }
    }

    protected void onMapIsSetUp(Activity activity) {
        mMapDrawer = new MapDrawer(mMap);
        mMap.setOnCameraChangeListener(mOnCameraChangeListener);
        mMap.setMyLocationEnabled(true);
        drawPolygonOverlay();

        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            GeoPoint lastKnownPosition = PreferencesHelper.restoreLastKnownLocationAsGeoPoint(activity);
            if (!lastKnownPosition.isValid()) {
                LowEmissionZone defaultLowEmissionZone = LowEmissionZone.getDefaultLowEmissionZone(activity);
                if (defaultLowEmissionZone != null) {
                    storeLastLowEmmisionZone(defaultLowEmissionZone);
                    drawPolygonOverlay();
                    zoomToBounds(defaultLowEmissionZone.boundingBox.toLatLngBounds());
                    storeLastMapState();
                }
            } else if (fragmentCreated) {
                // MapFragment gets created after app start
                fragmentCreated = false;
                float zoomLevel = PreferencesHelper.restoreZoomLevel(activity);
                zoomToLocation(lastKnownPosition, zoomLevel);
            }
        } else {
            String cityName = extras.getString(BundleKeys.CITY_CHANGE);
            if (cityName != null) {
                // City has been selected from the list
                BoundingBox lastKnownPosition = PreferencesHelper.restoreLastKnownLocationAsBoundingBox(activity);
                if (lastKnownPosition.isValid()) {
                    zoomToBounds(lastKnownPosition.toLatLngBounds());
                }
            } else {
                // Home button has been selected
                GeoPoint lastKnownPosition = PreferencesHelper.restoreLastKnownLocationAsGeoPoint(activity);
                float zoomLevel = PreferencesHelper.restoreZoomLevel(activity);
                zoomToLocation(lastKnownPosition, zoomLevel);
            }
        }
    }

    private void drawPolygonOverlay() {
        Activity activity = getActivity();
        String cityName = PreferencesHelper.restoreLastKnownLocationAsString(activity);
        if (cityName == null || cityName.length() < 1) {
            return;
        }
        Iterable<LatLng> points = PointsProvider.getCircuitPoints(activity, cityName);
        Resources resources = getResources();
        int fillColor = resources.getColor(R.color.shape_fill_color);
        int strokeColor = resources.getColor(R.color.shape_stroke_color);
        int strokeWidth = resources.getInteger(R.integer.shape_stroke_width);
        mMapDrawer.drawPolygon(points, fillColor, strokeColor, strokeWidth);
    }

    private void storeLastLowEmmisionZone(LowEmissionZone defaultLowEmissionZone) {
        Activity activity = getActivity();
        PreferencesHelper.storeLastKnownLocation(activity, defaultLowEmissionZone.name);
        PreferencesHelper.storeLastKnownLocation(activity, defaultLowEmissionZone.boundingBox);
    }

    private void storeLastMapState() {
        if (mMap != null) {
            GeoPoint mapCenter = new GeoPoint(mMap.getCameraPosition().target);
            PreferencesHelper.storeLastKnownLocation(getActivity(), mapCenter);
            float zoomLevel = mMap.getCameraPosition().zoom;
            PreferencesHelper.storeZoomLevel(getActivity(), zoomLevel);
        }
    }

    class OnCameraChangeListener implements GoogleMap.OnCameraChangeListener {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            storeLastMapState();
        }
    }
}
