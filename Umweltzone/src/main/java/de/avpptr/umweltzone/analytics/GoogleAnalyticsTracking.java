/*
 *  Copyright (C) 2018  Tobias Preuss
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

package de.avpptr.umweltzone.analytics;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.ExceptionParser;
import com.google.analytics.tracking.android.ExceptionReporter;
import com.google.analytics.tracking.android.MapBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GoogleAnalyticsTracking implements Tracking {

    private final EasyTracker mTracker;

    public GoogleAnalyticsTracking(Context context) {
        mTracker = EasyTracker.getInstance(context);

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                Thread.getDefaultUncaughtExceptionHandler();
        if (uncaughtExceptionHandler instanceof ExceptionReporter) {
            ExceptionReporter exceptionReporter = (ExceptionReporter) uncaughtExceptionHandler;
            exceptionReporter.setExceptionParser(new AnalyticsExceptionParser());
        }
    }

    @Override
    public void track(TrackingPoint eventName, final Object parameter) {
        switch (eventName) {
            case ActivityStart:
                trackActivityStart((Activity) parameter);
                break;
            case ActivityStop:
                trackActivityStop((Activity) parameter);
                break;
            case FaqItemClick:
                trackEvent("faq_list_action", "list_item_click", "faq_" + parameter);
                break;
            case FaqSourceUrlClick:
                trackEvent("faq_list_action", "url_click", "faq_source_url_" + parameter);
                break;
            case CityListItemClick:
                trackEvent("city_list_action", "list_item_click", "city_list_" + parameter);
                break;
            case CityInfoShowOnMapClick:
                trackEvent("city_info_action", "button_push", "show_on_map_" + parameter);
                break;
            case CityInfoFurtherInfoClick:
                trackEvent("city_info_action", "url_click", "further_info_" + parameter);
                break;
            case CityInfoBadgeOnlineClick:
                trackEvent("city_info_action", "url_click", "badge_online_" + parameter);
                break;
            case AboutItemClick:
                trackEvent("about_action", "url_click", "about_item_" + parameter);
                break;
            case SupportMailClick:
                trackEvent("about_action", "url_click", "support_mail");
                break;
            case UserVoiceClick:
                trackEvent("about_action", "url_click", "user_voice");
                break;
            case RatingClick:
                trackEvent("about_action", "url_click", "play_store_rating");
                break;
            case ZoneNotDrawableOpenEmailClick:
                trackEvent("zone_not_drawable", "button_push", "open_email_now_" + parameter);
                break;
            case ZoneNotDrawableLaterClick:
                trackEvent("zone_not_drawable", "button_push", "open_email_later_" + parameter);
                break;
            case ShareAppClick:
                trackEvent("menu", "menu_item_click", "share_app_" + parameter);
                break;
            case RequestPermission:
                trackEvent("permission", "request", "" + parameter);
                break;
            case PermissionResult:
                trackEvent("permission", "result", "" + parameter);
                break;
        }
    }

    @Override
    public void trackError(TrackingPoint eventName, Object parameter) {
        boolean errorIsFatal = false;
        String eventDescription = eventName.toString();
        switch (eventName) {
            case MapIsNullError:
            case ParsingZonesFromJSONFailedError:
            case CityRowCouldNotBeInflatedError:
                errorIsFatal = true;
                eventDescription += ", " + parameter;
                break;
            case ResourceNotFoundError:
                errorIsFatal = true;
                eventDescription += ", " + parameter;
                break;
            case GooglePlayServicesNotAvailableError:
                eventDescription += ", " + parameter;
                break;
        }
        trackException(eventDescription, errorIsFatal);
    }

    private void trackActivityStart(Activity activity) {
        mTracker.activityStart(activity);
    }

    private void trackActivityStop(Activity activity) {
        mTracker.activityStop(activity);
    }

    private void trackEvent(String category, String action, String label) {
        mTracker.send(MapBuilder.createEvent(
                category, action, label, null).build());
    }

    private void trackException(String exceptionDescription, boolean fatal) {
        mTracker.send(MapBuilder.createException(exceptionDescription, fatal).build());
    }

    private class AnalyticsExceptionParser implements ExceptionParser {

        @Override
        public String getDescription(String threadName, Throwable throwable) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            return "Thread: " + threadName + ", Exception: " + stringWriter.getBuffer().toString();
        }
    }
}
