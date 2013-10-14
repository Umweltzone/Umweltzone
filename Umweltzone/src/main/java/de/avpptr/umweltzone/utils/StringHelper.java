package de.avpptr.umweltzone.utils;

import android.content.Context;

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
