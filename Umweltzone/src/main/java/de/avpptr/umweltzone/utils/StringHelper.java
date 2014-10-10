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

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import org.ligi.tracedroid.logging.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class StringHelper {

    public static String getListOfCitiesText(Context context, LowEmissionZone lowEmissionZone) {
        List<String> listOfCities = lowEmissionZone.listOfCities;
        if (listOfCities == null || listOfCities.isEmpty()) {
            return null;
        }
        int lastCityIndex = listOfCities.size() - 1;
        String citiesString = TextUtils.join(", ", listOfCities.subList(0, lastCityIndex));
        String lastCity = listOfCities.get(lastCityIndex);
        return context.getString(R.string.city_info_list_of_cities, citiesString, lastCity);
    }

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

    public static String getGeometryUpdatedAtText(Context context,
            LowEmissionZone lowEmissionZone) {
        Date geometryUpdatedAt = lowEmissionZone.geometryUpdatedAt;
        if (geometryUpdatedAt == null) {
            return "";
        }
        String formattedDate = getFormattedDate(context,
                R.string.city_info_geometry_updated_at_date_format, geometryUpdatedAt);
        return context.getString(R.string.city_info_geometry_updated_at_text, formattedDate);
    }

    public static String getGeometrySourceText(final Context context,
            LowEmissionZone lowEmissionZone) {
        String geometrySource = lowEmissionZone.geometrySource;
        if (TextUtils.isEmpty(geometrySource)) {
            return "";
        }
        return context.getString(R.string.city_info_geometry_source_text, geometrySource);
    }

    // Compile date and colors into sentence
    private static String getZoneNumberInfoString(Context context, int resourceId, Date date, String color) {
        String formattedDate = getFormattedDate(context,
                R.string.city_info_zone_number_since_date_format, date);
        return context.getString(resourceId, formattedDate, color);
    }

    private static String getFormattedDate(Context context, int datePatternResourceId, Date date) {
        String datePattern = context.getString(datePatternResourceId);
        // TODO Move locale into XML configuration
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        return dateFormat.format(date);
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

    public static Spanned spannedLinkForString(final Context context, int titleResourceId, int urlResourceId) {
        final String title = context.getString(titleResourceId);
        final String url = context.getString(urlResourceId);
        return Html.fromHtml(linkifiedString(title, url));
    }

    public static Spanned spannedLinkForString(final String title, final String url) {
        return Html.fromHtml(linkifiedString(title, url));
    }

    public static String linkifiedString(final String title, final String url) {
        if (url == null || TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("URL cannot be null nor empty.");
        }
        String titleText = title;
        if (title == null || TextUtils.isEmpty(title)) {
            titleText = url;
        }
        if (url.startsWith("http")) {
            return "<a href=\"" + url + "\">" + titleText + "</a>";
        } else {
            return "<a href=\"mailto:" + url + "\">" + titleText + "</a>";
        }
    }

}
