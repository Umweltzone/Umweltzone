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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.models.LowEmissionZone;

public abstract class ContentProvider {

    public static List<Faq> getFaqs(Context context) {
        return getContent(context, R.raw.faqs_de, Faq.class);
    }

    public static List<LowEmissionZone> getLowEmissionZones(Context context) {
        return getContent(context, R.raw.zones_de, LowEmissionZone.class);
    }

    public static List<GeoPoint> getCircuitPoints(Context context, int resourceId) {
        return getContent(context, resourceId, GeoPoint.class);
    }

    private static <T> List<T> getContent(Context context, int rawResourceId, Class<T> contentType) {
        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        ObjectMapper objectMapper = new ObjectMapper();
        String datePattern = context.getString(R.string.config_zone_number_since_date_format);
        objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, contentType);
            return objectMapper.readValue(inputStream, collectionType);
        } catch (IOException e) {
            // TODO Aware that app will crash when JSON is mis-structured.
            e.printStackTrace();
        }
        return null;
    }

}
