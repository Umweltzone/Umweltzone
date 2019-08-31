/*
 *  Copyright (C) 2019  Tobias Preuss
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

import org.ligi.tracedroid.logging.Log;

import java.util.List;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseFragment;
import de.avpptr.umweltzone.extensions.ViewExtensions;
import de.avpptr.umweltzone.map.dataconverters.ChildZonesExtensions;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.ChildZone;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.CameraUpdateHelper;
import de.avpptr.umweltzone.utils.ConnectionResultHelper;
import de.avpptr.umweltzone.utils.ContentProvider;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".MAP_FRAGMENT_TAG";

    private static final String MAP_VIEW_BUNDLE_KEY =
            BuildConfig.APPLICATION_ID + ".MAP_VIEW_BUNDLE_KEY";

    private final View.OnClickListener MY_LOCATION_ACTIVATION_ON_CLICK_LISTENER =
            view -> requestMyLocationActivation();

    private Button mMyLocationActivationView;

    private MapView mMapView;

    private GoogleMap mMap;

    private MapDrawer mMapDrawer;

    private final GoogleMap.OnCameraIdleListener mOnCameraIdleListener;

    private final Tracking mTracking;

    private PreferencesHelper mPreferencesHelper;

    private MapReadyDelegate mMapReadyDelegate;

    private MyLocationPermission myLocationPermission;

    public MapFragment() {
        this.mOnCameraIdleListener = new OnCameraIdleListener();
        mTracking = Umweltzone.getTracker();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_map;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myLocationPermission = new MyLocationPermission(this, mTracking);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = requireActivity();
        final Umweltzone application = (Umweltzone) activity.getApplicationContext();
        mPreferencesHelper = application.getPreferencesHelper();
        mMapReadyDelegate = new MapReadyDelegate(
                mPreferencesHelper,
                () -> Umweltzone.centerZoneRequested,
                (centerZoneRequested) -> {
                    Umweltzone.centerZoneRequested = centerZoneRequested;
                    return null;
                },
                () -> AdministrativeZone.getDefaultAdministrativeZone(activity),
                new MapReadyDelegate.Listener() {

                    @Override
                    public void onDrawPolygonOverlay() {
                        drawPolygonOverlay();
                    }

                    @Override
                    public void onShowZoneNotDrawableDialog() {
                        showZoneNotDrawableDialog();
                    }

                    @Override
                    public void onStoreLastMapState() {
                        storeLastMapState();
                    }

                    @Override
                    public void onZoomToBounds(@NonNull LatLngBounds latLngBounds) {
                        zoomToBounds(latLngBounds);
                    }

                    @Override
                    public void onZoomToLocation(@NonNull CameraPosition cameraPosition) {
                        zoomToLocation(cameraPosition);
                    }

                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        if (layout != null) {
            mMapView = layout.findViewById(R.id.map_view);
            mMapView.onCreate(mapViewBundle);
            mMyLocationActivationView = layout.findViewById(R.id.map_my_location_activation);
            mMyLocationActivationView.setOnClickListener(MY_LOCATION_ACTIVATION_ON_CLICK_LISTENER);
        }
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStop() {
        mMapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void zoomToBounds(@NonNull LatLngBounds latLngBounds) {
        if (mMap == null) {
            mTracking.trackError(TrackingPoint.MapIsNullError, null);
            throw new IllegalStateException("Map is null");
        } else {
            CameraUpdate zoneBounds = CameraUpdateHelper.getCameraUpdate(requireActivity(), latLngBounds);
            mMap.moveCamera(zoneBounds);
        }
    }

    private void zoomToLocation(@NonNull CameraPosition cameraPosition) {
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            FragmentActivity activity = requireActivity();
            Context context = activity.getApplicationContext();
            int connectionResult = GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context);
            if (connectionResult != ConnectionResult.SUCCESS) {
                final String connectionResultString =
                        ConnectionResultHelper.connectionResultToString(connectionResult);
                mTracking.trackError(
                        TrackingPoint.GooglePlayServicesNotAvailableError,
                        connectionResultString);
                showGooglePlayServicesErrorDialog(activity, connectionResult);
            } else {
                mMapView.getMapAsync(this);
            }
        }
    }

    private void showGooglePlayServicesErrorDialog(Activity activity, int errorCode) {
        final Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, errorCode, 0);
        if (dialog == null) {
            Log.e("GooglePlayServicesErrorDialog is null.");
        } else {
            dialog.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMapDrawer = new MapDrawer(mMap);
        mMap.setOnCameraIdleListener(mOnCameraIdleListener);
        mMapReadyDelegate.evaluate();
        updateSubTitle();
        initMyLocationActivation();
    }

    private void initMyLocationActivation() {
        setMyLocationActivationViewVisibility(true);
        boolean isGranted = myLocationPermission.isGranted();
        boolean isDeclined = mPreferencesHelper.restoreMyLocationPermissionIsPermanentlyDeclined();
        boolean requestActivation = !isGranted && !isDeclined;
        setMyLocationActivationViewVisibility(requestActivation);
        setMyLocationViewVisibility(isGranted);
    }

    private void requestMyLocationActivation() {
        myLocationPermission.request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MyLocationPermission.ACCESS_LOCATION_REQUEST_CODE: {
                if (myLocationPermission.isGranted(grantResults)) {
                    setMyLocationActivationViewVisibility(false);
                    setMyLocationViewVisibility(true);
                } else {
                    if (myLocationPermission.canShowRationale()) {
                        setMyLocationActivationViewVisibility(true);
                    } else {
                        setMyLocationActivationViewVisibility(false);
                        mPreferencesHelper.storeMyLocationPermissionIsPermanentlyDeclined(true);
                    }
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setMyLocationActivationViewVisibility(boolean isVisible) {
        ViewExtensions.setVisible(mMyLocationActivationView, isVisible);
    }

    @SuppressLint("MissingPermission")
    private void setMyLocationViewVisibility(boolean isVisible) {
        mMap.setMyLocationEnabled(isVisible);
    }

    private void drawPolygonOverlay() {
        Activity activity = requireActivity();
        String cityName = mPreferencesHelper.restoreLastKnownLocationAsString();
        if (cityName == null || cityName.length() < 1) {
            return;
        }
        AdministrativeZone zone = ContentProvider.getAdministrativeZoneByName(activity, cityName);
        List<ChildZone> childZones = ChildZonesExtensions.getVisuallySorted(zone.childZones);
        mMapDrawer.drawPolygons(ChildZonesExtensions.toCircuitViewModels(childZones, activity));
    }

    private void showZoneNotDrawableDialog() {
        FragmentActivity activity = requireActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager
                .findFragmentByTag(ZoneNotDrawableDialog.FRAGMENT_TAG);
        if (fragment == null) {
            AdministrativeZone administrativeZone = AdministrativeZone.getRecentAdministrativeZone(activity);
            // TODO Future refactoring: drawPolygonOverlay() should already check if zone is drawable.
            if (!administrativeZone.containsGeometryInformation()) {
                ZoneNotDrawableDialog dialog = ZoneNotDrawableDialog
                        .newInstance(administrativeZone);
                dialog.show(fragmentManager, ZoneNotDrawableDialog.FRAGMENT_TAG);
            }
        }
    }

    private void storeLastMapState() {
        if (mMap != null) {
            mPreferencesHelper.storeCameraPosition(mMap.getCameraPosition());
        }
    }

    private void updateSubTitle() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        AdministrativeZone administrativeZone = AdministrativeZone.getRecentAdministrativeZone(activity);
        String title = administrativeZone.displayName;
        updateSubTitle(title);
    }

    private class OnCameraIdleListener implements GoogleMap.OnCameraIdleListener {

        @Override
        public void onCameraIdle() {
            storeLastMapState();
        }
    }
}
