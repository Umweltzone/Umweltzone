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

package de.avpptr.umweltzone.analytics;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.Map;

public class GoogleAnalyticsTracking implements Tracking {

    private EasyTracker mTracker;

    public GoogleAnalyticsTracking(Context context) {
        mTracker = EasyTracker.getInstance(context);
    }

    @Override public void track(TrackingPoint eventName) {
        track(eventName, null);
    }

    @Override public void track(TrackingPoint eventName, Map parameters) {
        switch (eventName) {
            case ActivityStart:
                trackActivityStart((Activity) parameters.get("activity"));
                break;
            case ActivityStop:
                trackActivityStop((Activity) parameters.get("activity"));
                break;
            case FaqItemClick:
                trackEvent("faq_list_action", "group_item_click", (String) parameters.get("position"));
                break;
            case FaqSourceClick:
                trackEvent("faq_list_action", "item_source_button_push",
                        (String) parameters.get("position"));
                break;
            case CityListItemClick:
                trackEvent("city_list_action", "item_click", (String) parameters.get("zone_name"));
                break;
            case CityInfoShowOnMapClick:
                trackEvent("city_info_action", "button_click", "show_on_map");
                break;
            case CityInfoFurtherInfoClick:
                trackEvent("city_info_action", "button_click", "further_info");
                break;
            case AboutItemClick:
                trackEvent("about_action", "item_click", (String) parameters.get("about_item"));
                break;
            case SupportMailClick:
                trackEvent("about_action", "item_click", "support_mail");
                break;
            case UserVoiceClick:
                trackEvent("about_action", "item_click", "uservoice");
                break;
            case AboutLibraryClick:
                trackEvent("about_action", "item_click", (String) parameters.get("about_library"));
                break;
        }
    }

    @Override public void trackError(TrackingPoint eventName) {
        trackError(eventName, null);
    }

    @Override public void trackError(TrackingPoint eventName, Map parameters) {
        boolean errorIsFatal = false;
        String eventDescription = eventName.toString();
        switch (eventName) {
            case MapIsNullError:
            case ParsingZonesFromJSONFailedError:
            case CityRowCouldNotBeInflatedError:
                errorIsFatal = true;
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

    private void trackView(String screenName) {
        mTracker.send(
                MapBuilder.createAppView().set(Fields.SCREEN_NAME, screenName).build()
        );
    }

    private void trackEvent(String category, String action, String label) {
        mTracker.send(MapBuilder.createEvent(
                category, action, label, null).build());
    }

    private void trackException(String exceptionDescription, boolean fatal) {
        mTracker.send(MapBuilder.createException(exceptionDescription, fatal).build());
    }

}
