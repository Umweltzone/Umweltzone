/*
 *  Copyright (C) 2021  Tobias Preuss
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
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ShareCompat;

import org.parceler.Parcels;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.about.AboutActivity;
import de.avpptr.umweltzone.contract.BundleKeys;
import de.avpptr.umweltzone.details.DetailsActivity;
import de.avpptr.umweltzone.details.DetailsFragment;
import de.avpptr.umweltzone.faqs.FaqActivity;
import de.avpptr.umweltzone.feedback.FeedbackActivity;
import de.avpptr.umweltzone.map.MainActivity;
import de.avpptr.umweltzone.models.AdministrativeZone;
import de.avpptr.umweltzone.settings.SettingsActivity;
import de.avpptr.umweltzone.zones.ZonesActivity;

public class IntentHelper {

    public static Intent getSendEmailIntent(
            @NonNull Activity activity,
            @NonNull String[] toRecipients,
            @Nullable String[] bccRecipient,
            @NonNull String subject,
            @NonNull String message) {
        ShareCompat.IntentBuilder builder = new ShareCompat.IntentBuilder(activity);
        builder.setEmailTo(toRecipients);
        if (bccRecipient != null) {
            builder.setEmailBcc(bccRecipient);
        }
        builder.setSubject(subject);
        builder.setType("message/rfc822");
        builder.setHtmlText(message);
        return builder.getIntent();
    }

    public static Intent getShareIntent(@NonNull Activity activity) {
        String message = activity.getString(R.string.share_intent_message);
        return new ShareCompat.IntentBuilder(activity)
                .setType("text/plain")
                .setText(message)
                .getIntent();
    }

    public static Intent getNewMapIntent(@NonNull Context context) {
        final Intent intent = getIntent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent getHomeIntent(@NonNull Context context) {
        final Intent intent = getIntent(context, MainActivity.class);
        intent.putExtra(BundleKeys.HOME, BundleKeys.HOME);
        return intent;
    }

    public static Intent getDetailsIntent(@NonNull Context context,
                                          @NonNull AdministrativeZone administrativeZone) {
        Intent intent = getIntent(context, DetailsActivity.class);
        intent.putExtra(DetailsFragment.BUNDLE_KEY_ADMINISTRATIVE_ZONE,
                Parcels.wrap(administrativeZone));
        return intent;
    }

    public static Intent getFeedbackIntent(@NonNull Context context) {
        return getIntent(context, FeedbackActivity.class);
    }

    public static Intent getSettingsIntent(@NonNull Context context) {
        return getIntent(context, SettingsActivity.class);
    }

    public static Intent getAboutIntent(@NonNull Context context) {
        return getIntent(context, AboutActivity.class);
    }

    public static Intent getCitiesIntent(@NonNull Context context) {
        return getIntent(context, ZonesActivity.class);
    }

    public static Intent getFaqsIntent(@NonNull Context context) {
        return getIntent(context, FaqActivity.class);
    }

    @VisibleForTesting
    public static Intent getIntent(Context context, Class<?> clazz) {
        final Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

}
