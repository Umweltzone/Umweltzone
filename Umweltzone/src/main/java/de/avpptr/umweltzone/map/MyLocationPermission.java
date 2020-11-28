/*
 *  Copyright (C) 2020  Tobias Preuss
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

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;

class MyLocationPermission {

    static final int ACCESS_LOCATION_REQUEST_CODE = 1337;

    private final Fragment fragment;
    private final static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final String[] permissions = {ACCESS_COARSE_LOCATION};

    MyLocationPermission(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    boolean isGranted() {
        return checkSelfPermission(fragment.requireActivity(), ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED;
    }

    void request() {
        fragment.requestPermissions(permissions, ACCESS_LOCATION_REQUEST_CODE);
    }

    boolean canShowRationale() {
        return fragment.shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION);
    }

    boolean isGranted(@NonNull int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED;
    }

}
