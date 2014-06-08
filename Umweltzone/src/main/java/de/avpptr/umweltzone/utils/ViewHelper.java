/*
 *  Copyright (C) 2014  Tobias Preuss, Peter Vasil
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
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public abstract class ViewHelper {

    public static void setTextOrHideView(final TextView textView, final String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }

    public static void setupTextViewSimple(final Activity activity, int textViewId,
                                           final String text) {
        TextView textView = (TextView) activity.findViewById(textViewId);
        textView.setText(text);
    }

    public static void setupTextViewSimple(final View view, int textViewId,
                                           final String text) {
        TextView textView = (TextView) view.findViewById(textViewId);
        textView.setText(text);
    }

    public static void setupTextViewExtended(final View view,
                                             int textViewId,
                                             final String title,
                                             final String url,
                                             final TrackingPoint trackingPoint,
                                             final String trackingString) {
        TextView textView = (TextView) view.findViewById(textViewId);
        setupTextViewExtended(textView,
                StringHelper.spannedLinkForString(title, url),
                trackingPoint, trackingString);
    }

    public static void setupTextViewExtended(final Activity activity,
                                             int textViewId,
                                             int titleResourceId,
                                             int urlResourceId,
                                             final TrackingPoint trackingPoint,
                                             final String trackingString) {

        TextView textView = (TextView) activity.findViewById(textViewId);
        setupTextViewExtended(textView,
                StringHelper.spannedLinkForString(activity.getApplicationContext(), titleResourceId, urlResourceId),
                trackingPoint, trackingString);
    }

    private static void setupTextViewExtended(TextView textView, final Spanned text,
                                              final TrackingPoint trackingPoint,
                                              final String trackingString) {
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackingString == null) {
                    Umweltzone.getTracker().track(trackingPoint);
                } else {
                    Umweltzone.getTracker().track(trackingPoint, trackingString);
                }

            }
        });
    }

}
