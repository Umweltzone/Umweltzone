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
