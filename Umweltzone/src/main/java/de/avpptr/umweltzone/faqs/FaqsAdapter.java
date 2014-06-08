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

package de.avpptr.umweltzone.faqs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.Tracking;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.utils.ViewHelper;

public class FaqsAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<Faq> mFaqs;
    protected final Tracking mTracking;

    public FaqsAdapter(Context context, List<Faq> faqs) {
        mContext = context;
        mFaqs = faqs;
        mTracking = Umweltzone.getTracker();
    }

    @Override
    public int getGroupCount() {
        return mFaqs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mFaqs.get(groupPosition).question;
    }

    @Override
    public Object getChild(final int groupPosition, int childPosition) {
        final String faqAnswer = mFaqs.get(groupPosition).answer;
        final String faqSourceUrl = mFaqs.get(groupPosition).sourceUrl;
        return new FaqAnswer() {{
            text = faqAnswer;
            sourceUrl = faqSourceUrl;
        }};
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View getNewView(int resourceId) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resourceId, null);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_group);
        }

        ViewHelper.setupTextViewSimple(convertView, R.id.faq_question, headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final FaqAnswer faqAnswer = (FaqAnswer) getChild(groupPosition, childPosition);

        final String itemDescription = getFaqDescription(groupPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_item);
            TextView sourceUrlTextView = (TextView) convertView.findViewById(R.id.faq_source_url);
            sourceUrlTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTracking.track(TrackingPoint.FaqSourceClick, itemDescription);
                }
            });
        }

        final String childText = faqAnswer.text;
        ViewHelper.setupTextViewSimple(convertView, R.id.faq_answer, childText);

        final String sourceUrlText = faqAnswer.sourceUrl;
        final String sourceUrlTitle = mContext.getString(R.string.faq_source_url_title_text);
        ViewHelper.setupTextViewExtended(convertView, R.id.faq_source_url, sourceUrlTitle, sourceUrlText,
                TrackingPoint.FaqSourceUrlClick, itemDescription);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class FaqAnswer {
        String text;
        String sourceUrl;
    }

    public String getFaqDescription(int position) {
        return mFaqs.get(position).toStringShort();
    }
}
