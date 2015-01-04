/*
 *  Copyright (C) 2015  Tobias Preuss, Peter Vasil
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

package de.avpptr.umweltzone.cities;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.contract.LowEmissionZoneNumbers;
import de.avpptr.umweltzone.models.LowEmissionZone;

public class CityListAdapter extends ArrayAdapter<LowEmissionZone> {

    protected final Context mContext;
    protected final int mResourceId;
    protected final LowEmissionZone[] mLowEmissionZones;
    protected final Tracking mTracking;

    public CityListAdapter(Context context, int resource, LowEmissionZone[] lowEmissionZones) {
        super(context, resource, lowEmissionZones);
        mContext = context;
        mResourceId = resource;
        mLowEmissionZones = lowEmissionZones;
        mTracking = Umweltzone.getTracker();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mResourceId, null);
            if (view != null) {
                viewHolder = new ViewHolder();
                viewHolder.zoneName = (TextView) view.findViewById(R.id.city_row_name);
                viewHolder.zoneBadge = (TextView) view.findViewById(R.id.city_row_badge);
                LayerDrawable badgeBackground = (LayerDrawable) viewHolder.zoneBadge.getBackground();
                if (badgeBackground != null) {
                    viewHolder.zoneShape =
                            (GradientDrawable) badgeBackground.findDrawableByLayerId(R.id.zone_shape);
                }
                view.setTag(viewHolder);
            } else {
                mTracking.trackError(TrackingPoint.CityRowCouldNotBeInflatedError);
                throw new IllegalStateException("City row could be inflated.");
            }
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LowEmissionZone lowEmissionZone = mLowEmissionZones[position];
        viewHolder.zoneName.setText(lowEmissionZone.displayName);
        Resources resources = mContext.getResources();
        int textColor = resources.getColor(
                zoneIsDrawableToColorId(lowEmissionZone.containsGeometryInformation()));
        viewHolder.zoneName.setTextColor(textColor);
        int zoneNumber = lowEmissionZone.zoneNumber;
        viewHolder.zoneBadge.setText(String.valueOf(zoneNumber));
        int badgeColor = resources.getColor(zoneNumberToColorId(zoneNumber));

        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            // Replaces the round badge with a colored square.
            ColorDrawable colorDrawable = new ColorDrawable(badgeColor);
            //noinspection deprecation
            viewHolder.zoneBadge.setBackgroundDrawable(colorDrawable);
        } else {
            viewHolder.zoneShape.setColor(badgeColor);
        }
        return view;
    }

    private int zoneIsDrawableToColorId(boolean zoneIsDrawable) {
        return (zoneIsDrawable) ?
                R.color.city_zone_name_drawable :
                R.color.city_zone_name_not_drawable;
    }

    private int zoneNumberToColorId(int zoneNumber) {
        switch (zoneNumber) {
            case LowEmissionZoneNumbers.RED:
                return R.color.city_zone_2;
            case LowEmissionZoneNumbers.YELLOW:
                return R.color.city_zone_3;
            case LowEmissionZoneNumbers.GREEN:
                return R.color.city_zone_4;
            default:
                return R.color.city_zone_none;
        }
    }

    private static class ViewHolder {
        TextView zoneName;
        TextView zoneBadge;
        GradientDrawable zoneShape;
    }

}
