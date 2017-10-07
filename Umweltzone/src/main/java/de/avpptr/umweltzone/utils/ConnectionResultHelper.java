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

package de.avpptr.umweltzone.utils;

import com.google.android.gms.common.ConnectionResult;

public class ConnectionResultHelper {

    public static String connectionResultToString(int connectionResult) {
        switch (connectionResult) {
            case ConnectionResult.SUCCESS: // 0
                return "SUCCESS";
            case ConnectionResult.SERVICE_MISSING: // 1
                return "SERVICE_MISSING";
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: // 2
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case ConnectionResult.SERVICE_DISABLED: // 3
                return "SERVICE_DISABLED";
            case ConnectionResult.SIGN_IN_REQUIRED: // 4
                return "SIGN_IN_REQUIRED";
            case ConnectionResult.INVALID_ACCOUNT: // 5
                return "INVALID_ACCOUNT";
            case ConnectionResult.RESOLUTION_REQUIRED: // 6
                return "RESOLUTION_REQUIRED";
            case ConnectionResult.NETWORK_ERROR: // 7
                return "NETWORK_ERROR";
            case ConnectionResult.INTERNAL_ERROR: // 8
                return "INTERNAL_ERROR";
            case ConnectionResult.SERVICE_INVALID: // 9
                return "SERVICE_INVALID";
            case ConnectionResult.DEVELOPER_ERROR: // 10
                return "DEVELOPER_ERROR";
            case ConnectionResult.LICENSE_CHECK_FAILED: // 11
                return "LICENSE_CHECK_FAILED";
            case 12: // Referenced as DATE_INVALID in API documentation.
                return "DATE_INVALID";
            case ConnectionResult.CANCELED: // 13
                return "CANCELED";
            case ConnectionResult.TIMEOUT: // 14
                return "TIMEOUT";
            case ConnectionResult.INTERRUPTED: // 15
                return "INTERRUPTED";
            case ConnectionResult.API_UNAVAILABLE: // 16
                return "API_UNAVAILABLE";
            case ConnectionResult.SIGN_IN_FAILED: // 17
                return "SIGN_IN_FAILED";
            case ConnectionResult.SERVICE_UPDATING: // 18
                return "SERVICE_UPDATING";
            case ConnectionResult.SERVICE_MISSING_PERMISSION: // 19
                return "SERVICE_MISSING_PERMISSION";
            case ConnectionResult.RESTRICTED_PROFILE: // 20
                return "RESTRICTED_PROFILE";
        }
        return "UNDOCUMENTED_CONNECTION_RESULT: " + connectionResult;
    }

}
