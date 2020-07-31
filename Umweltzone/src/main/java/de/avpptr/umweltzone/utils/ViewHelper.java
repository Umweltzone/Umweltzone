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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public abstract class ViewHelper {

    public static void setupTextViewSimple(
            final Activity activity,
            int textViewId,
            final String text) {
        TextView textView = activity.findViewById(textViewId);
        textView.setText(text);
    }

    public static void setupTextViewSimple(
            final View view,
            int textViewId,
            final String text) {
        TextView textView = view.findViewById(textViewId);
        textView.setText(text);
    }

    public static void setupTextViewExtended(
            final View view,
            int textViewId,
            final String title,
            final String url,
            final TrackingPoint trackingPoint,
            final String trackingString) {
        TextView textView = view.findViewById(textViewId);
        setupTextViewExtended(view.getContext(), textView,
                StringHelper.spannedLinkForString(title, url),
                url,
                trackingPoint, trackingString);
    }

    public static void setupTextViewExtended(
            final Activity activity,
            final TextView textView,
            int titleResourceId,
            final String url,
            final TrackingPoint trackingPoint,
            final String trackingString) {

        String title = activity.getString(titleResourceId);
        setupTextViewExtended(activity, textView,
                StringHelper.spannedLinkForString(title, url),
                url,
                trackingPoint, trackingString);
    }

    public static void setupTextViewExtended(
            final Activity activity,
            int textViewId,
            int titleResourceId,
            int urlResourceId,
            final TrackingPoint trackingPoint,
            final String trackingString) {

        TextView textView = activity.findViewById(textViewId);
        String url = activity.getString(urlResourceId);
        if (LinkMovementMethodCompat.shouldLegacyLinkMovementMethod()) {
            url = url.contains("@") ? "mailto:" + url : url;
        }
        final String title = activity.getString(titleResourceId);
        setupTextViewExtended(activity, textView,
                StringHelper.spannedLinkForString(title, url),
                url,
                trackingPoint, trackingString);
    }

    private static void setupTextViewExtended(
            final Context context,
            TextView textView,
            final Spanned text,
            final String url,
            final TrackingPoint trackingPoint,
            final String trackingString) {
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                    Umweltzone.getTracker().track(trackingPoint, trackingString);
                } else {
                    // TODO Present error to the user
                    Log.e(getClass().getName(),
                            "Activity for intent cannot be resolved. URL = " + url);
                }
            }
        });
    }

}
