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
import de.avpptr.umweltzone.contract.CityChangeListener;
import de.avpptr.umweltzone.utils.BoundingBox;
import de.avpptr.umweltzone.utils.Converter;
import de.avpptr.umweltzone.utils.GeoPoint;
import de.avpptr.umweltzone.utils.MapDrawer;
import de.avpptr.umweltzone.utils.PointsProvider;
import de.avpptr.umweltzone.utils.PreferencesHelper;

public class MapFragment extends BaseFragment {

    private GoogleMap mMap;
    private MapDrawer mMapDrawer;
    private final GoogleMap.OnCameraChangeListener mOnCameraChangeListener;

    private PointsProvider.Location mCurrentLocation = PointsProvider.Location.Berlin;

    public MapFragment() {
        this.mOnCameraChangeListener = new OnCameraChangeListener();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_map;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        String cityName = extras.getString(BundleKeys.CITY_CHANGE);
        if (cityName != null) {
            mCurrentLocation = Converter.cityNameToLocation(cityName);
            CityChangeListener cityChangeListener = (CityChangeListener) activity;
            cityChangeListener.cityChanged();

            BoundingBox boundingBox = Converter.cityNameToBoundingBox(activity.getResources(), cityName);
            if (boundingBox != null) {
                zoomToBounds(boundingBox.toLatLngBounds());
            }
        }
    }

    private void zoomToBounds(LatLngBounds latLngBounds) {
        if (mMap == null) {
            throw new IllegalStateException("Map is null");
        } else {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            CameraUpdate zoneBounds = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, 50);
            mMap.moveCamera(zoneBounds);
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
                    onMapIsSetUp();
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

    protected void onMapIsSetUp() {
        mMapDrawer = new MapDrawer(mMap);
        mMap.setOnCameraChangeListener(mOnCameraChangeListener);
        mMap.setMyLocationEnabled(true);
    }


    private void drawPolygonOverlay() {
        Iterable<LatLng> points = PointsProvider.getPoints(mCurrentLocation);
        Resources resources = getResources();
        int fillColor = resources.getColor(R.color.shape_fill_color);
        int strokeColor = resources.getColor(R.color.shape_stroke_color);
        int strokeWidth = resources.getInteger(R.integer.shape_stroke_width);
        mMapDrawer.drawPolygon(points, fillColor, strokeColor, strokeWidth);
    }

    private void storeLastKnownLocation() {
        if (mMap != null) {
            GeoPoint mapCenter = new GeoPoint(mMap.getCameraPosition().target);
            PreferencesHelper.storeLastKnownLocation(getActivity(), mapCenter);
        }
    }

    class OnCameraChangeListener implements GoogleMap.OnCameraChangeListener {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            drawPolygonOverlay();
            storeLastKnownLocation();
        }
    }
}
