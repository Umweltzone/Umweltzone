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

public enum TrackingPoint {
    ActivityStart("activity_start"),
    ActivityStop("activity_stop"),
    CityListItemClick("city_list"),
    CityInfoShowOnMapClick("city_info_show_on_map_click"),
    CityInfoFurtherInfoClick("city_info_further_info_click"),
    CityInfoBadgeOnlineClick("city_info_badge_online_click"),
    FaqItemClick("faq_item_click"),
    FaqSourceUrlClick("faq_source_url_click"),
    AboutItemClick("about_item_click"),
    SupportMailClick("support_mail_click"),
    UserVoiceClick("user_voice_click"),
    RatingClick("rating_click"),
    ZoneNotDrawableLaterClick("zone_not_drawable_later"),
    ZoneNotDrawableOpenEmailClick("zone_not_drawable_open_email"),
    ShareAppClick("share_app_click"),
    RequestPermission("request_permission"),
    PermissionResult("permission_result"),
    SettingsItemClick("settings_item_click"),

    // Errors
    ResourceNotFoundError("resource_not_found"),
    GooglePlayServicesNotAvailableError("google_play_services_not_available"),
    MapIsNullError("map_is_null"),
    ParsingZonesFromJSONFailedError("parsing_zones_from_json_failed"),
    CityRowCouldNotBeInflatedError("city_row_could_not_be_inflated");

    private final String mMessage;

    TrackingPoint(final String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return mMessage;
    }

}
