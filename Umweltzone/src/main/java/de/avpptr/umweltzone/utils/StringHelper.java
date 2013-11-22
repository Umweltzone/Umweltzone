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

import android.content.Context;

import org.ligi.tracedroid.logging.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class StringHelper {

    public static String getZoneNumberSinceText(Context context, LowEmissionZone lowEmissionZone) {
        String zoneNumberColor = zoneNumberToColor(context, lowEmissionZone.zoneNumber);
        if (zoneNumberColor == null) {
            // Static zone information
            return context.getString(R.string.city_info_zone_number_none);
        }
        return getZoneNumberInfoString(context,
                R.string.city_info_zone_number_since,
                lowEmissionZone.zoneNumberSince,
                zoneNumberColor);
    }

    public static String getNextZoneNumberAsOfText(Context context, LowEmissionZone lowEmissionZone) {
        if (lowEmissionZone.nextZoneNumberAsOf == null) {
            return null;
        }
        int nextZoneNumber = lowEmissionZone.zoneNumber + 1;
        String zoneNumberColor = zoneNumberToColor(context, nextZoneNumber);
        if (zoneNumberColor == null) {
            Log.e("Next zone number '" + nextZoneNumber +
                    "' cannot be converted into color text fragment.");
            return null;
        }
        return getZoneNumberInfoString(context,
                R.string.city_info_next_zone_number_as_of,
                lowEmissionZone.nextZoneNumberAsOf,
                zoneNumberColor);
    }

    public static String getAbroadLicensedVehicleZoneNumberText(Context context, LowEmissionZone lowEmissionZone) {
        if (lowEmissionZone.abroadLicensedVehicleZoneNumberUntil == null) {
            return null;
        }
        String zoneNumberColor = zoneNumberToColor(context, lowEmissionZone.abroadLicensedVehicleZoneNumber);
        if (zoneNumberColor == null) {
            Log.e("Abroad licensed vehicle zone number '" +
                    lowEmissionZone.abroadLicensedVehicleZoneNumber +
                    "' cannot be converted into color text fragment.");
            return null;
        }
        return getZoneNumberInfoString(context,
                R.string.city_info_abroad_licensed_vehicle_zone_info,
                lowEmissionZone.abroadLicensedVehicleZoneNumberUntil,
                zoneNumberColor);
    }

    // Compile date and colors into sentence
    private static String getZoneNumberInfoString(Context context, int resourceId, Date date, String color) {
        String datePattern = context.getString(R.string.city_info_zone_number_since_date_format);
        // TODO Move locale into XML configuration
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.GERMAN);
        String formattedDate = dateFormat.format(date);
        return context.getString(resourceId, formattedDate, color);
    }

    private static String zoneNumberToColor(Context context, int zoneNumber) {
        if (zoneNumber == LowEmissionZoneNumbers.GREEN) {
            return context.getString(R.string.city_info_zone_number_since_text_fragment_green);
        }
        if (zoneNumber == LowEmissionZoneNumbers.YELLOW) {
            return context.getString(R.string.city_info_zone_number_since_text_fragment_yellow);
        }
        if (zoneNumber == LowEmissionZoneNumbers.RED) {
            return context.getString(R.string.city_info_zone_number_since_text_fragment_red);
        }
        return null;
    }

}
