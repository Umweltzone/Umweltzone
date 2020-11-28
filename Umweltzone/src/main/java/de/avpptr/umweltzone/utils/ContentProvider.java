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

package de.avpptr.umweltzone.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LruCache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.ligi.tracedroid.logging.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.models.Circuit;
import de.avpptr.umweltzone.models.CircuitDeserializer;
import de.avpptr.umweltzone.models.Faq;
import kotlin.collections.CollectionsKt;

public abstract class ContentProvider {


    private static final LruCache<String, List<Circuit>> CIRCUITS_CACHE
            = new LruCache<>(6);

    private static final LruCache<String, Integer> RESOURCE_ID_CACHE
            = new LruCache<>(6);

    public static void enforceContentUpdate() {
        // Clear caches
        CIRCUITS_CACHE.evictAll();
        RESOURCE_ID_CACHE.evictAll();
    }

    @NonNull
    public static List<Faq> getFaqs(@NonNull final Context context) {
        return getContent(context, "faqs", Faq.class);
    }

    @NonNull
    public static AdministrativeZone getAdministrativeZoneByName(@NonNull final Context context, @NonNull final String name) {
        return CollectionsKt.single(getAdministrativeZones(context), zone -> name.equalsIgnoreCase(zone.name));
    }

    @NonNull
    public static List<AdministrativeZone> getAdministrativeZones(@NonNull final Context context) {
        List<AdministrativeZone> zones = getContent(context, "zones_de", AdministrativeZone.class);
        if (zones.isEmpty()) {
            throw new IllegalStateException("Parsing zones from JSON failed.");
        }
        return zones;
    }

    @NonNull
    public static List<Circuit> getCircuits(@NonNull final Context context, @NonNull final String zoneFileName) {
        List<Circuit> circuits = CIRCUITS_CACHE.get(zoneFileName);
        if (circuits == null) {
            circuits = getContent(context, zoneFileName, Circuit.class);
            if (!circuits.isEmpty()) {
                CIRCUITS_CACHE.put(zoneFileName, circuits);
            }
        }
        return circuits;
    }

    @NonNull
    private static <T> List<T> getContent(
            final Context context,
            final String fileName,
            Class<T> contentType) {
        return getContent(context, fileName, "raw", contentType);
    }

    @NonNull
    private static <T> List<T> getContent(
            final Context context,
            final String fileName,
            final String folderName,
            Class<T> contentType) {
        int rawResourceId = getResourceId(context, fileName, folderName);

        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Circuit.class, new CircuitDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        String datePattern = context.getString(R.string.config_zone_data_date_pattern);
        objectMapper.setDateFormat(DateFormats.getDateFormat(datePattern));
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(
                    List.class, contentType);
            return objectMapper.readValue(inputStream, collectionType);
        } catch (IOException e) {
            // TODO Aware that app will crash when JSON is mis-structured.
            e.printStackTrace();
        }
        Log.e(ContentProvider.class.getName(), "Failure parsing zone data for: " + fileName);
        return Collections.emptyList();
    }

    @VisibleForTesting
    @RawRes
    static Integer getResourceId(Context context, String fileName, String folderName) {
        String resourceKey = getFilePath(folderName, fileName);
        Integer rawResourceId = RESOURCE_ID_CACHE.get(resourceKey);
        if (rawResourceId == null) {
            // Invoke cache
            rawResourceId = getRawResourceId(context, fileName, folderName);
            RESOURCE_ID_CACHE.put(resourceKey, rawResourceId);
        }
        return rawResourceId;
    }

    @RawRes
    private static int getRawResourceId(Context context, String fileName, String folderName) {
        final Resources resources = context.getResources();
        // Look-up identifier using reflection (expensive)
        int rawResourceId = resources.getIdentifier(fileName, folderName, context.getPackageName());
        if (rawResourceId == de.avpptr.umweltzone.contract.Resources.INVALID_RESOURCE_ID) {
            String filePath = getFilePath(folderName, fileName);
            throw new IllegalStateException(
                    "Resource for file path '" + filePath + "' not found.");
        }
        return rawResourceId;
    }

    private static String getFilePath(final String folderName, final String fileName) {
        return folderName + "/" + fileName;
    }

}
